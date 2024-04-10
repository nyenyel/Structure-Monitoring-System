package com.example.structuremonitoringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.MPAndroidLineChart.XYZGraphs;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.google.android.material.navigation.NavigationView;

public class Manual extends AppCompatActivity {


    ImageView tutorial1,tutorial2,tutorial3,tutorial4,tutorial5,tutorial6,
            nextBtn;
    TextView section;
    private int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_manual);

        tutorial1 = findViewById(R.id.tutorial1);
        tutorial2 = findViewById(R.id.tutorial2);
        tutorial3 = findViewById(R.id.tutorial3);
        tutorial4 = findViewById(R.id.tutorial4);
        tutorial5 = findViewById(R.id.tutorial5);
        tutorial6 = findViewById(R.id.tutorial6);

        section = findViewById(R.id.section);
        nextBtn = findViewById(R.id.nextBtn);


        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++counter;
                if(counter == 2){
                    tutorial1.setVisibility(View.GONE);
                    tutorial2.setVisibility(View.VISIBLE);
                } else if (counter == 3) {
                    tutorial2.setVisibility(View.GONE);
                    tutorial3.setVisibility(View.VISIBLE);
                }
                else if (counter == 4) {
                    tutorial3.setVisibility(View.GONE);
                    tutorial4.setVisibility(View.VISIBLE);
                }
                else if (counter == 5) {
                    tutorial4.setVisibility(View.GONE);
                    tutorial5.setVisibility(View.VISIBLE);
                }
                else if (counter == 6) {
                    tutorial5.setVisibility(View.GONE);
                    tutorial6.setVisibility(View.VISIBLE);
                    section.setText("Bluetooth Setup");
                }else{
                    Intent intent = new Intent(Manual.this, RealtimeMonitoring.class);
                    startActivity(intent);
                }
            }
        });

    }
}