package com.example.smartvest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class ManualSmartVestActivity extends AppCompatActivity {
    ImageView back_manual_smartvest;
    ConstraintLayout manual_vest_connect;
    ImageView home_manual_smartvest;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_smartvest);
        back_manual_smartvest = findViewById(R.id.back_manual_smartvest);
        manual_vest_connect = findViewById(R.id.manual_vest_connect);
        home_manual_smartvest = findViewById(R.id.home_manual_smartvest);
        home_manual_smartvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WokerMainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        back_manual_smartvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        manual_vest_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ManualVestConnectActivity.class);
                startActivity(intent);
            }
        });
    }
}
