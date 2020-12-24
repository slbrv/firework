package com.firework.Modules.Graphics.Textures;

/**
 * Created by Slava on 11.03.2017.
 */

import com.firework.Modules.Graphics.Shaders.ShaderUtils;

import java.nio.FloatBuffer;

/**
 * Класс вырезки из атласа текстур
 */

public class Frame {

    protected float[] frame_cords;//Текстурные координаты кадра

    public Frame()
    {
        this.frame_cords = new float[]
                {
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f
                };
    }

    public Frame(int posX, int posY, int sizeX, int sizeY)
    {
        float fposX;
        float fposY;
        fposX = posX == 0 ? 0 : 1/posX;
        fposY = posY == 0 ? 0 : 1/posY;

        float fpointX = fposX + 1/sizeX;
        float fpointY = fposY + 1/sizeY;

        float[] frame_cords = new float[]
            {
                    fposX, fpointY,
                    fposX, fposY,
                    fpointX, fposY,
                    fpointX, fpointY
            };

        this.frame_cords = frame_cords;
    }

    public Frame(float[] frameCords)
    {
        this.frame_cords = frameCords;
    }

    public void setFrameCords(float[] frameCords)
    {
        this.frame_cords = frameCords;
    }

    public float[] getFrameCords()
    {
        return frame_cords;
    }
}
