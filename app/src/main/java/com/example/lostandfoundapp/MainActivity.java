package com.example.lostandfoundapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lostandfoundapp.NewAdvertActivity;
import com.example.lostandfoundapp.R;
import com.example.lostandfoundapp.ShowItemsActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnNewAdvert, btnShowItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNewAdvert = findViewById(R.id.btnNewAdvert);
        btnShowItems = findViewById(R.id.btnShowItems);

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
    }
}
