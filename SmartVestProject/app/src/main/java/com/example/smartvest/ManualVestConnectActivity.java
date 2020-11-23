package com.example.smartvest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ManualVestConnectActivity extends AppCompatActivity {
    ImageView back_manual_vest_connect;
    ImageView home_manual_vest_connect;
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_vest_connect);
        back_manual_vest_connect = findViewById(R.id.back_manual_vest_connect);
        home_manual_vest_connect = findViewById(R.id.home_manual_vest_connect);
        webView = findViewById(R.id.web_connect);
        home_manual_vest_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WokerMainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        back_manual_vest_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String url = "file:///android_asset/manual_vest_connect.html";
        webView.loadUrl(url);
    }
}
