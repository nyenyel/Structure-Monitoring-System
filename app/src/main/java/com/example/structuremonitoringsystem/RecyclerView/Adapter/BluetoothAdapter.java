package com.example.structuremonitoringsystem.RecyclerView.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;
import com.example.structuremonitoringsystem.Testing.BluetoothTerminalTest;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.RecyclerView.Item.BluetoothItem;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.BluetoothViewHolder;

import java.util.List;

public class BluetoothAdapter extends RecyclerView.Adapter<BluetoothViewHolder> {

    Context context;
    List<BluetoothItem> items;
    BluetoothConnection bluetoothConnection;


    public BluetoothAdapter(Context context, List<BluetoothItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public BluetoothViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BluetoothViewHolder(LayoutInflater.from(context).inflate(R.layout.card_bluetooth_device, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BluetoothViewHolder holder, int position) {
        bluetoothConnection = new BluetoothConnection();
        String selectedDeviceMAC = items.get(position).getDeviceMac();
        String selectedDeviceName = items.get(position).getDeviceName();

        holder.deviceName.setText(items.get(position).getDeviceName());
        holder.deviceMac.setText(selectedDeviceMAC);
        String uuid = bluetoothConnection.deviceUUID(context, selectedDeviceMAC);

        holder.deviceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BluetoothTerminalTest.class);
                intent.putExtra("UUID", uuid);
                intent.putExtra("MAC", selectedDeviceMAC);
                intent.putExtra("name", selectedDeviceName);
                startActivity(context, intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
