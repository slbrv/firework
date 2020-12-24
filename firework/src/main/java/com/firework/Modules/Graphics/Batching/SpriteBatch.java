package com.firework.Modules.Graphics.Batching;

import com.firework.Modules.GameObjects.Sprite;
import com.firework.Modules.Graphics.Camera;
import com.firework.Modules.Graphics.Shaders.Shader;
import com.firework.Modules.Graphics.Textures.Texture;
import com.firework.Modules.System.Releasable;

/**
 * Created by Slava on 05.03.2017.
 */

public class SpriteBatch implements Releasable {

    protected Shader shader;
    protected Texture texture;
    protected Sprite sprite;

    protected Camera camera;

    public SpriteBatch(Camera camera) {
        this.camera = camera;

        this.shader = new SpriteBatchShader(this);
    }

    public void start(Texture texture) {
        this.texture = texture;

        shader.start();
    }

    public void draw(Sprite sprite) {
        this.sprite = sprite;
        shader.draw();
    }

    public void stop() {

        shader.stop();
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Shader getShader() {
        return shader;
    }

    public void setShader(Shader shader) {
        this.shader = shader;
    }

    public Sprite getSprite() {
        return sprite;
    }

    @Override
    public void release() {

    }
}
