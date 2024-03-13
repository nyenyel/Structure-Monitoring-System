package com.example.structuremonitoringsystem.OtherFunction;

import static androidx.core.content.ContextCompat.startActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.structuremonitoringsystem.Dashboard;
import com.example.structuremonitoringsystem.DriftMonitoring;
import com.example.structuremonitoringsystem.Manual;
import com.example.structuremonitoringsystem.ObjectTemplate;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.RealtimeMonitoring;
import com.example.structuremonitoringsystem.Sensors;
import com.example.structuremonitoringsystem.Settings;
import com.google.android.material.navigation.NavigationView;

public class NavigationBar {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    public NavigationBar(DrawerLayout drawerLayout, NavigationView navigationView, Toolbar toolbar) {
        this.drawerLayout = drawerLayout;
        this.navigationView = navigationView;
        this.toolbar = toolbar;
    }

    public void setNavbar(Activity activity) {
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        Menu menu = navigationView.getMenu();

        MenuItem dashboard, rtMonitoring, driftMonitoring, sensors, objectTemp, manual, settings;
        dashboard = menu.findItem(R.id.navDashboard);
        rtMonitoring = menu.findItem(R.id.navRealtimeMonitoring);
        driftMonitoring = menu.findItem(R.id.navDrift);
        sensors = menu.findItem(R.id.navSensor);
        objectTemp = menu.findItem(R.id.navObject);
        manual = menu.findItem(R.id.navManual);
        settings= menu.findItem(R.id.navSettings);

        dashboard.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                Intent intent = new Intent(activity, Dashboard.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        });
        rtMonitoring.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                Intent intent = new Intent(activity, RealtimeMonitoring.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        });
        driftMonitoring.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                Intent intent = new Intent(activity, DriftMonitoring.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        });
        sensors.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                Intent intent = new Intent(activity, Sensors.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        });
        objectTemp.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                Intent intent = new Intent(activity, ObjectTemplate.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        });
        manual.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                Intent intent = new Intent(activity, Manual.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        });
        settings.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {

                Intent intent = new Intent(activity, Settings.class);
                activity.startActivity(intent);
                activity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                return true;
            }
        });
    }
}
