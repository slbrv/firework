package com.firework.Platforms.Android.Graphics;


import android.opengl.GLSurfaceView;

import com.firework.Modules.Graphics.Graphics;

/**
 * Created by slava on 21.01.17.
 */

public class AndroidGraphics implements Graphics{

    private GLSurfaceView glSurfaceView;

    public AndroidGraphics(GLSurfaceView glSurfaceView)
    {
        this.glSurfaceView = glSurfaceView;
    }

    public int getWidth()
    {
        return glSurfaceView.getWidth();
    }
    public int getHeight()
    {
        return glSurfaceView.getHeight();
    }
}
