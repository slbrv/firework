package com.firework.Modules.Physics;

import com.firework.Modules.Transform.Vector2;

/**
 * Created by Slava on 08.04.2017.
 */

public class RigidBody2D {

    protected Vector2 speed;//Скорость
    protected Vector2 acceleration;//Ускорение
    protected Vector2 impulse;//Импульс тела
    protected Vector2 force;//Сила приложеная к телу

    protected float mass;//Масса тела
    protected float friction;//Трение тела;

    public RigidBody2D()
    {
        this.speed = new Vector2();
        this.acceleration = new Vector2();
        this.impulse = new Vector2();
        this.force = new Vector2();
    }
}
