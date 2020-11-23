package com.example.smartvest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ManualGeneralActivity extends AppCompatActivity {
    ImageView back_manual_general;
    ImageView home_manual_general;
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_general);
        back_manual_general = findViewById(R.id.back_manual_general);
        webView = findViewById(R.id.web);
        home_manual_general = findViewById(R.id.home_manual_general);
        home_manual_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WokerMainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        back_manual_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        String url = "file:///android_asset/manual_general.html";
        webView.loadUrl(url);
        /*
        String text = new MyTextReader().readTextFile(this, R.raw.manual_general);
        textview_manual_general.setText(Html.fromHtml(text));
        */
    }
}
