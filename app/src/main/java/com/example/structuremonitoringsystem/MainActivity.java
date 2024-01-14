package com.example.structuremonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.structuremonitoringsystem.Bluetooth.BluetoothConnection;
import com.example.structuremonitoringsystem.OpenGL.OpenGLView;
import com.example.structuremonitoringsystem.Variables.GlobalVariable;

public class MainActivity extends AppCompatActivity {

    Context context;
    private OpenGLView openGLView;
    private BluetoothConnection bluetoothConnection;

    //movement btn
    private AppCompatButton upBtn, downBtn, leftBtn, rightBtn, centerBtn;

    //rotation btn
    private AppCompatButton rotateUpBtn, rotateDownBtn, rotateRightBtn, rotateLeftBtn,
            rotateCenterBtn, rotate45Btn, rotate315Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalVariable globalVariable = new GlobalVariable();

        openGLView = (OpenGLView) findViewById(R.id.openGLView);

        //Move Btn
        upBtn = (AppCompatButton) findViewById(R.id.upBtn);
        downBtn = (AppCompatButton) findViewById(R.id.downBtn);
        leftBtn = (AppCompatButton) findViewById(R.id.leftBtn);
        rightBtn = (AppCompatButton) findViewById(R.id.rightBtn);
        centerBtn = (AppCompatButton) findViewById(R.id.centerBtn);

        //Rotate Btn
        rotateUpBtn = (AppCompatButton) findViewById(R.id.rotateUpBtn);
        rotateDownBtn = (AppCompatButton) findViewById(R.id.rotateDownBtn);
        rotateRightBtn = (AppCompatButton) findViewById(R.id.rotateRightBtn);
        rotateLeftBtn = (AppCompatButton) findViewById(R.id.rotateLeftBtn);
        rotateCenterBtn = (AppCompatButton) findViewById(R.id.rotateCenterBtn);
        rotate45Btn = (AppCompatButton) findViewById(R.id.rotate45Btn);
        rotate315Btn = (AppCompatButton) findViewById(R.id.rotate315Btn);

        context = this;


        upBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Controller", "Up");
                openGLView.moveObject(0, 0.02f, 0);
            }
        });

        downBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Controller", "Down");
                openGLView.moveObject(0, -0.02f, 0);
            }
        });
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Controller", "Left");
                openGLView.moveObject(-0.02f, 0, 0);
            }
        });
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Controller", "Right");
                openGLView.moveObject(0.02f, 0, 0f);
            }
        });
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGLView.test();
                String MAC = globalVariable.getDeviceMacList(context);
                String name= globalVariable.getDeviceNameList(context);
                Log.e("MAC", MAC+"");
                Log.e("Name", name+"");
            }
        });

        rotateUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGLView.rotateObject(1f, 0f, 0f, 1f, 2f);
            }
        });
        rotateDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGLView.rotateObject(-1f, 0f, 0f, -1f, 2f);
            }
        });

        rotateLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGLView.rotateObject(0f, -1f, 0f, -1f, 2f);
            }
        });
        rotateRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGLView.rotateObject(0f, 1f, 0f, 1f, 2f);
            }
        });
        rotateCenterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGLView.rotateObject(0, 0, 0f, 0f, 2f);
            }
        });
        rotate45Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGLView.rotateObject(0f, 0f, 1f, 1f, 2f);
            }
        });
        rotate315Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGLView.rotateObject(0f, 0f, -1f, -1f, 2f);
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