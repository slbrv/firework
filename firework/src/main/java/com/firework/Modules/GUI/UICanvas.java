package com.firework.Modules.GUI;

import com.firework.Modules.GUI.Button.UICatchable;
import com.firework.Modules.Graphics.Batching.SpriteBatch;
import com.firework.Modules.Graphics.Camera;
import com.firework.Modules.Graphics.Textures.Frame;
import com.firework.Modules.Graphics.Textures.Texture;
import com.firework.Modules.Input.Input;
import com.firework.Platforms.Android.Input.TouchEvent;
import com.firework.Modules.OtherUtils.MathUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 30.03.2017.
 */

public class UICanvas {

    protected SpriteBatch batch;
    protected Camera uiCamera;
    protected Texture atlas;
    protected Frame[] frames;

    protected List<UIElement> elements;

    public static float CANVAS_WIDTH;
    public static float CANVAS_HEIGHT;

    public UICanvas(Camera uiCamera, Texture atlas, Frame[] frames) {
        this.uiCamera = uiCamera;
        this.atlas = atlas;
        this.frames = frames;
        elements = new ArrayList<>();
        this.batch = new SpriteBatch(uiCamera);
        this.CANVAS_WIDTH = uiCamera.getWidth();
        this.CANVAS_HEIGHT = 2.0f;
    }

    public void update(Input input, List<TouchEvent> events) {
        if (input.getTouchesCount() > 0) {
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getUIEventIndex() == UIEvent.CATCHABLE) {
                    UICatchable catchable = (UICatchable) elements.get(i).getUIEvent();
                    boolean hasOne = false;
                    boolean played = false;
                    for (int j = 0; j < events.size(); j++) {
                        boolean inRect = MathUtils.inRect(elements.get(i).getRect(), uiCamera.toCanvasCordsX(events.get(j).position.getX()), uiCamera.toCanvasCordsY(events.get(j).position.getY()));
                        if (inRect) {
                            switch (events.get(j).type) {
                                case TouchEvent.TOUCH_DOWN:
                                    hasOne = true;
                                    catchable.onClick();
                                    catchable.setCatchState(UICatchable.CLICKED);
                                    break;
                                case TouchEvent.TOUCH_MOVE:
                                    hasOne = true;
                                    catchable.onDrag();
                                    catchable.setCatchState(UICatchable.DRAGGED);
                                    break;
                                case TouchEvent.TOUCH_UP:
                                    hasOne = false;
                                    catchable.onDrop();
                                    catchable.setCatchState(UICatchable.DROPPED);
                                    break;
                            }
                        }
                        played = true;
                    }
                    if(!hasOne && played)
                    {
                        catchable.onDrop();
                        catchable.setCatchState(UICatchable.DROPPED);
                    }
                }
            }
        } else {
            for (int i = 0; i < elements.size(); i++) {
                if (elements.get(i).getUIEventIndex() == UIEvent.CATCHABLE) {
                    UICatchable UICatchable = (UICatchable) elements.get(i).getUIEvent();
                    if (UICatchable.getCatchState() == UICatchable.CLICKED || UICatchable.getCatchState() == UICatchable.DRAGGED) {
                        UICatchable.onDrop();
                        UICatchable.setCatchState(UICatchable.DROPPED);
                    }
                }
            }
        }
    }

    public void render() {
//        batch.start(atlas);
//        for (int i = 0; i < elements.size(); i++) {
//            batch.draw(elements.get(i).getSprite());
//        }
//        batch.stop();
    }

    public void addUIElement(UIElement element) {
        elements.add(element);
    }

    public void setCamera(Camera camera)
    {
        this.uiCamera = camera;
    }

    public Camera getCamera()
    {
        return uiCamera;
    }

    public Frame getPreparedFrame(int index) {
        return frames[index];
    }
}
