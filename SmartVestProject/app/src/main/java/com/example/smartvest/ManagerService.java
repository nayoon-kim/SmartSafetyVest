package com.example.smartvest;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ManagerService extends Service {
    boolean is_create = false;
    boolean run = true;
    int director_id = 0;
    NotificationManager notificationManager = null;
    NotificationCompat.Builder builder = null;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        is_create = true;

        notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String channelID="channel_manager"; //알림채널 식별자
            String channelName="channel_manager"; //알림채널의 이름(별명)

            //알림채널 객체 만들기
            NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

            //알림매니저에게 채널 객체의 생성을 요청
            notificationManager.createNotificationChannel(channel);

            //알림건축가 객체 생성
            builder=new NotificationCompat.Builder(getApplicationContext(), channelID);


        }else{
            //알림 건축가 객체 생성
            builder= new NotificationCompat.Builder(getApplicationContext(), null);
        }

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (is_create) {

            if (intent.getAction().toString().equals("director_id"))
                director_id = Integer.parseInt(intent.getStringExtra("director_id"));
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (run) {
                        try {
                            String url = getString(R.string.url) + "worker/director/info_list";
                            OkHttpClient client = new OkHttpClient();

                            FormBody.Builder formBodyBuilder = new FormBody.Builder().add("url", url).add("director_id", String.valueOf(director_id));

                            RequestBody requestBody = formBodyBuilder.build();
                            Request _request = new Request.Builder().url(url).post(requestBody).build();
                            final ArrayList<Worker> list_worker = new ArrayList<>();

                            client.newCall(_request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    Log.d("error", "Connect Server Error is " + e.toString());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    final String responseData = response.body().string();
                                    //Log.d("Response", responseData);

                                    Gson gson = new Gson();
                                    try {
                                        JSONObject jsonObject = new JSONObject(responseData);
                                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                                        for(int i = 0; i<jsonArray.length();i++){

                                            JSONObject object = jsonArray.getJSONObject(i);
                                            Worker worker = new Worker();
                                            worker = gson.fromJson(object.toString(), Worker.class);

                                            if (worker.report_signal == 1 || worker.warning_signal == 1) {
                                                make_notification(worker);
                                            }

                                            list_worker.add(worker);
                                            Log.d(String.valueOf(i), worker.toString());
                                        }

                                        Intent intent = new Intent("manager_safety");
                                        intent.setPackage("com.example.smartvest");
//                                    Intent intent = new Intent(getApplicationContext(), ManagerSafetyActivity.class);
                                        // 모달창 띄울 때, intent로 보내준 데이터를 그대로 사용.
                                        intent.putExtra("workers", list_worker);
                                        sendBroadcast(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            Thread.sleep(1000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }

        is_create = false;

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        run = false;
    }

    public void make_notification(Worker worker) {
        Uri soundUri = RingtoneManager.getActualDefaultRingtoneUri(getApplicationContext(), RingtoneManager.TYPE_NOTIFICATION);
        Intent intent = new Intent(getApplicationContext(), ManagerSafetyActivity.class);
        intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP | intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setSound(soundUri);
        builder.setVibrate(new long[]{0, 500});
        builder.setAutoCancel(true);

        if (worker.report_signal == 1) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.worker_report);
            builder.setSmallIcon(R.drawable.worker_report);
            builder.setLargeIcon(bitmap);
            builder.setContentTitle(getString(R.string.noti_report_title));
            builder.setContentText(worker.worker_id + getString(R.string.noti_report_content));
        }
        else {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.worker_safety_check);
            builder.setSmallIcon(R.drawable.worker_safety_check);
            builder.setLargeIcon(bitmap);
            builder.setContentTitle(getString(R.string.noti_warning_title));
            builder.setContentText(worker.worker_id + getString(R.string.noti_warning_content));
        }
        notificationManager.notify(worker.worker_id, builder.build());
    }
}
