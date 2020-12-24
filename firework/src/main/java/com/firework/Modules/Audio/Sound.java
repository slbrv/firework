package com.firework.Modules.Audio;

/**
 * Created by slava on 05.01.17.
 */

public interface Sound {
    /**
     * Play Sound
     * @param volume - 0.0f - 1.0f volume
     */
    void play(float volume);

    void play(float lVolume, float rVolume);

    void release();
}
