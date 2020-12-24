package com.firework.Platforms.Android.Input;

import android.content.Context;
import android.view.View;

import com.firework.Modules.Input.Input;
import com.firework.Modules.Transform.Vector2;

import java.util.List;

/**
 * Created by slava on 17.01.17.
 */

public class AndroidInput implements Input{

    AndroidTouchHandler touchHandler;
    AndroidAccelerometerHandler accelerometerHandler;

    public AndroidInput(Context context, View view, float width, float height, float scaleX, float scaleY) {
        touchHandler = new AndroidTouchHandler(scaleX, scaleY, view, new Vector2(width, height));
        accelerometerHandler = new AndroidAccelerometerHandler(context);
    }

    @Override
    public float getTouchX(int pointerID) {
        return touchHandler.getTouchX(pointerID);
    }

    @Override
    public float getTouchY(int pointerID) {
        return touchHandler.getTouchY(pointerID);
    }

    @Override
    public float getAccelerometerX() {
        return accelerometerHandler.getX();
    }

    @Override
    public float getAccelerometerY() {
        return accelerometerHandler.getY();
    }

    @Override
    public float getAccelerometerZ() {
        return accelerometerHandler.getZ();
    }

    @Override
    public List<TouchEvent> getTouchesList() {
        return touchHandler.getTouchEvents();
    }

    @Override
    public boolean isTouchDown(int keyCode) {
        return touchHandler.isTouchDown(keyCode);
    }

    @Override
    public boolean isTouchDown() {
        for (int i = 0; i < touchHandler.MAX_TOUCHES; i++) {
            if(isTouchDown(i))
                return true;
        }
        return false;
    }

    @Override
    public int getTouchesCount() {
        int count = 0;
        for (int i = 0; i < touchHandler.MAX_TOUCHES; i++) {
            if(isTouchDown(i))
                count++;
        }
        return count;
    }

}
