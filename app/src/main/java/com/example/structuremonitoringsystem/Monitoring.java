package com.example.structuremonitoringsystem;

import android.app.AlertDialog;
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

import com.example.structuremonitoringsystem.Item.DeviceItem;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.MPAndroidLineChart.XYZGraphs;
import com.example.structuremonitoringsystem.OpenGL.OpenGLView;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.example.structuremonitoringsystem.OtherFunction.Popups;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.navigation.NavigationView;

public class Monitoring extends AppCompatActivity {

    NavigationView navigationView;
    LineChart realtimeChartX, realtimeChartY, realtimeChartZ;
    OpenGLView openGLView;
    DatabaseObjectSize objectSize;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_monitoring);

        Popups popups = new Popups(this);
        AlertDialog dialog = popups.loadingScreen();
        dialog.show();
        dialog.dismiss();

        DatabaseLogging databaseLogging = new DatabaseLogging(this);
        float width = 0;
        float height = 0;
        float thickness= 0;

        XYZGraphs xyzGraphs = new XYZGraphs(this, Monitoring.this);
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

        openGLView = findViewById(R.id.openGLView);
        openGLView.init(this, width,height,thickness);
        openGLView.rotateObject(1,1,1,1,2);
        realtimeChartX = (LineChart) findViewById(R.id.realtimeSeismographX);
        realtimeChartY = (LineChart) findViewById(R.id.realtimeSeismographY);
        realtimeChartZ = (LineChart) findViewById(R.id.realtimeSeismographZ);

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String pos = intent.getStringExtra("pos");

        xyzGraphs.realtimeGraph(openGLView, realtimeChartX, realtimeChartY, realtimeChartZ, pos, databaseLogging);

    }
}