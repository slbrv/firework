package com.firework.Platforms.Android.Audio;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.firework.Modules.Audio.Music;

import java.io.IOException;

/**
 * Created by slava on 11.01.17.
 */

public class AndroidMusic implements Music, MediaPlayer.OnCompletionListener {

    protected MediaPlayer mediaPlayer;
    protected boolean prepared = false;

    public AndroidMusic(AssetFileDescriptor descriptor) {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getStartOffset());
            mediaPlayer.prepare();
            prepared = true;
            mediaPlayer.setOnCompletionListener(this);

        } catch (Exception e) {
            throw new RuntimeException("Exception: " + e.toString());
        }

    }

    @Override
    public void play() {
        if (mediaPlayer.isPlaying())
            return;
        synchronized (this) {
            if (!prepared)
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            mediaPlayer.start();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        mediaPlayer.stop();
        synchronized (this) {
            prepared = false;
        }
    }

    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume, volume);
    }

    @Override
    public void setVolume(float lVolume, float rVolume) {
        mediaPlayer.setVolume(lVolume, rVolume);
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public boolean isStopped() {
        return !prepared;
    }

    @Override
    public boolean isLooping() {
        return mediaPlayer.isLooping();
    }

    @Override
    public void setLooping(boolean set) {
        mediaPlayer.setLooping(set);
    }

    @Override
    public void release() {
        if (mediaPlayer.isPlaying())
            mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public synchronized void onCompletion(MediaPlayer mp) {
        prepared = false;
    }
}
