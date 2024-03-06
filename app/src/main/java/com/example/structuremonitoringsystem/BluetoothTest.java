package com.example.structuremonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;

public class BluetoothTest extends AppCompatActivity {

    BluetoothConnection btConnection;
    TextView deviceList;
    AppCompatButton cnBt;

    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);

        deviceList = (TextView) findViewById(R.id.devList);
        cnBt = (AppCompatButton) findViewById(R.id.cnBtn);

        btConnection = new BluetoothConnection();


        cnBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getDeviceNames = btConnection.getDeviceNameList(BluetoothTest.this);
                String getDeviceMAC = btConnection.getDeviceMACList(BluetoothTest.this);

                String[] deviceName = getDeviceNames.split(",");
                String[] deviceMAC = getDeviceMAC.split(",");

                String devUID = btConnection.deviceUUID(BluetoothTest.this, deviceMAC[counter]);
                String device = deviceName[counter] +" : "+ deviceMAC[counter] + " : " + devUID;

                deviceList.setText(deviceName[counter] +" : "+ deviceMAC[counter]+ " : " + devUID);
                Log.e("Bluetooth Name", device );
                counter++;
            }
        });

    }
}