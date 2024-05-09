package com.example.lostandfoundapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowItemsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemsAdapter mAdapter;
    private DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myDb = new DatabaseHelper(this);

        loadData();
    }

    private void loadData() {
        Cursor cursor = myDb.getAllData();
        try {
            if (cursor != null && cursor.getCount() == 0) {
                Toast.makeText(ShowItemsActivity.this, "No data to show", Toast.LENGTH_LONG).show();
            } else if (cursor != null) {
                mAdapter = new ItemsAdapter(this, cursor);
                recyclerView.setAdapter(mAdapter);
                setupItemClickListener();
            }
        } finally {
            if (cursor != null && cursor.getCount() == 0) {
                cursor.close();
            }
        }
    }

    private void setupItemClickListener() {
        mAdapter.setOnItemClickListener(new ItemsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                try (Cursor cursor = mAdapter.getCursor()) {
                    if (cursor != null && cursor.moveToPosition(position)) {
                        Intent intent = new Intent(ShowItemsActivity.this, ItemDetailActivity.class);
                        intent.putExtra("id", cursor.getString(cursor.getColumnIndex("ID"))); // Pass the ID
                        intent.putExtra("name", cursor.getString(cursor.getColumnIndex("NAME")));
                        intent.putExtra("details", cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
