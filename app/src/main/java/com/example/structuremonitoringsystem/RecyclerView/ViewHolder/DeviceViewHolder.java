package com.example.structuremonitoringsystem.RecyclerView.ViewHolder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.OpenGL.OpenGLView;
import com.example.structuremonitoringsystem.R;

public class DeviceViewHolder extends RecyclerView.ViewHolder {

    public TextView deviceName, numOfSensors;
    public RelativeLayout initialState, clickedState, logHistoryBtn, rltBtn;
    public DeviceViewHolder(@NonNull View itemView) {
        super(itemView);
        numOfSensors = itemView.findViewById(R.id.numOfSensors);
        deviceName = itemView.findViewById(R.id.deviceName);

        initialState = itemView.findViewById(R.id.initialState);
        clickedState = itemView.findViewById(R.id.clickedState);
        logHistoryBtn = itemView.findViewById(R.id.logHistoryBtn);
        rltBtn = itemView.findViewById(R.id.realtimeBtn);
    }
}
