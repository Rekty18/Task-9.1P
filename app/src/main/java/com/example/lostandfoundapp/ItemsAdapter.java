package com.example.lostandfoundapp;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private Context context;
    private Cursor cursor;

    public ItemsAdapter(Context context, Cursor cursor) {
        this.context = context;
        this.cursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName, tvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (!cursor.moveToPosition(position)) {
            return;
        }

        String name = cursor.getString(cursor.getColumnIndex("NAME"));
        String description = cursor.getString(cursor.getColumnIndex("DESCRIPTION"));
        holder.tvName.setText(name);
        holder.tvDescription.setText(description);
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }
}


