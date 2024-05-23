package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnNewAdvert, btnShowItems, btnShowOnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNewAdvert = findViewById(R.id.btnNewAdvert);
        btnShowItems = findViewById(R.id.btnShowItems);
        btnShowOnMap = findViewById(R.id.btnShowOnMap);

        btnNewAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewAdvertActivity.class));
            }
        });

        btnShowItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowItemsActivity.class));
            }
        });

        btnShowOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });
    }
}

