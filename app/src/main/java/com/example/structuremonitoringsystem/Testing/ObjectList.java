package com.example.structuremonitoringsystem.Testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseHelper;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.ObjectAdapter;
import com.example.structuremonitoringsystem.RecyclerView.Item.ObjectItem;

import java.util.ArrayList;
import java.util.List;

public class ObjectList extends AppCompatActivity {

    private RecyclerView recyclerView;

    private AppCompatButton createObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_object_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        createObject = (AppCompatButton) findViewById(R.id.createObj);

        List<ObjectItem> objectItems= new ArrayList<ObjectItem>();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        databaseHelper.changeDefaultObjectSize("3","2");
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

        createObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ObjectList.this, CreateObject.class);
                startActivity(intent);
            }
        });

    }
}