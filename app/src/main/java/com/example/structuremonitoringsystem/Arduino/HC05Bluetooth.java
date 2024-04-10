package com.example.structuremonitoringsystem.Arduino;

import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;
import com.example.structuremonitoringsystem.OtherFunction.Popups;

import java.io.IOException;
import java.io.InputStream;

public class HC05Bluetooth {
    Context context;

    public HC05Bluetooth(Context context) {
        this.context = context;
    }

    public void receiveData(final BluetoothSocket bluetoothSocket, final DataListener listener) {
//        Popups popups = new Popups(context);
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                InputStream inputStream = null;
//                try {
//                    inputStream = bluetoothSocket.getInputStream();
//                    StringBuilder messageBuffer = new StringBuilder();
//
//                    while (true) {
//                        int byteRead = inputStream.read();
//                        if (byteRead == -1) {
//                            // End of stream reached, break the loop
//                            break;
//                        }
//
//                        // Assuming newline ('\n') is the end marker in the protocol
//                        if (byteRead == '\n') {
//                            // Process the complete message
//                            String message = messageBuffer.toString();
//                            if (listener != null) {
//                                listener.onDataReceived(message);
//                            }
//                            // Clear the message buffer for the next message
//                            messageBuffer.setLength(0);
//                        } else {
//                            // Append the byte to the message buffer
//                            messageBuffer.append((char) byteRead);
//                        }
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                InputStream inputStream = null;
                try {
                    inputStream = bluetoothSocket.getInputStream();
                    StringBuilder messageBuffer = new StringBuilder();

                    while (true) {
                        int byteRead = inputStream.read();
                        if (byteRead == -1) {
                            // End of stream reached, break the loop
                            break;
                        }

                        // Assuming newline ('\n') is the end marker in the protocol
                        if (byteRead == '\n') {
                            // Process the complete message
                            String message = messageBuffer.toString();
                            if (listener != null) {
                                listener.onDataReceived(message);
                            }
                            // Clear the message buffer for the next message
                            messageBuffer.setLength(0);
                        } else {
                            // Append the byte to the message buffer
                            messageBuffer.append((char) byteRead);
                        }
                    }
                } catch (IOException e) {
                    // Handle IOException gracefully
                    e.printStackTrace();
//                    popups.bluetoothFailed();
                } catch (Exception e) {
                    // Handle any other unexpected exceptions
                    e.printStackTrace();
//                    popups.bluetoothFailed();
                } finally {
                    // Close the input stream if it was opened
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        if(bluetoothSocket.isConnected()){
            thread.start();
        }else{
            Popups popups = new Popups(context);
            popups.bluetoothFailed();
        }

    }

    public int getNumOfDevice(BluetoothSocket bluetoothSocket){
        BluetoothConnection bluetoothConnection = new BluetoothConnection();
        InputStream inputStream = null;
        String message = "";
        try {
            inputStream = bluetoothSocket.getInputStream();
            StringBuilder messageBuffer = new StringBuilder();

            while (true) {
                int byteRead = inputStream.read();
                if (byteRead == -1) {
                    // End of stream reached, break the loop

                    break;
                }

                // Assuming newline ('\n') is the end marker in the protocol
                if (byteRead == '\n') {
                    // Process the complete message
                    message = messageBuffer.toString();
                    bluetoothConnection.bluetoothDc(bluetoothSocket);
                } else {
                    // Append the byte to the message buffer
                    messageBuffer.append((char) byteRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String arrReceivedData[] = message.trim().split(",");
        int numOfDevice = arrReceivedData.length/3;
        return numOfDevice;
    }

    // Interface for data callbacks
    public interface DataListener {
        void onDataReceived(String data);
    }
}