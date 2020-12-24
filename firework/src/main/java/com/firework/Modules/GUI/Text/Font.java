package com.firework.Modules.GUI.Text;

import android.opengl.Matrix;

import com.firework.Modules.Graphics.Shaders.ShaderUtils;
import com.firework.Modules.Graphics.Textures.Frame;
import com.firework.Modules.Graphics.Textures.Texture;
import com.firework.Modules.Graphics.Textures.TextureUtils;
import com.firework.Modules.Transform.Rect2;
import com.firework.Modules.Transform.Vector2;

import java.nio.FloatBuffer;

/**
 * Created by Slava on 30.03.2017.
 */

public class Font {

    protected Texture font;
    protected String chars;
    protected FloatBuffer charsTextureCoordinates;
    protected Rect2 charRect;

    protected float[] modelMatrix;

    protected float size;
    protected float ratio;//Отношение ширины симпола к высоте символа

    /**
     * Класс шрифтов
     *
     * @param font       - Текстура шрифта
     * @param chars      - символы
     * @param charWidth  - ширина символа в текстуре
     * @param charHeight - высота символа в текстуре
     */
    public Font(Texture font, String chars, int charWidth, int charHeight, float size) {
        this.font = font;
        this.chars = chars;
        this.modelMatrix = new float[16];

        Matrix.setIdentityM(modelMatrix, 0);

        Frame[] frames = TextureUtils.cutTexture(font, charWidth, charHeight);
        charsTextureCoordinates = ShaderUtils.createFloatBuffer(frames.length * 8);
        charsTextureCoordinates.position(0);
        for (int i = 0; i < frames.length; i++) {
            charsTextureCoordinates.put(frames[i].getFrameCords());
        }
        charsTextureCoordinates.flip();

        this.ratio = charWidth / charHeight * size;
        this.size = size;
        this.charRect = new Rect2(-ratio, 1.0f * size, ratio, -1.0f * size, 0.0f);
    }

    /**
     * Получение буфера текстурных координат на основе строки
     *
     * @param text - исходная строка
     * @return - буфер тексутрных координат соответсующих строке
     */

    public FloatBuffer parse(String text) {
        FloatBuffer out = ShaderUtils.createFloatBuffer(text.length() * 8);
        out.position(0);
        for (int ch = 0; ch < text.length(); ch++)
            for (int pos = 0; pos < 8; pos++) {//Позиция в буфере; max = 8 так как всего 8 текстурных координат
                int index = chars.indexOf(text.charAt(ch));
                out.put(charsTextureCoordinates.get(index) * 8 + pos);//Получение координаты буквы на текстуре основываясь на массиве символов по индексу
            }
        out.flip();
        return out;
    }


    /**
     * Возвращение матрицы модели конкретного символа
     * @param charIndex - номер символа в строке
     * @param textFieldPos - позиция текстового поля на canvas
     * @return
     */
    public float[] getModelMatrix(int charIndex, Vector2 textFieldPos)
    {
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, charIndex*ratio*size + textFieldPos.getX(), textFieldPos.getY(), 0);
        return modelMatrix;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getSize() {
        return this.size;
    }

    public Texture getAtlas()
    {
        return font;
    }

    public void setChars(String chars) {
        this.chars = chars;
    }

    public float getRatio() {
        return ratio;
    }

    public String getChars() {
        return this.chars;
    }
}
