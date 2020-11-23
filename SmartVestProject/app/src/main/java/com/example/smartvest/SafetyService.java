package com.example.smartvest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;


public class SafetyService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(getApplicationContext(), "서비스 시작", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction() == null) {
            return START_REDELIVER_INTENT;
        }
        else if (intent.getAction().equals("worker_safety")) {
            Intent mintent = set(intent);
            sendBroadcast(mintent);

            Map<String, Object> request = new HashMap<>();

//            request.put("worker_id", "21");
//            request.put("vest_id", "123KP032402934");
//            request.put("warning_signal", String.valueOf(1));
//            request.put("latitude", String.valueOf(128.302));
//            request.put("longitude", String.valueOf(123.19));
//            request.put("methane", String.valueOf(29));
//            request.put("lpg", String.valueOf(12));
//            request.put("carbon_monoxide", String.valueOf(32));
//            request.put("temperature", String.valueOf(34.5));
//            request.put("humidity", String.valueOf(23));
//            request.put("state_methane", String.valueOf(1));
//            request.put("state_lpg", String.valueOf(0));
//            request.put("state_carbon_monoxide", String.valueOf(1));
//            request.put("state_temperature", String.valueOf(2));
//            request.put("state_humidity", String.valueOf(3));
            //Log.d("worker_service", String.valueOf(intent.getIntExtra(IntentWorker.WORKER_ID, 0)));
            request.put("worker_id", String.valueOf(intent.getIntExtra(IntentWorker.WORKER_ID, 0)));
            request.put("vest_id", String.valueOf(intent.getStringExtra(IntentWorker.VEST_ID)));
            request.put("warning_signal", String.valueOf(intent.getIntExtra(IntentWorker.WARNING_SIGNAL, 0)));
            request.put("latitude", String.valueOf(intent.getDoubleExtra(IntentWorker.LATITUDE, 0)));
            request.put("longitude", String.valueOf(intent.getDoubleExtra(IntentWorker.LONGITUDE, 0)));
            request.put("methane", String.valueOf(intent.getDoubleExtra(IntentWorker.METHANE,0)));
            request.put("lpg", String.valueOf(intent.getDoubleExtra(IntentWorker.LPG,0)));
            request.put("carbon_monoxide", String.valueOf(intent.getDoubleExtra(IntentWorker.CARBON_MONOXIDE,0)));
            request.put("temperature", String.valueOf(intent.getDoubleExtra(IntentWorker.TEMPERATURE,0)));
            request.put("humidity", String.valueOf(intent.getDoubleExtra(IntentWorker.HUMIDITY,0)));
            request.put("state_methane", String.valueOf(intent.getIntExtra(IntentWorker.STATE_METHANE,0)));
            request.put("state_lpg", String.valueOf(intent.getIntExtra(IntentWorker.STATE_LPG,0)));
            request.put("state_carbon_monoxide", String.valueOf(intent.getIntExtra(IntentWorker.STATE_CARBON_MONOXIDE,0)));
            request.put("state_temperature", String.valueOf(intent.getIntExtra(IntentWorker.STATE_TEMPERATURE,0)));
            request.put("state_humidity", String.valueOf(intent.getIntExtra(IntentWorker.STATE_HUMIDITY,0)));

            ConnectServer connectServer = new ConnectServer();
            connectServer.request_update_worker_information(request);
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public Intent set(Intent intent) {
        Intent mintent = new Intent("worker_safety");
        mintent.setPackage("com.example.smartvest");
        mintent.putExtra(IntentWorker.WORKER_ID, intent.getIntExtra(IntentWorker.WORKER_ID, 0));
        // Log.d("worker_id", intent.getStringExtra(IntentWorker.WORKER_ID));
        mintent.putExtra(IntentWorker.VEST_ID, intent.getStringExtra(IntentWorker.VEST_ID));
        mintent.putExtra(IntentWorker.DIRECTOR_ID, intent.getIntExtra(IntentWorker.DIRECTOR_ID, 0));
        mintent.putExtra(IntentWorker.WARNING_SIGNAL, intent.getIntExtra(IntentWorker.WARNING_SIGNAL, 0));
        mintent.putExtra(IntentWorker.LATITUDE, intent.getDoubleExtra(IntentWorker.LATITUDE, 0));
        mintent.putExtra(IntentWorker.LONGITUDE, intent.getDoubleExtra(IntentWorker.LONGITUDE, 0));
        mintent.putExtra(IntentWorker.METHANE, intent.getDoubleExtra(IntentWorker.METHANE, 0));
        mintent.putExtra(IntentWorker.LPG, intent.getDoubleExtra(IntentWorker.LPG, 0));
        mintent.putExtra(IntentWorker.CARBON_MONOXIDE, intent.getDoubleExtra(IntentWorker.CARBON_MONOXIDE, 0));
        mintent.putExtra(IntentWorker.TEMPERATURE, intent.getDoubleExtra(IntentWorker.TEMPERATURE, 0));
        mintent.putExtra(IntentWorker.HUMIDITY, intent.getDoubleExtra(IntentWorker.HUMIDITY, 0));
        mintent.putExtra(IntentWorker.STATE_METHANE, intent.getIntExtra(IntentWorker.STATE_METHANE, 0));
        mintent.putExtra(IntentWorker.STATE_LPG, intent.getIntExtra(IntentWorker.STATE_LPG, 0));
        mintent.putExtra(IntentWorker.STATE_CARBON_MONOXIDE, intent.getIntExtra(IntentWorker.STATE_CARBON_MONOXIDE, 0));
        mintent.putExtra(IntentWorker.STATE_TEMPERATURE, intent.getIntExtra(IntentWorker.STATE_TEMPERATURE, 0));
        mintent.putExtra(IntentWorker.STATE_HUMIDITY, intent.getIntExtra(IntentWorker.STATE_HUMIDITY, 0));

        return mintent;
    }
}
