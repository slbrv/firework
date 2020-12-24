package com.firework.Platforms.Android.Core;

import android.app.Activity;
import android.media.AudioManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.firework.LoadScene;
import com.firework.Modules.Assets.Loader;
import com.firework.Modules.Audio.Audio;
import com.firework.Modules.Graphics.Graphics;
import com.firework.Modules.Input.Input;
import com.firework.Modules.Internet.Internet;
import com.firework.Modules.System.ApplicationParams;
import com.firework.Modules.System.FileIO;
import com.firework.Modules.System.Game;
import com.firework.Modules.System.Scene;
import com.firework.Modules.System.Time;
import com.firework.Platforms.Android.Audio.AndroidAudio;
import com.firework.Platforms.Android.Files.AndroidFileIO;
import com.firework.Platforms.Android.Graphics.AndroidGraphics;
import com.firework.Platforms.Android.Input.AndroidInput;
import com.firework.Platforms.Android.Internet.AndroidInternet;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by slava on 27.01.17.
 */

public class AndroidGame extends Activity implements GLSurfaceView.Renderer, Game{

    protected GLSurfaceView glSurfaceView;
    protected static Graphics graphicsModule;
    protected static Input inputModule;
    protected static Audio audioModule;
    protected static FileIO fileIOModule;
    protected static Internet internet;

    protected static Loader loader;

    protected Scene scene;
    protected Scene startScene;

    protected float startTime;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);//Установка зависимости звука игры от звука телефона

        glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setRenderer(this);
        setContentView(glSurfaceView);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        inputModule = new AndroidInput(this, glSurfaceView, glSurfaceView.getWidth(), glSurfaceView.getHeight(), glSurfaceView.getWidth()
                , glSurfaceView.getHeight());
        audioModule = new AndroidAudio(this);
        fileIOModule = new AndroidFileIO(getAssets());
        loader = new Loader(this);
        internet = new AndroidInternet();
        graphicsModule = new AndroidGraphics(glSurfaceView);
        startScene = new LoadScene(this);

        startTime = System.nanoTime();

        scene = getStartScene();

        GLES20.glViewport(0, 0, glSurfaceView.getWidth(), glSurfaceView.getHeight());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        scene.start();
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f * Time.timeScale;
        startTime = System.nanoTime();

        Time.deltaTime = deltaTime;
        scene.update();
        scene.render();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        glSurfaceView.onPause();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        glSurfaceView.onResume();
    }

    /*
    SCENE
     */

    public void setScene(Scene scene) {
        if(scene != null)
        {
            this.scene.pause();
            this.scene.release();
            this.scene = scene;
            this.scene.start();
            this.scene.update();
        }
        else throw new NullPointerException("Scene can't be null");
    }


    @Override
    public Scene getCurrentScene() {
        return this.scene;
    }

    @Override
    public Scene getStartScene()
    {
        return startScene;
    }

    /*
    GETTERS
     */

    @Override
    public Graphics getGraphics() {
        return this.graphicsModule;
    }

    @Override
    public Input getInput() {
        return this.inputModule;
    }

    @Override
    public Audio getAudio() {
        return this.audioModule;
    }

    @Override
    public FileIO getFileIO() {
        return this.fileIOModule;
    }

    @Override
    public Internet getInternet() {
        return internet;
    }
}
