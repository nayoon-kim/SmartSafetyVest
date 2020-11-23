package com.example.smartvest;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

public class WokerMainActivity extends AppCompatActivity {
    ConstraintLayout location_worker;
    ConstraintLayout safety_worker;
    ConstraintLayout manual_worker;
    ConstraintLayout report_worker;
    TextView logout_worker;
    LinearLayout bluetooth_connect;
    TextView vest_connection;
    TextView safety_state_worker;
    TextView vest_number;
    ImageView logo_safety_worker;
    BroadcastReceiver safety_receiver = null;
    public static GeometryFactory factory = new GeometryFactory();

    String input_name = "";

    public static mBluetoothSPP bt;

    int worker_id = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_worker);
        if (bt == null)
            bt = new mBluetoothSPP(getApplicationContext());
        logo_safety_worker = findViewById(R.id.logo_safety_worker);
        location_worker = findViewById(R.id.location_worker);
        safety_worker = findViewById(R.id.safety_worker);
        manual_worker = findViewById(R.id.manual_worker);
        report_worker = findViewById(R.id.report_worker);
        logout_worker = findViewById(R.id.logout_worker);
        bluetooth_connect = findViewById(R.id.bluetooth_connect);
        vest_connection = findViewById(R.id.vest_connection);
        safety_state_worker = findViewById(R.id.safety_state_worker);
        vest_number = findViewById(R.id.vest_number);

