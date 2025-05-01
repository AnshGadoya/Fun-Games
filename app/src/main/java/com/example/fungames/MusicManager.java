package com.example.fungames;

import android.content.Context;
import android.media.MediaPlayer;

public class MusicManager {

    private static MediaPlayer mediaPlayer;
    private static float currentVolume = 1.0f; // default 100%
    private static boolean isPlaying = false;

    public static void start(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.game_bg);
            mediaPlayer.setLooping(true);
            mediaPlayer.setVolume(currentVolume, currentVolume);
        }

        if (!mediaPlayer.isPlaying()  && SettingsActivity.isCheck) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }

    public static void stop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    public static void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            isPlaying = false;
        }
    }

    public static void setVolume(float volume) {
        currentVolume = volume;
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
    }

    public static float getCurrentVolume() {
        return currentVolume;
    }

    public static boolean isPlaying() {
        return isPlaying;
    }
}
