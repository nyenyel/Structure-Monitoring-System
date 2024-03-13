package com.example.structuremonitoringsystem.RecyclerView.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.structuremonitoringsystem.OpenGL.OpenGLView;
import com.example.structuremonitoringsystem.R;

public class ObjectViewHolder extends RecyclerView.ViewHolder {

    public OpenGLView openGLView;
    public TextView width, height, thickness, tempName;
    public ObjectViewHolder(@NonNull View itemView) {
        super(itemView);
        openGLView = itemView.findViewById(R.id.openGLView);
        width = itemView.findViewById(R.id.widthValue);
        height = itemView.findViewById(R.id.heightValue);
        thickness = itemView.findViewById(R.id.thicknessValue);
        tempName = itemView.findViewById(R.id.tempName);
    }
}
