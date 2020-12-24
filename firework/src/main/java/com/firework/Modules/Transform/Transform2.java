package com.firework.Modules.Transform;

import android.opengl.Matrix;

import com.firework.Modules.OtherUtils.MatrixUtils;

/**
 * Created by Slava on 07.03.2017.
 */

public class Transform2 {

    protected Vector2 position;
    protected float angle;
    protected Vector2 scale;

    protected float[] modelMatrix = new float[16];

    private void setIdentity()
    {
        Matrix.setIdentityM(modelMatrix, 0);
        setPosition(position);
        setRotation(angle);
    }

    public Transform2() {
        this.angle = 0.0f;
        this.position = new Vector2(0,0,0);
        this.scale = new Vector2(1,1,1);
        setIdentity();
    }

    public Transform2(Vector2 position, Vector2 scale, float angle) {
        this.angle = angle;
        this.position = position;
        this.scale = scale;
        setIdentity();
    }

    public void setPosition(float x, float y, float depth)
    {
        Matrix.translateM(modelMatrix, 0, x - this.position.getX(), y - this.position.getY(), depth - this.position.getDepth());
        this.position.set(x, y, depth);
    }


    public void setPosition(Vector2 position)
    {
        setPosition(position.getX(), position.getY(), position.getDepth());
    }
    public void setPosition(Vector2 position, float depth)
    {
        setPosition(position.getX(), position.getY(), depth);
    }

    public void setPosition(float x, float y)
    {
        setPosition(x, y, 0.0f);
    }

    private float[] mm = new float[16];

    public void setRotation(float angle)
    {
        Matrix.setIdentityM(mm, 0);
        Matrix.setRotateEulerM(mm, 0, 0, 0, angle);
        MatrixUtils.setMM(modelMatrix, 0, mm, 0, 3, 3);
        this.angle = angle;
    }

    public Vector2 translate(float x, float y)
    {
        return translate(x, y, 0.0f);
    }

    public Vector2 translate(float x, float y, float depth)
    {
        Matrix.translateM(modelMatrix, 0, x, y, depth);
        return this.position.add(x, y, depth);
    }

    public Vector2 translate(Vector2 to)
    {
        return translate(to.getX(), to.getY(), to.getDepth());
    }

    public float rotate(float angle)
    {
        setRotation(this.angle += angle);
        return this.angle;
    }


    public Vector2 getPosition()
    {
        return position;
    }

    public Vector2 getScale()
    {
        return scale;
    }

    public float getRotation()
    {
        return angle;
    }

    public float[] getModelMatrix()
    {
        return modelMatrix;
    }

    /**
     * Нормализация вектора
     * !Attention! - исходный вектор тоже меняется
     * @param vector2 - нормализуемый вектор
     * @return - нормализованный вектор
     */
    public Vector2 normalize(Vector2 vector2)
    {
        return vector2.set(1/vector2.getX(), 1/vector2.getY());
    }
}
