package com.example.structuremonitoringsystem.RecyclerView.ViewHolder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.R;

public class BluetoothViewHolder extends RecyclerView.ViewHolder {
    public TextView deviceName, deviceMac;
    public RelativeLayout deviceBtn;
    public BluetoothViewHolder(@NonNull View itemView) {
        super(itemView);
        deviceName = itemView.findViewById(R.id.deviceName);
        deviceMac = itemView.findViewById(R.id.deviceMAC);
        deviceBtn = itemView.findViewById(R.id.deviceBtn);
    }
}
