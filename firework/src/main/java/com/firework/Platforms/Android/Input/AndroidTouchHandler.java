package com.firework.Platforms.Android.Input;

import android.view.MotionEvent;
import android.view.View;

import com.firework.Modules.Input.TouchHandler;
import com.firework.Modules.System.Pool;
import com.firework.Modules.System.PoolHandler;
import com.firework.Modules.Transform.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by slava on 20.01.17.
 */

public class AndroidTouchHandler implements TouchHandler {

    public final int MAX_TOUCHES = 10;

    protected boolean[] isTouch = new boolean[MAX_TOUCHES];
    protected int[] touchX = new int[MAX_TOUCHES];
    protected int[] touchY = new int[MAX_TOUCHES];
    protected int[] firstTouchesX = new int[MAX_TOUCHES];
    protected int[] firstTouchesY = new int[MAX_TOUCHES];

    protected Pool<TouchEvent> touchEventPool;

    protected List<TouchEvent> touchEventsList = new ArrayList<>();
    protected List<TouchEvent> touchEventsBuffer = new ArrayList<>();

    protected float scaleX, scaleY;
    protected Vector2 screenSize;

    protected int count;

    public AndroidTouchHandler(float scaleX, float scaleY, View view, Vector2 screenSize) {
        PoolHandler<TouchEvent> handler = new PoolHandler<TouchEvent>() {
            @Override
            public TouchEvent createObject() {
                return new TouchEvent();
            }
        };

        touchEventPool = new Pool<>(handler, 100);

        this.scaleX = scaleX;
        this.scaleY = scaleY;

        this.screenSize = screenSize;

        view.setOnTouchListener(this);
    }

    @Override
    public int getTouchX(int pointerID) {
        synchronized (this) {
            if (pointerID >= 0 && pointerID < MAX_TOUCHES)
                return touchX[pointerID];
            return 0;
        }
    }

    @Override
    public int getTouchY(int pointerID) {
        synchronized (this) {
            if (pointerID >= 0 && pointerID < MAX_TOUCHES)
                return touchY[pointerID];
            return 0;
        }
    }

    @Override
    public boolean isTouchDown(int pointerID) {
        synchronized (this) {
            if (pointerID >= 0 && pointerID < MAX_TOUCHES)
                return isTouch[pointerID];
            return isTouch[pointerID];
        }
    }

    @Override
    public List<TouchEvent> getTouchEvents() {
        synchronized (this) {
            int len = touchEventsList.size();
            for (int i = 0; i < len; i++) touchEventPool.free(touchEventsList.get(i));
            touchEventsList.clear();
            touchEventsList.addAll(touchEventsBuffer);
            touchEventsBuffer.clear();
            return touchEventsList;
        }
    }

    @Override
    public int getTouchesCount() {
        return count;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        synchronized (this) {
            int action = event.getActionMasked();
            int pointerIndex = event.getActionIndex();
            int pointerId = event.getPointerId(pointerIndex);
            TouchEvent touchEvent;
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_POINTER_DOWN:
                    touchEvent = touchEventPool.addObject();
                    touchEvent.type = TouchEvent.TOUCH_DOWN;
                    touchEvent.pointerID = pointerId;
                    touchX[pointerId] = (int) (event.getX(pointerIndex));
                    touchY[pointerId] = (int) (event.getY(pointerIndex));
                    touchEvent.position.set(touchX[pointerId], touchY[pointerId]);
                    isTouch[pointerId] = true;
                    touchEventsBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_POINTER_UP:
                case MotionEvent.ACTION_CANCEL:
                    touchEvent = touchEventPool.addObject();
                    touchEvent.type = TouchEvent.TOUCH_UP;
                    touchEvent.pointerID = pointerId;
                    touchX[pointerId] = (int) (event.getX(pointerIndex));
                    touchY[pointerId] = (int) (event.getY(pointerIndex));
                    touchEvent.position.set(touchX[pointerId], touchY[pointerId]);
                    isTouch[pointerId] = false;
                    touchEventsBuffer.add(touchEvent);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int pointerCount = event.getPointerCount();
                    for (int i = 0; i < pointerCount; i++) {
                        pointerIndex = i;
                        pointerId = event.getPointerId(pointerIndex);
                        touchEvent = touchEventPool.addObject();
                        touchEvent.type = TouchEvent.TOUCH_MOVE;
                        touchX[pointerId] = (int) (event.getX(pointerIndex));
                        touchY[pointerId] = (int) (event.getY(pointerIndex));
                        touchEvent.position.set(touchX[pointerId], touchY[pointerId]);
                        isTouch[pointerId] = true;
                        touchEventsBuffer.add(touchEvent);
                    }
                    break;
            }
            return true;
        }
    }
}