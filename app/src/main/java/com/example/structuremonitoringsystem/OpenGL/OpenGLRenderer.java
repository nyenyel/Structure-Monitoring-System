package com.example.structuremonitoringsystem.OpenGL;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.example.structuremonitoringsystem.OpenGL.Objects.Square;
import com.example.structuremonitoringsystem.OpenGL.Objects.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    private Triangle triangle;
    private Square square;
    public volatile float mAngle;

    private float[] scratch = new float[16];
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private float[] rotationMatrix = new float[16];

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private float objectPositionX = 0.0f;
    private float objectPositionY = 0.0f;
    private float objectPositionZ = 0.0f;

    private boolean isFirstRun = true;


    public void setFirstRun(boolean isFirstRun){
        this.isFirstRun = isFirstRun;
    }

    public float getAngle() {
        return mAngle;
    }

    public void setAngle(float angle) {
        mAngle = angle;
    }

    public OpenGLRenderer(Context context) {
        this.context = context;
    }

    public static int loadShader(int type, String shaderCode){
        //create a vertex shader type or a fragment shader type
        int shader = GLES20.glCreateShader(type);

        //add the sourcec code to the shader and compile
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.2f, 0.2f, 0.2f,  0.03f);
        triangle = new Triangle();
        square = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 4, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        moveObject();
        rotateObject();

        Log.d("rotation", "rotationMatrix: " + mergeFloatArrayToString(rotationMatrix, ","));

        // Draw triangle
        square.draw(scratch);
    }

    public void moveObject(){

        float[] translationMatrix = new float[16];
        // Update the object's position based on translation
        Matrix.setIdentityM(translationMatrix, 0);
        Matrix.translateM(translationMatrix, 0, -objectPositionX, objectPositionX, -objectPositionX);
        Matrix.multiplyMM(vPMatrix, 0, vPMatrix, 0, translationMatrix, 0);

        // Update the object's position for the next frame
        objectPositionX += 0.005f; // Adjust this value based on your desired movement speed

    }

    public void rotateObject(){

        long time = SystemClock.uptimeMillis() % 2000L;
        float angle = (360.0f / 2000.0f) * ((int) time);
        Matrix.setRotateM(rotationMatrix, 0, angle, 0.8f, 0.9f, -0.60f);

        // Combine the rotation matrix with the projection and camera view
        // Note that the vPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, vPMatrix, 0, rotationMatrix, 0);
    }
    public static String mergeFloatArrayToString(float[] floatArray, String delimiter) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < floatArray.length; i++) {
            stringBuilder.append(floatArray[i]);

            // Add the delimiter if it's not the last element
            if (i < floatArray.length - 1) {
                stringBuilder.append(delimiter);
            }
        }

        return stringBuilder.toString();
    }
}
