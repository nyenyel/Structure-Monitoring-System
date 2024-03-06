package com.example.structuremonitoringsystem.Bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.ParcelUuid;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.example.structuremonitoringsystem.Variables.GlobalVariable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothConnection {

    private static final UUID arduinoUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final int REQUEST_BLUETOOTH_PERMISSION = 123;

    private BluetoothDevice bluetoothDevice;

    public void test(){
        Log.e("Bluetooth Devices", "You have no permission");
    }
    public String getDeviceNameList(Context context) {

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            // Request Bluetooth permission
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUETOOTH_PERMISSION);
            return "";
        } else {
            // Permission already granted, proceed with Bluetooth operations
            String bluetoothAddress = "" + btAdapter.getBondedDevices();
            String blListMAC = squareBracketRemover(bluetoothAddress);
            String blListName = "";
            String arrBlListMac[] = stringToArray(blListMAC);
            for (int x = 0; x < arrBlListMac.length; x++) {
                bluetoothDevice = btAdapter.getRemoteDevice(arrBlListMac[x]);
                blListName = blListName +","+ bluetoothDevice.getName();
//                Log.e("Bluetooth", arrBlListMac[x] + " Name: " + bluetoothDevice.getName());
            }
//            Log.e("Global Variable", ""+blListName);
//            Log.e("Global Variable", "Data Added");
            return blListName.substring(1);

        }
    }

    public String getDeviceMACList(Context context) {

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            // Request Bluetooth permission
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUETOOTH_PERMISSION);
            return "";
        } else {
            // Permission already granted, proceed with Bluetooth operations
            String bluetoothAddress = "" + btAdapter.getBondedDevices();
            String blListMAC = squareBracketRemover(bluetoothAddress);
//            Log.e("Global Variable", ""+ blListMAC);
//            Log.e("Global Variable", "Data Added");
            return blListMAC;

        }
    }

    public String deviceUUID(Context context, String deviceMAC){

        // Get the Bluetooth device based on its address
        String deviceUUID = "";
        BluetoothDevice bluetoothDevice = BluetoothAdapter.getDefaultAdapter().getRemoteDevice(deviceMAC);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            // Request Bluetooth permission
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUETOOTH_PERMISSION);
        } else {
            // Permission already granted, proceed with Bluetooth operations
            // Get the UUIDs supported by the Bluetooth device
            ParcelUuid[] uuids = bluetoothDevice.getUuids();

            if (uuids != null) {
                // Print the UUIDs to the console
                for (ParcelUuid uuid : uuids) {
                    deviceUUID = uuid.getUuid().toString();
                    System.out.println("UUID: " + uuid.getUuid().toString());
                }
            } else {
                System.out.println("UUIDs not available for this device.");
                deviceUUID = "UUIDs not available for this device.";
            }
        }
        return deviceUUID;
    }

    public void bluetoothCn(Context context, String deviceMAC){

        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceMAC);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            // Request Bluetooth permission
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUETOOTH_PERMISSION);
        } else {
            // Permission already granted, proceed with Bluetooth operations
            BluetoothSocket bluetoothSocket = null;

            //Confirm of the device is connected through Bluetooth
            int counter = 0;
            boolean count = counter <3;
            do {
                try {
                    bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(arduinoUUID);
                    Log.e("Bluetooth Socket", ""+bluetoothSocket);
                    bluetoothSocket.connect();
                    Log.e("Bluetooth Connection", "Connected: " + bluetoothSocket.isConnected());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                counter++;
            } while (!bluetoothSocket.isConnected() && count);

            //send command to the device
            try {
                OutputStream outputStream = bluetoothSocket.getOutputStream();
                outputStream.write(48);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //get data from device
            InputStream inputStream = null;
            try {
                inputStream = bluetoothSocket.getInputStream();
                inputStream.skip(inputStream.available());

                for (int i = 0; i < 26; i++) {

                    byte b = (byte) inputStream.read();
                    System.out.println((char) b);

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            // Check if the permission was granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with Bluetooth operations
                Log.e("Bluetooth", "Permission granted");
            } else {
                // Permission denied, handle accordingly (e.g., show a message to the user)
                Log.e("Bluetooth", "Permission denied");
            }
        }
    }
    private String squareBracketRemover(String text){
        String temp = text.replaceAll("]", "");
        temp = temp.substring(1);
        return temp.replaceAll(" ","");
    }

    private String [] stringToArray(String list){
        return list.split(",");
    }
}
