package com.touchpro.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
@SuppressWarnings("all")
public class ViewAnimationUtil {
    private static int LENGTH;

    static {
        ViewAnimationUtil.LENGTH = 310;
    }

    public static void setViewScaleIn(final View view, final int n) {
        view.setVisibility(View.VISIBLE);
        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration((long) (ViewAnimationUtil.LENGTH - n * 70));
        scaleAnimation.setStartOffset((long) (ViewAnimationUtil.LENGTH - n * 70));
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener((Animation.AnimationListener) new Animation.AnimationListener() {
            public void onAnimationEnd(final Animation animation) {
                view.clearAnimation();
            }

            public void onAnimationRepeat(final Animation animation) {
            }

            public void onAnimationStart(final Animation animation) {
            }
        });
        view.startAnimation((Animation) scaleAnimation);
        view.setFocusable(true);
        view.setClickable(true);
    }

    public static void setViewScaleOut(final View view, final int n) {
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setDuration((long) (ViewAnimationUtil.LENGTH - n * 70));
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener((Animation.AnimationListener) new Animation.AnimationListener() {
            public void onAnimationEnd(final Animation animation) {
                view.clearAnimation();
            }

            public void onAnimationRepeat(final Animation animation) {
            }

            public void onAnimationStart(final Animation animation) {
            }
        });
        view.startAnimation((Animation) scaleAnimation);
        view.setVisibility(View.GONE);
        view.setFocusable(false);
        view.setClickable(false);
    }
}