package com.example.structuremonitoringsystem.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class   DatabaseDevice extends SQLiteOpenHelper{

    private Context context;

    private static String DATABASE_NAME = "Devices.db";

    private static String TABLE_NAME = "devices";
    private static String COL_ID = "_id";
    private static String COL_DEVICE_NAME = "device_name";
    private static String COL_SENSOR_LIST_ID = "sensor_list_id";
    public DatabaseDevice(Context context){
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME
                + " ("+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_DEVICE_NAME + " TEXT, "
                + COL_SENSOR_LIST_ID + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public String addDevice(String deviceName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String sensorListId = "";

        contentValues.put(COL_DEVICE_NAME, deviceName);

        // Insert the new row and get the ID of the inserted row
        long id = db.insert(TABLE_NAME, null, contentValues);

        if (id == -1) {
            Log.e("Data Progress", "Device insertion failed");
        } else {
            Log.e("Data Progress", "Device insertion success. ID: " + id);

            // Now you can use the newly inserted ID to update other columns if needed
            sensorListId = "device" + id;
            ContentValues updatedValues = new ContentValues();
            updatedValues.put(COL_SENSOR_LIST_ID, sensorListId);

            // Update the row with the newly generated sensorListId
            int rowsAffected = db.update(TABLE_NAME, updatedValues, "_id=?", new String[]{String.valueOf(id)});
            if (rowsAffected > 0) {
                Log.e("Data Progress", "Sensor list ID updated successfully");
            } else {
                Log.e("Data Progress", "Failed to update sensor list ID");
            }
        }
        return sensorListId;
    }

    public Cursor getDevices(){
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
