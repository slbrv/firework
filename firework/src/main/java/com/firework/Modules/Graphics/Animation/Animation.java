package com.firework.Modules.Graphics.Animation;

import com.firework.Modules.GameObjects.Sprite;
import com.firework.Modules.Graphics.Batching.SpriteBatch;
import com.firework.Modules.Graphics.Camera;
import com.firework.Modules.Graphics.Textures.Frame;
import com.firework.Modules.Graphics.Textures.Texture;
import com.firework.Modules.System.Releasable;
import com.firework.Modules.System.Time;

import java.util.ArrayList;

/**
 * Created by Slava on 11.03.2017.
 */

/**
 * Класс 2D спрайтовой анимации
 */

public class Animation implements Releasable {

    protected ArrayList<Frame> frames;//Лист кадров
    protected Texture texture;

    protected float time;//Текущее время кадра
    protected float delay;//Задержка кадра

    protected int active = 0;//Активный кадр

    protected boolean loop = true;//Повторяемая?
    protected boolean mirror = false;//Может ли идти в обратную сторону?

    protected boolean hasPlay = true;//Воспроизводится?

    protected boolean reverce = false;//false - анимация движиться от начала к концу, true - от конца к началу

    protected AnimationShader shader;

    protected Camera camera;



    protected float[] position;


    /**
     * Создание анимации
     *
     * @param atlas  - атлас спрайтов
     * @param frames - ключевые кадры
     * @param target - первый кадр
     */

    private void create(Texture atlas, Camera camera, Frame[] frames, int target) {
        this.camera = camera;
        this.texture = atlas;
        this.frames = new ArrayList<>(frames.length);
        for (Frame key :
                frames) {
            this.frames.add(key);
        }
        this.active = target;
        this.reverce = false;
        this.delay = 1.0f;
        this.position = new float[] {1.0f, 1.0f};
        this.shader = new AnimationShader(camera, texture, this.frames);
    }

    public Animation(Camera camera, Texture atlas, Frame... frames) {
        create(atlas, camera, frames, 0);
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    /**
     * Переход к следующему кадру
     */

    public void skipFrame() {
        if (reverce)
            this.active--;
        else
            this.active++;

        if (this.active >= this.frames.size()) {
            if (loop) {
                if (mirror) {
                    this.reverce = true;
                    this.active -= 2;
                } else {
                    this.active = 0;
                }
            } else {
                if (mirror) {
                    this.reverce = true;
                    this.active -= 2;
                } else {
                    this.hasPlay = false;
                    this.active = 0;
                    this.reverce = false;
                    return;
                }
            }
        }
        if (active < 0) {
            if (loop) {
                this.reverce = false;
                this.active += 2;
            } else {
                this.hasPlay = false;
                this.active = 0;
                this.reverce = false;
                return;
            }
        }
        this.time = this.delay;
    }

    public void skipFrame(int from, int to) {

        if (reverce)
            this.active--;
        else
            this.active++;

        if (this.active >= to) {
            if (loop) {
                if (mirror) {
                    this.reverce = true;
                    this.active -= 2;
                } else {
                    this.active = from;
                }
            } else {
                if (mirror) {
                    this.reverce = true;
                    this.active -= 2;
                } else {
                    this.hasPlay = false;
                    this.active = from;
                    this.reverce = false;
                    return;
                }
            }
        }
        if (active < from) {
            if (loop) {
                this.reverce = false;
                this.active += 2;
            } else {
                this.hasPlay = false;
                this.active = from;
                this.reverce = false;
                return;
            }
        }
        this.time = this.delay;
    }

    /**
     * Отрисовка анимации
     */

    public void play() {
        if (hasPlay) {
            this.time -= Time.deltaTime;
            if (this.time <= 0)
                skipFrame();
        }
        draw();
    }

    /**
     * Отрисовка анимации от from кадра до to кадра
     *
     * @param from - первый кадр
     * @param to   - последний кадр
     */

    public void play(int from, int to) {
        if (active < from || active > to) {
            this.active = from;
            this.reverce = false;
            time = delay;
            play(from, to);
        } else if (hasPlay) {
            this.time -= Time.deltaTime;
            if (this.time <= 0)
                skipFrame(from, to);
        }
        draw();
    }

    public void showFrame(int index) {
        this.active = index;
        draw();
    }

    private void draw()
    {
        shader.start();
        shader.draw(active, position);
        shader.stop();
    }

    /**
     * Метод добавления кадра к анимации
     *
     * @param cords - координаты в текстурном пространстве кадра
     */

    public void addKeyFrame(float[] cords) {
        Frame frame = new Frame(cords);
        this.frames.add(frame);
        shader.reload();
    }

    /**
     * То же самое, что и первый, только добавления кадра
     *
     * @param frame - область кадра
     */

    public void addKeyFrame(Frame frame) {
        this.frames.add(frame);
    }

    /**
     * Установка задержки перематывания кадра
     *
     * @param delay - задержка в сек
     */

    public void setDelay(float delay) {
        this.delay = delay;
    }

    /**
     * Получение задержки перематывания кадра
     *
     * @return - задержка в сек
     */

    public float getDelay() {
        return delay;
    }

    /**
     * Метод отражения(проигрывания анимации в обратную сторону)
     *
     * @param mirror - надо ли отражать
     */

    public void setMirror(boolean mirror) {
        this.mirror = mirror;
    }

    /**
     * Отражается ли?
     *
     * @return
     */

    public boolean isMirror() {
        return mirror;
    }

    /**
     * Установка повторения анимации
     *
     * @param loop - надо ли повторять
     */

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    /**
     * Повторяется ли анимация
     *
     * @return
     */

    public boolean hasPlay() {
        return hasPlay;
    }

    /**
     * Перейти к последнему кадру
     */

    public void toLastFrame() {
        this.active = frames.size() - 1;
    }

    /**
     * Перейти к index кадру
     *
     * @param index - номер кадра
     */

    public void toFrame(int index) {
        if (index >= 0 && index < frames.size())
            this.active = index;
    }

    /**
     * Перейти к первому кадру
     */

    public void toFirstFrame() {
        this.active = 0;
    }

    public float getPositionX() {
        return -position[0];
    }

    public float getPositionY() {
        return position[1];
    }

    /**
     * Установка позиции position[0] = x, position[1] = y
     * Умножение на 2, так как размеры спрайта -1 до 1
     * @param x - x;
     * @param y - y;
     */

    public void setPosition(float x, float y) {
        this.position[0] = -x;
        this.position[1] = y;
    }

    /**
     * Метод уничтожения анимации
     */

    @Override
    public void release() {
        this.frames = null;
        this.delay = 0.0f;
        this.time = 0.0f;
    }
}
