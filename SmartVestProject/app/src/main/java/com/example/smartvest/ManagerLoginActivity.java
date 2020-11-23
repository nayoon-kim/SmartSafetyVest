package com.example.smartvest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ManagerLoginActivity extends AppCompatActivity {

    Button button_login_manager;
    ImageView back_login_manager;
    EditText editText_id, editText_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_manager);

        editText_id = findViewById(R.id.editText_id);
        editText_pw = findViewById(R.id.editText_pw);

        back_login_manager = findViewById(R.id.back_login_manager);
        back_login_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        button_login_manager = findViewById(R.id.button_login_manager);
        button_login_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _url = getString(R.string.url) + "director/login";
                Map<String, Object> request = new HashMap<>();
                request.put("loginname", editText_id.getText().toString());
                request.put("password", editText_pw.getText().toString());

                OkHttpClient client = new OkHttpClient();

                FormBody.Builder formBodyBuilder = new FormBody.Builder().add("url", _url);

                for (Map.Entry<String, Object> entry : request.entrySet()) {
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
                        Gson gson = new Gson();

                        try {
                            JSONObject jsonObject = new JSONObject(responseData);

                            //로그인 실패.
                            if (jsonObject.get("result").toString().contains("0")){
                                Toast.makeText(getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Director director = new Director();
                            director = gson.fromJson(jsonObject.get("result").toString(), Director.class);
                            int director_id = director.getDirector_id();

                            Intent intent = new Intent(getApplicationContext(), ManagerMainActivity.class);
                            intent.putExtra("director_id", String.valueOf(director_id));
                            startActivity(intent);

                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
