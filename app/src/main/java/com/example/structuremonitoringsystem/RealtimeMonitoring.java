package com.example.structuremonitoringsystem;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.google.android.material.navigation.NavigationView;

public class RealtimeMonitoring extends AppCompatActivity {

    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime_monitoring);

        navigationView = (NavigationView) findViewById(R.id.navbarView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);
    }
}