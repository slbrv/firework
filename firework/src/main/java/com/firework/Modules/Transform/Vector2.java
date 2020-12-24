package com.firework.Modules.Transform;

/**
 * Created by slava on 20.01.17.
 */

public class Vector2 {

    protected float x;
    protected float y;
    protected float depth;

    public enum Direction
    {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        ZERO
    }

    protected Direction direction;

    //CONSTRUCTORS

    public Vector2(float x, float y, float depth) {
        this.x = x;
        this.y = y;
        this.depth = depth;
        this.direction = Direction.ZERO;
    }

    public Vector2(float x, float y)
    {
        this.x = x;
        this.y = y;
        this.depth = 0.0f;
        this.direction = Direction.ZERO;
    }

    public Vector2() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.depth = 0.0f;
        this.direction = Direction.ZERO;
    }

    public Vector2(Vector2 copy) {
        this.x = copy.x;
        this.y = copy.y;
        this.direction = Direction.ZERO;
    }

    //METHODS

    public Vector2 set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2 set(float x, float y, float depth) {
        set(x, y);
        this.depth = depth;
        return this;
    }

    public Vector2 set(Vector2 set)
    {
        this.x = set.x;
        this.y = set.y;
        this.depth = set.depth;
        return this;
    }

    public void setDepth(float depth)
    {
        this.depth = depth;
    }

    public Vector2 add(float x, float y)
    {
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2 add(float x, float y, float depth)
    {
        add(x, y);
        this.depth = depth;
        return this;
    }

    public Vector2 add(Vector2 vector2)
    {
        this.x += vector2.x;
        this.y += vector2.y;
        this.depth = vector2.depth;
        return this;
    }

    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public float getDepth()
    {
        return depth;
    }

    public void setDirection(Direction direction)
    {
        this.direction = direction;
    }

    public Direction getDirection()
    {
        return direction;
    }

    @Override
    public String toString()
    {
        String temp = "X: " + x + " Y: " + y;
        return temp;
    }
}
