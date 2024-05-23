package com.example.lostandfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "LostFound.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_NAME = "items";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "TYPE";
    private static final String COL_3 = "NAME";
    private static final String COL_4 = "PHONE";
    private static final String COL_5 = "DESCRIPTION";
    private static final String COL_6 = "DATE";
    private static final String COL_7 = "LOCATION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TYPE TEXT, NAME TEXT, PHONE TEXT, DESCRIPTION TEXT, DATE TEXT, LOCATION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String type, String name, String phone, String description, String date, String location) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, type);
        contentValues.put(COL_3, name);
        contentValues.put(COL_4, phone);
        contentValues.put(COL_5, description);
        contentValues.put(COL_6, date);
        contentValues.put(COL_7, location);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean deleteItemById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id}) > 0;
    }

    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
