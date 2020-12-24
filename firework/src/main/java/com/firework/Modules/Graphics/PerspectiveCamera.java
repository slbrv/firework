package com.firework.Modules.Graphics;

import android.opengl.Matrix;

/**
 * Created by Slava on 18.02.2017.
 */

public class PerspectiveCamera extends Camera {


    public PerspectiveCamera(float width, float height, float near, float far, float zoom) {
        super(width, height, near, far, zoom);
    }

    @Override
    public void linkProjection() {
        Matrix.frustumM(projectionMatrix, 0, -ratio * (1/zoom), ratio * (1/zoom), -1 * (1/zoom), 1 * (1/zoom), near, far);
    }
}
