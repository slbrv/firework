package com.firework.Modules.Input;


import com.firework.Platforms.Android.Input.TouchEvent;

import java.util.List;

/**
 * Created by slava on 05.01.17.
 */

public interface Input {

    boolean isTouchDown(int keyCode);

    boolean isTouchDown();//Есть ли вообще нажатия на экран?

    int getTouchesCount();

    float getTouchX(int pointerID);

    float getTouchY(int pointerID);

    float getAccelerometerX();

    float getAccelerometerY();

    float getAccelerometerZ();

    List<TouchEvent> getTouchesList();
}