package com.example.lostandfoundapp;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity {

    private MapView map;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);

        map = findViewById(R.id.map);
        map.setMultiTouchControls(true);
        myDb = new DatabaseHelper(this);

        loadMapMarkers();
    }

    private void loadMapMarkers() {
        Cursor cursor = myDb.getAllData();
        List<Marker> markers = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String type = cursor.getString(cursor.getColumnIndex("TYPE"));
                String name = cursor.getString(cursor.getColumnIndex("NAME"));
                String location = cursor.getString(cursor.getColumnIndex("LOCATION"));

                try {
                    String[] latLngStr = location.replace("Lat: ", "").replace("Lng: ", "").split(", ");
                    if (latLngStr.length == 2) {
                        double latitude = Double.parseDouble(latLngStr[0]);
                        double longitude = Double.parseDouble(latLngStr[1]);
                        GeoPoint point = new GeoPoint(latitude, longitude);

                        Marker marker = new Marker(map);
                        marker.setPosition(point);
                        marker.setTitle(type + ": " + name);
                        markers.add(marker);
                    } else {
                        throw new NumberFormatException("Invalid location format");
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid location data for: " + name, Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
        }

        for (Marker marker : markers) {
            map.getOverlays().add(marker);
        }
        if (!markers.isEmpty()) {
            map.getController().setCenter(markers.get(0).getPosition());
            map.getController().setZoom(10.0);
        }
    }
}
