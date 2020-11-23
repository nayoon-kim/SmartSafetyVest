package com.example.smartvest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.dinuscxj.progressbar.CircleProgressBar;


public class WorkerSafetyActivity extends AppCompatActivity {
    ImageView back_safety_worker;
    ConstraintLayout safety_methane;
    TextView text_safety_methane;
    TextView text_safety_methane_per;
    ImageView safety_methane_info;
    ConstraintLayout safety_lpg;
    TextView text_safety_lpg;
    TextView text_safety_lpg_per;
    ImageView safety_lpg_info;
    ConstraintLayout safety_carbon;
    TextView text_safety_carbon;
    TextView text_safety_carbon_per;
    ImageView safety_carbon_info;
    ConstraintLayout safety_temp;
    TextView text_safety_temp;
    TextView text_safety_temp_per;
    ImageView safety_temp_info;
    ImageView home_safety_worker;

    BroadcastReceiver safety_receiver = null;

    CircleProgressBar graph_temp;
    CircleProgressBar graph_methane;
    CircleProgressBar graph_lpg;
    CircleProgressBar graph_carbon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety_worker);
        safety_methane = findViewById(R.id.safety_methane);
        text_safety_methane = findViewById(R.id.text_safety_methane);
        text_safety_methane_per = findViewById(R.id.text_safety_methane_per);
        safety_methane_info = findViewById(R.id.safety_methane_info);
        safety_lpg = findViewById(R.id.safety_lpg);
        text_safety_lpg = findViewById(R.id.text_safety_lpg);
        text_safety_lpg_per = findViewById(R.id.text_safety_lpg_per);
        safety_lpg_info = findViewById(R.id.safety_lpg_info);
        safety_carbon = findViewById(R.id.safety_carbon);
        text_safety_carbon = findViewById(R.id.text_safety_carbon);
        text_safety_carbon_per = findViewById(R.id.text_safety_carbon_per);
        safety_carbon_info = findViewById(R.id.safety_carbon_info);
        safety_temp = findViewById(R.id.safety_temp);
        text_safety_temp = findViewById(R.id.text_safety_temp);
        text_safety_temp_per = findViewById(R.id.text_safety_temp_per);
        safety_temp_info = findViewById(R.id.safety_temp_info);
        back_safety_worker = findViewById(R.id.back_safety_worker);
        graph_temp = findViewById(R.id.graph_temp);
        graph_methane = findViewById(R.id.graph_methane);
        graph_lpg = findViewById(R.id.graph_lpg);
        graph_carbon = findViewById(R.id.graph_carbon);
        home_safety_worker = findViewById(R.id.home_safety_worker);

        home_safety_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WokerMainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        register_receiver();

        set_graph(graph_temp, 100);
        set_graph(graph_methane, 1000);
        set_graph(graph_lpg, 100);
        set_graph(graph_carbon, 1000);

        back_safety_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        safety_methane_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp(text_safety_methane, R.raw.safety_methane_info);
            }
        });
        safety_lpg_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp(text_safety_lpg, R.raw.safety_lpg_info);
            }
        });
        safety_carbon_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp(text_safety_carbon, R.raw.safety_carbon_info);
            }
        });
        safety_temp_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUp(text_safety_temp, R.raw.safety_temp_info);
            }
        });
    }

    private void popUp(TextView textView, int resId) {
        PopupFragment popupFragment = new PopupFragment();
        Bundle bundle = new Bundle();

        String title = textView.getText().toString();
        String content = new MyTextReader().readTextFile(getApplicationContext(), resId);

        bundle.putString("title", title);
        bundle.putString("content", content);
        popupFragment.setArguments(bundle);
        popupFragment.show(getSupportFragmentManager(), PopupFragment.TAG_POPUP_DIALOG);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void set_graph(CircleProgressBar graph, int max) {
        CircleProgressBar.ProgressFormatter progressFormatter = new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                return "";
            }
        };
        graph.setProgressFormatter(progressFormatter);
        graph.setProgressTextColor(ContextCompat.getColor(this, R.color.button_color));
        graph.setProgressBackgroundColor(ContextCompat.getColor(this, R.color.white_gray_color));

        graph.setMax(max);
        graph.setProgress(0);
        graph.setProgressStartColor(ContextCompat.getColor(getApplicationContext(), R.color.graph_start));
        graph.setProgressEndColor(ContextCompat.getColor(getApplicationContext(), R.color.graph_end));
    }

    public void register_receiver() {
        if (safety_receiver != null)
            return;
        IntentFilter filter = new IntentFilter();
        filter.addAction("worker_safety");

        this.safety_receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("worker_safety")) {
                    int worker_id = intent.getIntExtra(IntentWorker.WORKER_ID, 0);
                    String vest_id = intent.getStringExtra(IntentWorker.VEST_ID);
                    int director_id = intent.getIntExtra(IntentWorker.DIRECTOR_ID, 0);
                    int warning_signal = intent.getIntExtra(IntentWorker.WARNING_SIGNAL, 0);
                    double latitude = intent.getDoubleExtra(IntentWorker.LATITUDE, 0);
                    double longitude = intent.getDoubleExtra(IntentWorker.LONGITUDE, 0);
                    double methane = intent.getDoubleExtra(IntentWorker.METHANE, 0);
                    double lpg = intent.getDoubleExtra(IntentWorker.LPG, 0);
                    double carbon_monoxide = intent.getDoubleExtra(IntentWorker.CARBON_MONOXIDE, 0);
                    double temperature = intent.getDoubleExtra(IntentWorker.TEMPERATURE, 0);
                    double humidity = intent.getDoubleExtra(IntentWorker.HUMIDITY, 0);
                    int state_methane = intent.getIntExtra(IntentWorker.STATE_METHANE, 0);
                    int state_lpg = intent.getIntExtra(IntentWorker.STATE_LPG, 0);
                    int state_carbon = intent.getIntExtra(IntentWorker.STATE_CARBON_MONOXIDE, 0);
                    int state_temperature = intent.getIntExtra(IntentWorker.STATE_TEMPERATURE, 0);
                    int state_humidity = intent.getIntExtra(IntentWorker.STATE_HUMIDITY, 0);

                    String temp_per = (int)temperature + "℃ / " + (int)humidity + "%";
                    String methane_per = (int)methane + "ppm";
                    String lpg_per = (int)lpg + "ppm";
                    String carbon_per = (int)carbon_monoxide + "ppm";
                    text_safety_temp_per.setText(temp_per);
                    text_safety_methane_per.setText(methane_per);
                    text_safety_lpg_per.setText(lpg_per);
                    text_safety_carbon_per.setText(carbon_per);

                    graph_temp.setProgress((int)temperature);
                    graph_methane.setProgress((int)methane);
                    graph_lpg.setProgress((int)lpg);
                    graph_carbon.setProgress((int)carbon_monoxide);
                    set_progress_text(graph_temp, state_temperature);
                    set_progress_text(graph_methane, state_methane);
                    set_progress_text(graph_lpg, state_lpg);
                    set_progress_text(graph_carbon, state_carbon);
                }
            }
        };

        this.registerReceiver(this.safety_receiver, filter);
    }

    public void set_progress_text(CircleProgressBar graph, int state) {
        final String s;
        int color;
        if (state == 1) {
            s = "주의";
            color = ContextCompat.getColor(this, R.color.warning);
        }
        else if (state == 2) {
            s = "나쁨";
            color = ContextCompat.getColor(this, R.color.bad);
        }
        else {
            s = "좋음";
            color = ContextCompat.getColor(this, R.color.good);
        }

        CircleProgressBar.ProgressFormatter progressFormatter = new CircleProgressBar.ProgressFormatter() {
            @Override
            public CharSequence format(int progress, int max) {
                return s;
            }
        };
        graph.setProgressFormatter(progressFormatter);
        graph.setProgressTextColor(color);
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
