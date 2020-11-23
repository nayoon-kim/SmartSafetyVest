package com.example.smartvest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ManagerSafetyActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ManagerAdapter adapter;
    ImageView back_safety_mgr;
    BroadcastReceiver safety_receiver = null;
    ArrayList<Worker> workers = new ArrayList<Worker>();
    public static Comparator<Worker> comparator = new Comparator<Worker>() {
        @Override
        public int compare(Worker w1, Worker w2) {
            int com = 0;
            if (w1.report_signal < w2.report_signal)
                com = 1;
            else if (w1.report_signal == w2.report_signal) {
                if (w1.warning_signal < w2.warning_signal)
                    com = 1;
                else if (w1.warning_signal == w2.warning_signal)
                    com = 0;
                else
                    com = -1;
            }
            else
                com = -1;
            return com;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_manager);

        back_safety_mgr = findViewById(R.id.back_safety_mgr);
        back_safety_mgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.recyclerView_manager);
        adapter = new ManagerAdapter(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        register_receiver();
    }

    public void register_receiver() {
        if (safety_receiver != null)
            return;
        IntentFilter filter = new IntentFilter();
        filter.addAction("manager_safety");

        this.safety_receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("manager_safety")) {
                    if ((ArrayList<Worker>) intent.getSerializableExtra("workers") != null) {
                        workers = (ArrayList<Worker>) intent.getSerializableExtra("workers");
                        Collections.sort(workers, comparator);
                        adapter.setItems(workers);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        };

        this.registerReceiver(this.safety_receiver, filter);
    }

    public void unregister_receiver() {
        if (safety_receiver != null) {
            this.unregisterReceiver(safety_receiver);
            safety_receiver = null;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister_receiver();
    }
}
