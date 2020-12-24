package com.firework.Modules.GUI.Button;

import com.firework.Modules.GUI.UIEvent;

/**
 * Created by Slava on 30.03.2017.
 */

public interface UICatchable extends UIEvent {

    int CLICKED = 0;
    int DRAGGED = 1;
    int DROPPED = 2;

    int getCatchState();
    void setCatchState(int state);

    UICatchHandler getCatchHandler();
    void setCatchHandler(UICatchHandler handler);

    boolean onClick();
    boolean onDrag();
    boolean onDrop();
}
