package com.example.structuremonitoringsystem;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
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
import com.example.structuremonitoringsystem.OtherFunction.Popups;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DataLogs extends AppCompatActivity {

    NavigationView navigationView;
    LineChart chartX, chartY, chartZ;
    DatabaseObjectSize objectSize;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    DatabaseLogging logs;
    OpenGLView openGLView;
    AppCompatButton applyBtn;
    AppCompatRadioButton allRd, dailyRd, weeklyRd, monthlyRd;
    RelativeLayout allBtn, dailyBtn, weeklyBtn, monthlyBtn,
                dailyHolder, weeklyHolder, monthlyHolder,
                initialState, holderHolder,
                dayInpHolderD, monthInpHolderD,
                weekInpHolderW, monthInpHolderW,
                monthInpHolderM;
    TextView dayInpD, monthInpD,
            weekInpW, monthInpW,
            monthInpM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_data_logs);

        XYZGraphs xyzGraphs = new XYZGraphs(this, DataLogs.this);
        DatabaseLogging databaseLogging = new DatabaseLogging(this);
        Popups popups = new Popups(this);
        float width = 0;
        float height = 0;
        float thickness= 0;


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

        allRd = findViewById(R.id.allRdBtn);
        dailyRd= findViewById(R.id.dailyRdBtn);
        weeklyRd = findViewById(R.id.weeklyRdBtn);
        monthlyRd = findViewById(R.id.monthlyRdBtn);

        allBtn = findViewById(R.id.allBtn);
        dailyBtn= findViewById(R.id.dailyBtn);
        weeklyBtn = findViewById(R.id.weeklyBtn);
        monthlyBtn = findViewById(R.id.monthlyBtn);

        dailyHolder = findViewById(R.id.dailyHolder);
        monthlyHolder = findViewById(R.id.monthlyHolder);
        weeklyHolder = findViewById(R.id.weeklyHolder);

        initialState = findViewById(R.id.initialState);
        holderHolder = findViewById(R.id.holderHolder);

        applyBtn = findViewById(R.id.applyBtn);

        dayInpHolderD = findViewById(R.id.dayInpHolderD);
        monthInpHolderD = findViewById(R.id.monthInpHolderD);

        weekInpHolderW = findViewById(R.id.weekInpHolderW);
        monthInpHolderW = findViewById(R.id.monthInpHolderW);

        monthInpHolderM = findViewById(R.id.monthInpHolderM);

        dayInpD = findViewById(R.id.dayInpD);
        monthInpD = findViewById(R.id.monthInpD);

        weekInpW = findViewById(R.id.weekInpW);
        monthInpW = findViewById(R.id.monthInpW);

        monthInpM = findViewById(R.id.monthInpM);

        chartX = (LineChart) findViewById(R.id.seismographX);
        chartY = (LineChart) findViewById(R.id.seismographY);
        chartZ = (LineChart) findViewById(R.id.seismographZ);

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);

        Intent intent = getIntent();

        String sensor = intent.getStringExtra("sensor");
        String pos = intent.getStringExtra("pos");

        Log.e("Debug", sensor +" : "+pos);




        applyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String month = "";
                String dayWeek = "0";
                Cursor cursorPlot = null;
                if(allRd.isChecked()){
                    cursorPlot = databaseLogging.getAllData();
                }

                if(dailyRd.isChecked()){
                    month = convertMonthToInt(monthInpD.getText().toString());
                    dayWeek = dayInpD.getText().toString();
                    cursorPlot = databaseLogging.dailyLog(month, dayWeek);
                }

                if(weeklyRd.isChecked()){
                    month = convertMonthToInt(monthInpW.getText().toString());
                    dayWeek = convertWeekToInt(weekInpW.getText().toString());
                    cursorPlot = databaseLogging.weeklyLog(month, dayWeek);
                }

                if(monthlyRd.isChecked()){
                    month = convertMonthToInt(monthInpM.getText().toString());
                    cursorPlot = databaseLogging.monthlyLog(month);
                }
                Log.e("sample", month + " " + dayWeek);

                xyzGraphs.plotDataLogs(chartX, chartY, chartZ, pos, cursorPlot, sensor);
                holderHolder.setVisibility(View.GONE);
            }
        });
        dayInpD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popups.selectDay(new Popups.SensorSelectionListener() {
                    @Override
                    public void onSensorSelected(String sensor) {
                        dayInpD.setText(sensor);
                    }
                });
            }
        });
        monthInpD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popups.selectMonth(new Popups.SensorSelectionListener() {
                    @Override
                    public void onSensorSelected(String sensor) {
                        monthInpD.setText(sensor);
                    }
                });
            }
        });

        weekInpW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popups.selectWeek(new Popups.SensorSelectionListener() {
                    @Override
                    public void onSensorSelected(String sensor) {
                        weekInpW.setText(sensor);
                    }
                });
            }
        });
        monthInpW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popups.selectMonth(new Popups.SensorSelectionListener() {
                    @Override
                    public void onSensorSelected(String sensor) {
                        monthInpW.setText(sensor);
                    }
                });
            }
        });

        monthInpM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popups.selectMonth(new Popups.SensorSelectionListener() {
                    @Override
                    public void onSensorSelected(String sensor) {
                        monthInpM.setText(sensor);
                    }
                });
            }
        });

        initialState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holderHolder.getVisibility() == View.GONE){
                    holderHolder.setVisibility(View.VISIBLE);
                } else if(holderHolder.getVisibility() == View.VISIBLE){
                    holderHolder.setVisibility(View.GONE);
                }
            }
        });
        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allRd.setChecked(true);
                dailyRd.setChecked(false);
                weeklyRd.setChecked(false);
                monthlyRd.setChecked(false);

                dailyHolder.setVisibility(View.GONE);
                weeklyHolder.setVisibility(View.GONE);
                monthlyHolder.setVisibility(View.GONE);
            }
        });

        dailyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allRd.setChecked(false);
                dailyRd.setChecked(true);
                weeklyRd.setChecked(false);
                monthlyRd.setChecked(false);

                dailyHolder.setVisibility(View.VISIBLE);
                weeklyHolder.setVisibility(View.GONE);
                monthlyHolder.setVisibility(View.GONE);
            }
        });
        weeklyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allRd.setChecked(false);
                dailyRd.setChecked(false);
                weeklyRd.setChecked(true);
                monthlyRd.setChecked(false);

                dailyHolder.setVisibility(View.GONE);
                weeklyHolder.setVisibility(View.VISIBLE);
                monthlyHolder.setVisibility(View.GONE);
            }
        });
        monthlyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allRd.setChecked(false);
                dailyRd.setChecked(false);
                weeklyRd.setChecked(false);
                monthlyRd.setChecked(true);

                dailyHolder.setVisibility(View.GONE);
                weeklyHolder.setVisibility(View.GONE);
                monthlyHolder.setVisibility(View.VISIBLE);
            }
        });
    }

    private String convertMonthToInt(String month) {
        int counter = 0;
        int temp = 0;
        String arr[] = new String[]{
                "JAN","FEB","MAR","APR",
                "MAY","JUN","JUL","AUG",
                "SEP","OCT","NOV","DEC"
        };

        for (String x : arr){
            if(x.equals(month)){
                temp = counter;
            }
            counter++;
        }
        return String.valueOf(temp +1);
    }

    private String convertWeekToInt(String week) {
        int counter = 0;
        int temp = 0;
        String arr[] = new String[]{
                "Week 1","Week 2","Week 3","Week 4","Week 5"
        };

        for (String x : arr){
            if(x.equals(week)){
                temp = counter;
            }
            counter++;
        }
        return String.valueOf(temp +1);
    }
}