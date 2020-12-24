package com.firework.Modules.GUI.Button;

import android.support.annotation.Nullable;

import com.firework.Modules.GUI.UIElement;
import com.firework.Modules.GUI.UIEvent;
import com.firework.Modules.GameObjects.Sprite;
import com.firework.Modules.Graphics.Textures.Frame;
import com.firework.Modules.Transform.Rect2;

/**
 * Created by Slava on 30.03.2017.
 */
@Deprecated
public class UIButton extends UIElement implements UICatchable {

    protected UICatchHandler handler;
    protected Frame[] frames;
    protected int state;

    public UIButton(Rect2 rect, @Nullable UICatchHandler handler, Frame... frames)
    {
        if(handler == null)
            this.handler = new UICatchHandler() {
                @Override
                public boolean onClick() {
                    return true;
                }

                @Override
                public boolean onDrag() {
                    return true;
                }

                @Override
                public boolean onDrop() {
                    return true;
                }
            };
        else
            this.handler = handler;
        this.rect = rect;
        this.frames = frames;
        this.state = UICatchable.DROPPED;
        //this.sprite = new Sprite(rect, frames[0]);
    }

    @Override
    public UIEvent getUIEvent() {
        return this;
    }

    @Override
    public int getUIEventIndex() {
        return UIEvent.CATCHABLE;
    }

    @Override
    public int getCatchState() {
        return state;
    }

    @Override
    public void setCatchState(int state) {
        this.state = state;
    }

    @Override
    public UICatchHandler getCatchHandler() {
        return handler;
    }

    @Override
    public void setCatchHandler(UICatchHandler handler) {
        this.handler = handler;
    }

    @Override
    public boolean onClick() {
        //sprite.setFrame(frames[1]);
        return handler.onClick();
    }

    @Override
    public boolean onDrag() {
        //sprite.setFrame(frames[1]);
        return handler.onDrag();
    }

    @Override
    public boolean onDrop() {
        //sprite.setFrame(frames[0]);
        return handler.onDrop();
    }
}
