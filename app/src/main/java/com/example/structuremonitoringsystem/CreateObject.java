package com.example.structuremonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class CreateObject extends AppCompatActivity {

    private AppCompatButton createObj;
    private EditText height,width,thickness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_object);

        createObj = (AppCompatButton) findViewById(R.id.createObj);

        height = (EditText) findViewById(R.id.height);
        width = (EditText) findViewById(R.id.width);
        thickness = (EditText) findViewById(R.id.thickness);

        createObj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    float getHeight = Float.parseFloat(height.getText().toString());
                    float getWidth = Float.parseFloat(width.getText().toString());
                    float getThickness = Float.parseFloat(thickness.getText().toString());

                    Intent intent = new Intent(CreateObject.this, MainActivity.class);
                    intent.putExtra("height", getHeight);
                    intent.putExtra("width", getWidth);
                    intent.putExtra("thickness", getThickness);
                    startActivity(intent);

                }catch (NumberFormatException e){
                    Log.e("Error", "Please Enter Data");
                }

            }
        });
    }
}