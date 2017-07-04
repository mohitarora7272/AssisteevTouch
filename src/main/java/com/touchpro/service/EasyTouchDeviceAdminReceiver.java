package com.touchpro.service;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
@SuppressWarnings("all")
public class EasyTouchDeviceAdminReceiver extends DeviceAdminReceiver {
    static final String TAG = "DemoDeviceAdminReceiver";

    public void onDisabled(final Context context, final Intent intent) {
        super.onDisabled(context, intent);
        Log.d("DemoDeviceAdminReceiver", "onDisabled");
    }

    public void onEnabled(final Context context, final Intent intent) {
        super.onEnabled(context, intent);
        Toast.makeText(context, (CharSequence) "You can lock screen now", Toast.LENGTH_LONG).show();
        Log.d("DemoDeviceAdminReceiver", "onEnabled");
    }

    public void onPasswordChanged(final Context context, final Intent intent) {
        super.onPasswordChanged(context, intent);
        Log.d("DemoDeviceAdminReceiver", "onPasswordChanged");
    }

    public void onPasswordFailed(final Context context, final Intent intent) {
        super.onPasswordFailed(context, intent);
        Log.d("DemoDeviceAdminReceiver", "onPasswordFailed");
    }

    public void onPasswordSucceeded(final Context context, final Intent intent) {
        super.onPasswordSucceeded(context, intent);
        Log.d("DemoDeviceAdminReceiver", "onPasswordSucceeded");
    }
}