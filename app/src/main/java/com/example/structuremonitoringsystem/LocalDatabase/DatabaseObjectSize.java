package com.example.structuremonitoringsystem.LocalDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseObjectSize extends SQLiteOpenHelper {
    private Context context;

    private static String DATABASE_NAME = "MonitoringSystem.db";

    private static String TABLE_NAME = "object_template";
    private static String COL_ID = "_id";
    private static String COL_TEMPLATE_NAME = "template_name";
    private static String COL_TEMPLATE_WIDTH = "_width";
    private static String COL_TEMPLATE_HEIGHT = "_height";
    private static String COL_TEMPLATE_THICKNESS = "_thickness";
    private static String COL_DEFAULT = "_default";
    public DatabaseObjectSize(Context context){
        super(context, DATABASE_NAME, null,1);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME
                + " ("+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TEMPLATE_NAME + " TEXT, "
                + COL_TEMPLATE_HEIGHT + " REAL, "
                + COL_TEMPLATE_WIDTH + " REAL, "
                + COL_TEMPLATE_THICKNESS + " REAL, "
                + COL_DEFAULT + " INTEGER DEFAULT 0);";
        db.execSQL(query);

        // Set the value of default column to 1 when id is 1
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_TEMPLATE_NAME, "Sample");
        contentValues.put(COL_TEMPLATE_WIDTH, "50");
        contentValues.put(COL_TEMPLATE_HEIGHT, "50");
        contentValues.put(COL_TEMPLATE_THICKNESS, "50");
        contentValues.put(COL_DEFAULT, 1);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "Data insertion Failed");
        }else{ Log.e("Data Progress", "Data insertion Success");}
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addTemplate(String templateName, float width, float height, float thickness){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_TEMPLATE_NAME, templateName);
        contentValues.put(COL_TEMPLATE_WIDTH, width);
        contentValues.put(COL_TEMPLATE_HEIGHT, height);
        contentValues.put(COL_TEMPLATE_THICKNESS, thickness);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if(result == -1){
            Log.e("Data Progress", "Data insertion Failed");
        }else{ Log.e("Data Progress", "Data insertion Success");}
    }

    public void updateTemplateData(String id ,String templateName, float width, float height, float thickness){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_TEMPLATE_NAME, templateName);
        contentValues.put(COL_TEMPLATE_WIDTH, width);
        contentValues.put(COL_TEMPLATE_HEIGHT, height);
        contentValues.put(COL_TEMPLATE_THICKNESS, thickness);

        long result = db.update(TABLE_NAME, contentValues, COL_ID+"=?", new String[]{id});
        if(result == -1){
            Log.e("Data Progress", "Data update Failed");
        }else{ Log.e("Data Progress", "Data update Success");}

    }
    public void deleteTemplate(String id){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME, COL_ID+"=?", new String[]{id});
        if(result == -1){
            Log.e("Data Progress", "Data Delete Failed");
        }else{ Log.e("Data Progress", "Data Delete Success");}

    }
    public Cursor readAllData(){
        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getSpecificDataById(String id){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor getDefaultObjectSize(){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_DEFAULT + " = " + 1;

        if (db != null){cursor = db.rawQuery(query, null);}

        return cursor;
    }

    public void changeDefaultObjectSize(String oldDefault, String newDefault){
        updateDefaultValues(newDefault, 1);
        updateDefaultValues(oldDefault, 0);
    }

    private void updateDefaultValues(String id, int isDefault){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_DEFAULT, isDefault);

        long result = db.update(TABLE_NAME, contentValues, COL_ID+"=?", new String[]{id});
        if(result == -1){
            Log.e("Data Progress", "Data update Failed");
        }else{ Log.e("Data Progress", "Data update Success");}
    }
}
