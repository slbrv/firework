package com.firework.Modules.GameObjects;

import com.firework.Modules.Graphics.Textures.Frame;
import com.firework.Modules.Graphics.Meshes.Mesh;
import com.firework.Modules.Graphics.Shaders.ShaderUtils;
import com.firework.Modules.Transform.Rect2;
import com.firework.Modules.Transform.Transform2;

import java.nio.FloatBuffer;

/**
 * Created by Slava on 15.02.2017.
 */

@Deprecated
public class Sprite {
    protected Transform2 transform;
    protected Frame frame;
    protected FloatBuffer colors;
    protected Mesh mesh;

    private void Create(Transform2 transform, Frame frame, float[] colors, Mesh mesh) {
        this.transform = transform;
        this.frame = frame;
        this.mesh = mesh;
        this.colors = ShaderUtils.createFloatBuffer(colors);
    }

    public Sprite() {
        this.Create(
        new Transform2(),
        new Frame(new float[]
                {
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f
                }),
        new float[]
                {
                        1.0f, 1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f, 1.0f,
                        1.0f, 1.0f, 1.0f, 1.0f
                },
        new Mesh()
        );
    }

    public Sprite(Mesh mesh) {
        this.Create(new Transform2(), new Frame(new float[]
                        {
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f
                        }), new float[]
                        {
                                1.0f, 1.0f, 1.0f, 1.0f,
                                1.0f, 1.0f, 1.0f, 1.0f,
                                1.0f, 1.0f, 1.0f, 1.0f,
                                1.0f, 1.0f, 1.0f, 1.0f
                        },
                mesh);
    }

    public Sprite(Mesh mesh, float[] colors) {
        this.Create(new Transform2(), new Frame(new float[]
                        {
                                0.0f, 0.0f,
                                0.0f, 1.0f,
                                1.0f, 1.0f,
                                1.0f, 0.0f
                        }),
                colors, mesh);
    }

    public Sprite(Transform2 transform, float[] colors, float[] texture_cords) {
        this.Create(transform, new Frame(texture_cords), colors, new Mesh());
    }

    public Sprite(Transform2 transform, float[] colors, float[] texture_cords, Mesh mesh) {
        this.Create(transform, new Frame(texture_cords), colors, mesh);
    }

    public Sprite(Rect2 rect, Frame frame)
    {
        float[] vertices = new float[]
                {
                        rect.getPoint2().getX(), rect.getPoint1().getY(), rect.getPoint1().getDepth(),
                        rect.getPoint2().getX(), rect.getPoint2().getY(), rect.getPoint1().getDepth(),
                        rect.getPoint1().getX(), rect.getPoint2().getY(), rect.getPoint1().getDepth(),
                        rect.getPoint1().getX(), rect.getPoint1().getY(), rect.getPoint1().getDepth()
                };
        Mesh mesh = new Mesh(vertices);
        this.Create(new Transform2(), frame, new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f}, mesh);
    }

    public FloatBuffer getColors() {
        return colors;
    }

    public void setColors(float[] colors) {
        this.colors = ShaderUtils.createFloatBuffer(colors);
    }

    public Mesh getMesh() {
        return mesh;
    }

    public void setMesh(Mesh mesh) {
        this.mesh = mesh;
    }

    public float[] getTexture_cords() {
        return frame.getFrameCords();
    }

    public Frame getFrame()
    {
        return frame;
    }

    public void setFrame(Frame frame)
    {
        this.frame = frame;
    }

    public Transform2 getTransform() {
        return transform;
    }

    public void setTransform(Transform2 transform) {
        this.transform = transform;
    }
}
