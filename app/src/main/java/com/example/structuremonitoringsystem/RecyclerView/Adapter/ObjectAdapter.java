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
import com.example.structuremonitoringsystem.R;
import com.example.structuremonitoringsystem.Item.ObjectItem;
import com.example.structuremonitoringsystem.RecyclerView.ViewHolder.ObjectViewHolder;
import com.example.structuremonitoringsystem.Settings;

import java.util.List;

public class ObjectAdapter extends RecyclerView.Adapter<ObjectViewHolder> {

    Context context;
    List<ObjectItem> items;
    DatabaseObjectSize objectSize;
    public ObjectAdapter(Context context, List<ObjectItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ObjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ObjectViewHolder(LayoutInflater.from(context).inflate(R.layout.card_object_template, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectViewHolder holder, int position) {
        objectSize = new DatabaseObjectSize(context);

        String objectId = items.get(position).getId();
        String objectTempName = items.get(position).getTempName();
        String objectHeight = items.get(position).getHeight();
        String objectWidth = items.get(position).getWidth();
        String objectThickness = items.get(position).getThickness();

        holder.tempName.setText(objectTempName);
        holder.height.setText(String.valueOf(Float.parseFloat(objectHeight)/10));
        holder.width.setText(String.valueOf(Float.parseFloat(objectWidth)/10));
        holder.thickness.setText(String.valueOf(Float.parseFloat(objectThickness)/10));

        holder.openGLView.init(
                context.getApplicationContext(),
                Float.parseFloat(objectWidth),
                Float.parseFloat(objectHeight),
                Float.parseFloat(objectThickness));
        holder.openGLView.rotateObject(45,45,120,-1,2f);
        holder.templateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Settings.class);
                String oldID = "";
                Cursor cursor = objectSize.getDefaultObjectSize();
                try {
                    if (cursor != null && cursor.moveToFirst()){
                        do {
                            oldID = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                        }while (cursor.moveToNext());
                    }
                }finally {
                    if (cursor != null){cursor.close();}
                }
                
                objectSize.changeDefaultObjectSize(oldID, objectId);
                startActivity(context, intent, null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
