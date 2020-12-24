package com.ad2d.slava.abandoneddungeons.Game;

import android.util.Log;

import com.ad2d.slava.abandoneddungeons.Shaders.MapShader;
import com.firework.Modules.Graphics.Camera;
import com.firework.Modules.Graphics.Textures.Texture;

import java.nio.FloatBuffer;
import java.util.Random;

/**
 * Created by Slava on 20.04.2017.
 */

public class Map {

    public static final int NONE = 0;
    public static final int FLOOR = 1;
    public static final int STONE = 2;
    public static final int BONES_1 = 3;
    public static final int BONES_2 = 4;
    public static final int WALL= 5;
    public static final int CLOSED_DOOR = 6;//Без замка
    public static final int LOCKED_DOOR = 7;//С замком
    public static final int OPEN_DOOR = 8;

    //Dynamic objects
    public static final int KEY = 9;
    public static final int BOWL_OF_HEAL = 10;//Чаша жизни
    public static final int PREPARED_TRAP = 11;//Ловука закрыта
    public static final int ACTIVATED_TRAP = 12;//Ловушка открыта


    public static final int LAVA = 15;


    protected int[] cells;//Ячейки карты

    protected Random random;

    protected int sizeX;
    protected int sizeY;

    protected int capacity;

    protected MapShader shader;

    protected boolean hasFirstGen = false;//Была ли первая генерация
    protected int openDoorPos;

    protected Player player;


    public Map(int sizeX, int sizeY, Camera camera)
    {
        this.sizeX = sizeX;
        this.sizeY = sizeY;

        this.capacity = sizeX * sizeY;

        cells = new int[capacity];

        random = new Random();

        generate();

        hasFirstGen = true;

        this.shader = new MapShader(camera, this, new Texture("map.png"), 32);
    }

    public void generate()
    {
        if(hasFirstGen)
        {
            sizeX = random.nextInt(3) + 7;
            sizeY = sizeX;
            capacity = sizeX * sizeY;
            cells = new int[capacity];
        }
        for (int i = 0; i < sizeX; i++) {
            cells[i] = WALL;
        }
        for (int i = sizeX * sizeY - sizeX; i < sizeX * sizeY; i++) {
            cells[i] = WALL;
        }
        for (int i = sizeX; i < sizeX*sizeY - sizeX; i++) {
            cells[i] = FLOOR;
        }
        for (int i = 0; i < random.nextInt(capacity / 6 - 7) + 7; i++) {
            cells[(random.nextInt(sizeY - 2) + 1) * sizeX + random.nextInt(sizeX)] = STONE;
        }

        int trapsCount = random.nextInt(capacity/6);
        for (int i = 0; i < trapsCount; i++) {
            cells[(random.nextInt(sizeY - 2) + 1) * sizeX + random.nextInt(sizeX)] = PREPARED_TRAP;
        }

        openDoorPos = 1 + random.nextInt(sizeX - 1);
        if(cells[openDoorPos + sizeX - 1] != WALL)
            cells[openDoorPos + sizeX - 1] = FLOOR;
        if(cells[openDoorPos + sizeX] != WALL)
            cells[openDoorPos + sizeX] = FLOOR;
        if(cells[openDoorPos + sizeX + 1] != WALL)
            cells[openDoorPos + sizeX + 1] = FLOOR;

        cells[openDoorPos] = OPEN_DOOR;//Нижняя дверь

        int escapeDoorPos = capacity - sizeX + 1 + random.nextInt(sizeX-2);//Верхняя дверь
        int doorType = CLOSED_DOOR + random.nextInt(2);
        cells[escapeDoorPos] = doorType;
        if(cells[escapeDoorPos - sizeX - 1] != WALL)
            cells[escapeDoorPos - sizeX - 1] = FLOOR;
        if(cells[escapeDoorPos - sizeX] != WALL)
            cells[escapeDoorPos - sizeX] = FLOOR;
        if(cells[escapeDoorPos - sizeX + 1] != WALL)
            cells[escapeDoorPos - sizeX + 1] = FLOOR;
        for (int i = 0; i < sizeX; i++) {
            cells[capacity - sizeX - 1 - i] = FLOOR;
        }

        if(doorType == Map.LOCKED_DOOR)
        {
            int keyPos = (random.nextInt(sizeY - 2) + 1) * sizeX + random.nextInt(sizeX);
            cells[keyPos] = KEY;
        }
        if(shader != null)
            shader.reload();
    }

    public int getOpenDoorPosition()
    {
        return openDoorPos;
    }

    public int getCapacity()
    {
        return capacity;
    }
    
    public int getFullCount()
    {
        int count = 0;
        for (int i = 0; i < capacity; i++) {
            if(cells[i] != NONE)
                count++;
        }
        return count;
    }

    public int getSizeX()
    {
        return sizeX;
    }

    public int getSizeY()
    {
        return sizeY;
    }

    public int getCellType(int index)
    {
        return cells[index];
    }

    public int getCellPosition(int type)
    {
        for (int i = 0; i < capacity; i++) {
            if(cells[i] == type)
                return i;
        }
        return -1;
    }

    public void setCell(int pos, int type)
    {
        this.cells[pos] = type;
        shader.refresh();
    }

    public MapShader getShader()
    {
        return shader;
    }

    public void setPlayer(Player player)
    {
        this.player = player;
    }

    public Player getPlayer()
    {
        return player;
    }

    public void draw()
    {
        shader.start();
        shader.draw();
        shader.stop();
    }
}
