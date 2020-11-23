package com.example.smartvest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class ManagerMainActivity extends AppCompatActivity {
    ConstraintLayout location_mgr;
    ConstraintLayout safety_mgr;
    TextView logout_mgr;
    TextView worker_count;
    BroadcastReceiver manager_receiver = null;
    ArrayList<Worker> workers = new ArrayList<Worker>();
    int director_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);
        location_mgr = findViewById(R.id.location_mgr);
        safety_mgr = findViewById(R.id.safety_mgr);
        logout_mgr = findViewById(R.id.logout_mgr);
        director_id = Integer.parseInt(getIntent().getStringExtra("director_id"));
        worker_count = findViewById(R.id.worker_count);

        location_mgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagerMapActivity.class);
                startActivity(intent);
            }
        });
        safety_mgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagerSafetyActivity.class);
                startActivity(intent);
            }
        });
        logout_mgr.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        logout_mgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), SafetyService.class);
                stopService(intent1);
                PreferenceManager.setString(getApplicationContext(), "login_manager", "false");
                PreferenceManager.setString(getApplicationContext(), "logout_click", "true");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        Intent _intent = new Intent(getApplicationContext(), ManagerService.class);
        _intent.setAction("director_id");
        _intent.putExtra("director_id", String.valueOf(director_id));
        startService(_intent);
        PreferenceManager.setString(getApplicationContext(), "login_manager", "true");
    }

    @Override
    protected void onResume() {
        super.onResume();
        register_receiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregister_receiver();
    }

    public void register_receiver() {
        if (manager_receiver != null)
            return;
        IntentFilter filter = new IntentFilter();
        filter.addAction("manager_safety");

        this.manager_receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("manager_safety")) {
                    if ((ArrayList<Worker>) intent.getSerializableExtra("workers") != null) {
                        workers = (ArrayList<Worker>) intent.getSerializableExtra("workers");
                        String count = Integer.toString(workers.size());
                        worker_count.setText(count);
                    }
                }
            }
        };

        this.registerReceiver(this.manager_receiver, filter);
    }

    public void unregister_receiver() {
        if (manager_receiver != null) {
            this.unregisterReceiver(manager_receiver);
            manager_receiver = null;
        }
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                | Intent.FLAG_ACTIVITY_FORWARD_RESULT
                | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
    }
}
