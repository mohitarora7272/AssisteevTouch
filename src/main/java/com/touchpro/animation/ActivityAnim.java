package com.touchpro.animation;

import android.app.Activity;

import com.develop.touchpro.R;
@SuppressWarnings("all")
public class ActivityAnim {
    public static void fadeIn(final Activity activity) {
        activity.overridePendingTransition(R.anim.enter_activity_in, R.anim.exit_activity);
    }

    public static void fadeOut(final Activity activity) {
        activity.overridePendingTransition(0, R.anim.enter_activity_out);
    }

    public static void slideIn(final Activity activity) {
        activity.overridePendingTransition(R.anim.slide_in, R.anim.notrans_activity);
    }

    public static void slideOut(final Activity activity) {
        activity.overridePendingTransition(0, R.anim.slide_out);
    }
}