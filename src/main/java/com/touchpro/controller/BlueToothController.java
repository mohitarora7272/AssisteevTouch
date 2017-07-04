package com.touchpro.controller;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
@SuppressWarnings("all")
public class BlueToothController {
    private BluetoothAdapter mBluetoothAdapter;
    private Context mContext;

    public BlueToothController(final Context mContext) {
        super();
        this.mContext = mContext;
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public void gotoBluetoothSetting() {
        final Intent intent = new Intent("android.settings.BLUETOOTH_SETTINGS");
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }

    public int isBluetoothEnable() {
        if (this.mBluetoothAdapter != null && !this.mBluetoothAdapter.isEnabled()) {
            return 0;
        }
        return 1;
    }

    public void setBluetooth(final int n) {
        if (n == 0) {
            if (this.mBluetoothAdapter != null) {
                this.mBluetoothAdapter.disable();
            }
            return;
        }
        this.mBluetoothAdapter.enable();
    }
}