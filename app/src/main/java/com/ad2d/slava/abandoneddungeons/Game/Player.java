package com.ad2d.slava.abandoneddungeons.Game;

import android.util.Log;

import com.firework.Modules.Audio.Sound;
import com.firework.Modules.Graphics.Animation.Animation;
import com.firework.Modules.OtherUtils.MathUtils;
import com.firework.Modules.Transform.Vector3;

import static com.ad2d.slava.abandoneddungeons.Game.Map.*;

/**
 * Created by Slava on 30.04.2017.
 */

public class Player {

    public static final int UP = 0;
    public static final int RIGHT = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;

    protected Animation animation;
    protected Map map;
    protected Sound pullHandleSound;

    protected int mapNumber;

    protected int lights;
    protected boolean hasKey = false;

    public Player(Animation animation, Map map, Sound pullHandleSound)
    {
        this.animation = animation;
        this.lights = 5;
        this.map = map;
        this.mapNumber = 0;
        this.pullHandleSound = pullHandleSound;
    }

    public void move(int dir)
    {
        int nextCell = -1;
        float[] nextPos = new float[2];
        switch (dir)
        {
            case UP:
                    if(animation.getPositionY()+1 <= map.getSizeY())
                    {
                        nextPos[0] = animation.getPositionX();
                        nextPos[1] = animation.getPositionY() + 1;
                        nextCell = (int) (nextPos[0] + nextPos[1] * map.getSizeX());
                    }
                break;
            case RIGHT:
                if(animation.getPositionX() + 1 < map.getSizeX())
                {
                    nextPos[0] = animation.getPositionX() + 1;
                    nextPos[1] = animation.getPositionY();
                    nextCell = (int) (nextPos[0] + nextPos[1] * map.getSizeX());
                }
                break;
            case DOWN:
                if(animation.getPositionY() - 1 >= 0)
                {
                    nextPos[0] = animation.getPositionX();
                    nextPos[1] = animation.getPositionY() - 1;
                    nextCell = (int) (nextPos[0] + nextPos[1] * map.getSizeX());
                }
                break;
            case LEFT:
                if(animation.getPositionX() - 1 >= 0)
                {
                    nextPos[0] = animation.getPositionX() - 1;
                    nextPos[1] = animation.getPositionY();
                    nextCell = (int) (nextPos[0] + nextPos[1] * map.getSizeX());
                }
                break;
        }
        boolean canMove = false;
        if(nextCell != -1) {
            int type = map.getCellType(nextCell);
            switch (type) {
                case FLOOR:
                    canMove = true;
                    break;
                case WALL:

                    break;
                case STONE:

                    break;
                case CLOSED_DOOR:
                    nextMap();
                    break;
                case PREPARED_TRAP:
                    lights--;
                    map.setCell(nextCell, ACTIVATED_TRAP);
                    canMove = true;
                    break;
                case ACTIVATED_TRAP:
                    lights--;
                    canMove = true;
                    break;
                case LOCKED_DOOR:
                    if(hasKey)
                        nextMap();
                    else
                        pullHandleSound.play(1.0f);
                    break;
                case KEY:
                    hasKey = true;
                    map.setCell(map.getCellPosition(KEY), FLOOR);
                    canMove = true;
                    break;
            }
        }
        if(canMove)
            animation.setPosition(nextPos[0], nextPos[1]);
        if(lights <= 0)
        {
            lights = 5;
            map.generate();
            mapNumber = 0;
            animation.setPosition(map.getOpenDoorPosition(), 1.0f);
            animation.getCamera().setPosition(map.getSizeX()/2.0f-0.5f, map.getSizeY()/2.0f-0.5f, -5.0f);
            animation.getCamera().setZoom(1/MathUtils.max(map.getSizeX()/2.0f, (map.getSizeY()/2.0f)));
            animation.getCamera().prepare();
            hasKey = false;
        }
    }

    public void draw()
    {
        animation.play((5 - lights)*4, (5 - lights)*4 + 4);
    }

    public void nextMap()
    {
        mapNumber++;
        map.generate();
        animation.setPosition(map.getOpenDoorPosition(), 1.0f);
        animation.getCamera().setPosition(map.getSizeX()/2.0f-0.5f, map.getSizeY()/2.0f-0.5f, -5.0f);
        animation.getCamera().setZoom(1/MathUtils.max(map.getSizeX()/2.0f, (map.getSizeY()/2.0f)));
        animation.getCamera().prepare();
        hasKey = false;
    }

    public float getPositionX()
    {
        return animation.getPositionX();
    }

    public float getPositionY()
    {
        return animation.getPositionY();
    }
}
