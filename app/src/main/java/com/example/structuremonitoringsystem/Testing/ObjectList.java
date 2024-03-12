package com.example.structuremonitoringsystem.Testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseHelper;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.BluetoothAdapter;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.ObjectAdapter;
import com.example.structuremonitoringsystem.RecyclerView.Item.ObjectItem;

import java.util.ArrayList;
import java.util.List;

public class ObjectList extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        List<ObjectItem> objectItems= new ArrayList<ObjectItem>();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        Cursor objectList = databaseHelper.readAllData();
        try {
            if (objectList != null && objectList.moveToFirst()){
                do {
                    String id = objectList.getString(objectList.getColumnIndexOrThrow("_id"));
                    String name = objectList.getString(objectList.getColumnIndexOrThrow("template_name"));
                    String width = objectList.getString(objectList.getColumnIndexOrThrow("_width"));
                    String height = objectList.getString(objectList.getColumnIndexOrThrow("_height"));
                    String thickness = objectList.getString(objectList.getColumnIndexOrThrow("_thickness"));

                    ObjectItem currentTemplate = new ObjectItem(width, height, thickness, name, id);
                    objectItems.add(currentTemplate);
                }while (objectList.moveToNext());
            }
        }finally {
            if (objectList != null){objectList.close();}
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(ObjectList.this));
        recyclerView.setAdapter(new ObjectAdapter(getApplicationContext(), objectItems));


    }
}