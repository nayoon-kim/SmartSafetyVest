package com.example.smartvest;

import android.content.Context;
import android.content.Intent;

public class IntentWorker {
    public static final String WORKER_ID = "worker_id";
    public static final String VEST_ID = "vest_id";
    public static final String DIRECTOR_ID = "director_id";
    public static final String WARNING_SIGNAL = "warning_signal";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String METHANE = "methane";
    public static final String LPG = "lpg";
    public static final String CARBON_MONOXIDE = "carbon+monoxide";
    public static final String TEMPERATURE = "temperature";
    public static final String HUMIDITY = "humidity";
    public static final String STATE_METHANE = "state_methane";
    public static final String STATE_LPG = "state_lpg";
    public static final String STATE_CARBON_MONOXIDE = "state_carbon_monoxide";
    public static final String STATE_TEMPERATURE = "state_temperature";
    public static final String STATE_HUMIDITY = "state_humidity";


    public static Intent makeIntent(Worker worker, String action, Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        intent.setAction(action);
        intent.setPackage("com.example.smartvest");
        intent.putExtra(WORKER_ID, worker.worker_id);
        intent.putExtra(VEST_ID, worker.vest_id);
        intent.putExtra(DIRECTOR_ID, worker.director_id);
        intent.putExtra(WARNING_SIGNAL, worker.warning_signal);
        intent.putExtra(LATITUDE, worker.latitude);
        intent.putExtra(LONGITUDE, worker.longitude);
        intent.putExtra(METHANE, worker.methane);
        intent.putExtra(CARBON_MONOXIDE, worker.carbon_monoxide);
        intent.putExtra(LPG, worker.lpg);
        intent.putExtra(TEMPERATURE, worker.temperature);
        intent.putExtra(HUMIDITY, worker.humidity);
        intent.putExtra(STATE_METHANE, worker.state_methane);
        intent.putExtra(STATE_LPG, worker.state_lpg);
        intent.putExtra(STATE_CARBON_MONOXIDE, worker.state_carbon_monoxide);
        intent.putExtra(STATE_TEMPERATURE, worker.state_temperature);
        intent.putExtra(STATE_HUMIDITY, worker.state_humidity);

        return intent;
    }
}
