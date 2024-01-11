package com.example.structuremonitoringsystem.OpenGL;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class OpenGLView extends GLSurfaceView {

    OpenGLRenderer renderer;
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

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float previousX;
    private float previousY;
    private float previousZ;

    public static void test(){
        Log.e("Tst", "Test");
    }

    public void reset(){
        renderer.setReset(true);
        renderer.resetView();
        requestRender();
    }

    public void left(){
        renderer.moveLeft();
        requestRender();
    }
/**
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.
//
//        float x = e.getX();
//        float y = e.getY();
//
//        switch (e.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//
//                float dx = x - previousX;
//                float dy = y - previousY;
//
//                // reverse direction of rotation above the mid-line
//                if (y > getHeight() / 2) {
//                    dx = dx * -.2f ;
//                }
//
//                // reverse direction of rotation to left of the mid-line
//                if (x < getWidth() / 2) {
//                    dy = dy * -.2f ;
//                }
//
//                renderer.setAngle(
//                        renderer.getAngle() +
//                                ((dx + dy) * TOUCH_SCALE_FACTOR));
//                Log.e("Coordinates", "X: " + dx);
//                Log.e("Coordinates", "Y: " + dy);
//                requestRender();
//        }
//
//        previousX = x;
//        previousY = y;
//        return true;

        float x = e.getX();
        float y = e.getY();
        float z = 0;

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float dx = x - previousX;
                float dy = y - previousY;

                // Scale the rotation based on touch sensitivity
                dx *= TOUCH_SCALE_FACTOR;
                dy *= TOUCH_SCALE_FACTOR;

                // Reverse direction of rotation based on touch position
                if (y > getHeight() / 2) {
                    dx = -dx;
                }

                // Reverse direction of rotation based on touch position
                if (x < getWidth() / 2) {
                    dy = -dy;
                }

                // Use dy directly for Z-axis rotation
                Log.e("Touch Scale Factor", "tCF: " + TOUCH_SCALE_FACTOR);
                Log.e("Prev Coordinates", "pX: " + previousX);
                Log.e("Prev Coordinates", "pY: " + previousY);
                Log.e("Current Coordinates", "cX: " + dx);
                Log.e("Current Coordinates", "cY: " + dy);

                renderer.rotate(dx, dy, 0f);

                previousX = 0;
                previousY = 0;
                previousZ = z;
                // Request a render to update the view
                requestRender();
        }


        return true;
    }*/
}
