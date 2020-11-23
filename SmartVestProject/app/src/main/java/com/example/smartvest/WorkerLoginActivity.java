package com.example.smartvest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WorkerLoginActivity extends AppCompatActivity {
    private List<String> portList;
    private List<List<String>> wharfList;
    private List<String> selected_wharfList;
    private List<String> companyList;
    private String url;
    AutoCompleteAdapter adapter_wharf;
    ImageView back_login_worker;
    Button button_login_worker;
    AutoCompleteTextView select_port;
    AutoCompleteTextView select_wharf;
    AutoCompleteTextView select_company;
    EditText input_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_worker);
        select_port = findViewById(R.id.select_port);
        select_wharf = findViewById(R.id.select_wharf);
        select_company = findViewById(R.id.select_company);
        input_name = findViewById(R.id.input_name);

        button_login_worker = findViewById(R.id.button_login_worker);
        button_login_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        try {
                            OkHttpClient client = new OkHttpClient();

                            url = getString(R.string.url) + "director/worker/director";
                            Log.d("==========url", url);

                            FormBody.Builder formBodyBuilder = new FormBody.Builder()
                                    .add("url", url)
                                    .add("harbor", select_port.getText().toString())
                                    .add("dock", select_wharf.getText().toString())
                                    .add("company", select_company.getText().toString());
                            RequestBody requestBody = formBodyBuilder.build();

                            Request request = new Request.Builder()
                                    .url(url)
                                    .post(requestBody) //POST로 전달할 내용 설정
                                    .build();

                            //동기 처리시 execute함수 사용
                            Response response = client.newCall(request).execute();

                            //출력
                            String message = response.body().string();
                            int director_id = 0;
                            Log.d("======director message", message);
                            if (!response.isSuccessful()) {
                                return null;
                            } else {
                                Gson gson = new Gson();

                                try {
                                    JSONObject jsonObject = new JSONObject(message);

                                    //로그인 실패.
                                    if (jsonObject.get("result").toString().contains("0")) {
                                        Toast.makeText(getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                        return null;
                                    }
                                    Director director = new Director();
                                    director = gson.fromJson(jsonObject.get("result").toString(), Director.class);
                                    director_id = director.getDirector_id();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                url = getString(R.string.url) + "worker/join";
                                Log.d("==========url", url);
                                formBodyBuilder = new FormBody.Builder().add("url", url).add("director_id", String.valueOf(director_id));

                                requestBody = formBodyBuilder.build();
                                Request _request = new Request.Builder().url(url).post(requestBody).build();

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
                                            if (jsonObject.get("result").toString().contains("0")) {
                                                Toast.makeText(getApplicationContext(), "로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                            Worker worker = new Worker();
                                            worker = gson.fromJson(jsonObject.get("result").toString(), Worker.class);
                                            int worker_id = worker.getWorker_id();

                                            Intent intent = new Intent(getApplicationContext(), WokerMainActivity.class);
                                            intent.putExtra("worker_id", String.valueOf(worker_id));
                                            Log.d("worker_login", String.valueOf(worker_id));
                                            String name = input_name.getText().toString();
                                            intent.putExtra("name", name);
                                            startActivity(intent);

                                        } catch (Exception ee) {
                                            ee.printStackTrace();
                                        }
                                    }
                                });

                                return response.body().string();

                            }
                        } catch (Exception e) {
                            System.err.println(e.toString());
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                    }
                };

                asyncTask.execute();
            }
        });

        back_login_worker = findViewById(R.id.back_login_worker);
        back_login_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        fillPortList();

        fillCompanyList();

        fillWharfList();

        final AutoCompleteTextView select_port = (AutoCompleteTextView) findViewById(R.id.select_port);
        final AutoCompleteTextView select_wharf = (AutoCompleteTextView) findViewById(R.id.select_wharf);

        AutoCompleteAdapter adapter_port = new AutoCompleteAdapter(this, portList);
        select_port.setAdapter(adapter_port);

        select_port.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                select_port.showDropDown();
                return false;
            }
        });

        select_port.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected_port = select_port.getAdapter().getItem(i).toString();
                for (int index = 0; index < portList.size(); index++) {
                    if (selected_port.equals(portList.get(index))) {
                        selected_wharfList = wharfList.get(index);
                        break;
                    }
                }
                adapter_wharf = new AutoCompleteAdapter(WorkerLoginActivity.this, selected_wharfList);
                select_wharf.setAdapter(adapter_wharf);
            }
        });

        select_wharf.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (select_port.getText().toString().equals("") ||
                        select_port.getText().toString() == null ||
                        selected_wharfList.isEmpty()) {
                    Toast.makeText(WorkerLoginActivity.this, "항만을 선택한 후 부두를 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    select_wharf.showDropDown();
                }
                return false;
            }
        });

        final AutoCompleteTextView select_company = (AutoCompleteTextView) findViewById(R.id.select_company);
        AutoCompleteAdapter adapter_company = new AutoCompleteAdapter(this, companyList);
        select_company.setAdapter(adapter_company);

        select_company.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                select_company.showDropDown();
                return false;
            }
        });
    }

    private void fillPortList() {
        String[] ports = getResources().getStringArray(R.array.select_port);
        portList = new ArrayList<>(Arrays.asList(ports));
    }

    private void fillWharfList() {
        String[] wharf_ulsan_main = getResources().getStringArray(R.array.select_wharf_ulsan_main_port);
        String[] wharf_onsan = getResources().getStringArray(R.array.select_wharf_onsan_port);
        String[] wharf_mipo = getResources().getStringArray(R.array.select_wharf_mipo_port);
        String[] wharf_ulsan_new = getResources().getStringArray(R.array.select_wharf_ulsan_new_port);
        List<String> wharf_ulsan_main_list = new ArrayList<>(Arrays.asList(wharf_ulsan_main));
        List<String> wharf_onsan_list = new ArrayList<>(Arrays.asList(wharf_onsan));
        List<String> wharf_mipo_list = new ArrayList<>(Arrays.asList(wharf_mipo));
        List<String> wharf_ulsan_new_list = new ArrayList<>(Arrays.asList(wharf_ulsan_new));
        wharfList = new ArrayList<List<String>>();
        wharfList.add(wharf_ulsan_main_list);
        wharfList.add(wharf_onsan_list);
        wharfList.add(wharf_mipo_list);
        wharfList.add(wharf_ulsan_new_list);
        selected_wharfList = new ArrayList<>();
    }

    private void fillCompanyList() {
        String[] company = getResources().getStringArray(R.array.select_company);
        companyList = new ArrayList<>(Arrays.asList(company));
    }

    class AutoCompleteAdapter extends ArrayAdapter<String> {
        private List<String> list;

        public AutoCompleteAdapter(@NonNull Context context, @NonNull List<String> list) {
            super(context, 0, list);
            this.list = new ArrayList<>(list);
        }

        public void setList(List<String> list) {
            this.list = new ArrayList<>(list);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_dropbox, parent, false);
            }
            TextView textView = convertView.findViewById(R.id.text_drop);
            String text = getItem(position);

            if (text != null) {
                textView.setText(text);
            }
            return convertView;
        }

        @NonNull
        @Override
        public Filter getFilter() {
            return filter;
        }

        private Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults results = new FilterResults();
                List<String> searchedList = new ArrayList<>();

                if (charSequence == null || charSequence.length() == 0) {
                    searchedList.addAll(list);
                } else {
                    String pattern = charSequence.toString().toLowerCase().trim();

                    for (String item : list) {
                        if (item.toLowerCase().contains(pattern)) {
                            searchedList.add(item);
                        }
                    }
                }
                results.values = searchedList;
                results.count = searchedList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                clear();
                addAll((List) filterResults.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return (String) resultValue;
            }
        };
    }
}

