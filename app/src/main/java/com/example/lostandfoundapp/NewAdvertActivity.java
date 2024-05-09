package com.example.lostandfoundapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NewAdvertActivity extends AppCompatActivity {
    private EditText etName, etPhone, etDescription, etDate, etLocation;
    private RadioButton rbLost, rbFound;
    private Button btnSave;
    private DatabaseHelper myDb;

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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });
    }

    private void insertData() {
        String type = rbLost.isChecked() ? "Lost" : "Found";
        String name = etName.getText().toString();
        String phone = etPhone.getText().toString();
        String description = etDescription.getText().toString();
        String date = etDate.getText().toString();
        String location = etLocation.getText().toString();

        // Basic validation
        if (name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = myDb.insertData(type, name, phone, description, date, location);
        if (isInserted)
            Toast.makeText(NewAdvertActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(NewAdvertActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

}
