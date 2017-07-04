package com.touchpro.controller;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
@SuppressWarnings("all")
public class LocationController {
    private LocationManager locationManager;
    private Context mContext;

    public LocationController(final Context mContext) {
        super();
        this.mContext = mContext;
        this.locationManager = (LocationManager) this.mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    public void gotoLocationSetting() {
        final Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }

    public int isLocation() {
        if (!this.locationManager.isProviderEnabled("gps")) {
            return 0;
        }
        return 1;
    }
}