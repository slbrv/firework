package com.ad2d.slava.abandoneddungeons.Scenes;

import com.ad2d.slava.abandoneddungeons.Game.Map;
import com.ad2d.slava.abandoneddungeons.Game.Player;
import com.firework.Modules.GUI.UICanvas;
import com.firework.Modules.Graphics.Animation.Animation;
import com.firework.Modules.Graphics.Camera;
import com.firework.Modules.Graphics.Color;
import com.firework.Modules.Graphics.OrthoCamera;
import com.firework.Modules.Graphics.Textures.Frame;
import com.firework.Modules.Graphics.Textures.Texture;
import com.firework.Modules.Graphics.Textures.TextureUtils;
import com.firework.Modules.OtherUtils.ColorUtils;
import com.firework.Modules.OtherUtils.MathUtils;
import com.firework.Modules.System.Game;
import com.firework.Modules.System.Scene;
import com.firework.Modules.System.Time;
import com.firework.Modules.Transform.Vector2;
import com.firework.Platforms.Android.Input.TouchEvent;

import java.io.File;
import java.util.List;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_CULL_FACE;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glEnable;

/**
 * Created by slava on 28.01.17.
 */

public class MainScene extends Scene {

    protected Camera camera;
    protected Camera uiCamera;
    protected UICanvas UICanvas;

    protected Vector2 screenSize;


    protected List<TouchEvent> touchEvents;

    protected Map map;

    private Color maxLavaSplash;
    private Color minLavaSplash;
    private Color animationLavaSplash;

    private Player player;

    private int MAP_WIDTH = 7;
    private int MAP_HEIGHT = 7;

    public MainScene(Game game) {
        super(game);
    }

    @Override
    public void start() {
        camera = new OrthoCamera(game.getGraphics().getWidth(), game.getGraphics().getHeight(), -1.0f, 10.0f, 1.0f);
        screenSize = new Vector2(game.getGraphics().getWidth(), game.getGraphics().getHeight());

        camera.setPosition(MAP_WIDTH / 2.0f - 0.5f, MAP_HEIGHT / 2.0f - 0.5f, -5.0f);
        camera.setZoom(1 / MathUtils.max(MAP_WIDTH / 2.0f, MAP_HEIGHT / 2.0f));
        camera.prepare();

        this.map = new Map(MAP_WIDTH, MAP_HEIGHT, camera);

        this.minLavaSplash = new Color(72, 2, 3);
        this.maxLavaSplash = new Color(67, 16, 4);
        this.animationLavaSplash = new Color(minLavaSplash);

        Texture playerTxtr = new Texture("player.png", true);

        Frame[] frames = TextureUtils.cutTexture(playerTxtr, 32);

        Animation playerAnimation = new Animation(camera, playerTxtr, frames);
        playerAnimation.setDelay(0.3f);
        playerAnimation.setPosition(map.getOpenDoorPosition(), 1.0f);
        this.player = new Player(playerAnimation, map, game.getAudio().loadSound("Sounds/door_ratting.mp3"));
        map.setPlayer(player);
        guiInit();

        glEnable(GL_CULL_FACE);
    }

    public void guiInit() {
        uiCamera = new OrthoCamera(game.getGraphics().getWidth(), game.getGraphics().getHeight(), -10.0f, 10.0f, 1.0f);
        uiCamera.prepare();
        Texture guiAtlas = new Texture("GUI" + File.separatorChar + "Standard" + File.separatorChar + "GUI.png");
        UICanvas = new UICanvas(uiCamera, guiAtlas, TextureUtils.cutTexture(guiAtlas, 64));
        //Font font = new Font(new Texture("Fonts" + File.separatorChar + "adfont.png"), "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz`12334567890-=~!@#$%^&*()_+,./;'{}\\<>?:{}|", 36, 70, 1.0f);
    }

    @Override
    public void update() {
        touchEvents = game.getInput().getTouchesList();
        UICanvas.update(game.getInput(), touchEvents);
    }

    private boolean lastClick = false;

    @Override
    public void render() {
        lavaSplashCalculate();

        glClear(GL_COLOR_BUFFER_BIT);

        map.draw();

        player.draw();

        if (game.getInput().isTouchDown(0)) {

            float x = game.getInput().getTouchX(0);
            float y = game.getInput().getTouchY(0);
            if (x < screenSize.getX() / 3 && !lastClick) {

                player.move(Player.LEFT);
                lastClick = true;
            } else if (x > screenSize.getX() / 3 * 2 && !lastClick) {

                player.move(Player.RIGHT);
                lastClick = true;

            } else if (y < screenSize.getY() / 3 && !lastClick) {

                player.move(Player.UP);
                lastClick = true;

            } else if (y > screenSize.getY() / 3 * 2 && !lastClick) {

                player.move(Player.DOWN);
                lastClick = true;

            } else if (x > screenSize.getX() / 3 &&
                    x < screenSize.getX() / 3 * 2 &&
                    y > screenSize.getY() / 3 &&
                    y < screenSize.getY() / 3 * 2) {


            }
        } else {
            lastClick = false;
        }

        camera.prepare();
        UICanvas.render();
    }

    @Override
    public void pause() {

    }


    private float lavaSplash = 0.1f;
    private boolean rise = true;
    private float lavaSplashSpeed = 1f;

    private void lavaSplashCalculate() {
        if (rise)
            lavaSplash += (lavaSplashSpeed * Time.deltaTime);
        else
            lavaSplash -= (lavaSplashSpeed * Time.deltaTime);
        if (lavaSplash >= 1.0f)
            rise = false;
        else if (lavaSplash <= 0.0f)
            rise = true;
        ColorUtils.mix(animationLavaSplash, maxLavaSplash, minLavaSplash, lavaSplash);
        glClearColor(animationLavaSplash.r, animationLavaSplash.g, animationLavaSplash.b, 1.0f);
    }

    @Override
    public void release() {

    }
}
