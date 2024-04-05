package com.example.structuremonitoringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDevice;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseSensor;
import com.example.structuremonitoringsystem.OtherFunction.Popups;

public class DeviceSetup extends AppCompatActivity {

    EditText deviceName, numOfData;
    TextView sensorName;
    RelativeLayout finBtn;
    DatabaseDevice databaseDevice;
    DatabaseSensor databaseSensor;

    RelativeLayout selectSensor;
    Popups popups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_device_setup);

        DatabaseDefaultSettings defaultSettings = new DatabaseDefaultSettings(this);
        defaultSettings.seedDefaultData();

        popups = new Popups(DeviceSetup.this);

        databaseDevice = new DatabaseDevice(this);
        databaseSensor = new DatabaseSensor(this);

        sensorName = findViewById(R.id.sensorName);
        deviceName = findViewById(R.id.deviceName);
        numOfData = findViewById(R.id.numOfData);
        selectSensor = findViewById(R.id.firstCol);
        Intent intent = getIntent();
        String temp = intent.getStringExtra("key");



        finBtn = findViewById(R.id.finishBtn);

        finBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sName = sensorName.getText().toString();
                String dName = deviceName.getText().toString();
//                String noOfData = numOfData.getText().toString();

                if (sName.isEmpty() || dName.isEmpty() ){
                    return;
                }
                String sensorListID = databaseDevice.addDevice(dName);
                databaseSensor.addSensor(sName, sensorListID, 0);
                if(temp.equals("hehe")){
                    Intent intent = new Intent(DeviceSetup.this, Sensors.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(DeviceSetup.this, PinPage.class);
                    startActivity(intent);
                }
            }
        });

        selectSensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popups.selectSensor(new Popups.SensorSelectionListener() {
                    @Override
                    public void onSensorSelected(String sensor) {
                        sensorName.setText(sensor);
                    }
                });
            }
        });
    }
}