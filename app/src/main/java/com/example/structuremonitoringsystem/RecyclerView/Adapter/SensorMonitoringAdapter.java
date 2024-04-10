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

import com.example.structuremonitoringsystem.DataLogs;
import com.example.structuremonitoringsystem.Gyroscope;
import com.example.structuremonitoringsystem.Item.DeviceItem;
import com.example.structuremonitoringsystem.Item.ObjectItem;
import com.example.structuremonitoringsystem.Item.SensorItem;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseSensor;
import com.example.structuremonitoringsystem.Monitoring;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.RTMonitoring;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.ObjectViewHolder;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.RTDeviceViewHolder;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.SensorViewHolder;
import com.example.structuremonitoringsystem.Settings;

import java.util.List;

public class SensorMonitoringAdapter extends RecyclerView.Adapter<SensorViewHolder> {

    Context context;
    List<SensorItem> items;
    DatabaseSensor sensors;
    public SensorMonitoringAdapter(Context context, List<SensorItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public SensorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SensorViewHolder(LayoutInflater.from(context).inflate(R.layout.card_sensors, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SensorViewHolder holder, int position) {
        sensors = new DatabaseSensor(context);
//
        String sensorName = items.get(position).getSensorName();
        String id = items.get(position).getId();
        String log = items.get(position).getLog();
        holder.sensorText.setText(sensorName);

        holder.sensorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String position = "";
                if(sensorName.equals("Accelerometer")) {
                    if (id.equals("device1")) {
                        position = "0,1,2";
                    } else if (id.equals("device2")) {
                        position = "3,4,5";
                    }
                } else if (sensorName.equals("Gyroscope")) {
                    if (id.equals("device1")) {
                        position = "6,7,8";
                    } else if (id.equals("device2")) {
                        position = "9,10,11";
                    }
                }

                if(!sensorName.equals("Gyroscope")) {
                    if (!log.equals("log")) {
                        Intent intent = new Intent(context, Monitoring.class);
                        intent.putExtra("pos", position);
                        intent.putExtra("id", id);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(context, intent, null);
                    } else {
                        Intent intent = new Intent(context, DataLogs.class);
                        intent.putExtra("pos", position);
                        intent.putExtra("sensor", sensorName);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(context, intent, null);
                    }
                }
                else{
                    Intent intent = new Intent(context, Gyroscope.class);
                    intent.putExtra("pos", position);
                    intent.putExtra("sensor", sensorName);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(context, intent, null);
                }
//                Intent intent = new Intent(context, Monitoring.class);


            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
