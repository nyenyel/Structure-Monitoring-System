package com.example.structuremonitoringsystem.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.structuremonitoringsystem.Item.DataLogsItem;

public class DatabaseLogging extends SQLiteOpenHelper {

    private Context context;

    private static String DATABASE_NAME = "Devices.db";

    private static String TABLE_NAME = "data_logs";
    private static String COL_DEVICE_ID = "device_id";
    private static String COL_SENSOR_TYPE = "_sensor";
    private static String COL_DATE = "_date";
    private static String COL_TIME = "_time";
    private static String COL_XYZ_DATA = "xyz_data";
    public DatabaseLogging(Context context){
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME
                + " ("+ COL_DEVICE_ID + " INTEGER, "
                + COL_SENSOR_TYPE + " TEXT, "
                + COL_DATE + " TEXT, "
                + COL_TIME + " TEXT, "
                + COL_XYZ_DATA + " TEXT);";
        db.execSQL(query);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addData(DataLogsItem item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_DEVICE_ID, item.getDeviceId());
        contentValues.put(COL_SENSOR_TYPE, item.getSensorType());
        contentValues.put(COL_DATE, item.getDates());
        contentValues.put(COL_TIME, item.getTimes());
        contentValues.put(COL_XYZ_DATA, item.getXyzData());

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "PIN insertion Failed");
        }else{ Log.e("Data Progress", "PIN insertion Success");}
    }

    public Cursor getAllData(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor getDataPerDevice(String deviceID){
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE "+ COL_DEVICE_ID +"="+deviceID;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getDataPerSensor(String deviceID, String sensorType){
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE "+ COL_DEVICE_ID +"=" + deviceID +" AND "+ COL_SENSOR_TYPE + sensorType;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void clearAllDataLogging(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
