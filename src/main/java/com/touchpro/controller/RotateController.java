package com.touchpro.controller;

import android.content.Context;
import android.provider.Settings;
@SuppressWarnings("all")
public class RotateController {
    private Context mContext;

    public RotateController(final Context mContext) {
        super();
        this.mContext = mContext;
    }

    public int isRotate() {
        try {
            return Settings.System.getInt(this.mContext.getContentResolver(), "accelerometer_rotation");
        } catch (Settings.SettingNotFoundException ex) {
            ex.printStackTrace();
            return 0;
        }
    }

    public void setRotateSetting(final int n) {
        Settings.System.putInt(this.mContext.getContentResolver(), "accelerometer_rotation", n);
    }
}