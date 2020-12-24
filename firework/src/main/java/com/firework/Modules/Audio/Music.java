package com.firework.Modules.Audio;

/**
 * Created by slava on 05.01.17.
 */

public interface Music {
    void play();

    void pause();

    void stop();

    void setVolume(float volume);

    void setVolume(float lVolume, float rVolume);

    boolean isPlaying();

    boolean isStopped();

    boolean isLooping();

    void setLooping(boolean set);

    void release();
}
