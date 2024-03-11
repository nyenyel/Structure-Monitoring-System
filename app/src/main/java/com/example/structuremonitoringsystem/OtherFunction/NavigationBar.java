package com.example.structuremonitoringsystem.OtherFunction;

import android.app.Activity;
import android.content.Context;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.structuremonitoringsystem.R;
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
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }
}
