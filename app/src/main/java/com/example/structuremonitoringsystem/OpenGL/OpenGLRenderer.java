package com.example.structuremonitoringsystem.OpenGL;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.util.Log;

import com.example.structuremonitoringsystem.OpenGL.Objects.Cube;
import com.example.structuremonitoringsystem.OpenGL.Objects.CustomCube;
import com.example.structuremonitoringsystem.OpenGL.Objects.Square;
import com.example.structuremonitoringsystem.OpenGL.Objects.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private final Context context;
    private Triangle triangle;
    private Square square;
    private CustomCube customCube;

    private Cube cube;
    private float[] scratch = new float[16];
    private final float[] vPMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private float[] rotationMatrix = new float[16];

    // vPMatrix is an abbreviation for "Model View Projection Matrix"
    private float objectPositionX = 0.0f;
    private float objectPositionY = 0.0f;
    private float objectPositionZ = 0.0f;

    private float objectRotationX = 0f;

    public float getObjectRotationX() {
        return objectRotationX;
    }

    public float getObjectRotationY() {
        return objectRotationY;
    }

    public float getObjectRotationZ() {
        return objectRotationZ;
    }

    private float objectRotationY = 0f;
    private float objectRotationZ = 0f;

    public float getAngle() {
        return angle;
    }

    //until what degree will it go
    private float angle = 0f;

    //how long will it take for the object to rotate a full objectRotationInDegree Variable 1000f = 1s
    private float objectRotateDuration = 0f;

    private boolean isFirstRun = true;

    private float oWidth = 0f;
    private float oHeight = 0f;
    private float oThickness = 0f;


    public void setFirstRun(boolean isFirstRun){
        this.isFirstRun = isFirstRun;
    }

    public void setPosition(float xAxis, float yAxis, float zAxis ){
        objectPositionX += xAxis;
        objectPositionY += yAxis;
        objectPositionZ += zAxis;
    }

    public void setRotation(float xRotation, float yRotation, float zRotation, float a, float rotationDuration){
        objectRotationX += xRotation;
        objectRotationY += yRotation;
        objectRotationZ += zRotation;
        angle += a;
        objectRotateDuration = rotationDuration;
    }

    public OpenGLRenderer(Context context, float width, float height, float thickness) {
        this.context = context;
        oWidth = width;
        oHeight = height;
        oThickness = thickness;
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
        cube = new Cube();

        customCube = new CustomCube(oWidth,oHeight,oThickness);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0,0,width, height);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);


        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 2, 22);
    }


    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(viewMatrix, 0, 0, 0, 4, 0f, 0f, 0f, 0f, 1.0f, 0.0f);

        // Calculate the projection and view transformation
        Matrix.multiplyMM(vPMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

        moveObject();
        rotateObject();

        customCube.draw(scratch, viewMatrix);
    }

    public void moveObject(){

        float[] translationMatrix = new float[16];
        // Update the object's position based on translation
        Matrix.setIdentityM(translationMatrix, 0);
        Matrix.translateM(translationMatrix, 0, objectPositionX, objectPositionY, objectPositionZ);
        Matrix.multiplyMM(vPMatrix, 0, vPMatrix, 0, translationMatrix, 0);

        // Update the object's position for the next frame
        // objectPositionX += 0.005f; // Adjust this value based on your desired movement speed

    }

    public void rotateObject(){

        //animate the object
        long time = SystemClock.uptimeMillis() % 2000L;
        //this will complete a full rotation of the angle with in the specified time 1000f is equal to 1s
        //remove "this." at this.angle to animate within a set of time
        //and change the this.angle at float angle to 360f
//        float angle = (360 / objectRotateDuration) * ((int) time);
        float tempX = objectRotationX;
        float tempY = objectRotationY;
        float tempZ = objectRotationZ;

        if(tempX < 0){
            tempX = -tempX;
        }

        if(tempY < 0){
            tempY = -tempY;
        }

        if(tempZ < 0){
            tempZ = -tempZ;
        }
        float finalAngle = (tempX + tempY+ tempZ)/6;

//        angle = (objectPositionX + objectRotationY+ objectRotationZ)/6;
        //finalAngle = (finalAngle % 360 + 360) % 360;

        Matrix.setRotateM(rotationMatrix, 0, finalAngle, objectRotationX, objectRotationY, objectRotationZ);
        Log.e("Angle", finalAngle+" + " + objectRotationX+" + "  + objectRotationY+" + "  + objectRotationZ );

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
