package com.firework.Modules.GUI;

import com.firework.Modules.GameObjects.Sprite;
import com.firework.Modules.Transform.Rect2;
import com.firework.Modules.Transform.Vector2;

/**
 * Created by Slava on 30.03.2017.
 */

public abstract class UIElement {

//    public static final int PIN_LEFT_TOP = 0;
//    public static final int PIN_CENTER_TOP = 1;
//    public static final int PIN_RIGHT_TOP = 2;
//    public static final int PIN_RIGHT_MIDDLE = 3;
//    public static final int PIN_RIGHT_BOTTOM = 4;
//    public static final int PIN_CENTER_BOTTOM = 5;
//    public static final int PIN_LEFT_BOTTOM = 6;
//    public static final int PIN_LEFT_MIDDLE = 7;
//    public static final int PIN_CENTER = 8;

    protected Rect2 rect;
    protected Sprite sprite;
    protected static UICanvas canvas;

    public Rect2 getRect() {
        return rect;
    }

    public void setRect(Rect2 rect) {
        this.rect = rect;
    }

    @Deprecated
    public Sprite getSprite() {    //Не рекомендуется использовать для установки позиции и др.
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public abstract UIEvent getUIEvent();

    public abstract int getUIEventIndex();

    public void setPosition(float x, float y) {
        this.sprite.getTransform().setPosition(x, y);
        this.rect.setPosition(sprite.getTransform().getPosition());
    }

    public void setPosition(Vector2 vector) {
        setPosition(vector.getX(), vector.getY());
    }

    public static void setCanvas(UICanvas canvas)
    {
        UIElement.canvas = canvas;
    }

    public static UICanvas getCanvas()
    {
        return canvas;
    }
}
