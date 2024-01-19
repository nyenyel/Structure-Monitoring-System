package com.example.structuremonitoringsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

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

    private float x = 0f;
    private float y = 0f;
    private float z = 0f;
    private  float a = 0f;

    private float fullRotation = 360*6;

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getThickness() {
        return thickness;
    }

    private static float width, height, thickness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        width = (getIntent().getFloatExtra("width", 0f))/100;
        height = (getIntent().getFloatExtra("height", 0f))/100;
        thickness = (getIntent().getFloatExtra("thickness", 0f))/100;
        Log.e("Test", thickness+"");
//        openGLView.thickness = thickness;
        setContentView(R.layout.activity_main);


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

            }
        });

        rotateUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isZero = openGLView.getCurrentRotationX() <= 0;
                boolean angleIsZero = openGLView.getCurrentAngle() == 0;
                x = -6f;
                y = 0f;
                z = 0f;
                a = -1f;
//                if(isZero){
//                    x = fullRotation;
//                }
//                if (angleIsZero){
//                    a = 360f;
//                }
                openGLView.rotateObject(x, y , z, a, 2f);

            }
        });
        rotateDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFullyRotated = openGLView.getCurrentRotationX() >= fullRotation;
                boolean angleIs360 = openGLView.getCurrentAngle() >= 360;
                x = 6f;
                y = 0f;
                z = 0f;
                a = 1f;
//                if(isFullyRotated){
//                    x = -fullRotation;
//                }
//                if(angleIs360){
//                    a = -360;
//                }
                openGLView.rotateObject(x, y , z, a, 2f);

            }
        });

        rotateLeftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isZero = openGLView.getCurrentRotationY() <= 0;
                boolean angleIsZero = openGLView.getCurrentAngle() == 0;
                x = 0f;
                y = -6f;
                z = 0f;
                a = -1f;
//                if(isZero){
//                    y = fullRotation;
//                }
//                if (angleIsZero){
//                    a = 360f;
//                }
                openGLView.rotateObject(x, y , z, a, 2f);

            }
        });
        rotateRightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFullyRotated = openGLView.getCurrentRotationY() >= fullRotation;
                boolean angleIs360 = openGLView.getCurrentAngle() >= 360;
                x = 0f;
                y = 6f;
                z = 0f;
                a = 1f;
//                if(isFullyRotated){
//                    y = -fullRotation;
//                }
//                if(angleIs360){
//                    a = -360;
//                }
                openGLView.rotateObject(x, y , z, a, 2f);

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

                boolean isZero = openGLView.getCurrentRotationZ() <= 0;
                boolean angleIsZero = openGLView.getCurrentAngle() == 0;
                x = 0f;
                y = 0f;
                z = -6f;
                a = -1f;
//                if(isZero){
//                    z = fullRotation;
//                }
//                if (angleIsZero){
//                    a = 360f;
//                }
                openGLView.rotateObject(x, y , z, a, 2f);
            }

        });
        rotate315Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isFullyRotated = openGLView.getCurrentRotationZ() >= fullRotation;
                boolean angleIs360 = openGLView.getCurrentAngle() >= 360;
                x = 0f;
                y = 0f;
                z = 6f;
                a = 1f;
//                if(isFullyRotated){
//                    z = -fullRotation;
//                }
//                if(angleIs360){
//                    a = -360;
//                }
                openGLView.rotateObject(x, y , z, a, 2f);


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