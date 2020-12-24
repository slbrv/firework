package com.firework.Platforms.Android.Audio;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.firework.Modules.Audio.Audio;
import com.firework.Modules.Audio.Music;
import com.firework.Modules.Audio.Sound;
import com.firework.Platforms.Android.Files.AndroidFileIO;

import java.io.IOException;

/**
 * Created by slava on 10.01.17.
 */

public class AndroidAudio implements Audio {

    private SoundPool soundPool;

    public AndroidAudio(Activity current) {
        current.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 0);
    }

    @Override
    public Sound loadSound(String soundName) {
        try {
            AssetFileDescriptor descriptor = AndroidFileIO.getAssetManager().openFd(soundName);
            int ID = soundPool.load(descriptor, 0);
            return new AndroidSound(soundPool, ID);
        } catch (IOException e) {
            Log.e("Exception: ", e.toString());
        }
        return null;
    }

    @Override
    public Music loadMusic(String musicName) {
        try {
            AssetFileDescriptor assetFileDescriptor = AndroidFileIO.getAssetManager().openFd(musicName);
            return new AndroidMusic(assetFileDescriptor);
        } catch (IOException e) {
            Log.e("Exception: ", e.toString());
        }
        return null;
    }
}
