package com.example.structuremonitoringsystem;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.Excel.ExportDataToExcel;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.example.structuremonitoringsystem.RecyclerView.ShowItemList;
import com.google.android.material.navigation.NavigationView;

public class ConvertToExcel extends AppCompatActivity {
    NavigationView navigationView;

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RelativeLayout saveBtn, clearBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_convert_to_excel);
        ExportDataToExcel excel = new ExportDataToExcel(this);
        DatabaseLogging logging = new DatabaseLogging(this);

        navigationView = (NavigationView) findViewById(R.id.navbarView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);

        saveBtn = findViewById(R.id.saveBtn);
        clearBtn = findViewById(R.id.clearBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor data =  logging.getAllData();
                excel.exportData(data, 0);
            }
        });

    }
}