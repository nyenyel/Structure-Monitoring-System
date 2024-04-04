package com.example.structuremonitoringsystem;

import android.content.Intent;
import android.database.Cursor;
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

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseSensor;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.example.structuremonitoringsystem.RecyclerView.ShowItemList;
import com.google.android.material.navigation.NavigationView;

public class RTMonitoring extends AppCompatActivity {

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView recyclerView;
    DatabaseSensor databaseSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_rtmonitoring);

        databaseSensor = new DatabaseSensor(this);
        Intent intent = getIntent();
        String sensorListID = intent.getStringExtra("idList");
        String log = intent.getStringExtra("key");
        Cursor cursor = databaseSensor.getSensorsOnDevice(sensorListID);

        navigationView = (NavigationView) findViewById(R.id.navbarView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);

        ShowItemList showItemList = new ShowItemList(RTMonitoring.this);
        showItemList.showSensor(recyclerView, sensorListID, log);
    }
}