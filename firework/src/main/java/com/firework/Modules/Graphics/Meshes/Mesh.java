package com.firework.Modules.Graphics.Meshes;

import com.firework.Modules.Graphics.Shaders.ShaderUtils;
import com.firework.Modules.Transform.Rect2;

import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Slava on 25.02.2017.
 */

public class Mesh {
    protected FloatBuffer vertices;//позиции вершин
    protected ShortBuffer indices;//индексы вершин

    public Mesh()
    {
        float[] vertices = new float[]
                {
                        0.5f, 0.5f, -0.5f,
                        0.5f, -0.5f, -0.5f,
                        -0.5f, -0.5f, -0.5f,
                        -0.5f, 0.5f, -0.5f
                };
        short[] indices = new short[]
                {
                        0, 1, 2, 2, 3, 0
                };
        this.vertices = ShaderUtils.createFloatBuffer(vertices);
        this.indices = ShaderUtils.createShortBuffer(indices);
    }

    public Mesh(float[] vertices, short[] indices)
    {
        this.vertices = ShaderUtils.createFloatBuffer(vertices);
        this.indices = ShaderUtils.createShortBuffer(indices);
    }

    public Mesh(float[] vertices)
    {
        short[] indices = new short[]
                {
                        0, 1, 2, 2, 3, 0
                };
        this.vertices = ShaderUtils.createFloatBuffer(vertices);
        this.indices = ShaderUtils.createShortBuffer(indices);
    }

    public Mesh(Rect2 rect)
    {
        float[] vertices = new float[]
                {
                        rect.getPoint2().getX(), rect.getPoint1().getY(), rect.getPoint1().getDepth(),
                        rect.getPoint2().getX(), rect.getPoint2().getY(), rect.getPoint1().getDepth(),
                        rect.getPoint1().getX(), rect.getPoint2().getY(), rect.getPoint1().getDepth(),
                        rect.getPoint1().getX(), rect.getPoint1().getY(), rect.getPoint1().getDepth()
                };
        short[] indices = new short[]
                {
                        0, 1, 2, 2, 3, 0
                };
        this.vertices = ShaderUtils.createFloatBuffer(vertices);
        this.indices = ShaderUtils.createShortBuffer(indices);
    }

    public void release()
    {

    }


    public void setIndices(short[] indices) {
        this.indices = ShaderUtils.createShortBuffer(indices);
    }
    public void setVertices(float[] vertices) {
        this.vertices = ShaderUtils.createFloatBuffer(vertices);
    }

    public FloatBuffer getVertices()
    {
        return vertices;
    }
    public ShortBuffer getIndices(){return indices;}

    @Override
    public String toString()
    {
        return "Vertices: " + vertices.toString() + "\n Indices: " + indices.toString();
    }
}
