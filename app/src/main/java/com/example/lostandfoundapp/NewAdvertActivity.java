package com.example.lostandfoundapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;

public class NewAdvertActivity extends AppCompatActivity {
    private EditText etName, etPhone, etDescription, etDate, etLocation;
    private RadioButton rbLost, rbFound;
    private Button btnSave, buttonGetCurrentLocation;
    private DatabaseHelper myDb;
    private FusedLocationProviderClient fusedLocationClient;

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_advert);

        myDb = new DatabaseHelper(this);
        etName = findViewById(R.id.etName);
        etPhone = findViewById(R.id.etPhone);
        etDescription = findViewById(R.id.etDescription);
        etDate = findViewById(R.id.etDate);
        etLocation = findViewById(R.id.etLocation);
        rbLost = findViewById(R.id.rbLost);
        rbFound = findViewById(R.id.rbFound);
        btnSave = findViewById(R.id.btnSave);
        buttonGetCurrentLocation = findViewById(R.id.buttonGetCurrentLocation);

        // Ensure the Places SDK is initialized
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // Handle the selected place
                String placeName = place.getName();
                String placeLatLng = "Lat: " + place.getLatLng().latitude + ", Lng: " + place.getLatLng().longitude;
                etLocation.setText(placeLatLng);
                Toast.makeText(NewAdvertActivity.this, "Place: " + placeName + ", " + placeLatLng, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull com.google.android.gms.common.api.Status status) {
                // Handle the error
                Toast.makeText(NewAdvertActivity.this, "An error occurred: " + status, Toast.LENGTH_SHORT).show();
            }
        });

        // Setup Fused Location Provider
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        buttonGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            String currentLocation = "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude();
                            etLocation.setText(currentLocation);
                        } else {
                            Toast.makeText(NewAdvertActivity.this, "Unable to retrieve location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void insertData() {
        String type = rbLost.isChecked() ? "Lost" : "Found";
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();
        String location = etLocation.getText().toString();

        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = myDb.insertData(type, name, phone, description, date, location);
        if (isInserted) {
            Toast.makeText(NewAdvertActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
            finish(); // Close the activity after saving
        } else {
            Toast.makeText(NewAdvertActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
        }
    }
}
