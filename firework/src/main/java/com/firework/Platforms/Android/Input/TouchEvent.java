package com.firework.Platforms.Android.Input;

import com.firework.Modules.Transform.Vector2;

/**
 * Created by slava on 26.01.17.
 */

public class TouchEvent {
    public static final int TOUCH_DOWN = 0;
    public static final int TOUCH_UP = 1;
    public static final int TOUCH_MOVE = 2;

    public Vector2 position;
    public int type;
    public int pointerID;

    public TouchEvent()
    {
        this.position = new Vector2();
        this.type = TOUCH_UP;
        this.pointerID = -1;
    }
}
