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
import com.example.structuremonitoringsystem.OtherFunction.Popups;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.RTMonitoring;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.DeviceViewHolder;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.ObjectViewHolder;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.RTDeviceViewHolder;
import com.example.structuremonitoringsystem.Settings;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceViewHolder> {

    Context context;
    List<DeviceItem> items;
    DatabaseSensor sensors;
    String temp ="";
    public DeviceAdapter(Context context, List<DeviceItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DeviceViewHolder(LayoutInflater.from(context).inflate(R.layout.card_device_list_2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        sensors = new DatabaseSensor(context);
//
        Popups popups = new Popups(context);
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
                popups.selectSensor(new Popups.SensorSelectionListener() {
                    @Override
                    public void onSensorSelected(String sensor) {
                        DatabaseSensor databaseSensor = new DatabaseSensor(context);
                        databaseSensor.addSensor(temp, deviceID, 3);
                    }
                });



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