//        int worker_id = Integer.parseInt(this.getIntent().getStringExtra("worker_id"));
        location_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkerMapActivity.class);
                startActivity(intent);
            }
        });
        safety_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkerSafetyActivity.class);
                startActivity(intent);
            }
        });
        manual_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WorkerManualActivity.class);
                startActivity(intent);
            }
        });
        report_worker.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // warning_signal 1로 해서 서버에 보내기

                ConnectServer connectServer = new ConnectServer();
                connectServer.request_update_warning_information(PreferenceManager.getInt(getApplicationContext(),"worker_id"), true);
                Toast.makeText(getApplicationContext(), "신고가 접수 되었습니다.", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        logout_worker.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        logout_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                

                bt.disconnect();
                bt.stopService();
                Intent intent = new Intent(getApplicationContext(), SafetyService.class);
                stopService(intent);

                ConnectServer connectServer = new ConnectServer();
                connectServer.request_delete_worker(PreferenceManager.getInt(getApplicationContext(),"worker_id"));

                PreferenceManager.setString(getApplicationContext(), "login_worker", "false");
                PreferenceManager.setString(getApplicationContext(), "logout_click", "true");
                PreferenceManager.setInt(getApplicationContext(), "worker_id", 0);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        bluetooth_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bt.isBluetoothEnabled()) { //
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
                } else {
                    if (!bt.isServiceAvailable()) {
                        bt.setupService();
                        bt.startService(BluetoothState.DEVICE_OTHER);
                    }
                }
                if (bt.getServiceState() == BluetoothState.STATE_CONNECTED) {
                    bt.disconnect();
                    vest_connection.setText(getString(R.string.text_bluetooth_connect));
                } else {
                    Intent intent = new Intent(getApplicationContext(), DeviceList.class);
                    startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                }

            }
        });
        vest_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bt.send(input_name, true);
                // Toast.makeText(getApplicationContext(), input_name, Toast.LENGTH_SHORT).show();
            }
        });
        if (getIntent().getStringExtra("name") != null) {
            input_name = getIntent().getStringExtra("name").trim();
        }
        if (getIntent().getStringExtra("worker_id") != null) {
            if(!getIntent().getStringExtra("worker_id").equals("0"))
                PreferenceManager.setInt(getApplicationContext(), "worker_id", Integer.parseInt(getIntent().getStringExtra("worker_id")));
        }

        Intent intent = new Intent(getApplicationContext(), SafetyService.class);
        intent.setAction("start_service");
        startService(intent);
        setBluetoothListener(bt, input_name);
        PreferenceManager.setString(getApplicationContext(), "login_worker", "true");
    }

    @Override
    protected void onResume() {
        super.onResume();
        register_receiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregister_receiver();
    }

    @Override
    protected void onStart() {
        super.onStart();

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
//                    worker_id = intent.getIntExtra(IntentWorker.WORKER_ID, 0);
                    int state_methane = intent.getIntExtra(IntentWorker.STATE_METHANE, 0);
                    int state_lpg = intent.getIntExtra(IntentWorker.STATE_LPG, 0);
                    int state_carbon = intent.getIntExtra(IntentWorker.STATE_CARBON_MONOXIDE, 0);
                    int state_temperature = intent.getIntExtra(IntentWorker.STATE_TEMPERATURE, 0);

                    final String s;
                    int color;
                    if (state_methane == 1 || state_lpg == 1 || state_carbon == 1 || state_temperature == 1) {
                        s = "주의";
                        color = ContextCompat.getColor(context, R.color.warning);
                        logo_safety_worker.setImageResource(R.drawable.safety_normal);
                    }
                    else if (state_methane == 2 || state_lpg == 2 || state_carbon == 2 || state_temperature == 2) {
                        s = "나쁨";
                        color = ContextCompat.getColor(context, R.color.bad);
                        logo_safety_worker.setImageResource(R.drawable.safety_bad);
                    }
                    else {
                        s = "좋음";
                        color = ContextCompat.getColor(context, R.color.good);
                        logo_safety_worker.setImageResource(R.drawable.safety_good);
                    }
                    safety_state_worker.setText(s);
                    safety_state_worker.setTextColor(color);
                }
            }
        };

        this.registerReceiver(this.safety_receiver, filter);
    }

    public void unregister_receiver() {
        if (safety_receiver != null) {
            this.unregisterReceiver(safety_receiver);
            safety_receiver = null;
        }
    }

    public void setBluetoothListener(final BluetoothSPP bt, final String input_name) {
        if (!bt.isBluetoothAvailable()) { //블루투스 사용 불가
            Toast.makeText(getApplicationContext(), "휴대폰의 블루투스를 켜주세요.", Toast.LENGTH_SHORT).show();
        }
        else {
            bt.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
                @Override
                public void onDataReceived(byte[] data, String message) {
                    String[] worker_info = message.split(",");
                    Worker worker = new Worker();
                    worker.setWorker_id(PreferenceManager.getInt(getApplicationContext(), "worker_id"));
                    worker.setVest_id(worker_info[0]);
                    worker.setLatitude(Double.parseDouble(worker_info[1]));
                    worker.setLongitude(Double.parseDouble(worker_info[2]));
                    worker.setMethane(Double.parseDouble(worker_info[3]));
                    worker.setLpg(Double.parseDouble(worker_info[4]));
                    worker.setCarbon_monoxide(Double.parseDouble(worker_info[5]));
                    worker.setTemperature(Double.parseDouble(worker_info[6]));
                    worker.setHumidity(Double.parseDouble(worker_info[7]));
                    worker.setState_methane(Integer.parseInt(worker_info[8]));
                    worker.setState_lpg(Integer.parseInt(worker_info[9]));
                    worker.setState_carbon_monoxide(Integer.parseInt(worker_info[10]));
                    worker.setState_temperature(Integer.parseInt(worker_info[11]));
                    worker.setState_humidity(Integer.parseInt(worker_info[12]));
                    worker.setWarning_signal(Integer.parseInt(worker_info[13]));

                    String s = ""+worker.worker_id;
                    vest_number.setText(s);

                    String text_danger_zone_1 = getApplicationContext().getString(R.string.danger_zone_1);
                    String text_danger_zone_2 = getApplicationContext().getString(R.string.danger_zone_2);
                    Polygon danger_zone_1 = factory.createPolygon(DangerZone.get_coordinates(text_danger_zone_1));
                    Polygon danger_zone_2 = factory.createPolygon(DangerZone.get_coordinates(text_danger_zone_2));
                    Point point = factory.createPoint(new Coordinate(worker.latitude, worker.longitude));
                    if (point.within(danger_zone_1) || point.within(danger_zone_2)) {
                        bt.send("!", true);
                    }

                    Intent intent = IntentWorker.makeIntent(worker, "worker_safety", getApplicationContext(), SafetyService.class);
                    startService(intent);
                }
            });

            bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
                public void onDeviceConnected(String name, String address) {
                    String s = getString(R.string.text_vest_connection)+"연결";
                    vest_connection.setText(s);
                    Toast.makeText(getApplicationContext()
                            , "Connected to " + name + "\n" + address
                            , Toast.LENGTH_SHORT).show();
                    bt.send(input_name, true);
                }

                public void onDeviceDisconnected() { //연결해제
                    String s = getString(R.string.text_bluetooth_connect);
                    vest_connection.setText(s);
                }

                public void onDeviceConnectionFailed() { //연결실패
                    Toast.makeText(getApplicationContext()
                            , "블루투스 연결 실패. 다시 시도 해주세요.", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                bt.connect(data);
            }
        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
            } else {
                Toast.makeText(getApplicationContext()
                        , "블루투스를 사용할 수 없습니다. 다시 시도해주세요."
                        , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.HOME");
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS
                | Intent.FLAG_ACTIVITY_FORWARD_RESULT
                | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        startActivity(intent);
    }
}
