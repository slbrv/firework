package com.firework.Modules.Graphics.Textures;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

import com.firework.Modules.Assets.Loader;
import com.firework.Modules.Graphics.Shaders.ShaderUtils;
import com.firework.Modules.System.Releasable;

import java.nio.FloatBuffer;

import static android.opengl.GLES20.GL_CLAMP_TO_EDGE;
import static android.opengl.GLES20.GL_NEAREST;
import static android.opengl.GLES20.GL_REPEAT;
import static android.opengl.GLES20.GL_TEXTURE0;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_S;
import static android.opengl.GLES20.GL_TEXTURE_WRAP_T;
import static android.opengl.GLES20.GL_UNPACK_ALIGNMENT;
import static android.opengl.GLES20.glActiveTexture;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glPixelStorei;
import static android.opengl.GLES20.glTexParameteri;
import static android.opengl.GLES20.glUniform1i;

/**
 * Created by Slava on 15.02.2017.
 */

public class Texture implements Releasable{

    protected int textureID;// id текстуры

    protected boolean alpha = false;//Иеется ли альфа
    protected boolean mirrored = false;//Отражена ли

    protected int width;//Ширина
    protected int height;//Высота

    public Texture(String textureName,float[] colors, int minFilter, int magFilter, boolean repeatable, boolean alpha) {
        createTexture(textureName, minFilter, magFilter, repeatable, alpha);
    }

    public Texture(String textureName, boolean alpha)
    {
        createTexture(textureName, GL_NEAREST, GL_NEAREST, true, alpha);
    }

    public Texture(String textureName, boolean alpha, float[] texture_cords)
    {
        createTexture(textureName, GL_NEAREST, GL_NEAREST, true, alpha);
    }

    public Texture(String textureName)
    {
        createTexture(textureName, GL_NEAREST, GL_NEAREST, true, false);
    }

    /**
     * Метод создания тектсуры
     * @param textureName - название и путь с файлом текстуры
     * @param minFilter - минификационный фильтр
     * @param magFilter - магнифиционный фильтр
     * @param repeatable - повторяемость текстры
     * @param alpha - просчитывать ли альфу
     */

    public void createTexture(String textureName, int minFilter, int magFilter, boolean repeatable, boolean alpha) {
        Bitmap texture = Loader.loadBitmap(textureName);

        int[] ids = new int[1];
        glGenTextures(1, ids, 0);
        this.textureID = ids[0];
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glBindTexture(GL_TEXTURE_2D, this.textureID);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, minFilter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, magFilter);

        if (repeatable)
        {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
        }
        else
        {
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        }

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, texture, 0);
        this.width = texture.getWidth();
        this.height = texture.getHeight();
        texture.recycle();
        this.alpha = alpha;
    }

    /**
     * Поключение тексуры в шейдер
     * @param position - позиция текстуры в шейдере(название позиции)
     */

    public void link(int position)
    {
        glActiveTexture(GL_TEXTURE0);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glUniform1i(position, 0);
    }

    /**
     * НЕОБХОДИМО ДОРАБОТАТЬ!
     * метод отражения текстуры
     * @param value - отражение текстуры
     */

    @Deprecated
    public void setMirror(boolean value)
    {
        this.mirrored = value;
    }

    /**
     * Метод получения id текстуры
     * @return - id текстуры
     */
    public int getTextureID()
    {
        return this.textureID;
    }

    /*
     * Метод получения альфы
     * @return - просчитвать ли альфу
     */
    public boolean hasAlpha()
    {
        return alpha;
    }

    /**
     * Метод получения ширины изображения
     * @return - ширина изображения
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * Метод получения высоты изображения
     * @return - высоты изображения
     */
    public int getHeight()
    {
        return height;
    }

    @Override
    public void release() {

    }
}
