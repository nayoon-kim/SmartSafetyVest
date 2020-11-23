package com.example.smartvest;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
        String text = textView.getText().toString();
        changeText(text);
        loading();
    }

    private  void loading() {
        alpha();
        Handler handler = new Handler();
        handler.postDelayed( new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }

    private void alpha() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1500);
        textView.startAnimation(alphaAnimation);
        imageView.startAnimation(alphaAnimation);
    }

    private void changeText(String text) {
        SpannableString spannableString = new SpannableString(text);
        int start_1 = text.indexOf("S");
        int end_1 = start_1 + 1;
        int start_2 = text.indexOf("V");
        int end_2 = start_2 + 1;

        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#78bef0")), start_1, end_1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.2f), start_1, end_1, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#78bef0")), start_2, end_2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new RelativeSizeSpan(1.2f), start_2, end_2, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
    }
}
