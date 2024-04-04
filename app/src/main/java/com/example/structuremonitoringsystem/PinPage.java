package com.example.structuremonitoringsystem;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.MPAndroidLineChart.XYZGraphs;
import com.example.structuremonitoringsystem.OtherFunction.PINActionListener;
import com.example.structuremonitoringsystem.OtherFunction.Popups;

public class PinPage extends AppCompatActivity {
    AppCompatButton oneBtn, twoBtn, threeBtn,fourBtn,fiveBtn,sixBtn,sevenBtn,eightBtn,nineBtn,zeroBtn,backspaceBtn;
    Popups popups;
    View view;
    PINActionListener pinAL;
    TextView instruction;
    ImageView circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pin_page);

        DatabaseLogging databaseLogging = new DatabaseLogging(this);

        XYZGraphs xyzGraphs = new XYZGraphs(this, PinPage.this);
        xyzGraphs.startCollectingData(databaseLogging);

        view = getWindow().getDecorView();
        popups = new Popups(this);
        pinAL = new PINActionListener(PinPage.this,view,69);

        oneBtn = findViewById(R.id.one);
        twoBtn = findViewById(R.id.two);
        threeBtn = findViewById(R.id.three);
        fourBtn = findViewById(R.id.four);
        fiveBtn = findViewById(R.id.five);
        sixBtn = findViewById(R.id.six);
        sevenBtn = findViewById(R.id.seven);
        eightBtn = findViewById(R.id.eight);
        nineBtn = findViewById(R.id.nine);
        zeroBtn = findViewById(R.id.zero);
        backspaceBtn = findViewById(R.id.backspace);

        oneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("1");


            }
        });

        twoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("2");

            }
        });

        threeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("3");

            }
        });

        fourBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("4");

            }
        });

        fiveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("5");

            }
        });

        sixBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("6");

            }
        });

        sevenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("7");

            }
        });

        eightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("8");
            }
        });

        nineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("9");

            }
        });

        zeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.VISIBLE);
                pinAL.createPIN("0");

            }
        });

        backspaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pinAL.backspace();
                circle = pinAL.getCircleToFill(pinAL.getCurrentPIN());
                pinAL.fillVisibility(circle, View.INVISIBLE);

            }
        });
    }
}