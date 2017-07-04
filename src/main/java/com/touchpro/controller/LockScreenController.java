package com.touchpro.controller;

import android.app.admin.*;
import android.os.*;
import android.app.*;
import com.touchpro.service.*;
import com.touchpro.easytouch.*;
import android.content.*;
@SuppressWarnings("all")
public class LockScreenController
{
    private ComponentName componentName;
    private DevicePolicyManager devicePolicyManager;
    private Context mContext;
    
    public LockScreenController(final Context mContext) {
        super();
        this.mContext = mContext;
    }
    
    private void lockScreenNow() {
        final KeyguardManager.KeyguardLock keyguardLock = ((KeyguardManager)this.mContext.getSystemService(Context.KEYGUARD_SERVICE)).newKeyguardLock(this.mContext.getPackageName());
        if (Build.VERSION.SDK_INT >= 14) {
            if (keyguardLock != null) {
                keyguardLock.disableKeyguard();
                this.devicePolicyManager.lockNow();
            }
            this.devicePolicyManager.lockNow();
            if (keyguardLock != null) {
                keyguardLock.reenableKeyguard();
            }
            return;
        }
        this.devicePolicyManager.lockNow();
    }
    
    public void handleClickLockScreen() {
        this.devicePolicyManager = (DevicePolicyManager)this.mContext.getSystemService(Context.DEVICE_POLICY_SERVICE);
        this.componentName = new ComponentName(this.mContext, (Class)EasyTouchDeviceAdminReceiver.class);
        if (this.devicePolicyManager.isAdminActive(this.componentName)) {
            this.lockScreenNow();
            return;
        }
        final Intent intent = new Intent(this.mContext, (Class)DeviceAdminActivity.class);
        intent.setFlags(268435456);
        this.mContext.startActivity(intent);
    }
}