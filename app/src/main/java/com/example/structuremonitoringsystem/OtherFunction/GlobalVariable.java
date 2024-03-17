package com.example.structuremonitoringsystem.OtherFunction;

import android.view.View;
import android.view.WindowInsets;

import androidx.viewpager.widget.ViewPager;

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

    public static String[] getLoggingBehavior() {
        String loggingBehavior[] = new String[]{
                "While App is Running",
                "While Monitoring Only",
                "Collect Data Even the App is NOT Running"};
        return loggingBehavior;
    }

    public static String[] getLoggingType() {
        String loggingType[] = new String[]{
                "All Data",
                "Per Device",
                "Per Sensor on Device"};
        return loggingType;
    }
    public int hideSystemBars(){


        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    }

    public void hideSystemBars(View decorView){

        decorView.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
            @Override
            public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
                // Perform your actions here when system UI visibility changes
                decorView.setSystemUiVisibility(hideSystemBars());
                return windowInsets;
            }
        });

    }


}

