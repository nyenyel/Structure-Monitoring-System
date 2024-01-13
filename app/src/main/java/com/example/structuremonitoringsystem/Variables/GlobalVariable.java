package com.example.structuremonitoringsystem.Variables;

import android.content.Context;
import android.util.Log;

import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;

public class GlobalVariable {

    public String arduinoDeviceMAC = "";

    private BluetoothConnection bluetoothConnection;

    public String getDeviceMacList(Context context) {
        bluetoothConnection = new BluetoothConnection();
        String deviceMacList = bluetoothConnection.getDeviceMACList(context);
        return deviceMacList;
    }


    public String getDeviceNameList(Context context) {
        bluetoothConnection = new BluetoothConnection();
        String deviceNameList = bluetoothConnection.getDeviceNameList(context);
        return deviceNameList;
    }




}
