package com.touchpro.easytouch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.develop.touchpro.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.touchpro.adapter.CustomTypefaceSpan;
import com.touchpro.datamodel.DialogSelectListener;
import com.touchpro.dialog.BackgoundSelectDialog;
import com.touchpro.dialog.IconSelectDialog;
import com.touchpro.dialog.UninstallDialog;
import com.touchpro.service.EasyTouchDeviceAdminReceiver;
import com.touchpro.service.EasyTouchService;

@SuppressWarnings("all")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static MainActivity mContext;

    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ImageView imageEnable;
    private TextView textEnable;

    public static Typeface type_Roboto_Bold;
    public static Typeface type_Roboto_Medium;
    public static Typeface type_Roboto_Regular;
    private EasyTouchApplication mApp;

    private static boolean isStart;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    public static MainActivity getInstance() {
        return mContext;
    }

    public void enableTouch(View v) {
        if (!MainActivity.this.isStart) {
            MainActivity.this.startService();
            isStart = true;
            imageEnable.setImageResource(R.drawable.ic_disable_touch);
            textEnable.setText(R.string.disable_touch);
            showInterstitial();
        } else {
            MainActivity.this.stopService();
            isStart = false;
            imageEnable.setImageResource(R.drawable.ic_enable_touch);
            textEnable.setText(R.string.enable_touch);
        }
    }

    public void settingsChooser(View v) {
        startActivity(new Intent(this, PanelSettingActivity.class));
    }


    public void backgroundChooser(View v) {
        BackgoundSelectDialog backgoundSelectDialog;
        if (Build.VERSION.SDK_INT >= 11) {
            backgoundSelectDialog = new BackgoundSelectDialog(this, 16973937);
        } else {
            backgoundSelectDialog = new BackgoundSelectDialog(this, 0);
        }
        backgoundSelectDialog.setDialogSelectListener(new DialogSelectListener() {
            @Override
            public void onSelected(final int n) {
                mApp.setDisplayBackground(mApp.getColorList()[n]);
                startService();
            }
        });
    }


    public void iconChooser(View v) {
        IconSelectDialog iconSelectDialog;
        if (Build.VERSION.SDK_INT >= 11) {
            iconSelectDialog = new IconSelectDialog(this, 16973937);
        } else {
            iconSelectDialog = new IconSelectDialog(this, 0);
        }
        iconSelectDialog.setDialogSelectListener(new DialogSelectListener() {
            @Override
            public void onSelected(final int displayTheme) {
                mApp.setDisplayTheme(displayTheme);
                startService();
            }
        });
    }

    public void deactiveApp() {
        ((DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE)).removeActiveAdmin(new ComponentName(this, EasyTouchDeviceAdminReceiver.class));
        Toast.makeText(this, "Lock screen has been disabled", Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= 11) {
            new UninstallDialog(this, 16973937);
            return;
        }
        new UninstallDialog(this, 0);
    }

    public void startService() {
        final Intent intent = new Intent((Context) this, EasyTouchService.class);
        intent.setAction("com.touchpro.foregroundservice.action.startforeground");
        this.startService(intent);
    }

    public void stopService() {
        final Intent intent = new Intent((Context) this, EasyTouchService.class);
        intent.setAction("com.touchpro.foregroundservice.action.stopforeground");
        this.startService(intent);
    }

    public void uninstallApp() {
        final Intent intent = new Intent("android.intent.action.DELETE");
        intent.setData(Uri.parse("package:" + this.getPackageName()));
        this.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mContext = MainActivity.this;
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setItemIconTintList(null);
        imageEnable = (ImageView) findViewById(R.id.ic_enable_touch);
        textEnable = (TextView) findViewById(R.id.txtEnable);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkCAMERAPermission();
        } else {
            this.startService();
            isStart = true;
            imageEnable.setImageResource(R.drawable.ic_disable_touch);
            textEnable.setText(R.string.disable_touch);
        }

        showBannerAd();
        initInterstitialAd();

        mApp = (EasyTouchApplication) getApplication();
        this.mApp = (EasyTouchApplication) getApplicationContext();
        this.mApp.setLaunchTime();
        type_Roboto_Bold = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Bold.ttf");
        type_Roboto_Medium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");
        type_Roboto_Regular = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");


        if (navigationView != null) {
            Menu m = navigationView.getMenu();

            for (int i = 0; i < m.size(); i++) {
                MenuItem mi = m.getItem(i);

                SubMenu subMenu = mi.getSubMenu();
                if (subMenu != null && subMenu.size() > 0) {
                    for (int j = 0; j < subMenu.size(); j++) {
                        MenuItem subMenuItem = subMenu.getItem(j);
                        applyFontToMenuItem(subMenuItem);
                    }
                }
                applyFontToMenuItem(mi);
            }
        }
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.ad:
                        final String recmyscreen = "com.androiddeveloper";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + recmyscreen)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + recmyscreen)));
                        }
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.ms:
                        final String pv = "com.motivationselfie";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pv)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pv)));
                        }
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.bw:
                        final String bw = "com.boardwords";
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + bw)));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + bw)));
                        }
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.uninstall:
                        ((DevicePolicyManager) getApplicationContext().getSystemService(Context.DEVICE_POLICY_SERVICE)).removeActiveAdmin(new ComponentName(getApplicationContext(), (Class) EasyTouchDeviceAdminReceiver.class));
                        uninstallApp();
                        drawerLayout.closeDrawers();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Nothing Selected!", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };


        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_rate) {
            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        } else if (id == R.id.action_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Hey, Try Assisteev Touch, a fantastic app that allows you to organize your device's screen placing shortcuts. Available for Free on Google Play, DOWNLOAD NOW! https://play.google.com/store/apps/details?id=com.com.develop.touchpro";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Assisteev Touch - Free Mobile Touch Assistant");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share with..."));
        } else if (id == R.id.action_more) {
            final String publisher = "com.motivationselfie";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://developer?id=" + publisher)));
            } catch (ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=" + publisher)));
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void finishApp() {
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkCAMERAPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 2);
        } else {
            checkINTERNETPermission();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkINTERNETPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE}, 3);
        } else {
            check();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 2:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    checkINTERNETPermission();
                } else {
                    checkINTERNETPermission();
                }
                break;
            case 3:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    check();
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void check() {
        if (!Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);
        } else {
            this.startService();
            isStart = true;
            imageEnable.setImageResource(R.drawable.ic_disable_touch);
            textEnable.setText(R.string.disable_touch);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent in = new Intent(MainActivity.this, MainActivity.class);
        startActivity(in);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                break;
            default:
                break;
        }
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

    // Initialize InterstitialAd
    private void initInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);
        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial));
        loadInterstitialAds();
    }

    // Show Interstitial Ads
    private void loadInterstitialAds() {
        // Request a new ad if one isn't already loaded, hide the button, and kick off the timer.
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            // For Testing Purpose
//            AdRequest adRequest = new AdRequest.Builder()
//                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
//                    // Check the LogCat to get your test device ID
//                    .addTestDevice("9E847D99F08C0028B6613E597754B38A")
//                    .build();
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                Log.e("Interstitial", "onAdLoaded");
            }

            @Override
            public void onAdClosed() {
                Log.e("Interstitial", "onAdClosed");
                // Load the next interstitial.
                loadInterstitialAds();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.e("Interstitial", "onAdFailedToLoad>>" + errorCode);
            }

            @Override
            public void onAdLeftApplication() {
                Log.e("Interstitial", "onAdLeftApplication");
            }

            @Override
            public void onAdOpened() {
                Log.e("Interstitial", "onAdOpened");
            }
        });
    }

    // Show Interstitial Ad
    private void showInterstitial() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // Show Ads
                if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @SuppressLint("NewApi")
    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }

        super.onPause();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }

        if (mInterstitialAd != null) {
            mInterstitialAd = null;
        }

        super.onDestroy();
    }
}