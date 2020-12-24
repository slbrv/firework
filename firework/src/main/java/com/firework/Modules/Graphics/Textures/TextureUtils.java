package com.firework.Modules.Graphics.Textures;

import android.util.Log;

/**
 * Created by Slava on 11.03.2017.
 */

public class TextureUtils {

    /**
     * Метод нарезки атласа текстур на равные области текстуры
     * @param texture - нарезаемый атлас текстур
     * @param frameSize - размер области
     * @return - нарезанные области
     */
    public static Frame[] cutTexture(Texture texture, int frameSize)
    {
        float oneKeyFrameX = (1.0f/texture.getWidth())*frameSize;
        float oneKeyFrameY = (1.0f/texture.getHeight())*frameSize;
        int countX = texture.getWidth()/frameSize;
        int countY = texture.getHeight()/frameSize;

        Frame[] frames = new Frame[countX * countY];

        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                float[] cords = new float[]
                        {
                                oneKeyFrameX*x, oneKeyFrameY*y,
                                oneKeyFrameX*x, oneKeyFrameY*y + oneKeyFrameY,
                                oneKeyFrameX*x + oneKeyFrameX, oneKeyFrameY*y + oneKeyFrameY,
                                oneKeyFrameX*x + oneKeyFrameX, oneKeyFrameY*y,
                        };
                frames[y * countX + x] = new Frame(cords);
            }
        }

        if(frames.length == 0)
        {
            Log.e("Textures", "Texture Cutting warning: frames.length = 0!");
        }

        return frames;
    }

    public static Frame[] cutTexture(Texture texture, int width, int height)
    {
        float oneKeyFrameX = (1.0f/texture.getWidth())*width;
        float oneKeyFrameY = (1.0f/texture.getHeight())*height;
        int countX = texture.getWidth()/width;
        int countY = texture.getHeight()/height;

        Frame[] frames = new Frame[countX * countY];

        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                float[] cords = new float[]
                        {
                                oneKeyFrameX*x, oneKeyFrameY*y,
                                oneKeyFrameX*x, oneKeyFrameY*y + oneKeyFrameY,
                                oneKeyFrameX*x + oneKeyFrameX, oneKeyFrameY*y + oneKeyFrameY,
                                oneKeyFrameX*x + oneKeyFrameX, oneKeyFrameY*y,
                        };
                frames[y * countX + x] = new Frame(cords);
            }
        }

        if(frames.length == 0)
        {
            Log.e("Textures", "Texture Cutting warning: frames.length = 0!");
        }

        return frames;
    }

    public static float[] cutTextureFloat(Texture texture, int frameSize)
    {
        float oneKeyFrameX = (1.0f/texture.getWidth())*frameSize;
        float oneKeyFrameY = (1.0f/texture.getHeight())*frameSize;
        int countX = texture.getWidth()/frameSize;
        int countY = texture.getHeight()/frameSize;

        float[] buffer = new float[countX * countY * 8];

        int idx = 0;

        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                float[] cords = new float[]
                        {
                                oneKeyFrameX*x, oneKeyFrameY*y,
                                oneKeyFrameX*x, oneKeyFrameY*y + oneKeyFrameY,
                                oneKeyFrameX*x + oneKeyFrameX, oneKeyFrameY*y + oneKeyFrameY,
                                oneKeyFrameX*x + oneKeyFrameX, oneKeyFrameY*y,
                        };
                for (int i = 0; i < 8; i++) {
                    buffer[i + idx] = cords[i];
                }
                idx += 8;
            }
        }

        if(buffer.length == 0)
        {
            Log.e("Textures", "Texture Cutting warning: frames.length = 0!");
        }

        return buffer;
    }
}
