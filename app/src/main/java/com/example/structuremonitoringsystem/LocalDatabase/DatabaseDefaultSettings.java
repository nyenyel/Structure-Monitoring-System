package com.example.structuremonitoringsystem.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.structuremonitoringsystem.OtherFunction.GlobalVariable;

public class DatabaseDefaultSettings extends SQLiteOpenHelper {
    private Context context;

    private static String DATABASE_NAME = "Settings.db";

    private static String TABLE_NAME = "system_setting";
    private static String COL_ID = "setting_name";
    private static String COL_VALUE = "_value";
    private static String ID_NAME_PIN = "pin";
    public static String ID_NAME_LOGGING = "logging";
    public static String ID_NAME_BEHAVIOR = "behavior";
    public static String ID_NAME_MAC = "mac";
    public static String ID_THRESHOLD= "threshold";
    public static String ID_IS_INITIAL_RUN= "initial";

    public DatabaseDefaultSettings(Context context){
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME
                + " ("+ COL_ID + " TEXT, "
                + COL_VALUE + " TEXT);" ;
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

        contentValues.put(COL_VALUE, PIN);
        contentValues.put(COL_ID, ID_NAME_PIN);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "PIN insertion Failed");
        }else{ Log.e("Data Progress", "PIN insertion Success");}
    }

    public void changePIN(String PIN){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_VALUE, PIN);

        long result = db.update(TABLE_NAME, contentValues, COL_ID+"=?", new String[]{ID_NAME_PIN});
        if(result == -1){
            Log.e("Data Progress", "Data update Failed");
        }else{ Log.e("Data Progress", "Data update Success");}
    }

    public boolean PINIsCorrect(String PIN){
        String query = "SELECT * " + " FROM " + TABLE_NAME + " WHERE " + COL_ID + "=" + "\""+ID_NAME_PIN+"\"";
        SQLiteDatabase db = this.getReadableDatabase();
        boolean isCorrect = false;

        Cursor cursor = null;

        if (db != null) {
            cursor = db.rawQuery(query, null);

            if (cursor != null && cursor.moveToFirst()) {

                do {
                    // Retrieve the PIN from the current row
                    int colIndex = cursor.getColumnIndex(COL_VALUE);
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

    public void setLoggingType(String logType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, ID_NAME_BEHAVIOR);
        contentValues.put(COL_VALUE, logType);


        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "Logging Type insertion Failed");
        }else{ Log.e("Data Progress", "Logging Type insertion Success");}
    }


    public String getLoggingType(){
        String logginType ="";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " +"\""+ ID_NAME_LOGGING+"\"";

        if (db != null){cursor = db.rawQuery(query, null);}
        try {
            if (cursor != null && cursor.moveToFirst()){
                do {
                    logginType = cursor.getString(cursor.getColumnIndexOrThrow(COL_VALUE));
                }while (cursor.moveToNext());
            }
        }finally {
            if (cursor != null){cursor.close();}
        }
        
        return logginType;
    }

    public void setLoggingBehavior(String behavior){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, ID_NAME_LOGGING);
        contentValues.put(COL_VALUE, behavior);


        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "Logging Behavior insertion Failed");
        }else{ Log.e("Data Progress", "Logging Behavior insertion Success");}
    }

    public String getLoggingBehavior(){
        String logginBehavior ="";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + "\"" +ID_NAME_BEHAVIOR+ "\"";

        if (db != null){cursor = db.rawQuery(query, null);}
        try {
            if (cursor != null && cursor.moveToFirst()){
                do {
                    logginBehavior = cursor.getString(cursor.getColumnIndexOrThrow(COL_VALUE));
                }while (cursor.moveToNext());
            }
        }finally {
            if (cursor != null){cursor.close();}
        }

        return logginBehavior;
    }

    public void setDefaultBluetoothMac(String mac){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, ID_NAME_MAC);
        contentValues.put(COL_VALUE, mac);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "Bluetooth insertion Failed");
        }else{ Log.e("Data Progress", "Bluetooth insertion Success");}
    }

    public String getDefaultMac(){
        String defaultMac ="";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + "\""+ID_NAME_MAC+"\"";

        if (db != null){cursor = db.rawQuery(query, null);}
        try {
            if (cursor != null && cursor.moveToFirst()){
                do {
                    defaultMac = cursor.getString(cursor.getColumnIndexOrThrow(COL_VALUE));
                }while (cursor.moveToNext());
            }
        }finally {
            if (cursor != null){cursor.close();}
        }

        return defaultMac;
    }

    public void updateLoggingType(String logType){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_VALUE, logType);

        int rowsAffected = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{ID_NAME_LOGGING});

        if (rowsAffected == 0) {
            Log.e("Data Progress", "Logging Type update Failed");
        } else {
            Log.e("Data Progress", "Logging Type update Success");
        }
    }

    public void updateLoggingBehavior(String logBehavior){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_VALUE, logBehavior);

        int rowsAffected = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{ID_NAME_BEHAVIOR});

        if (rowsAffected == 0) {
            Log.e("Data Progress", "Logging Type update Failed");
        } else {
            Log.e("Data Progress", "Logging Type update Success");
        }

    }
    public void updateDefaultMac(String mac){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_VALUE, mac);

        int rowsAffected = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{ID_NAME_MAC});

        if (rowsAffected == 0) {
            Log.e("Data Progress", "Logging Type update Failed");
        } else {
            Log.e("Data Progress", "Logging Type update Success");
        }
    }

    public void setInitial(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, ID_IS_INITIAL_RUN);
        contentValues.put(COL_VALUE, "0");

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "Bluetooth insertion Failed");
        }else{ Log.e("Data Progress", "Bluetooth insertion Success");}
    }

    public String getInitial(){
        String initialVal ="";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + "\""+ID_IS_INITIAL_RUN+"\"";

        if (db != null){cursor = db.rawQuery(query, null);}
        try {
            if (cursor != null && cursor.moveToFirst()){
                do {
                    initialVal = cursor.getString(cursor.getColumnIndexOrThrow(COL_VALUE));
                }while (cursor.moveToNext());
            }
        }finally {
            if (cursor != null){cursor.close();}
        }

        return initialVal;
    }

    public void updateInitial(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_VALUE, "1");

        int rowsAffected = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{ID_IS_INITIAL_RUN});

        if (rowsAffected == 0) {
            Log.e("Data Progress", "Logging Type update Failed");
        } else {
            Log.e("Data Progress", "Logging Type update Success");
        }

    }


    public void setThreshold(String threshold){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_ID, ID_THRESHOLD);
        contentValues.put(COL_VALUE, threshold);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "Bluetooth insertion Failed");
        }else{ Log.e("Data Progress", "Bluetooth insertion Success");}
    }

    public String getThreshold(){
        String defaultThreshold ="";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + "\""+ID_THRESHOLD+"\"";

        if (db != null){cursor = db.rawQuery(query, null);}
        try {
            if (cursor != null && cursor.moveToFirst()){
                do {
                    defaultThreshold = cursor.getString(cursor.getColumnIndexOrThrow(COL_VALUE));
                }while (cursor.moveToNext());
            }
        }finally {
            if (cursor != null){cursor.close();}
        }

        return defaultThreshold;
    }

    public void updateThreshold(String threshold){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_VALUE, threshold);

        int rowsAffected = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{ID_THRESHOLD});

        if (rowsAffected == 0) {
            Log.e("Data Progress", "Logging Type update Failed");
        } else {
            Log.e("Data Progress", "Logging Type update Success");
        }

    }
    public void seedDefaultData(){
        GlobalVariable globalVariable = new GlobalVariable();
        String[] behavior = globalVariable.getLoggingBehavior();
        String[] type = globalVariable.getLoggingType();

        createPIN("0000");
        setLoggingBehavior(behavior[0]);
        setLoggingType(type[0]);
        setDefaultBluetoothMac("na");
        setThreshold("1");
    }

    public void resetToDefaultData(){
        GlobalVariable globalVariable = new GlobalVariable();
        String[] behavior = globalVariable.getLoggingBehavior();
        String[] type = globalVariable.getLoggingType();

        changePIN("0000");
        updateLoggingBehavior(behavior[0]);
        updateLoggingType(type[0]);
        updateDefaultMac("na");
        updateThreshold("1");
    }

}

