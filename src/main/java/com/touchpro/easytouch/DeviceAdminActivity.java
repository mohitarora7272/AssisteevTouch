package com.touchpro.easytouch;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import com.touchpro.service.EasyTouchDeviceAdminReceiver;
@SuppressWarnings("all")
public class DeviceAdminActivity extends Activity {
    private static final int DEVICE_ADMIN_REQUEST = 1000;

    protected void onActivityResult(final int n, final int n2, final Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 1000) {
            this.finish();
        }
    }

    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        final Intent intent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
        intent.putExtra("android.app.extra.DEVICE_ADMIN", new ComponentName(this, (Class) EasyTouchDeviceAdminReceiver.class));
        intent.putExtra("android.app.extra.ADD_EXPLANATION", "For Assistive Touch Lock Screen");
        this.startActivityForResult(intent, 1000);
    }
}