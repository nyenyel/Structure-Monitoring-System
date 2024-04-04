package com.example.structuremonitoringsystem;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.MPAndroidLineChart.XYZGraphs;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.example.structuremonitoringsystem.RecyclerView.ShowItemList;
import com.google.android.material.navigation.NavigationView;

public class RealtimeMonitoring extends AppCompatActivity {

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_realtime_monitoring);

        navigationView = (NavigationView) findViewById(R.id.navbarView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);

        ShowItemList showItemList = new ShowItemList(this);
        showItemList.showRTDevices(recyclerView);

        DatabaseLogging databaseLogging = new DatabaseLogging(this);

        XYZGraphs xyzGraphs = new XYZGraphs(this, RealtimeMonitoring.this);
        xyzGraphs.startCollectingData(databaseLogging);

    }
}