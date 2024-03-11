package com.example.structuremonitoringsystem;

import static kotlinx.coroutines.flow.FlowKt.skip;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.example.structuremonitoringsystem.Arduino.Formula;
import com.example.structuremonitoringsystem.Arduino.HC05Bluetooth;
import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;
import com.example.structuremonitoringsystem.MPAndroidLineChart.Seismograph;
import com.example.structuremonitoringsystem.OpenGL.OpenGLView;
import com.github.mikephil.charting.charts.LineChart;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;


public class BluetoothTerminalTest extends AppCompatActivity {

    TextView btStat;

    AppCompatButton cancelBtn, showBtn;
    LineChart realtimeChartX, realtimeChartY, realtimeChartZ;
    Seismograph seismographX, seismographY, seismographZ;
    BluetoothConnection bluetoothConnection;
    HC05Bluetooth hc05Bluetooth;
    OpenGLView openGLView;

    private static String textTerminal = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_terminal_test);

        cancelBtn = (AppCompatButton) findViewById(R.id.cancelBtn);
        showBtn = (AppCompatButton) findViewById(R.id.showBtn);
        realtimeChartX = (LineChart) findViewById(R.id.realtimeSeismographX);
        realtimeChartY = (LineChart) findViewById(R.id.realtimeSeismographY);
        realtimeChartZ = (LineChart) findViewById(R.id.realtimeSeismographZ);
        openGLView = (OpenGLView) findViewById(R.id.openGLView);

        String mac = getIntent().getStringExtra("MAC");
        String name = getIntent().getStringExtra("name");

        bluetoothConnection = new BluetoothConnection();
        hc05Bluetooth = new HC05Bluetooth();
        seismographX = new Seismograph(realtimeChartX);
        seismographY = new Seismograph(realtimeChartY);
        seismographZ = new Seismograph(realtimeChartZ);

        //set the ui for the graph
        seismographX.realTimeSeismograph();
        seismographY.realTimeSeismograph();
        seismographZ.realTimeSeismograph();

        BluetoothSocket bluetoothSocket = bluetoothConnection.bluetoothCn(this, mac);

        // Create an instance of DisplacementCalculator with your desired alpha value
        Formula calculator = new Formula(0.1f); // Adjust alpha as needed

// Start receiving data in a separate thread
        hc05Bluetooth.receiveData(bluetoothSocket, new HC05Bluetooth.DataListener() {
            @Override
            public void onDataReceived(final String data) {

                // Splits the data to x y z
                String receivedData[] = data.trim().split(",");

                double x = Double.parseDouble(receivedData[0]);
                double y = Double.parseDouble(receivedData[1]);
                double z = Double.parseDouble(receivedData[2]);

                // Calculate displacement for x-axis using the DisplacementCalculator
                final float displacementX = calculator.displacement(x);
                final float displacementY = calculator.displacement(y);
                final float displacementZ = calculator.displacement(z);

                // Update UI or perform any action with the received data
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Displacement", displacementX + " : " + receivedData[0]);
                        openGLView.moveObject(displacementX, displacementY, displacementZ);

                        seismographX.addRealtimeEntry(x);
                        seismographY.addRealtimeEntry(y);
                        seismographZ.addRealtimeEntry(z);
                    }
                });
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float x,y,z,a;
                boolean isZero = openGLView.getCurrentRotationZ() <= 0;
                boolean angleIsZero = openGLView.getCurrentAngle() == 0;
                x = 0f;
                y = 0f;
                z = -6f;
                a = -1f;
//                if(isZero){
//                    z = fullRotation;
//                }
//                if (angleIsZero){
//                    a = 360f;
//                }
                openGLView.rotateObject(x, y , z, a, 2f);
            }
        });
        // Remember to disconnect the Bluetooth socket when no longer needed
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bluetoothConnection.bluetoothDc(bluetoothSocket);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        openGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        openGLView.onPause();
    }
}