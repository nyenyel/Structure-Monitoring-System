package com.example.structuremonitoringsystem;

import android.app.Dialog;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.MPAndroidLineChart.XYZGraphs;
import com.example.structuremonitoringsystem.OtherFunction.GlobalVariable;
import com.example.structuremonitoringsystem.OtherFunction.NavigationBar;
import com.example.structuremonitoringsystem.OtherFunction.Popups;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.BluetoothAdapter;
import com.google.android.material.navigation.NavigationView;

public class Settings extends AppCompatActivity {

    NavigationView navigationView;
    GlobalVariable globalVariable = new GlobalVariable();
    Toolbar toolbar;
    View decorView;
    Popups popups;
    Dialog dialog;
    DrawerLayout drawerLayout;
    TextView runningTxt, monitoringTxt, notRunningTxt,
            allDataTxt, perDeviceTxt, perSensorTxt,
            btMacTxt, resetBtn, templateNameTxt;
    RelativeLayout appIsRunningBtn, whileMonitoringBtn, appIsNotRunningBtn,
            allBehaviorBtn, perDeviceBehaviorBtn, perSensorBehaviorBtn,
            defaultMacBtn, defaultTemplateBtn, changePinBtn;
    LinearLayout dataBehavior;
    private String behavior = "";
    private String type = "";
    private String mac = "";
    private String tempName = "";
    AppCompatButton saveBtn;
    AppCompatRadioButton appIsRunningRdBtn, whileMonitoringRdBtn, appIsNotRunningRdBtn,
            allBehaviorRdBtn, perDeviceBehaviorRdBtn, perSensorBehaviorRdBtn;
    DatabaseDefaultSettings defaultSettings;
    DatabaseObjectSize objectSize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_settings);

        BluetoothManager bluetoothManager = getSystemService(BluetoothManager.class);
        android.bluetooth.BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

        decorView = getWindow().getDecorView();
        globalVariable.hideSystemBars(decorView);

        DatabaseLogging databaseLogging = new DatabaseLogging(this);

        XYZGraphs xyzGraphs = new XYZGraphs(this, Settings.this);
        xyzGraphs.startCollectingData(databaseLogging);

        defaultSettings = new DatabaseDefaultSettings(this);
        objectSize = new DatabaseObjectSize(this);
        popups = new Popups(this);

        navigationView = (NavigationView) findViewById(R.id.navbarView);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        behavior = defaultSettings.getLoggingBehavior();
        type = defaultSettings.getLoggingType();
        mac = defaultSettings.getDefaultMac();

        dataBehavior = findViewById(R.id.dataBehavior);

        saveBtn = findViewById(R.id.saveBtn);
        resetBtn = findViewById(R.id.resetSettingsBtn);

        appIsRunningBtn = findViewById(R.id.appIsRunningBtn);
        whileMonitoringBtn = findViewById(R.id.whileMonitoringBtn);
        appIsNotRunningBtn = findViewById(R.id.appIsNotRunningBtn);

        runningTxt = findViewById(R.id.runningTxt);
        monitoringTxt = findViewById(R.id.monitoringTxt);
        notRunningTxt = findViewById(R.id.notRunningTxt);
        allDataTxt = findViewById(R.id.allDataTxt);
        perSensorTxt = findViewById(R.id.perSensorTxt);
        perDeviceTxt = findViewById(R.id.perDeviceTxt);
        btMacTxt = findViewById(R.id.btMacAddress);
        templateNameTxt = findViewById(R.id.templateName);

        appIsRunningRdBtn = findViewById(R.id.appIsRunningRdBtn);
        whileMonitoringRdBtn = findViewById(R.id.whileMonitoringRdBtn);
        appIsNotRunningRdBtn = findViewById(R.id.appIsNotRunningRdBtn);

        allBehaviorBtn = findViewById(R.id.allBehaviorBtn);
        perDeviceBehaviorBtn = findViewById(R.id.perDeviceBehaviorBtn);
        perSensorBehaviorBtn = findViewById(R.id.perSensorBehaviorBtn);

        allBehaviorRdBtn = findViewById(R.id.allBehaviorRdBtn);
        perDeviceBehaviorRdBtn = findViewById(R.id.perDeviceBehaviorRdBtn);
        perSensorBehaviorRdBtn = findViewById(R.id.perSensorBehaviorRdBtn);

        defaultMacBtn = findViewById(R.id.defaultMacBtn);
        defaultTemplateBtn = findViewById(R.id.defaultTemplateBtn);
        changePinBtn = findViewById(R.id.changePINBtn);

        String tempMon = monitoringTxt.getText().toString();
        String tempRunning = runningTxt.getText().toString();;
        String tempNotRunning = notRunningTxt.getText().toString();
        String tempAllData = allDataTxt.getText().toString();
        String tempPerDevice = perDeviceTxt.getText().toString();
        String tempPerSensor = perSensorTxt.getText().toString();
        boolean initialRun = true;

        //set the default checked(Radio Button)
        if(type.equals(tempMon)){
            whileMonitoringRdBtn.setChecked(true);
            appIsRunningRdBtn.setChecked(false);
            appIsNotRunningRdBtn.setChecked(false);

            if(behavior.equals(tempAllData)){
                allBehaviorRdBtn.setChecked(true);
                perDeviceBehaviorRdBtn.setChecked(false);
                perSensorBehaviorRdBtn.setChecked(false);
            }
            else if (behavior.equals(tempPerDevice)) {
                allBehaviorRdBtn.setChecked(false);
                perDeviceBehaviorRdBtn.setChecked(true);
                perSensorBehaviorRdBtn.setChecked(false);
            }
            else if (behavior.equals(tempPerSensor)) {
                allBehaviorRdBtn.setChecked(false);
                perDeviceBehaviorRdBtn.setChecked(false);
                perSensorBehaviorRdBtn.setChecked(true);
            }
        }
        else if (type.equals(tempRunning)) {
            appIsRunningRdBtn.setChecked(true);
            whileMonitoringRdBtn.setChecked(false);
            appIsNotRunningRdBtn.setChecked(false);

            allBehaviorRdBtn.setChecked(true);
            perDeviceBehaviorRdBtn.setChecked(false);
            perSensorBehaviorRdBtn.setChecked(false);
        }
        else if (type.equals(tempNotRunning)) {
            appIsNotRunningRdBtn.setChecked(true);
            appIsRunningRdBtn.setChecked(false);
            whileMonitoringRdBtn.setChecked(false);

            allBehaviorRdBtn.setChecked(true);
            perDeviceBehaviorRdBtn.setChecked(false);
            perSensorBehaviorRdBtn.setChecked(false);
        }
        else {
            appIsRunningRdBtn.setChecked(true);
            whileMonitoringRdBtn.setChecked(false);
            appIsNotRunningRdBtn.setChecked(false);

            allBehaviorRdBtn.setChecked(true);
            perDeviceBehaviorRdBtn.setChecked(false);
            perSensorBehaviorRdBtn.setChecked(false);
        }

        //get default template
        Cursor cursor = objectSize.getDefaultObjectSize();
        try {
            if (cursor != null && cursor.moveToFirst()){
                do {
                    tempName = cursor.getString(cursor.getColumnIndexOrThrow("template_name"));
                }while (cursor.moveToNext());
            }
        }finally {
            if (cursor != null){cursor.close();}
        }

        appIsRunningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBehavior.setVisibility(View.GONE);
                appIsRunningRdBtn.setChecked(true);
                whileMonitoringRdBtn.setChecked(false);
                appIsNotRunningRdBtn.setChecked(false);

                allBehaviorRdBtn.setChecked(true);
                perDeviceBehaviorRdBtn.setChecked(false);
                perSensorBehaviorRdBtn.setChecked(false);

            }
        });
        whileMonitoringBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBehavior.setVisibility(View.VISIBLE);
                appIsRunningRdBtn.setChecked(false);
                whileMonitoringRdBtn.setChecked(true);
                appIsNotRunningRdBtn.setChecked(false);

                allBehaviorRdBtn.setChecked(true);
                perDeviceBehaviorRdBtn.setChecked(false);
                perSensorBehaviorRdBtn.setChecked(false);

            }
        });
        appIsNotRunningBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBehavior.setVisibility(View.GONE);
                appIsRunningRdBtn.setChecked(false);
                whileMonitoringRdBtn.setChecked(false);
                appIsNotRunningRdBtn.setChecked(true);

                allBehaviorRdBtn.setChecked(true);
                perDeviceBehaviorRdBtn.setChecked(false);
                perSensorBehaviorRdBtn.setChecked(false);

            }
        });
        allBehaviorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allBehaviorRdBtn.setChecked(true);
                perDeviceBehaviorRdBtn.setChecked(false);
                perSensorBehaviorRdBtn.setChecked(false);

            }
        });
        perDeviceBehaviorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allBehaviorRdBtn.setChecked(false);
                perDeviceBehaviorRdBtn.setChecked(true);
                perSensorBehaviorRdBtn.setChecked(false);

            }
        });
        perSensorBehaviorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allBehaviorRdBtn.setChecked(false);
                perDeviceBehaviorRdBtn.setChecked(false);
                perSensorBehaviorRdBtn.setChecked(true);

            }
        });
        defaultMacBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!bluetoothAdapter.isEnabled()) {
                    popups.bluetoothFailed();
                }else {
                    Intent intent = new Intent(Settings.this, SelectDefaultBluetooth.class);
                    startActivity(intent);
                }
            }
        });
        defaultTemplateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, SelectDefaultTemplate.class);
                startActivity(intent);

            }
        });
        changePinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.this, ChangePIN.class);
                startActivity(intent);

            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(whileMonitoringRdBtn.isChecked()){
                    type = monitoringTxt.getText().toString();
                    if(allBehaviorRdBtn.isChecked()){
                        behavior = allDataTxt.getText().toString();
                    }
                    if(perSensorBehaviorRdBtn.isChecked()){
                        behavior = perSensorTxt.getText().toString();
                    }
                    if(perDeviceBehaviorRdBtn.isChecked()){
                        behavior = perDeviceTxt.getText().toString();
                    }
                }else{
                    behavior = allDataTxt.getText().toString();
                    if(appIsRunningRdBtn.isChecked()){
                        type = runningTxt.getText().toString();
                    }
                    if(appIsNotRunningRdBtn.isChecked()){
                        type = notRunningTxt.getText().toString();
                    }
                }

                popups.saveConfirmationPopupWindow(defaultSettings, behavior, type);

            }
        });
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popups.resetConfirmationPopupWindow(defaultSettings);

            }
        });

        NavigationBar navigationBar = new NavigationBar(drawerLayout, navigationView, toolbar);
        navigationBar.setNavbar(this);

        if (whileMonitoringRdBtn.isChecked()){
            dataBehavior.setVisibility(View.VISIBLE);
        }

        btMacTxt.setText(mac);
        templateNameTxt.setText(tempName);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(globalVariable.hideSystemBars());
        }
    }
}