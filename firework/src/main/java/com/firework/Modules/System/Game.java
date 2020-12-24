package com.firework.Modules.System;

import com.firework.Modules.Audio.Audio;
import com.firework.Modules.Graphics.Graphics;
import com.firework.Modules.Input.Input;
import com.firework.Modules.Internet.Internet;

/**
 * Created by slava on 05.01.17.
 */

public interface Game {

    void setScene(Scene scene);

    Graphics getGraphics();//GET GRAPHICS MODULE

    Input getInput();//GET INPUT MODULE

    Audio getAudio();//GET AUDIO MODULE

    FileIO getFileIO();//GET FILESYSTEM MODULE

    Internet getInternet();

    Scene getCurrentScene();//GET CURRENT SCREEN

    Scene getStartScene();//GET START SCREEN
}
