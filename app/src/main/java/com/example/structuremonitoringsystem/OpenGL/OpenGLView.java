package com.example.structuremonitoringsystem.OpenGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class OpenGLView extends GLSurfaceView {

    OpenGLRenderer renderer;
    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;
    private float previousZ;

    public static void test(){
        Log.e("Tst", "Test");
    }
    public OpenGLView(Context context) {
        super(context);
        init(context);

    }

    public OpenGLView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        setEGLContextClientVersion(2);
        setPreserveEGLContextOnPause(true);


        // Pass the context to the OpenGLRenderer constructor
        renderer = new OpenGLRenderer(context);
        setRenderer(renderer);

        // Render the view only when there is a change in the drawing data
//        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    public void left(){
        renderer.moveObject();
        requestRender();
    }
}
