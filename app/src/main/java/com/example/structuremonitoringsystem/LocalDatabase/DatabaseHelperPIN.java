package com.example.structuremonitoringsystem.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelperPIN extends SQLiteOpenHelper {
    private Context context;

    private static String DATABASE_NAME = "MonitoringSystemPIN.db";

    private static String TABLE_NAME = "system_PIN";
    private static String COL_ID = "_id";
    private static String COL_SYSTEM_PIN = "_PIN";
    public DatabaseHelperPIN(Context context){
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME
                + " ("+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_SYSTEM_PIN + " TEXT);" ;
        db.execSQL(query);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void createPIN(String PIN){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SYSTEM_PIN, PIN);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "PIN insertion Failed");
        }else{ Log.e("Data Progress", "PIN insertion Success");}
    }

    public void changePIN(String PIN){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SYSTEM_PIN, PIN);

        long result = db.update(TABLE_NAME, contentValues, COL_ID+"=?", new String[]{"1"});
        if(result == -1){
            Log.e("Data Progress", "Data update Failed");
        }else{ Log.e("Data Progress", "Data update Success");}

    }

    public boolean PINIsCorrect(String PIN){
        String query = "SELECT " + COL_SYSTEM_PIN + " FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isCorrect = false;

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve the PIN from the current row
                    int colIndex = cursor.getColumnIndex(COL_SYSTEM_PIN);
                    String databasePIN = cursor.getString(colIndex);

                    // Compare the entered PIN with the database PIN
                    if (PIN.equals(databasePIN)) {
                        isCorrect = true;
                        break;  // Exit the loop if a match is found
                    }
                } while (cursor.moveToNext());
            }

            if (cursor != null) {
                cursor.close();
            }
        }

        return isCorrect;
    }

}

