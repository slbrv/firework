package com.firework.Modules.Graphics;

/**
 * Created by slava on 05.01.17.
 */

public interface Graphics {

    int getWidth();
    int getHeight();

    enum TextureFormat {
        RGBA8888,
        RGBA4444,
        RGB565,
    }
}
