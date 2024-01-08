package com.example.structuremonitoringsystem.OpenGL.Objects;

import android.opengl.GLES20;

import com.example.structuremonitoringsystem.OpenGL.OpenGLRenderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Square {
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

    // num of coordinates per vertex in the array
    static final int COORDS_PER_VERTEX = 3;
    static float squareCoords[] = { // counter-clockwise
            -0.5f,  0.5f, 0.0f,   // top left
            0.5f,  0.5f, 0.0f,   // top right
            0.5f, -0.5f, 0.0f,   // bottom right
            -0.5f, -0.5f, 0.0f    // bottom left
    };

    // object Color
    float squareColor[] = {0f, 1f, 0f, 1f}; // Green color for the square

    public Square() {
        // initialize vertex byte buffer for shape coordinates
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(squareCoords.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        vertexBuffer = byteBuffer.asFloatBuffer();
        vertexBuffer.put(squareCoords);
        vertexBuffer.position(0);

        int vertexShader = OpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = OpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    private int positionHandle;
    private int colorHandle;

    private final int vertexCount = squareCoords.length / COORDS_PER_VERTEX;
    private final int vertexStride = COORDS_PER_VERTEX * 4;

    public void draw() {
        GLES20.glUseProgram(mProgram);
        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(colorHandle, 1, squareColor, 0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);

        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
