package com.touchpro.controller;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;
@SuppressWarnings("all")
public class SoundModeController {
    private AudioManager audioManager;
    private int maxMusic;
    private int maxRing;

    public SoundModeController(final Context context) {
        super();
        this.audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        this.maxRing = this.audioManager.getStreamMaxVolume(2);
        this.maxMusic = this.audioManager.getStreamMaxVolume(3);
    }

    public void changeSoundMode() {
        if (this.audioManager.getRingerMode() == 0) {
            this.audioManager.setRingerMode(2);
            return;
        }
        this.audioManager.setRingerMode(-1 + this.audioManager.getRingerMode());
    }

    public void decreaseVolume(final int n, final int n2) {
        Log.d("TEST", "decreaseVolume: " + n + " / " + n2);
        final int streamVolume = this.audioManager.getStreamVolume(n);
        Log.d("TEST", "RING: " + streamVolume + " / " + this.audioManager.getStreamMaxVolume(n));
        final int streamVolume2 = this.audioManager.getStreamVolume(n2);
        Log.d("TEST", "MUCSIC: " + streamVolume2 + " / " + this.audioManager.getStreamMaxVolume(n2));
        this.audioManager.adjustStreamVolume(n, -1, 23);
        if (streamVolume != 0) {
            this.audioManager.setStreamVolume(n2, streamVolume2 - streamVolume2 / streamVolume, 0);
            return;
        }
        this.audioManager.setStreamVolume(n2, 0, 0);
    }

    public int getRingerMode() {
        return this.audioManager.getRingerMode();
    }

    public void increaseVolume(final int n, final int n2) {
        Log.d("TEST", "increaseVolume: " + n + " / " + n2);
        final int streamVolume = this.audioManager.getStreamVolume(n);
        final int streamMaxVolume = this.audioManager.getStreamMaxVolume(n);
        Log.d("TEST", "RING: " + streamVolume + " / " + streamMaxVolume);
        final int streamVolume2 = this.audioManager.getStreamVolume(n2);
        final int streamMaxVolume2 = this.audioManager.getStreamMaxVolume(n2);
        Log.d("TEST", "MUCSIC: " + streamVolume2 + " / " + streamMaxVolume2);
        this.audioManager.adjustStreamVolume(n, 1, 7);
        if (streamMaxVolume - streamVolume != 0) {
            this.audioManager.setStreamVolume(n2, streamVolume2 + (streamMaxVolume2 - streamVolume2) / (streamMaxVolume - streamVolume), 0);
            return;
        }
        this.audioManager.setStreamVolume(n2, streamMaxVolume2, 0);
    }

    public void volumeDown() {
        if (this.maxRing < this.maxMusic) {
            this.decreaseVolume(2, 3);
            return;
        }
        this.decreaseVolume(3, 2);
    }

    public void volumeMax() {
        this.audioManager.setStreamVolume(3, this.audioManager.getStreamMaxVolume(3), 0);
        this.audioManager.setStreamVolume(2, this.audioManager.getStreamMaxVolume(2), 1);
    }

    public void volumeMute() {
        this.audioManager.setStreamVolume(3, 0, 0);
        this.audioManager.setStreamVolume(2, 0, 17);
    }

    public void volumeUp() {
        if (this.maxRing < this.maxMusic) {
            this.increaseVolume(2, 3);
            return;
        }
        this.increaseVolume(3, 2);
    }
}