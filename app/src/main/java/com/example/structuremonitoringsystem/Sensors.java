package com.example.structuremonitoringsystem;

import android.bluetooth.BluetoothSocket;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.Arduino.HC05Bluetooth;
import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseSensor;
import com.example.structuremonitoringsystem.MPAndroidLineChart.XYZGraphs;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.example.structuremonitoringsystem.RecyclerView.ShowItemList;
import com.google.android.material.navigation.NavigationView;

public class Sensors extends AppCompatActivity {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    BluetoothConnection bluetoothConnection;
    BluetoothSocket bluetoothSocket;
    HC05Bluetooth hc05Bluetooth;
    RecyclerView recyclerView;
    int numOfDevice = 0; // Declare outside onCreate to access it throughout the activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sensors);

        DatabaseLogging databaseLogging = new DatabaseLogging(this);
        DatabaseSensor sensor = new DatabaseSensor(this);

        XYZGraphs xyzGraphs = new XYZGraphs(this, Sensors.this);
        xyzGraphs.startCollectingData(databaseLogging);

        bluetoothConnection = new BluetoothConnection();
        hc05Bluetooth = new HC05Bluetooth(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navbarView);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);

        setSupportActionBar(toolbar);

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);

//        bluetoothSocket = bluetoothConnection.bluetoothCn(this, new DatabaseDefaultSettings(this).getDefaultMac());
//
//        int x = hc05Bluetooth.getNumOfDevice(bluetoothSocket);
//        Log.e("Number of Device", "" + x);

        ShowItemList showItemList = new ShowItemList(Sensors.this);
        showItemList.showSensor(recyclerView, "device1", "fuck you");

    }
}
