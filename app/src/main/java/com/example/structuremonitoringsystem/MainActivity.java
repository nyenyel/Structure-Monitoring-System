package com.example.structuremonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.structuremonitoringsystem.OpenGL.OpenGLView;

public class MainActivity extends AppCompatActivity {

    Context context;
    private OpenGLView openGLView;
    private AppCompatButton upBtn, downBtn, leftBtn, rightBtn, centerBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        openGLView = (OpenGLView) findViewById(R.id.openGLView);
        upBtn = (AppCompatButton) findViewById(R.id.upBtn);
        downBtn = (AppCompatButton) findViewById(R.id.downBtn);
        leftBtn = (AppCompatButton) findViewById(R.id.leftBtn);
        rightBtn = (AppCompatButton) findViewById(R.id.rightBtn);
        centerBtn = (AppCompatButton) findViewById(R.id.centerBtn);

        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Controller", "Up");
            }
        });

        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Controller", "Down");
            }
        });
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Controller", "Left");
            }
        });
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Controller", "Right");
            }
        });
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGLView.test();
                openGLView.reset();
                openGLView.reset();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        openGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        openGLView.onPause();
    }
}