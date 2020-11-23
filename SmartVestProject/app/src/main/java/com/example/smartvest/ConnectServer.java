package com.example.smartvest;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ConnectServer {
    OkHttpClient client = new OkHttpClient();
    public static final String url = "https://www.safetysmartvest.com/smartvest/";
    private Handler mHandler;

    public void request_join_worker(Map<String, Object> request) throws InterruptedException {
        String _url = url + "worker/join";
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBodyBuilder = new FormBody.Builder().add("url", _url);

        for (Map.Entry<String, Object> entry : request.entrySet()) {
            Log.d(entry.getKey(), (String) entry.getValue());
            formBodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        RequestBody requestBody = formBodyBuilder.build();
        Request _request = new Request.Builder().url(_url).post(requestBody).build();

        client.newCall(_request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("error", "Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();

                Log.d("Response", responseData);

            }
        });
    }

    // parameter: worker에 대한 정보 update
    public void request_update_worker_information(Map<String, Object> request) {
        String _url = url + "worker/info_update";
        OkHttpClient client = new OkHttpClient();
        Worker worker = new Worker();

        FormBody.Builder formBodyBuilder = new FormBody.Builder().add("url", _url);

        for (Map.Entry<String, Object> entry : request.entrySet()) {
            if (entry.getValue() != null)
                formBodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
            else return;
        }
        RequestBody requestBody = formBodyBuilder.build();
        Request _request = new Request.Builder().url(_url).post(requestBody).build();

        client.newCall(_request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("error", "Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();

                Log.d("Response", responseData);
            }
        });
    }
    public void request_update_warning_information(int worker_id, boolean is_worker) {
        String _url = url + "worker/report_signal";
        OkHttpClient client = new OkHttpClient();
        Worker worker = new Worker();

        FormBody.Builder formBodyBuilder = new FormBody.Builder().add("url", url);
        formBodyBuilder.add("worker_id", String.valueOf(worker_id));

        if (is_worker)
            formBodyBuilder.add("report_signal", String.valueOf(1));
        else
            formBodyBuilder.add("report_signal", String.valueOf(0));

        RequestBody requestBody = formBodyBuilder.build();
        Request _request = new Request.Builder().url(_url).post(requestBody).build();

        client.newCall(_request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("error", "Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();

                Log.d("Response", responseData);
            }
        });
    }

    public void request_delete_worker(int worker_id){
        String _url = url + "worker/delete_worker";
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBodyBuilder = new FormBody.Builder().add("url", url);
        formBodyBuilder.add("worker_id", String.valueOf(worker_id));

        RequestBody requestBody = formBodyBuilder.build();
        Request _request = new Request.Builder().url(_url).post(requestBody).build();

        client.newCall(_request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("error", "Connect Server Error is " + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseData = response.body().string();

                Log.d("Response", responseData);
            }
        });
    }

}
