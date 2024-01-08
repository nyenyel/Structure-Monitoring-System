package com.example.structuremonitoringsystem.OpenGL;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.structuremonitoringsystem.OpenGL.Objects.Square;
import com.example.structuremonitoringsystem.OpenGL.Objects.Triangle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class OpenGLRenderer implements GLSurfaceView.Renderer {

    private Triangle triangle;
    private Square square;

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
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        square.draw();
    }
}
