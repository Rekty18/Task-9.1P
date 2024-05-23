package com.example.lostandfoundapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailActivity extends AppCompatActivity {
    private String itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        TextView tvItemName = findViewById(R.id.tvItemName);
        TextView tvItemDetails = findViewById(R.id.tvItemDetails);
        Button btnRemove = findViewById(R.id.btnRemove);

        itemId = getIntent().getStringExtra("id");  // Retrieve the item ID
        String itemName = getIntent().getStringExtra("name");
        String itemDetails = getIntent().getStringExtra("details");

        tvItemName.setText(itemName);
        tvItemDetails.setText(itemDetails);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem();
            }
        });
    }

    private void removeItem() {
        DatabaseHelper db = new DatabaseHelper(this);
        boolean success = db.deleteItemById(itemId);
        if (success) {
            Toast.makeText(ItemDetailActivity.this, "Item removed successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(ItemDetailActivity.this, "Failed to remove item", Toast.LENGTH_SHORT).show();
        }
    }
}
