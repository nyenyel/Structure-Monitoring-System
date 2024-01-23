package com.example.structuremonitoringsystem.Variables;

import android.content.Context;
import android.util.Log;

import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;

import java.util.Random;

public class GlobalVariable {

    public String arduinoDeviceMAC = "";
    public float width = 0f;
    public float height = 0f;
    public float thickness = 0f;


    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getThickness() {
        return thickness;
    }

    public void setThickness(float thickness) {
        this.thickness = thickness;
    }


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

    public static String mergeFloatArrayToString(float[] floatArray, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < floatArray.length; i++) {
            stringBuilder.append(floatArray[i]+"f");

            // Add the delimiter if it's not the last element
            if (i < floatArray.length - 1) {
                stringBuilder.append(delimiter);
            }
        }

        return stringBuilder.toString();
    }

    public static double getRandomDouble(double min, double max) {
        Random random = new Random();
        return min + (max - min) * random.nextDouble();
    }

}

