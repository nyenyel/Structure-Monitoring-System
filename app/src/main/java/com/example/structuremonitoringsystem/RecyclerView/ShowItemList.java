package com.example.structuremonitoringsystem.RecyclerView;

import android.content.Context;
import android.database.Cursor;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;
import com.example.structuremonitoringsystem.Item.BluetoothItem;
import com.example.structuremonitoringsystem.Item.ObjectItem;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.BluetoothAdapter;
import com.example.structuremonitoringsystem.RecyclerView.Adapter.ObjectAdapter;
import com.example.structuremonitoringsystem.Testing.ObjectList;

import java.util.ArrayList;
import java.util.List;

public class ShowItemList {
    Context context;
    BluetoothConnection bluetoothConnection;
    public ShowItemList(Context context) {
        this.context = context;
    }

    public void showListOfBluetooth(RecyclerView recyclerView){
        bluetoothConnection = new BluetoothConnection();

        String getDeviceNames = bluetoothConnection.getDeviceNameList(context);
        String getDeviceMAC = bluetoothConnection.getDeviceMACList(context);

        String[] deviceName = getDeviceNames.split(",");
        String[] deviceMAC = getDeviceMAC.split(",");

        List<BluetoothItem> deviceItemList = new ArrayList<BluetoothItem>();

        //converts the array to list
        for (int x = 0; x < deviceName.length; x++) {
            deviceItemList.add(new BluetoothItem(deviceName[x], deviceMAC[x]));
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
}

