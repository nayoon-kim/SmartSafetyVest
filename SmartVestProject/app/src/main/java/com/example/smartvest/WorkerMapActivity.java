package com.example.smartvest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;

public class WorkerMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView back_location_worker;
    BroadcastReceiver safety_receiver = null;
    Marker my_marker;
    ImageView home_location_worker;
    Double base_latitude = 0.0;
    Double base_longitude = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_worker);
        back_location_worker = findViewById(R.id.back_location_worker);
        home_location_worker = findViewById(R.id.home_location_worker);
        home_location_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), WokerMainActivity.class);
                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        back_location_worker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_worker);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng ULSAN = new LatLng(base_latitude, base_longitude);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(ULSAN);
        markerOptions.snippet("상태 : 안전");

        my_marker = mMap.addMarker(markerOptions);
        my_marker.showInfoWindow();
        String text_danger_zone = this.getString(R.string.danger_zone_1);
        ArrayList<LatLng> points = DangerZone.get_points(text_danger_zone);
        draw_danger_zone(mMap, points);
        text_danger_zone = this.getString(R.string.danger_zone_2);
        points = DangerZone.get_points(text_danger_zone);
        draw_danger_zone(mMap, points);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ULSAN, 16));
        register_receiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregister_receiver();
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
                    double latitude = intent.getDoubleExtra(IntentWorker.LATITUDE, 0);
                    double longitude = intent.getDoubleExtra(IntentWorker.LONGITUDE, 0);
                    if (base_latitude == 0 && base_longitude == 0) {
                        base_latitude = latitude;
                        base_longitude = longitude;
                        LatLng base = new LatLng(base_latitude, base_longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(base, 16));
                    }
                    LatLng p = new LatLng(latitude, longitude);
                    my_marker.setTitle("작업자 " + intent.getIntExtra(IntentWorker.WORKER_ID, 0));
                    my_marker.setSnippet("");
                    my_marker.setPosition(p);
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

    protected void draw_danger_zone(GoogleMap mMap, ArrayList<LatLng> points) {
        Polygon polygon = mMap.addPolygon(new PolygonOptions().addAll(points));
        polygon.setStrokeColor(ContextCompat.getColor(getApplicationContext(), R.color.danger_zone_border_color));
        polygon.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.danger_zone_inner_color));
        polygon.setStrokeWidth(3.0f);
    }
}
