package com.example.structuremonitoringsystem.OpenGL.Objects;

import android.opengl.GLES20;
import android.util.Log;

import com.example.structuremonitoringsystem.OpenGL.OpenGLRenderer;
import com.example.structuremonitoringsystem.OtherFunction.GlobalVariable;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class CustomCube {
    private final int mProgram;

    private GlobalVariable globalVariable = new GlobalVariable();
    private FloatBuffer vertexBuffer;
    private int positionHandle;
    private int mvpMatrixHandle;

    // num of coordinates per vertex in the array
    static final int COORDS_PER_VERTEX = 3;
    static final int vertexCount = 36;
    static final int vertexStride = COORDS_PER_VERTEX * 4;
    public static float a = 100f;

    private int lightPositionHandle;
    private int lightColorHandle;

    private float[] lightPosition = {1.0f, 2.0f, 0.0f};
    private float[] lightColor = {5.0f, 5.0f, 5.0f};


    // Coordinates of the cube vertices


    // Color for cube edges (black)
    static float frontColor[] = {
            1.0f, 0.0f, 0.0f, a
    };
    static float backColor[] = {
            0.0f, 1.0f, 0.0f, a
    };
    static float aboveColor[] = {
            0.0f, 0.0f, 1.0f, a
    };
    static float bellowColor[] = {
            1.0f, 1.0f, 0.0f, a
    };
    static float rightColor[] = {
            0.0f, 1.0f, 1.0f, a
    };
    static float leftColor[] = {
            1.0f, 0.0f, 1.0f, a
    };


    public CustomCube(float width, float height, float thickness) {

        float cubeCoords[] = {
                // Front face
                -width, -height,  thickness,
                width, -height,  thickness,
                width,  height,  thickness,
                -width, -height,  thickness,
                width,  height,  thickness,
                -width,  height,  thickness,

                // Right face
                width, -height,  thickness,
                width, -height, -thickness,
                width,  height,  thickness,
                width, -height, -thickness,
                width,  height, -thickness,
                width,  height,  thickness,

                // Back face
                width, -height, -thickness,
                -width, -height, -thickness,
                width,  height, -thickness,
                -width, -height, -thickness,
                -width,  height, -thickness,
                width,  height, -thickness,

                // Left face
                -width, -height, -thickness,
                -width, -height,  thickness,
                -width,  height, -thickness,
                -width, -height,  thickness,
                -width,  height,  thickness,
                -width,  height, -thickness,

                // Top face
                -width,  height,  thickness,
                width,  height,  thickness,
                width,  height, -thickness,
                -width,  height,  thickness,
                width,  height, -thickness,
                -width,  height, -thickness,

                // Bottom face
                -width, -height,  thickness,
                width, -height,  thickness,
                width, -height, -thickness,
                -width, -height,  thickness,
                width, -height, -thickness,
                -width, -height, -thickness
        };

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(cubeCoords.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        vertexBuffer = byteBuffer.asFloatBuffer();
        OpenGLRenderer openGLRenderer;
        String coords = globalVariable.mergeFloatArrayToString(cubeCoords, ",");
        Log.e("Size", coords);
        vertexBuffer.put(cubeCoords);
        vertexBuffer.position(0);

        int vertexShader = OpenGLRenderer.loadShader(GLES20.GL_VERTEX_SHADER, getVertexShaderCode());
        int fragmentShader = OpenGLRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, getFragmentShaderCode());

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, vertexShader);
        GLES20.glAttachShader(mProgram, fragmentShader);
        GLES20.glLinkProgram(mProgram);
    }

    private String getVertexShaderCode() {
        return "uniform mat4 uMVPMatrix;\n" +
                "uniform mat4 uMVMatrix;\n" +
                "uniform vec3 uLightPosition; // Light position in world space\n" +
                "attribute vec4 vPosition;\n" +
                "attribute vec3 vNormal;\n" +
                "varying vec3 normal;\n" +
                "varying vec3 lightVector;\n" +
                "\n" +
                "void main() {\n" +
                "    gl_Position = uMVPMatrix * vPosition;\n" +
                "    vec3 modelViewVertex = vec3(uMVMatrix * vPosition);\n" +
                "    normal = mat3(uMVMatrix) * vNormal; // Transform normal to eye space\n" +
                "    lightVector = normalize(uLightPosition - modelViewVertex);\n" +
                "}\n";
    }

    private String getFragmentShaderCode() {
        return "precision mediump float;\n" +
                "uniform vec4 vColor;\n" +
                "uniform vec3 uLightColor;\n" +
                "varying vec3 normal;\n" +
                "varying vec3 lightVector;\n" +
                "\n" +
                "void main() {\n" +
                "    float ambientStrength = 0.2;\n" +
                "    vec3 ambient = ambientStrength * uLightColor;\n" +
                "\n" +
                "    float diff = max(dot(normalize(normal), lightVector), 0.0);\n" +
                "    vec3 diffuse = diff * uLightColor;\n" +
                "\n" +
                "    gl_FragColor = vColor * vec4(ambient + diffuse, 1.0);\n" +
                "}\n";
    }

    public void draw(float[] mvpMatrix, float[] mvMatrix) {
        GLES20.glUseProgram(mProgram);

        positionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(positionHandle);
        GLES20.glVertexAttribPointer(positionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, vertexStride, vertexBuffer);

        mvpMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        GLES20.glUniformMatrix4fv(mvpMatrixHandle, 1, false, mvpMatrix, 0);

        int mvMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVMatrix");
        GLES20.glUniformMatrix4fv(mvMatrixHandle, 1, false, mvMatrix, 0);

        lightPositionHandle = GLES20.glGetUniformLocation(mProgram, "uLightPosition");
        GLES20.glUniform3fv(lightPositionHandle, 1, lightPosition, 0);

        lightColorHandle = GLES20.glGetUniformLocation(mProgram, "uLightColor");
        GLES20.glUniform3fv(lightColorHandle, 1, lightColor, 0);

        int colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

//        GLES20.glUniform4fv(colorHandle, 1, new float[]{1f,1f,1f,1f}, 0);
//        GLES20.glDrawArrays(GLES20.GL_LINES, 0, vertexCount);

        // Draw each face of the cube
        GLES20.glUniform4fv(colorHandle, 1, backColor, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

        GLES20.glUniform4fv(colorHandle, 1, aboveColor, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, vertexCount/6, vertexCount/6);

        GLES20.glUniform4fv(colorHandle, 1, frontColor, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, (vertexCount/6)*2, vertexCount/6);

        GLES20.glUniform4fv(colorHandle, 1, bellowColor, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, (vertexCount/6)*3, vertexCount/6);
//
        GLES20.glUniform4fv(colorHandle, 1, rightColor, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, (vertexCount/6)*4, vertexCount/6);
//
        GLES20.glUniform4fv(colorHandle, 1, leftColor, 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, (vertexCount/6)*5, vertexCount/6);


        GLES20.glDisableVertexAttribArray(positionHandle);
    }
}
