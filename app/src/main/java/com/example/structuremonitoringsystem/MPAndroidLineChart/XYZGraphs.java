package com.example.structuremonitoringsystem.MPAndroidLineChart;

import android.app.Activity;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.view.ContentInfoCompat;

import com.example.structuremonitoringsystem.Arduino.Formula;
import com.example.structuremonitoringsystem.Arduino.HC05Bluetooth;
import com.example.structuremonitoringsystem.Arduino.KalmanFilter;
import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;
import com.example.structuremonitoringsystem.Item.DataLogsItem;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseLogging;
import com.example.structuremonitoringsystem.OpenGL.OpenGLView;
import com.example.structuremonitoringsystem.OtherFunction.Notification;
import com.example.structuremonitoringsystem.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class XYZGraphs {
    Context context;
    Activity activity;
    BluetoothConnection bluetoothConnection;
    Seismograph seismographX, seismographY, seismographZ,
            seismographX2, seismographY2, seismographZ2;
    HC05Bluetooth hc05Bluetooth;
    private int gCounter = 0;
    // Boolean flag to track movement state
    ArrayList<Float> dataX = new ArrayList<>();
    ArrayList<Float> dataY = new ArrayList<>();
    ArrayList<Float> dataZ = new ArrayList<>();
    ArrayList<Float> dataX2 = new ArrayList<>();
    ArrayList<Float> dataY2 = new ArrayList<>();
    ArrayList<Float> dataZ2 = new ArrayList<>();
    private boolean isMoving = false;
    public XYZGraphs(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void realtimeGraph(OpenGLView  openGLView, LineChart x, LineChart y, LineChart z, String pos, DatabaseLogging logging){
        int position[] = new int[3];
        String temp[] = pos.trim().split(",");
        int count = 0;
        for(String data : temp){
            position[count] = Integer.parseInt(data);
            Log.e("sample", ""+position[count]);
            count++;

        }

        DatabaseDefaultSettings defaultSettings = new DatabaseDefaultSettings(context);
        String mac =defaultSettings.getDefaultMac();

        bluetoothConnection = new BluetoothConnection();
        hc05Bluetooth = new HC05Bluetooth(context);
        seismographX = new Seismograph(x,"Accelerometer X Axis");
        seismographY = new Seismograph(y, "Accelerometer Y Axis");
        seismographZ = new Seismograph(z, "Accelerometer Z Axis");

        //set the ui for the graph
        seismographX.realTimeSeismograph();
        seismographY.realTimeSeismograph();
        seismographZ.realTimeSeismograph();

        BluetoothSocket bluetoothSocket = bluetoothConnection.bluetoothCn(context, mac);



        // Create an instance of DisplacementCalculator with your desired alpha value
        Formula calculator = new Formula(0.7f); // Adjust alpha as needed
        int j = 0;
        // Start receiving data in a separate thread
        hc05Bluetooth.receiveData(bluetoothSocket, new HC05Bluetooth.DataListener() {
            @Override
            public void onDataReceived(final String data) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
                int year = calendar.get(Calendar.YEAR);
                int week = calendar.get(Calendar.WEEK_OF_MONTH);

                // Define the desired time format
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
                // Format the current time
                String formattedTime = sdf.format(calendar.getTime());
                logging.addData(new DataLogsItem(formattedTime, data, day, week, month, year));


                // Splits the data to x y z
                String receivedData[] = data.trim().split(",");

                double x = Double.parseDouble(receivedData[position[0]]);
                double y = Double.parseDouble(receivedData[position[1]]);
                double z = Double.parseDouble(receivedData[position[2]]);

                // Calculate displacement for x-axis using the DisplacementCalculator
                final float displacementX = calculator.displacement(x);
                final float displacementY = calculator.displacement(y);
                final float displacementZ = calculator.displacement(z);

//                if (displacementX != 0){
//                    currentDisplacementX = currentDisplacementX + displacementX;
//                    Log.e("Current Displacement", "CD: "+gCounter);
//                    dataHolder.add(displacementX);
//                    gCounter++;
//                }




                // Update UI or perform any action with the received data
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("Displacement", "X = " +displacementX + " : " + receivedData[0]);

//                        Log.e("Displacement", "Y = " +displacementY + " : " + receivedData[1]);
//                        Log.e("Displacement", "Z = " +displacementZ + " : " + receivedData[2]);

                        openGLView.moveObject(displacementX, displacementY, displacementZ);


                        seismographX.addRealtimeEntry(x);
                        seismographY.addRealtimeEntry(y);
                        seismographZ.addRealtimeEntry(z);
                    }
                });


            }
        });
    }


    public void plotDataLogs(LineChart x, LineChart y, LineChart z, String pos, DatabaseLogging logging, String sensor){

        int position[] = new int[3];
        String temp[] = pos.trim().split(",");
        int count = 0;
        for(String data : temp){
            position[count] = Integer.parseInt(data);
            Log.e("sample", ""+position[count]);
            count++;

        }

//        random values of data logging
        ArrayList<Entry> dataX = new ArrayList<>();
        ArrayList<Entry> dataY = new ArrayList<>();
        ArrayList<Entry> dataZ = new ArrayList<>();



        Cursor cursor = logging.getAllData();
        try {
            if (cursor != null && cursor.moveToFirst()){
                float counter = 0;
                do {
                    String dataValues = cursor.getString(cursor.getColumnIndexOrThrow("xyz_data"));
                    String time = cursor.getString(cursor.getColumnIndexOrThrow("_time"));

                    // Check if dataValues is not empty or null before parsing
                    if (!TextUtils.isEmpty(dataValues)) {
                        String receivedData[] = dataValues.trim().split(",");

                        // Ensure that the position indices are valid
                        if (receivedData.length > position[0] && receivedData.length > position[1] && receivedData.length > position[2]) {
                            try {
                                double xValues = Double.parseDouble(receivedData[position[0]]);
                                double yValues = Double.parseDouble(receivedData[position[1]]);
                                double zValues = Double.parseDouble(receivedData[position[2]]);

                                dataX.add(new Entry(counter, (float) xValues));
                                dataY.add(new Entry(counter, (float) yValues));
                                dataZ.add(new Entry(counter, (float) zValues));
                                counter++;
                            } catch (NumberFormatException e) {
                                Log.e("PlotDataLogs", "NumberFormatException: "+  e.getMessage());
                            }
                        } else {
                            Log.e("PlotDataLogs", "Invalid position indices");
                        }
                    } else {
                        Log.e("PlotDataLogs", "Empty or null dataValues");
                    }
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        DatabaseDefaultSettings defaultSettings = new DatabaseDefaultSettings(context);
        String mac =defaultSettings.getDefaultMac();

        bluetoothConnection = new BluetoothConnection();
        hc05Bluetooth = new HC05Bluetooth(context);
        seismographX = new Seismograph(x,"X Axis",sensor);
        seismographY = new Seismograph(y, "Y Axis",sensor);
        seismographZ = new Seismograph(z, "Z Axis",sensor);

        //set the ui for the graph
        seismographX.dataLoggingSeismograph(dataX);
        seismographY.dataLoggingSeismograph(dataY);
        seismographZ.dataLoggingSeismograph(dataZ);

    }

    public void startCollectingData(DatabaseLogging logging){
//        DatabaseDefaultSettings defaultSettings = new DatabaseDefaultSettings(context);
//        String mac =defaultSettings.getDefaultMac();
//
//        bluetoothConnection = new BluetoothConnection();
//        hc05Bluetooth = new HC05Bluetooth();
//
//        BluetoothSocket bluetoothSocket = bluetoothConnection.bluetoothCn(context, mac);
//        // Create an instance of DisplacementCalculator with your desired alpha value
//        Formula calculator = new Formula(0.7f); // Adjust alpha as needed
//
//        // Start receiving data in a separate thread
//        hc05Bluetooth.receiveData(bluetoothSocket, new HC05Bluetooth.DataListener() {
//            @Override
//            public void onDataReceived(final String data) {
//
//                Calendar calendar = Calendar.getInstance();
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
//                int year = calendar.get(Calendar.YEAR);
//                int week = calendar.get(Calendar.WEEK_OF_MONTH);
//
//                // Define the desired time format
//                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
//                // Format the current time
//                String formattedTime = sdf.format(calendar.getTime());
//
//                logging.addData(new DataLogsItem(formattedTime, data, day, week, month, year));
//                Log.e("Start", "Collecting Data");
//            }
//        });
    }

    public void realtimeGraph(OpenGLView  openGLView, LineChart x, LineChart y, LineChart z, String pos,
                              OpenGLView  openGLView2, LineChart x2, LineChart y2, LineChart z2, String pos2,
                              DatabaseLogging logging){

        DatabaseDefaultSettings defaultSettings = new DatabaseDefaultSettings(context);
        String mac =defaultSettings.getDefaultMac();
        float threshold = Float.parseFloat(defaultSettings.getThreshold());

        // Initial state, initial error covariance, process noise covariance, measurement noise covariance
        KalmanFilter kf = new KalmanFilter(0, 1, 0.01f, 0.1f);   // Handler for countdown
        Handler handler = new Handler();
        Runnable countdownRunnable = new Runnable() {
            @Override
            public void run() {
                float sec = gCounter/10;

                float xMean = summation(dataX)/gCounter;
                float yMean = summation(dataX)/gCounter;
                float zMean = summation(dataX)/gCounter;

                float xDisplacement = xMean * sec;
                float yDisplacement = yMean * sec;
                float zDisplacement = zMean * sec;


                float xMean2 = summation(dataX2)/gCounter;
                float yMean2 = summation(dataX2)/gCounter;
                float zMean2 = summation(dataX2)/gCounter;

                float xDisplacement2 = xMean2 * sec;
                float yDisplacement2 = yMean2 * sec;
                float zDisplacement2 = zMean2 * sec;

                float xDistance = xDisplacement - xDisplacement2;
                float yDistance = yDisplacement - yDisplacement2;
                float zDistance = zDisplacement - zDisplacement2;

                if (xDistance > threshold || yDistance > threshold || zDistance > threshold){
                    Notification notification = new Notification(activity, context);
                    notification.notifyDrift(" X = " + xDistance +
                                                    " Y = " + yDistance +
                                                    " Z = " + zDistance);
                }

                Log.e("Notify", "No movement for 5sec final distance between the 2 object per axis are:" +
                        " X = " + xDistance +
                        " Y = " + yDistance +
                        " Z = " + zDistance);

                dataX = new ArrayList<>();
                dataY = new ArrayList<>();
                dataZ = new ArrayList<>();
                dataX2 = new ArrayList<>();
                dataY2 = new ArrayList<>();
                dataZ2 = new ArrayList<>();
            }
        };

        int position[] = new int[3];
        String temp[] = pos.trim().split(",");
        int count = 0;
        for(String data : temp){
            position[count] = Integer.parseInt(data);
//            Log.e("sample", ""+position[count]);
            count++;

        }

        //2nd device--------------------------------------------------------
        int position2[] = new int[3];
        String temp2[] = pos2.trim().split(",");
        int count2 = 0;
        for(String data : temp2){
            position2[count2] = Integer.parseInt(data);
//            Log.e("sample", ""+position2[count2]);
            count2++;

        }
        //------------------------------------------------------------------


        bluetoothConnection = new BluetoothConnection();
        hc05Bluetooth = new HC05Bluetooth(context);

        seismographX = new Seismograph(x,"1st Device X Axis");
        seismographY = new Seismograph(y, "1st Device Y Axis");
        seismographZ = new Seismograph(z, "1st Device Z Axis");

        //set the ui for the graph
        seismographX.realTimeSeismograph();
        seismographY.realTimeSeismograph();
        seismographZ.realTimeSeismograph();

        //2nd device--------------------------------------------------------
        seismographX2 = new Seismograph(x2,"2nd Device X Axis");
        seismographY2 = new Seismograph(y2, "2nd Device Y Axis");
        seismographZ2 = new Seismograph(z2, "2nd Device Z Axis");

        //set the ui for the graph
        seismographX2.realTimeSeismograph();
        seismographY2.realTimeSeismograph();
        seismographZ2.realTimeSeismograph();
        //------------------------------------------------------------------


        BluetoothSocket bluetoothSocket = bluetoothConnection.bluetoothCn(context, mac);

        // Create an instance of DisplacementCalculator with your desired alpha value
        Formula calculator = new Formula(0.7f); // Adjust alpha as needed

        // Start receiving data in a separate thread
        hc05Bluetooth.receiveData(bluetoothSocket, new HC05Bluetooth.DataListener() {
            @Override
            public void onDataReceived(final String data) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
                int year = calendar.get(Calendar.YEAR);
                int week = calendar.get(Calendar.WEEK_OF_MONTH);

                // Define the desired time format
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
                // Format the current time
                String formattedTime = sdf.format(calendar.getTime());
                logging.addData(new DataLogsItem(formattedTime, data, day, week, month, year));


                // Splits the data to x y z
                String receivedData[] = data.trim().split(",");

                double x = Double.parseDouble(receivedData[position[0]]);
                double y = Double.parseDouble(receivedData[position[1]]);
                double z = Double.parseDouble(receivedData[position[2]]);

                //2nd device--------------------------------------------------------
                double x2 = Double.parseDouble(receivedData[position2[0]]);
                double y2 = Double.parseDouble(receivedData[position2[1]]);
                double z2 = Double.parseDouble(receivedData[position2[2]]);
                //------------------------------------------------------------------

                // Calculate displacement for x-axis using the DisplacementCalculator
                final float displacementX = calculator.displacement(x);
                final float displacementY = calculator.displacement(y);
                final float displacementZ = calculator.displacement(z);

                //2nd device--------------------------------------------------------
                // Calculate displacement for x-axis using the DisplacementCalculator
                final float displacementX2 = calculator.displacement(x2);
                final float displacementY2 = calculator.displacement(y2);
                final float displacementZ2 = calculator.displacement(z2);
                //------------------------------------------------------------------

                // Check for movement (displacementX is zero)
                if (displacementX == 0 && displacementY == 0 && displacementZ == 0 &&
                        displacementX2 == 0 && displacementY2 == 0 && displacementZ2 == 0 ) {
                    // If there is no movement, start the countdown if it's not already started
                    if (!isMoving) {
                        handler.postDelayed(countdownRunnable, 5000); // Start the countdown
                        isMoving = true; // Set movement state to true

                    }
                } else {

                    // If there is movement, reset the countdown if it's already started
                    // Reset the countdown
                    isMoving = false; // Set movement state to false
                    dataX.add((float) x);
                    dataY.add((float) y);
                    dataZ.add((float) z);

                    dataX2.add((float) x2);
                    dataY2.add((float) y2);
                    dataZ2.add((float) z2);

                    Log.e("Start", "moving" + gCounter);
                    gCounter++;
                    handler.removeCallbacks(countdownRunnable);
                }

//                Log.e("Start", "Collecting Data on Device");
                // Update UI or perform any action with the received data
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("Displacement", "X = " +displacementX + " : " + receivedData[0]);
//                        Log.e("Displacement", "Y = " +displacementY + " : " + receivedData[1]);
//                        Log.e("Displacement", "Z = " +displacementZ + " : " + receivedData[2]);
                        openGLView.moveObject(displacementX, displacementY, displacementZ);
                        openGLView2.moveObject(displacementX2, displacementY2, displacementZ2);

                        seismographX.addRealtimeEntry(x);
                        seismographY.addRealtimeEntry(y);
                        seismographZ.addRealtimeEntry(z);

                        seismographX2.addRealtimeEntry(x2);
                        seismographY2.addRealtimeEntry(y2);
                        seismographZ2.addRealtimeEntry(z2);
                    }
                });
            }
        });
    }

    public void realtimeGraph(OpenGLView  openGLView,String pos,
                              OpenGLView  openGLView2,String pos2,
                              DatabaseLogging logging){

        DatabaseDefaultSettings defaultSettings = new DatabaseDefaultSettings(context);
        String mac =defaultSettings.getDefaultMac();
        float threshold = Float.parseFloat(defaultSettings.getThreshold());


        int position[] = new int[3];
        String temp[] = pos.trim().split(",");
        int count = 0;
        for(String data : temp){
            position[count] = Integer.parseInt(data);
//            Log.e("sample", ""+position[count]);
            count++;

        }

        //2nd device--------------------------------------------------------
        int position2[] = new int[3];
        String temp2[] = pos2.trim().split(",");
        int count2 = 0;
        for(String data : temp2){
            position2[count2] = Integer.parseInt(data);
//            Log.e("sample", ""+position2[count2]);
            count2++;

        }
        //------------------------------------------------------------------



        BluetoothSocket bluetoothSocket = bluetoothConnection.bluetoothCn(context, mac);

        // Create an instance of DisplacementCalculator with your desired alpha value
        Formula calculator = new Formula(0.7f); // Adjust alpha as needed

        // Start receiving data in a separate thread
        hc05Bluetooth.receiveData(bluetoothSocket, new HC05Bluetooth.DataListener() {
            @Override
            public void onDataReceived(final String data) {

                Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1
                int year = calendar.get(Calendar.YEAR);
                int week = calendar.get(Calendar.WEEK_OF_MONTH);

                // Define the desired time format
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault());
                // Format the current time
                String formattedTime = sdf.format(calendar.getTime());
                logging.addData(new DataLogsItem(formattedTime, data, day, week, month, year));


                // Splits the data to x y z
                String receivedData[] = data.trim().split(",");

                double x = Double.parseDouble(receivedData[position[0]]);
                double y = Double.parseDouble(receivedData[position[1]]);
                double z = Double.parseDouble(receivedData[position[2]]);

                //2nd device--------------------------------------------------------
                double x2 = Double.parseDouble(receivedData[position2[0]]);
                double y2 = Double.parseDouble(receivedData[position2[1]]);
                double z2 = Double.parseDouble(receivedData[position2[2]]);
                //------------------------------------------------------------------

//                Log.e("Start", "Collecting Data on Device");
                // Update UI or perform any action with the received data
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        openGLView.rotateObject((float) x,(float)y,(float)z,0, 0);
                        openGLView2.rotateObject((float) x2,(float)y2,(float)z2,0, 0);

                    }
                });
            }
        });
    }

    public float summation (ArrayList<Float> data){
        float sum = 0f;
        for (float acceleration : data) {
            sum = sum + acceleration;
        }
        return sum;
    }

    public static float calculateDistance(float x1, float y1, float z1, float x2, float y2, float z2) {
        return (float)Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z2 - z1, 2));
    }
}
