package com.example.structuremonitoringsystem.OpenGL.Objects;

import android.opengl.GLES20;

import com.example.structuremonitoringsystem.OpenGL.OpenGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle {

    private final int mProgram;
    private final String vertexShaderCode =
            "attribute vec4 vPosition;  \n" +
            "void main(){               \n" +
            " gl_Position = vPosition; \n" +
            "}  \n";

    private final String fragmentShaderCode =
            "precision mediump float;  \n" +
            "uniform vec4 vColor;  \n" +
            "void main(){               \n" +
            " gl_FragColor = vColor; \n" +
            "}  \n";

    private FloatBuffer vertexBuffer;

    //num of coordinates per vertex in the array
    static final int COORDS_PER_VERTEX = 3;
    static float triangleCoords [] = { // counter clockwise
            // array components are "array[] = {x axis, y axis, z axis}"
          0.0f, .5f, 0.0f,    //top
          1f, 0.00f, 0.0f,    //bottom right
          0.0f, -.5f, 0.0f,   //bottom left
    };

    //object Color
    float triangleColor[] = {1f, 0, 0, 1f};

    public Triangle(){

        //initialize vertex byte buffer for shape coordinates
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(

                //number of coordinate values multiply by 4 bytes per float
                triangleCoords.length * 4
        );

        //use the device hardware's native byte other
        byteBuffer.order(ByteOrder.nativeOrder());

        //create a floating point buffer from the ByteBuffer
        vertexBuffer = byteBuffer.asFloatBuffer();

        //add the coordinates to the FloatBuffer
        vertexBuffer.put(triangleCoords);

        //set the buffer to read first coordinate
        vertexBuffer.position(0);

        int vertexShader = OpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = OpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        //create empty OoenGL ES Program();
        mProgram = GLES20.glCreateProgram();

        //add the vertex shader program
        GLES20.glAttachShader(mProgram, vertexShader);

        //add the fragment shader program
        GLES20.glAttachShader(mProgram, fragmentShader);

        //make the program executable
        GLES20.glLinkProgram(mProgram);
    }

    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = triangleCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public void draw(){
        //add the program to the environment(surface)
        GLES20.glUseProgram(mProgram);

        //get handle to the triangle vertices
        GLES20.glEnableVertexAttribArray(positionHandle);

        //Enable handle to triangle vertices
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        //get handle fragment shaders vColor member
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

        //set triangle color
        GLES20.glUniform4fv(colorHandle, 1, triangleColor, 0);

        //draw the Triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        //disable vertex array
        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
