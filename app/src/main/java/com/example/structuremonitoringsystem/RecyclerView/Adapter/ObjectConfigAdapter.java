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

import com.example.structuremonitoringsystem.LocalDatabase.DatabaseDefaultSettings;
import com.example.structuremonitoringsystem.LocalDatabase.DatabaseObjectSize;
import com.example.structuremonitoringsystem.OtherFunction.Popups;
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.Item.ObjectItem;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.ObjectConfigViewHolder;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.ObjectViewHolder;
import com.example.structuremonitoringsystem.Settings;

import java.util.List;

public class ObjectConfigAdapter extends RecyclerView.Adapter<ObjectConfigViewHolder> {

    Context context;
    List<ObjectItem> items;
    DatabaseObjectSize objectSize;
    Popups popups;
    public ObjectConfigAdapter(Context context, List<ObjectItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ObjectConfigViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ObjectConfigViewHolder(LayoutInflater.from(context).inflate(R.layout.card_object_template_config, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectConfigViewHolder holder, int position) {
        objectSize = new DatabaseObjectSize(context);
        popups = new Popups(context);

        String objectId = items.get(position).getId();
        String objectTempName = items.get(position).getTempName();
        String objectHeight = items.get(position).getHeight();
        String objectWidth = items.get(position).getWidth();
        String objectThickness = items.get(position).getThickness();

        holder.tempName.setText(objectTempName);
        holder.height.setText(String.valueOf(objectHeight));
        holder.width.setText(String.valueOf(objectWidth));
        holder.thickness.setText(String.valueOf(objectThickness));

        holder.openGLView.init(
                context.getApplicationContext(),
                Float.parseFloat(objectWidth),
                Float.parseFloat(objectHeight),
                Float.parseFloat(objectThickness));
        holder.openGLView.rotateObject(45,45,120,-1,2f);
        holder.initialState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.configHolder.getVisibility() == View.VISIBLE){
                    holder.configHolder.setVisibility(View.GONE);
                }
                else if(holder.configHolder.getVisibility() == View.GONE){
                    holder.configHolder.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popups.editTemplate(objectSize, objectId, objectTempName, objectWidth,objectHeight, objectThickness);
            }
        });
        holder.delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popups.deleteTemplateWarning(objectSize, objectId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
