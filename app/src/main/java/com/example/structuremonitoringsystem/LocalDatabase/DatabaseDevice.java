package com.example.structuremonitoringsystem.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseDevice extends SQLiteOpenHelper{

    private Context context;

    private static String DATABASE_NAME = "Devices.db";

    private static String TABLE_NAME = "devices";
    private static String COL_ID = "_id";
    private static String COL_DEVICE_NAME = "device_name";
    public DatabaseDevice(Context context){
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME
                + " ("+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DEVICE_NAME + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void setDeviceName(String deviceName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, deviceName);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "PIN insertion Failed");
        }else{ Log.e("Data Progress", "PIN insertion Success");}
    }

    public Cursor getDeviceName(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void renameDevice(String id, String deviceName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_DEVICE_NAME, deviceName);

        long result = db.update(TABLE_NAME, contentValues, COL_ID+"=?", new String[]{id});
        if(result == -1){
            Log.e("Data Progress", "Data update Failed");
        }else{ Log.e("Data Progress", "Data update Success");}
    }



}
