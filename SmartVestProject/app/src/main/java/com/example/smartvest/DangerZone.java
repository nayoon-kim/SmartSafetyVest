package com.example.smartvest;

import com.google.android.gms.maps.model.LatLng;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Coordinates;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.util.ArrayList;

public class DangerZone {

    public static ArrayList<LatLng> get_points(String text_danger_zone) {
        ArrayList<LatLng> points = new ArrayList<LatLng>();
        String[] list_danger_zone = text_danger_zone.split(",");
        for (int i = 0; i < list_danger_zone.length; i+=2) {
            Double lat = Double.parseDouble(list_danger_zone[i]);
            Double lng = Double.parseDouble(list_danger_zone[i+1]);
            LatLng point = new LatLng(lat, lng);
            points.add(point);
        }

        return points;
    }

    public static Coordinate[] get_coordinates(String text_danger_zone) {
        String[] list_danger_zone = text_danger_zone.split(",");
        Coordinate[] coordinates = new Coordinate[(list_danger_zone.length/2)];
        for (int i = 0; i < list_danger_zone.length; i+=2) {
            Double lat = Double.parseDouble(list_danger_zone[i]);
            Double lng = Double.parseDouble(list_danger_zone[i+1]);
            Coordinate coordinate = new Coordinate(lat, lng);
            coordinates[i/2] = coordinate;
        }

        return coordinates;
    }
}
