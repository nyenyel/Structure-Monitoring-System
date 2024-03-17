package com.example.structuremonitoringsystem.Arduino;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class HC05Bluetooth {

    public void receiveData(final BluetoothSocket bluetoothSocket, final DataListener listener) {
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
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    // Interface for data callbacks
    public interface DataListener {
        void onDataReceived(String data);
    }
}