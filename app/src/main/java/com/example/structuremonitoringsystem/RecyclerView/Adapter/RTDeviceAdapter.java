package com.example.structuremonitoringsystem.RecyclerView.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.Item.DeviceItem;
import com.example.structuremonitoringsystem.Item.ObjectItem;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseSensor;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.RTMonitoring;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.ObjectViewHolder;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.RTDeviceViewHolder;
import com.example.structuremonitoringsystem.Settings;

import java.util.List;

public class RTDeviceAdapter extends RecyclerView.Adapter<RTDeviceViewHolder> {

    Context context;
    List<DeviceItem> items;
    DatabaseSensor sensors;
    public RTDeviceAdapter(Context context, List<DeviceItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public RTDeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RTDeviceViewHolder(LayoutInflater.from(context).inflate(R.layout.card_device_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RTDeviceViewHolder holder, int position) {
        sensors = new DatabaseSensor(context);
//
        String deviceID = items.get(position).getDeviceID();
        String deviceName = items.get(position).getDeviceName();
        String sensorListID = items.get(position).getSensorListID();
        int count = sensors.getNumOfSensors(sensorListID);

        holder.numOfSensors.setText(String.valueOf(count));
        holder.deviceName.setText(deviceName);
//

        holder.initialState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.clickedState.getVisibility() == View.GONE){
                    holder.clickedState.setVisibility(View.VISIBLE);
                }
                else{
                    holder.clickedState.setVisibility(View.GONE);
                }

            }
        });

        holder.rltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RTMonitoring.class);
                intent.putExtra("idList", sensorListID);
                intent.putExtra("key", "fuck you");
                startActivity(context, intent, null);

            }
        });

        holder.logHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RTMonitoring.class);
                intent.putExtra("idList", sensorListID);
                intent.putExtra("key", "log");
                startActivity(context, intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
