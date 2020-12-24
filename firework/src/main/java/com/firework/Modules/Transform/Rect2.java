package com.firework.Modules.Transform;

/**
 * Created by Slava on 06.03.2017.
 */

public class Rect2 {

    /**
     * |p1-----------|
     * |-------------|
     * |-------------|
     * |-----------p2|
     */

    protected Vector2 point1;
    protected Vector2 point2;
    protected Vector2 position;

    public Rect2(Rect2 copy)
    {
        this.point1 = new Vector2(copy.getPoint1());
        this.point2 = new Vector2(copy.getPoint2());
        this.position = new Vector2(copy.getPosition());
    }

    public Rect2(Vector2 point1, Vector2 point2) {
        this.point1 = point1;
        this.point2 = point2;
        this.position = new Vector2(0, 0, -1.0f);
    }

    public Rect2(float x1, float y1, float x2, float y2, float depth)
    {
        this.point1 = new Vector2(x1, y1, depth);
        this.point2 = new Vector2(x2, y2, depth);
        this.position = new Vector2(0, 0, -1.0f);
    }

    public Rect2(float x1, float y1, float x2, float y2)
    {
        this.point1 = new Vector2(x1, y1, 0);
        this.point2 = new Vector2(x2, y2, 0);
        this.position = new Vector2(0, 0, -1.0f);
    }

    public Rect2()
    {
        this.point1 = new Vector2(-1.0f, 1.0f, 0.0f);
        this.point2 = new Vector2(1.0f, -1.0f, 0.0f);
        this.position = new Vector2(0, 0, -1.0f);
    }

    /**
     * Setters и Getters
     * @return point1, point2
     */

    public Vector2 getPoint2() {
        return point2;
    }

    public void setPoint2(Vector2 point2) {
        this.point2 = point2;
    }

    public Vector2 getPoint1() {
        return point1;
    }

    public void setPoint1(Vector2 point1) {
        this.point1 = point1;
    }

    public void setPosition(Vector2 position)
    {
        this.position.set(position);
    }

    public void setPosition(float x, float y)
    {
        this.position.set(x, y);
    }

    public Vector2 getPosition()
    {
        return this.position;
    }
}
