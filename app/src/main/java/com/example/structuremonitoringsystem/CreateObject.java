package com.example.structuremonitoringsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.OpenGL.OpenGLView;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.example.structuremonitoringsystem.Testing.MainActivity;
import com.google.android.material.navigation.NavigationView;

public class CreateObject extends AppCompatActivity {

    DrawerLayout drawerLayout;

    Context context;
    NavigationView navigationView;
    Toolbar toolbar;
    DatabaseObjectSize objectSize;
    EditText tempName, height, width, thickness;
    AppCompatButton showBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_object);

        objectSize = new DatabaseObjectSize(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navbarView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tempName = findViewById(R.id.tempName);
        width = findViewById(R.id.width);
        thickness = findViewById(R.id.thickness);
        height = findViewById(R.id.height);

        showBtn = findViewById(R.id.showBtn);

        setSupportActionBar(toolbar);
        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView,toolbar);
        navigationBar.setNavbar(this);

        final boolean[] rendererInitialized = {false}; // Add this flag
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name;
                float w, h, t;
                w = Float.parseFloat(width.getText().toString());
                h = Float.parseFloat(height.getText().toString());
                t = Float.parseFloat(thickness.getText().toString());
                name = tempName.getText().toString();

                objectSize.addTemplate(name, w,h,t);
                finish();

            }
        });

    }
}