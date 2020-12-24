package com.firework.Modules.Graphics.Meshes;

import com.firework.Modules.Graphics.Shaders.ShaderUtils;

import java.nio.FloatBuffer;

/**
 * Created by Slava on 25.02.2017.
 */

public class ModelMesh extends Mesh {

    protected FloatBuffer normalsBuffer;

    public ModelMesh(float[] vertices, short[] indices, float[] normals) {
        super(vertices, indices);
        normalsBuffer = ShaderUtils.createFloatBuffer(normals);
    }

    public FloatBuffer getNormals()
    {
        return normalsBuffer;
    }
}
