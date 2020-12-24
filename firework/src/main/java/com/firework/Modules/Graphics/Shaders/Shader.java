package com.firework.Modules.Graphics.Shaders;

import android.support.annotation.Nullable;

import com.firework.Modules.GameObjects.Sprite;

import static android.opengl.GLES20.GL_FRAGMENT_SHADER;
import static android.opengl.GLES20.GL_VERTEX_SHADER;

/**
 * Created by Slava on 15.02.2017.
 */

public abstract class Shader{
    protected int programID;

    protected int vertexShaderID;
    protected int fragmentShaderID;

    public Shader()
    {
        generateShader();
    }

    public Shader(String vertexShader, String fragmentShader)
    {
        generateShader(vertexShader, fragmentShader);
    }

    public void generateShader()
    {
        String vertexShader = "#version 120\n" +
                "\n" +
                "uniform mat4 u_mvp_matrix;\n" +
                "\n" +
                "attribute vec3 a_vertex;\n" +
                "attribute vec4 a_color;\n" +
                "attribute vec2 a_texture_cords;\n" +
                "\n" +
                "varying vec3 v_vertex;\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texture_cords;\n" +
                "\n" +
                "void main() {\n" +
                "    v_texture_cords.s = a_texture_cords.s;\n" +
                "    v_texture_cords.t = a_texture_cords.t;\n" +
                "\n" +
                "    v_vertex = a_vertex;\n" +
                "    v_color = a_color;\n" +
                "\n" +
                "    gl_Position = u_mvp_matrix * vec4(a_vertex, 1.0f);\n" +
                "}\n";

        String fragmentShader = "#version 120\n" +
                "precision mediump float;\n" +
                "\n" +
                "uniform sampler2D u_texture;\n" +
                "\n" +
                "varying vec3 v_vertex;\n" +
                "varying vec4 v_color;\n" +
                "varying vec2 v_texture_cords;\n" +
                "\n" +
                "void main() {\n" +
                "    vec4 pixelColor = texture2D(u_texture, v_texture_cords);\n" +
                "    gl_FragColor = pixelColor;\n" +
                "}";
        generateShader(vertexShader, fragmentShader);
    }

    public void generateShader(String vertexShader, String fragmentShader)
    {
        vertexShaderID = ShaderUtils.createShader(GL_VERTEX_SHADER, vertexShader);
        fragmentShaderID = ShaderUtils.createShader(GL_FRAGMENT_SHADER, fragmentShader);
        programID = ShaderUtils.createProgram(vertexShaderID, fragmentShaderID);
    }

    public abstract void start();

    public abstract void draw();

    public abstract void stop();

    public abstract void release();

    public int getProgramID()
    {
        return programID;
    }

    public int getVertexShaderID()
    {
        return vertexShaderID;
    }

    public int getFragmentShaderID()
    {
        return fragmentShaderID;
    }
}
