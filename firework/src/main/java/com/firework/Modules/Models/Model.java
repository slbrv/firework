package com.firework.Modules.Models;

import com.firework.Modules.Graphics.Meshes.ModelMesh;
import com.firework.Modules.Graphics.Textures.Texture;

/**
 * Created by Slava on 18.02.2017.
 */

public class Model {

    protected ModelMesh mesh;
    protected Texture texture;

    //protected ModelShader shader;

    public void draw()
    {

    }

    public void setTexture(Texture texture)
    {
        this.texture = texture;
    }

    public void setMesh(ModelMesh Mesh)
    {
        this.mesh = Mesh;
    }

    //
    //GETTERS
    //

    public Texture getTexture()
    {
        return this.texture;
    }

    public ModelMesh getMesh()
    {
        return this.mesh;
    }
}
