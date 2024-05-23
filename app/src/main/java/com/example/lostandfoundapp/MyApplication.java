package com.example.lostandfoundapp;

import android.app.Application;
import com.google.android.libraries.places.api.Places;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize the Places SDK
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getString(R.string.google_maps_key));
        }
    }
}
