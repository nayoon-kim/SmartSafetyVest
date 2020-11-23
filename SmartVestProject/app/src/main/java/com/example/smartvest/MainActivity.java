package com.example.smartvest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button login_worker;
    Button login_manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (PreferenceManager.getString(getApplicationContext(), "login_worker").equals("true")) {
            Intent intent = new Intent(getApplicationContext(), WokerMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (PreferenceManager.getString(getApplicationContext(), "login_manager").equals("true")) {
            Intent intent = new Intent(getApplicationContext(), ManagerMainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        else if (!PreferenceManager.getString(getApplicationContext(), "logout_click").equals("true")){
            Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
            startActivity(intent);
        }
        PreferenceManager.setString(getApplicationContext(), "logout_click", "false");

        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView2);
        login_worker = findViewById(R.id.login_worker);
        login_manager = findViewById(R.id.login_manager);
        String text = textView.getText().toString();
        changeText(text);
        login_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), WorkerLoginActivity.class);
                startActivity(intent);
            }
        });
        login_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ManagerLoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void changeText(String text) {
        SpannableString spannableString = new SpannableString(text);
        int start_1 = text.indexOf("S");
        int end_1 = start_1 + 1;
        int start_2 = text.indexOf("V");
        int end_2 = start_2 + 1;

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#78bef0")), start_1, end_1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.1f), start_1, end_1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#78bef0")), start_2, end_2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.1f), start_2, end_2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
