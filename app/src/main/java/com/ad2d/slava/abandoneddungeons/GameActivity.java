package com.ad2d.slava.abandoneddungeons;


import com.ad2d.slava.abandoneddungeons.Scenes.MainScene;
import com.firework.Modules.System.Scene;
import com.firework.Platforms.Android.Core.AndroidGame;

public class GameActivity extends AndroidGame {
    public Scene getStartScene()
    {
        return new MainScene(this);
    }
}
