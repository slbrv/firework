package com.firework.Modules.Physics;

import com.firework.Modules.OtherUtils.MathUtils;
import com.firework.Modules.Transform.Rect2;
import com.firework.Modules.Transform.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 08.04.2017.
 */

public class Collider2D{
    protected Rect2 rect;
    protected CollisionHandler2D handler;

    protected List<Collider2D> collisions;

    public static final int NONE = 0;
    public static final int ENTER = 1;//Состояние коллизии(начало столкновения)
    public static final int STAY = 2;//столкновение идет
    public static final int EXIT = 3;//столкновение закончено

    protected int status = NONE;


    public Collider2D(Rect2 rect, CollisionHandler2D handler)
    {
        this.rect = rect;
        this.handler = handler;
        this.collisions = new ArrayList<>();
    }

    public void setPosition(Vector2 position)
    {
        this.rect.setPosition(position);
    }

    public Rect2 getRect()
    {
        return rect;
    }

    public int hasCollision(Collider2D collider)
    {
        if(MathUtils.isRectCross(rect, collider.getRect()))
        {
            if(!collisions.contains(collider))
            {
                this.collisions.add(collider);
                this.status = ENTER;
            }
            else
                this.status = STAY;
        }
        else if(this.collisions.contains(collider))
        {
            this.status = EXIT;
            this.collisions.remove(collider);
        }
        else
            this.status = NONE;
        return status;
    }
}
