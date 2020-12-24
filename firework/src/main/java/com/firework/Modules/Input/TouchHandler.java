package com.firework.Modules.Input;

import android.view.View;

import com.firework.Platforms.Android.Input.TouchEvent;

import java.util.List;

/**
 * Created by slava on 20.01.17.
 */

public interface TouchHandler extends View.OnTouchListener {
    int getTouchX(int pointerID);

    int getTouchY(int pointerID);

    boolean isTouchDown(int pointerID);

    List<TouchEvent> getTouchEvents();

    int getTouchesCount();
}
