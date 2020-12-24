package com.firework.Modules.Assets;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.firework.Modules.System.FileIO;
import com.firework.Modules.System.Game;

import java.io.IOException;

/**
 * Created by Slava on 18.02.2017.
 */

public class Loader {
    protected static FileIO fileIO;

    public Loader(Game _game)
    {
        fileIO = _game.getFileIO();
    }

    public static FileIO getFileIO()
    {
        return fileIO;
    }

    /**
     * @param name - название битмапа
     * @return null - если не загружена картинка
     * @return bitmap - загрузка битмапа
     */

    public static Bitmap loadBitmap(String name)
    {
        try {
            return BitmapFactory.decodeStream(fileIO.loadAsset(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
