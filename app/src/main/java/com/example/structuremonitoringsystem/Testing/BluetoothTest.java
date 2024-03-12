package com.example.structuremonitoringsystem.Testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.BluetoothAdapter;
import com.example.structuremonitoringsystem.RecyclerView.Item.BluetoothItem;

import java.util.ArrayList;
import java.util.List;

public class BluetoothTest extends AppCompatActivity {

    BluetoothConnection btConnection;
    TextView deviceList;
    RecyclerView recyclerView;

    AppCompatButton createObj;
    static int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_bluetooth_test);

        deviceList = (TextView) findViewById(R.id.devList);
        recyclerView = (RecyclerView) findViewById(R.id.recView);
        createObj = (AppCompatButton) findViewById(R.id.createClass);

        btConnection = new BluetoothConnection();

        String getDeviceNames = btConnection.getDeviceNameList(BluetoothTest.this);
        String getDeviceMAC = btConnection.getDeviceMACList(BluetoothTest.this);
//
        String[] deviceName = getDeviceNames.split(",");
        String[] deviceMAC = getDeviceMAC.split(",");

        List<BluetoothItem> deviceItemList = new ArrayList<BluetoothItem>();

        //converts the array to list
        for(int x = 0; x < deviceName.length; x++){
            deviceItemList.add(new BluetoothItem(deviceName[x], deviceMAC[x]));
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(BluetoothTest.this));
        recyclerView.setAdapter(new BluetoothAdapter(getApplicationContext(), deviceItemList));

        createObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BluetoothTest.this, CreateObject.class);
                startActivity(intent);
            }
        });


    }
}