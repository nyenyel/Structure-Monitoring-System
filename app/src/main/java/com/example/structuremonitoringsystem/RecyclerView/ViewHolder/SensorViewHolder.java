package com.example.structuremonitoringsystem.RecyclerView.ViewHolder;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.R;

public class SensorViewHolder extends RecyclerView.ViewHolder {

    public TextView sensorText;
    public RelativeLayout sensorBtn;
    public SensorViewHolder(@NonNull View itemView) {
        super(itemView);

        sensorBtn = itemView.findViewById(R.id.sensorBtn);
        sensorText = itemView.findViewById(R.id.sensorText);
    }
}
