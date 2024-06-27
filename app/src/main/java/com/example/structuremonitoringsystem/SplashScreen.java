package com.example.structuremonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.OtherFunction.Notification;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        DatabaseDefaultSettings defaultSettings = new DatabaseDefaultSettings(this);

        boolean isFirstRun = defaultSettings.getInitial().equals("");

        Handler handler = new Handler();
        Runnable countdownRunnable = new Runnable() {
            @Override
            public void run() {
                if(isFirstRun){
                    Intent intent = new Intent(SplashScreen.this, DeviceSetup.class);
                    defaultSettings.setInitial();
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreen.this, PinPage.class);
                    startActivity(intent);
                }
            }
        };
        handler.postDelayed(countdownRunnable, 3000);
    }
}