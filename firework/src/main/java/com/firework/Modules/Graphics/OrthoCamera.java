package com.firework.Modules.Graphics;

import android.opengl.Matrix;

import com.firework.Modules.GameObjects.GameObject2D;

/**
 * Created by slava on 10.02.17.
 */

public class OrthoCamera extends Camera{

    public OrthoCamera(float width, float height, float near, float far, float zoom)
    {
        super(width, height, near, far, zoom);
    }

    @Override
    public void linkProjection() {
        Matrix.orthoM(projectionMatrix, 0, -ratio * (1/zoom), ratio * (1/zoom), -(1/zoom), (1/zoom), near, far);
    }


}
