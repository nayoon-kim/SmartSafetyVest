package com.example.smartvest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
import java.util.HashMap;

public class ManagerMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    ImageView back_location_mgr;

    BroadcastReceiver safety_receiver = null;
    Double base_latitude = 35.52189;
    Double base_longitude = 129.37316;
    ArrayList<Worker> workers = new ArrayList<Worker>();
    HashMap<Integer, Marker> markers = new HashMap<Integer, Marker>();
    HashMap<Integer, Integer> connected_workers = new HashMap<Integer, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_manager);

        back_location_mgr = findViewById(R.id.back_location_mgr);
        back_location_mgr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_manager);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        LatLng base = new LatLng(base_latitude, base_longitude);
        
        String text_danger_zone = this.getString(R.string.danger_zone_1);
        ArrayList<LatLng> points = DangerZone.get_points(text_danger_zone);
        draw_danger_zone(mMap, points);
        text_danger_zone = this.getString(R.string.danger_zone_2);
        points = DangerZone.get_points(text_danger_zone);
        draw_danger_zone(mMap, points);

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(base, 16));
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
        filter.addAction("manager_safety");

        this.safety_receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals("manager_safety")) {
                    if ((ArrayList<Worker>) intent.getSerializableExtra("workers") != null) {
                        workers = (ArrayList<Worker>) intent.getSerializableExtra("workers");

                        for (Worker worker : workers) {
                            connected_workers.put(worker.worker_id, worker.worker_id);
                            double latitude = worker.latitude;
                            double longitude = worker.longitude;
                            LatLng p = new LatLng(latitude, longitude);
                            if (markers.containsKey(worker.worker_id)) {
                                markers.get(worker.worker_id).setTitle("작업자 번호 : " + worker.worker_id);
                                markers.get(worker.worker_id).setPosition(p);
                            }
                            else {
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.position(p);
                                Marker marker = mMap.addMarker(markerOptions);
                                marker.showInfoWindow();
                                markers.put(worker.worker_id, marker);
                            }
                        }
                        if (connected_workers.keySet().size() != workers.size()) {
                            for (Worker worker : workers) {
                                connected_workers.remove(worker.worker_id);
                            }
                            for (int key : connected_workers.keySet()) {
                                markers.get(key).remove();
                                markers.remove(key);
                            }
                            for (Worker worker : workers) {
                                connected_workers.put(worker.worker_id, worker.worker_id);
                            }
                        }
                    }
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
