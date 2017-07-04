package com.touchpro.controller;

import android.content.Context;
import android.content.Intent;
@SuppressWarnings("all")
public class VolumeController {
    private Context mContext;

    public VolumeController(final Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void gotoSoundSetting() {
        final Intent intent = new Intent("android.settings.SOUND_SETTINGS");
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }
}