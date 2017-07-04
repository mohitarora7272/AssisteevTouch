package com.touchpro.easytouch;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.develop.touchpro.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.touchpro.adapter.PanelSettingPagerAdapter;
import com.touchpro.animation.ActivityAnim;

@SuppressWarnings("all")
public class PanelSettingActivity extends FragmentActivity {
    View.OnClickListener onClickListener;
    private AdView mAdView;

    public PanelSettingActivity() {
        super();
        this.onClickListener = (new View.OnClickListener() {
            public void onClick(final View view) {
                switch (view.getId()) {
                    default: {
                    }
                    case R.id.panel_setting_layout_back_container: {
                        PanelSettingActivity.this.finish();
                        ActivityAnim.slideOut(PanelSettingActivity.this);
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActivityAnim.slideOut(this);
    }

    @Override
    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.panel_setting_activity_layout);
        ViewPager viewPager;
        (viewPager = (ViewPager) this.findViewById(R.id.setting_layout_viewpager)).setAdapter(new PanelSettingPagerAdapter(this.getSupportFragmentManager()));
        viewPager.setCurrentItem(0);
        (this.findViewById(R.id.panel_setting_layout_back_container)).setOnClickListener(this.onClickListener);
        showBannerAd();
    }

    // Show Banner Ads
    private void showBannerAd() {
        mAdView = (AdView) findViewById(R.id.adsView);
        // For Testing Purpose
//        AdRequest adRequest = new AdRequest.Builder()
//                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                // Check the LogCat to get your test device ID
//                .addTestDevice("9E847D99F08C0028B6613E597754B38A")
//                .build();
        mAdView.loadAd(new AdRequest.Builder().build());
        mAdView.setAdListener(new AdListener() {
            public void onAdLoaded() {
                Log.e("Banner", "onAdLoaded");
            }

            @Override
            public void onAdClosed() {
                Log.e("Banner", "onAdClosed");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("Banner", "onAdFailedToLoad>>" + errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                Log.e("Banner", "onAdLeftApplication");
            }

            @Override
            public void onAdOpened() {
                Log.e("Banner", "onAdOpened");

            }
        });
    }
}