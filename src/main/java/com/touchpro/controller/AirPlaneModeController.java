package com.touchpro.controller;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
@SuppressWarnings("all")
public class AirPlaneModeController {
    private Context mContext;

    public AirPlaneModeController(final Context mContext) {
        super();
        this.mContext = mContext;
    }

    public void gotoAirplaneSetting() {
        final Intent intent = new Intent("android.settings.AIRPLANE_MODE_SETTINGS");
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }

    public boolean isAirplane() {
        final int int1 = Settings.System.getInt(this.mContext.getContentResolver(), "airplane_mode_on", 0);
        boolean b = false;
        if (int1 != 0) {
            b = true;
        }
        return b;
    }
}