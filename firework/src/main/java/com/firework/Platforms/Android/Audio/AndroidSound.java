package com.firework.Platforms.Android.Audio;

import android.media.SoundPool;

import com.firework.Modules.Audio.Sound;

/**
 * Created by slava on 11.01.17.
 */

public class AndroidSound implements Sound {
    protected int id;
    private SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int id) {
        this.id = id;
        this.soundPool = soundPool;
    }

    @Override
    public void play(float volume) {
        soundPool.play(id, volume, volume, 0, 0, 1);
    }

    @Override
    public void play(float lVolume, float rVolume) {
        soundPool.play(id, lVolume, rVolume, 0, 0, 1);
    }

    @Override
    public void release() {
        soundPool.unload(id);
    }
}
