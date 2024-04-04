package com.example.structuremonitoringsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.MPAndroidLineChart.XYZGraphs;
import com.example.structuremonitoringsystem.OpenGL.OpenGLView;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class DataLogs extends AppCompatActivity {

    NavigationView navigationView;
    LineChart chartX, chartY, chartZ;
    DatabaseObjectSize objectSize;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    DatabaseLogging logs;
    OpenGLView openGLView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_data_logs);

        XYZGraphs xyzGraphs = new XYZGraphs(this, DataLogs.this);
        DatabaseLogging databaseLogging = new DatabaseLogging(this);
        float width = 0;
        float height = 0;
        float thickness= 0;


        objectSize = new DatabaseObjectSize(this);
        Cursor cursor = objectSize.getDefaultObjectSize();
        try {
            if (cursor != null && cursor.moveToFirst()){
                do {

                    width = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow("_width")));
                    height = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow("_height")));
                    thickness = Float.parseFloat(cursor.getString(cursor.getColumnIndexOrThrow("_thickness")));

                }while (cursor.moveToNext());
            }
        }finally {
            if (cursor != null){cursor.close();}
        }


        navigationView = (NavigationView) findViewById(R.id.navbarView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        chartX = (LineChart) findViewById(R.id.seismographX);
        chartY = (LineChart) findViewById(R.id.seismographY);
        chartZ = (LineChart) findViewById(R.id.seismographZ);

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);

        Intent intent = getIntent();

        String sensor = intent.getStringExtra("sensor");
        String pos = intent.getStringExtra("pos");

        Log.e("Debug", sensor +" : "+pos);

        xyzGraphs.plotDataLogs(chartX, chartY, chartZ, pos, databaseLogging, sensor);
    }
}