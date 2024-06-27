package com.example.structuremonitoringsystem.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseSensor extends SQLiteOpenHelper{

    private Context context;

    private static String DATABASE_NAME = "Sensors.db";

    private static String TABLE_NAME = "devices";
    private static String COL_ID = "_id";
    private static String COL_SENSOR_NAME = "sensor_name";
    private static String COL_NUMBER_OF_DATA = "number_of_data";
    public DatabaseSensor(Context context){
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME
                + " ("+ COL_ID + " TEXT, "
                + COL_SENSOR_NAME + " TEXT, "
                + COL_NUMBER_OF_DATA + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addSensor(String sensorName, String sensorID, int numberOfData){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SENSOR_NAME, sensorName);
        contentValues.put(COL_ID, sensorID);
        contentValues.put(COL_NUMBER_OF_DATA, numberOfData);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "PIN insertion Failed");
        }else{ Log.e("Data Progress", "PIN insertion Success");}
    }

    public Cursor getSensorsOnDevice(String sensorListID){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, new String[]{sensorListID});

        }
        return cursor;
    }

    public void renameSensor(String id, String sensorName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SENSOR_NAME, sensorName);

        long result = db.update(TABLE_NAME, contentValues, COL_ID+"=?", new String[]{id});
        if(result == -1){
            Log.e("Data Progress", "Data update Failed");
        }else{ Log.e("Data Progress", "Data update Success");}
    }

    public int getNumOfSensors(String sensorListID) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int count = 0;

        if (db != null) {
            cursor = db.rawQuery(query, new String[]{sensorListID});
            if (cursor != null) {
                count = cursor.getCount(); // Get the number of rows
                cursor.close(); // Close the cursor when done
            }
        }
        return count;
    }

}
