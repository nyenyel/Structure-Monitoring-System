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

    private static String DATABASE_NAME = "Logs.db";

    private static String TABLE_NAME = "data_logs";
    private static String COL_YEAR = "_year";
    private static String COL_MONTH ="_month";
    private static String COL_WEEK ="_week";
    private static String COL_DAY ="_day";
    private static String COL_TIME = "_time";
    private static String COL_XYZ_DATA = "xyz_data";
    public DatabaseLogging(Context context){
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME
                + " (" + COL_YEAR + " INTEGER, "
                + COL_MONTH + " INTEGER, "
                + COL_WEEK + " INTEGER, "
                + COL_DAY + " INTEGER, "
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

        contentValues.put(COL_DAY, item.getDay());
        contentValues.put(COL_WEEK, item.getWeek());
        contentValues.put(COL_MONTH, item.getMonth());
        contentValues.put(COL_YEAR, item.getYear());

        contentValues.put(COL_TIME, item.getTime());
        contentValues.put(COL_XYZ_DATA, item.getXyzData());

        long result = db.insert(TABLE_NAME, null, contentValues);
//        if(result == -1){
//            Log.e("Data Progress", "XYZ insertion Failed");
//        }else{ Log.e("Data Progress", "XYZ insertion Success");}
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

    public Cursor yearlyLog(int year){
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ COL_YEAR + "="+year;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor monthlyLog(String month){
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COL_MONTH + "=" + month;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor weeklyLog(String month, String week){
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COL_MONTH + "=" + month + " AND "
                + COL_WEEK + "=" + week;
        SQLiteDatabase db = this.getReadableDatabase();

        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor dailyLog( String month, String day){
        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "
                + COL_MONTH + "=" + month + " AND "
                + COL_DAY + "=" + day;
        SQLiteDatabase db = this.getReadableDatabase();

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
