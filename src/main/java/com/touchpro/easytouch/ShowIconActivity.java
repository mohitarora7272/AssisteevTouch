package com.touchpro.easytouch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.touchpro.service.EasyTouchService;
@SuppressWarnings("all")
public class ShowIconActivity extends Activity {
    public void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        final Intent intent = new Intent(this, (Class) EasyTouchService.class);
        intent.setAction("com.touchpro.foregroundservice.action.startforeground");
        this.startService(intent);
        this.finish();
    }
}