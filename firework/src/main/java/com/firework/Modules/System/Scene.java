package com.firework.Modules.System;

/**
 * Created by slava on 06.01.17.
 */

public abstract class Scene{
    protected final Game game;

    public Scene(Game game) {
        this.game = game;
    }

    public abstract void start();

    public abstract void update();//Game logic update

    public abstract void render();//Scene update


    public abstract void pause();

    public abstract void release();
}
