package com.firework.Modules.Transform;

/**
 * Created by slava on 10.02.17.
 */

public class Vector3 {
    protected float x;
    protected float y;
    protected float z;

    protected Vector2 vector2;

    public Vector3()
    {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.vector2 = new Vector2(0,0,0);
    }
    public Vector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vector2 = new Vector2(x, y, z);
    }
    public Vector3(Vector3 copy)
    {
        this.x = copy.x;
        this.y = copy.y;
        this.z = copy.z;
        this.vector2 = new Vector2(x, y, z);
    }

    public void copy(Vector3 source)
    {
        this.x = source.x;
        this.y = source.y;
        this.z = source.z;
    }

    public void copy(Vector2 source)
    {
        this.x = source.getX();
        this.y = source.getY();
        this.z = source.getDepth();
    }

    public void set(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(float x, float y)
    {
        this.x = x;
        this.y = y;
        this.z = -1.0f;
    }

    public Vector3 add(float x, float y, float z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vector3 add(float x, float y)
    {
        this.x += x;
        this.y += y;
        return this;
    }

    /**
     * Getters и Setters
     * @return x, y, z
     */

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    @Override
    public String toString()
    {
        return "X: " + x + " Y: " + y + " Z: " + z;
    }

    public Vector2 toVector2()
    {
        vector2.set(x, y, z);
        return vector2;
    }
}
