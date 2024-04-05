package com.example.structuremonitoringsystem.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;
import com.example.structuremonitoringsystem.Item.BluetoothItem;
import com.example.structuremonitoringsystem.Item.DeviceItem;
import com.example.structuremonitoringsystem.Item.ObjectItem;
import com.example.structuremonitoringsystem.Item.SensorItem;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDevice;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.BluetoothAdapter;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.DeviceAdapter;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.ObjectAdapter;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.RTDeviceAdapter;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.SensorMonitoringAdapter;
import com.example.structuremonitoringsystem.Testing.ObjectList;

import java.util.ArrayList;
import java.util.List;

public class ShowItemList {
    Context context;
    BluetoothConnection bluetoothConnection;
    public ShowItemList(Context context) {
        this.context = context;
    }

    public void showListOfBluetooth(RecyclerView recyclerView, Activity activity){
        bluetoothConnection = new BluetoothConnection();

        String getDeviceNames = bluetoothConnection.getDeviceNameList(context, activity);
        String getDeviceMAC = bluetoothConnection.getDeviceMACList(context, activity);

        String[] deviceName = getDeviceNames.split(",");
        String[] deviceMAC = getDeviceMAC.split(",");

        Log.e("MAC", getDeviceMAC);
        Log.e("Names", getDeviceNames);

        List<BluetoothItem> deviceItemList = new ArrayList<BluetoothItem>();

        //converts the array to list
        int x = 0;
        for (String dev : deviceName) {
            deviceItemList.add(new BluetoothItem(dev, deviceMAC[x]));
            x++;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new BluetoothAdapter(context.getApplicationContext(), deviceItemList));
    }

    public void showListOfTemplates(RecyclerView recyclerView){
        List<ObjectItem> objectItems= new ArrayList<ObjectItem>();
        DatabaseObjectSize databaseObjectSize = new DatabaseObjectSize(context);

        Cursor objectList = databaseObjectSize.readAllData();
        try {
            if (objectList != null && objectList.moveToFirst()){
                do {
                    String id = objectList.getString(objectList.getColumnIndexOrThrow("_id"));
                    String name = objectList.getString(objectList.getColumnIndexOrThrow("template_name"));
                    String width = objectList.getString(objectList.getColumnIndexOrThrow("_width"));
                    String height = objectList.getString(objectList.getColumnIndexOrThrow("_height"));
                    String thickness = objectList.getString(objectList.getColumnIndexOrThrow("_thickness"));

                    ObjectItem currentTemplate = new ObjectItem(width, height, thickness, name, id);
                    objectItems.add(currentTemplate);
                }while (objectList.moveToNext());
            }
        }finally {
            if (objectList != null){objectList.close();}
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new ObjectAdapter(context.getApplicationContext(), objectItems));

    }

    public void showRTDevices(RecyclerView recyclerView){
        List<DeviceItem> list= new ArrayList<DeviceItem>();
        DatabaseDevice databaseDevice = new DatabaseDevice(context);

        Cursor deviceList = databaseDevice.getDevices();
        try {
            if (deviceList != null && deviceList.moveToFirst()){
                do {

                    String id = deviceList.getString(deviceList.getColumnIndexOrThrow("_id"));
                    String name = deviceList.getString(deviceList.getColumnIndexOrThrow("device_name"));
                    String deviceSensorListID = deviceList.getString(deviceList.getColumnIndexOrThrow("sensor_list_id")).toString();

                    Log.e("Striiiing", ""+id +name+deviceSensorListID);
                    DeviceItem deviceItem = new DeviceItem(name, id, deviceSensorListID);
                    list.add(deviceItem);
                }while (deviceList.moveToNext());
            }
        }finally {
            if (deviceList != null){deviceList.close();}
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new RTDeviceAdapter(context.getApplicationContext(), list));

    }


    public void showDevices(RecyclerView recyclerView){
        List<DeviceItem> list= new ArrayList<DeviceItem>();
        DatabaseDevice databaseDevice = new DatabaseDevice(context);

        Cursor deviceList = databaseDevice.getDevices();
        try {
            if (deviceList != null && deviceList.moveToFirst()){
                do {

                    String id = deviceList.getString(deviceList.getColumnIndexOrThrow("_id"));
                    String name = deviceList.getString(deviceList.getColumnIndexOrThrow("device_name"));
                    String deviceSensorListID = deviceList.getString(deviceList.getColumnIndexOrThrow("sensor_list_id")).toString();

                    Log.e("Striiiing", ""+id +name+deviceSensorListID);
                    DeviceItem deviceItem = new DeviceItem(name, id, deviceSensorListID);
                    list.add(deviceItem);
                }while (deviceList.moveToNext());
            }
        }finally {
            if (deviceList != null){deviceList.close();}
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new DeviceAdapter(context.getApplicationContext(), list));

    }
    public void showSensor(RecyclerView recyclerView, String id, String log){
        List<SensorItem> list= new ArrayList<SensorItem>();

        SensorItem sensorItem1 = new SensorItem("Accelerometer", id, log);
        SensorItem sensorItem2 = new SensorItem("Gyroscope", id, log);
        list.add(sensorItem1);
        list.add(sensorItem2);


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new SensorMonitoringAdapter(context.getApplicationContext(), list));

    }
}

