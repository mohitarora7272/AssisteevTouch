package com.touchpro.controller;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
@SuppressWarnings("all")
public class WifiController {
    private Context mContext;
    private WifiManager wifiManager;

    public WifiController(final Context mContext) {
        super();
        this.mContext = mContext;
        this.wifiManager = (WifiManager) this.mContext.getSystemService(Context.WIFI_SERVICE);
    }

    public void gotoWifiSetting() {
        final Intent intent = new Intent("android.settings.WIFI_SETTINGS");
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }

    public int isWifiEnable() {
        if (!this.wifiManager.isWifiEnabled()) {
            return 0;
        }
        return 1;
    }

    public void setWifi(final int n) {
        if (n == 0) {
            this.wifiManager.setWifiEnabled(false);
            return;
        }
        this.wifiManager.setWifiEnabled(true);
    }

    public void switchWifi() {
        if (!this.wifiManager.isWifiEnabled()) {
            this.wifiManager.setWifiEnabled(true);
            return;
        }
        this.wifiManager.setWifiEnabled(false);
    }
}