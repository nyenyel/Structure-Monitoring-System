package com.example.structuremonitoringsystem.OpenGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.example.structuremonitoringsystem.Testing.MainActivity;

public class OpenGLView extends GLSurfaceView {

    OpenGLRenderer renderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;
    private float previousZ;

    private float width = 0f;
    private float height = 0f;
    public float thickness = 0f;

    public static void test(String loc){
        Log.e("Tsts", "Test: "+loc );
    }
    public OpenGLView(Context context) {
        super(context);
//        init(context);

    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        init(context);
    }

    public void init(Context context, float width, float height, float thickness) {
        if (renderer == null) { // Check if the renderer is already set
            setEGLContextClientVersion(2);
            setPreserveEGLContextOnPause(true);

            setObjectSize(width, height, thickness);
            // Pass the context to the OpenGLRenderer constructor
            renderer = new OpenGLRenderer(context, width, height, thickness);
            setRenderer(renderer);

            // Render the view only when there is a change in the drawing data
            setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        }
    }



    public void moveObject(float x, float y, float z){
        renderer.setPosition(x, y ,z);
        requestRender();
    }

    public void rotateObject(float x, float y, float z, float deg, float time){
        renderer.setRotation(x, y, z, deg, time*1000);
        requestRender();
    }

    public void setObjectSize(float width, float height, float thickness){
        this.width = width;
        this.height = height;
        this.thickness =thickness;
    }

    public float getCurrentRotationX(){
        return renderer.getObjectRotationX();
    }

    public float getCurrentRotationY(){
        return renderer.getObjectRotationY();
    }

    public float getCurrentRotationZ(){
        return renderer.getObjectRotationZ();
    }

    public float getCurrentAngle(){
        return renderer.getAngle();
    }
}
