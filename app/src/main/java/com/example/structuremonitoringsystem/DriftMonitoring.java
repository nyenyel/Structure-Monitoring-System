package com.example.structuremonitoringsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
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
import com.example.structuremonitoringsystem.OtherFunction.Notification;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.navigation.NavigationView;

public class DriftMonitoring extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    AppCompatButton notif;
    RelativeLayout hide;
    LineChart realtimeChartX1, realtimeChartY1, realtimeChartZ1,
            realtimeChartX, realtimeChartY, realtimeChartZ;
    OpenGLView openGLViewD1, openGLViewD2;
    DatabaseObjectSize objectSize;
    Toolbar toolbar;
    ImageView arrowDown;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_drift_monitoring);


        DatabaseLogging databaseLogging = new DatabaseLogging(this);
        float width = 0;
        float height = 0;
        float thickness= 0;
        XYZGraphs xyzGraphs = new XYZGraphs(this, DriftMonitoring.this);
        Notification notification = new Notification(DriftMonitoring.this, this);
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
        hide = (RelativeLayout) findViewById(R.id.hideBtn);
        arrowDown = (ImageView) findViewById(R.id.arrowDown);
        notif = (AppCompatButton) findViewById(R.id.notif);

        openGLViewD1 = findViewById(R.id.openGLViewD1);
        openGLViewD1.init(this, width,height,thickness);
        openGLViewD1.rotateObject(1,1,1,1,2);

        openGLViewD2 = findViewById(R.id.openGLViewD2);
        openGLViewD2.init(this, width,height,thickness);
        openGLViewD2.rotateObject(1,1,1,1,2);

        realtimeChartX1 = (LineChart) findViewById(R.id.realtimeSeismographX1);
        realtimeChartY1 = (LineChart) findViewById(R.id.realtimeSeismographY1);
        realtimeChartZ1 = (LineChart) findViewById(R.id.realtimeSeismographZ1);

        realtimeChartX = (LineChart) findViewById(R.id.realtimeSeismographX);
        realtimeChartY = (LineChart) findViewById(R.id.realtimeSeismographY);
        realtimeChartZ = (LineChart) findViewById(R.id.realtimeSeismographZ);

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String pos1 = "0,1,2";
        String pos2 = "3,4,5";

        xyzGraphs.realtimeGraph(openGLViewD1, realtimeChartX1, realtimeChartY1, realtimeChartZ1, pos1,
                                openGLViewD2, realtimeChartX, realtimeChartY, realtimeChartZ, pos2,
                                databaseLogging);
        hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(openGLViewD1.getVisibility() == View.VISIBLE && openGLViewD2.getVisibility() == View.VISIBLE ){
                    openGLViewD1.setVisibility(View.GONE);
                    openGLViewD2.setVisibility(View.GONE);
                    arrowDown.setRotation(0);
                }else{
                    openGLViewD1.setVisibility(View.VISIBLE);
                    openGLViewD2.setVisibility(View.VISIBLE);
                    arrowDown.setRotation(180);
                }

            }
        });
        notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notification.notifyDrift("69");
            }
        });
    }
}