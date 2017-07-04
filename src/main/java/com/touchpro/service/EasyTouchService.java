package com.touchpro.service;

import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.develop.touchpro.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.touchpro.adapter.ActionAdapter;
import com.touchpro.animation.ViewAnimationUtil;
import com.touchpro.constant.ACTION;
import com.touchpro.controller.AirPlaneModeController;
import com.touchpro.controller.BlueToothController;
import com.touchpro.controller.ClearRamController;
import com.touchpro.controller.FlashlightController;
import com.touchpro.controller.HomePressController;
import com.touchpro.controller.LaunchAppController;
import com.touchpro.controller.LocationController;
import com.touchpro.controller.LockScreenController;
import com.touchpro.controller.RotateController;
import com.touchpro.controller.SoundModeController;
import com.touchpro.controller.WifiController;
import com.touchpro.datamodel.ActionItem;
import com.touchpro.datamodel.AppInfo;
import com.touchpro.easytouch.AllAppActivity;
import com.touchpro.easytouch.EasyTouchApplication;
import com.touchpro.easytouch.MainActivity;
import com.touchpro.easytouch.ShowIconActivity;

import java.util.ArrayList;

@SuppressWarnings("all")
public class EasyTouchService extends Service {
    private static final String TAG = "EASY TOUCH DEBUG";
    private static final int TYPE_FAVOR = 3;
    private static final int TYPE_MAIN = 1;
    private static final int TYPE_SETTING = 2;
    private ArrayList<ActionItem> actionListCurrent;
    private ArrayList actionListFavor;
    private ArrayList<ActionItem> actionListMain;
    private ArrayList<ActionItem> actionListSetting;
    Animation action_animation;
    private ActionAdapter adapterFavor;
    private ActionAdapter adapterMain;
    private ActionAdapter adapterSetting;
    private AirPlaneModeController airPlaneModeController;
    LayoutAnimationController animControllerFadeIn;
    LayoutAnimationController animControllerFadeOut;
    LayoutAnimationController animControllerLeft;
    LayoutAnimationController animControllerRight;
    private BlueToothController blueToothController;
    private ImageView chatHead;
    private ClearRamController clearRamController;
    float displayAlpha;
    int displayAnim;
    int displayColor;
    int displaySize;
    int displayTheme;
    private FlashlightController flashlightController;
    GridView gvBackground;
    GridView gvFavor;
    GridView gvMain;
    GridView gvSetting;
    final Handler handler;
    private Handler hideHandler;
    private Runnable hideRunnable;
    HomePressController homePressController;
    int iconHeight;
    private Bitmap iconNotification;
    int iconWidth;
    private boolean isTipFavor;
    private boolean isTipMain;
    private boolean isTipSetting;
    private LaunchAppController lauchAppController;
    private LocationController locationController;
    EasyTouchApplication mApp;
    private int mCurrentType;
    Runnable mLongPressed;
    private ViewGroup mParentView;
    private Notification notification;
    private AdapterView.OnItemClickListener onItemClickListener;
    AdapterView.OnItemLongClickListener onItemLongClickListener;
    private WindowManager.LayoutParams params;
    private Dialog popupTouch;
    private RotateController rotateController;
    private int screenHeight;
    private int screenWidth;
    private SoundModeController soundModeController;
    private WifiController wifiController;
    private WindowManager windowManager;
    private InterstitialAd mInterstitialAd;

    public EasyTouchService() {
        super();
        this.handler = new Handler();
        this.mParentView = null;
        this.mCurrentType = 1;
        this.actionListCurrent = new ArrayList(9);
        this.actionListMain = new ArrayList(9);
        this.actionListSetting = new ArrayList(9);
        this.actionListFavor = new ArrayList(9);
        this.onItemClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView adapterView, final View view, final int n, final long n2) {
                final ActionItem actionItem = EasyTouchService.this.actionListCurrent.get(n);
                if (actionItem != null) {
                    switch (actionItem.getAction()) {
                        case 1000: {
                            EasyTouchService.this.popupTouch.dismiss();
                            EasyTouchService.this.homePressController.handleHomePress();
                            return;
                        }
                        case 1001: {
                            showInterstitial();
                            EasyTouchService.this.setListViewDisplay(2, false);
                            return;
                        }
                        case 1002: {
                            EasyTouchService.this.popupTouch.dismiss();
                            new LockScreenController(EasyTouchService.this).handleClickLockScreen();
                            return;
                        }
                        case 1003: {
                            EasyTouchService.this.setListViewDisplay(3, false);
                            return;
                        }
                        case 1004: {
                            EasyTouchService.this.popupTouch.dismiss();
                            EasyTouchService.this.locationController.gotoLocationSetting();
                            return;
                        }
                        case 1005: {
                            EasyTouchService.this.refreshWifi(actionItem, false);
                            EasyTouchService.this.wifiController.setWifi(actionItem.getValue());
                            return;
                        }
                        case 1006: {
                            EasyTouchService.this.popupTouch.dismiss();
                            EasyTouchService.this.airPlaneModeController.gotoAirplaneSetting();
                            return;
                        }
                        case 1007: {
                            //EasyTouchService.this.refreshBluetooth(actionItem, false);
                            //EasyTouchService.this.blueToothController.setBluetooth(actionItem.getValue());
                            EasyTouchService.this.popupTouch.dismiss();
                            stopForeground(true);
                            stopSelf();
                            if (MainActivity.getInstance() != null) {
                                MainActivity.getInstance().finishApp();
                            }
                            return;
                        }
                        case 1008: {
                            EasyTouchService.this.refreshRotate(actionItem, false);
                            EasyTouchService.this.rotateController.setRotateSetting(actionItem.getValue());
                            return;
                        }
                        case 1009: {
                            view.startAnimation(EasyTouchService.this.action_animation);
                            EasyTouchService.this.clearRamController.cleanRam();
                            return;
                        }
                        case 1010: {
                            if (!EasyTouchService.this.flashlightController.isFlashOn()) {
                                EasyTouchService.this.flashlightController.setFlashlight(true);
                            } else {
                                EasyTouchService.this.flashlightController.setFlashlight(false);
                            }
                            EasyTouchService.this.refreshFlashlight(actionItem, false);
                            return;
                        }
                        case 1011:
                        case 1012: {
                            EasyTouchService.this.soundModeController.changeSoundMode();
                            EasyTouchService.this.refreshSoundMode(actionItem);
                            return;
                        }
                        case 1013: {
                            EasyTouchService.this.popupTouch.dismiss();
                            final Intent intent = new Intent(EasyTouchService.this, AllAppActivity.class);
                            intent.setFlags(268435456);
                            intent.putExtra("pos", n);
                            EasyTouchService.this.startActivity(intent);
                            return;
                        }
                        case 1014: {
                            EasyTouchService.this.setListViewDisplay(1, false);
                            return;
                        }
                        case 1015: {
                            EasyTouchService.this.soundModeController.volumeUp();
                            return;
                        }
                        case 1016: {
                            EasyTouchService.this.soundModeController.volumeDown();
                            return;
                        }
                        case 2000: {
                            EasyTouchService.this.popupTouch.dismiss();
                            EasyTouchService.this.lauchAppController.launchApp(actionItem.getPackageName());
                            return;
                        }
                        default:
                            return;
                    }
                }
            }
        };
        this.onItemLongClickListener = (new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(final AdapterView adapterView, final View view, final int n, final long n2) {
                switch (EasyTouchService.this.actionListCurrent.get(n).getAction()) {
                    default: {
                        return true;
                    }
                    case 1000: {
                        EasyTouchService.this.popupTouch.dismiss();
                        EasyTouchService.this.homePressController.openRecentApp();
                        return true;
                    }
                    case 1005: {
                        EasyTouchService.this.popupTouch.dismiss();
                        EasyTouchService.this.wifiController.gotoWifiSetting();
                        return true;
                    }
                    case 1007: {
                        //EasyTouchService.this.popupTouch.dismiss();
                        //EasyTouchService.this.blueToothController.gotoBluetoothSetting();
                        return true;
                    }
                    case 2000: {
                        EasyTouchService.this.actionListFavor.remove(n);
                        EasyTouchService.this.actionListFavor.add(n, ACTION.ADD_APP);
                        EasyTouchService.this.adapterFavor.notifyDataSetChanged();
                        EasyTouchService.this.setListViewDisplay(3, true);
                        EasyTouchService.this.mApp.saveList("list_favor", EasyTouchService.this.actionListFavor);
                        return true;
                    }
                }
            }
        });
        this.mLongPressed = new Runnable() {
            @Override
            public void run() {
                try {
                    EasyTouchService.this.windowManager.removeView(EasyTouchService.this.chatHead);
                    final Intent intent = new Intent(EasyTouchService.this, (Class) ShowIconActivity.class);
                    intent.setFlags(268435456);
                    EasyTouchService.access$16(EasyTouchService.this, new NotificationCompat.Builder(EasyTouchService.this).setContentTitle(String.valueOf(EasyTouchService.this.getResources().getString(R.string.app_name)) + " in here").setTicker(String.valueOf(EasyTouchService.this.getResources().getString(R.string.app_name)) + " in here").setContentText("Click here back to screen").setSmallIcon(R.drawable.ic_small_noti_circle).setLargeIcon(Bitmap.createScaledBitmap(EasyTouchService.this.iconNotification, 128, 128, false)).setContentIntent(PendingIntent.getActivity(EasyTouchService.this, 0, (Intent) intent, 0)).setOngoing(true).build());

                    //EasyTouchService.this.notification = new Builder(EasyTouchService.this).setContentTitle(new StringBuilder(String.valueOf(EasyTouchService.this.getResources().getString(R.string.app_name))).append(" in here").toString()).setTicker(new StringBuilder(String.valueOf(EasyTouchService.this.getResources().getString(R.string.app_name))).append(" in here").toString()).setContentText("Click here back to screen").setSmallIcon(R.drawable.ic_small_noti_circle).setLargeIcon(Bitmap.createScaledBitmap(EasyTouchService.this.iconNotification, TransportMediator.FLAG_KEY_MEDIA_NEXT, TransportMediator.FLAG_KEY_MEDIA_NEXT, false)).setContentIntent(PendingIntent.getActivity(EasyTouchService.this, 0, obj, 0)).setOngoing(true).build();

                    EasyTouchService.this.startForeground(101, EasyTouchService.this.notification);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                }


            }
        };
    }

    static /* synthetic */ void access$16(final EasyTouchService easyTouchService, final Notification notification) {
        easyTouchService.notification = notification;
    }

    public void adapterDataSetChanged() {
        this.adapterMain.notifyDataSetChanged();
        this.adapterSetting.notifyDataSetChanged();
    }

    public void hide() {
        this.hideHandler.removeCallbacks(this.hideRunnable);
        this.hideHandler.postDelayed(this.hideRunnable, 2000L);
    }

    public void initAnimController() {
        final AnimationSet set = new AnimationSet(true);
        final AlphaAnimation alphaAnimation = new AlphaAnimation(0.5f, 1.0f);
        (alphaAnimation).setDuration((long) (400 - 100 * this.displayAnim));
        set.addAnimation(alphaAnimation);
        final TranslateAnimation translateAnimation = new TranslateAnimation(1, 1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
        (translateAnimation).setInterpolator(new LinearInterpolator());
        (translateAnimation).setDuration((long) (400 - 100 * this.displayAnim));
        set.addAnimation(translateAnimation);
        this.animControllerRight = new LayoutAnimationController(set, 0.0f);
        final AnimationSet set2 = new AnimationSet(true);
        final AlphaAnimation alphaAnimation2 = new AlphaAnimation(0.5f, 1.0f);
        (alphaAnimation2).setDuration((long) (400 - 100 * this.displayAnim));
        set2.addAnimation(alphaAnimation2);
        final TranslateAnimation translateAnimation2 = new TranslateAnimation(1, -1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
        (translateAnimation2).setDuration((long) (400 - 100 * this.displayAnim));
        (translateAnimation2).setInterpolator(new LinearInterpolator());
        set2.addAnimation(translateAnimation2);
        this.animControllerLeft = new LayoutAnimationController(set2, 0.0f);
        final AnimationSet set3 = new AnimationSet(true);
        final AlphaAnimation alphaAnimation3 = new AlphaAnimation(0.0f, 1.0f);
        (alphaAnimation3).setDuration((long) (400 - 100 * this.displayAnim));
        (alphaAnimation3).setStartOffset((long) (400 - 100 * this.displayAnim));
        set3.addAnimation(alphaAnimation3);
        this.animControllerFadeIn = new LayoutAnimationController(set3, 0.0f);
        final AnimationSet set4 = new AnimationSet(true);
        final AlphaAnimation alphaAnimation4 = new AlphaAnimation(1.0f, 0.0f);
        (alphaAnimation4).setDuration((long) (400 - 100 * this.displayAnim));
        set4.addAnimation(alphaAnimation4);
        this.animControllerFadeOut = new LayoutAnimationController(set4, 0.0f);
    }

    @SuppressWarnings("ResourceType")
    public void initPopup() {
        this.popupTouch = new Dialog(this);
        this.popupTouch.getWindow().setBackgroundDrawableResource(17170445);
        this.popupTouch.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        this.popupTouch.requestWindowFeature(1);
        this.popupTouch.setContentView(R.layout.main_popup);
        this.popupTouch.getWindow().setType(2003);
        this.popupTouch.getWindow().clearFlags(TYPE_SETTING);
        this.popupTouch.setCanceledOnTouchOutside(true);
        this.popupTouch.setOnDismissListener((new DialogInterface.OnDismissListener() {
            final EasyTouchService this$0 = EasyTouchService.this;

            public void onDismiss(final DialogInterface dialogInterface) {
                EasyTouchService.this.params.alpha = 1.0f;
                EasyTouchService.this.hide();
                try {
                    EasyTouchService.this.windowManager.addView(EasyTouchService.this.chatHead, EasyTouchService.this.params);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));
        this.gvBackground = (GridView) this.popupTouch.findViewById(R.id.main_popup_gv_background);
        this.gvMain = (GridView) this.popupTouch.findViewById(R.id.main_popup_gv_main);
        this.gvSetting = (GridView) this.popupTouch.findViewById(R.id.main_popup_gv_setting);
        this.gvFavor = (GridView) this.popupTouch.findViewById(R.id.main_popup_gv_favor);
        this.adapterMain = new ActionAdapter(this, 0, this.actionListMain);
        this.gvMain.setAdapter(this.adapterMain);
        this.adapterSetting = new ActionAdapter(this, 0, this.actionListSetting);
        this.gvSetting.setAdapter(this.adapterSetting);
        this.adapterFavor = new ActionAdapter(this, 0, this.actionListFavor);
        this.gvFavor.setAdapter(this.adapterFavor);
        ((GradientDrawable) this.gvBackground.getBackground()).setColor((this.displayColor - 3158064) - 268435456);
        this.gvMain.setOnItemClickListener(this.onItemClickListener);
        this.gvSetting.setOnItemClickListener(this.onItemClickListener);
        this.gvFavor.setOnItemClickListener(this.onItemClickListener);
        this.gvMain.setOnItemLongClickListener(this.onItemLongClickListener);
        this.gvSetting.setOnItemLongClickListener(this.onItemLongClickListener);
        this.gvFavor.setOnItemLongClickListener(this.onItemLongClickListener);

    }

    public IBinder onBind(final Intent intent) {
        return null;
    }

    public void onConfigurationChanged(final Configuration configuration) {
        final int screenHeight = this.screenHeight;
        final int screenWidth = this.screenWidth;
        this.screenHeight = this.windowManager.getDefaultDisplay().getHeight();
        this.screenWidth = this.windowManager.getDefaultDisplay().getWidth();
        Log.d("TEST", "OLD: " + screenHeight + " " + screenWidth + " " + this.params.x + " " + this.params.y);
        this.params.x = this.params.x * this.screenWidth / screenWidth;
        this.params.y = this.params.y * this.screenHeight / screenHeight;
        Log.d("TEST", "NEW: " + this.screenHeight + " " + this.screenWidth + " " + this.params.x + " " + this.params.y);
        this.updatePositionAfterMove();
        super.onConfigurationChanged(configuration);
    }

    public void onCreate() {
        super.onCreate();
        Log.d("on Create service", "onCreate Service");
        for (final ActionItem actionItem : this.actionListMain) {
            if (actionItem == null) {
                Log.d("TEST", "Get action NULL");
            } else {
                Log.d("TEST", "Get action " + actionItem.getName());
            }
        }
        this.soundModeController = new SoundModeController(this);
        this.wifiController = new WifiController(this);
        this.blueToothController = new BlueToothController(this);
        this.locationController = new LocationController(this);
        this.airPlaneModeController = new AirPlaneModeController(this);
        this.rotateController = new RotateController(this);
        this.clearRamController = new ClearRamController(this);
        this.flashlightController = new FlashlightController();
        this.lauchAppController = new LaunchAppController(this);
        this.homePressController = new HomePressController(this);
        this.action_animation = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.scale_action);
        this.windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        (this.chatHead = new ImageView(this)).setImageResource(R.drawable.theme_blue);
        this.chatHead.setClickable(true);
        this.screenHeight = this.windowManager.getDefaultDisplay().getHeight();
        this.screenWidth = this.windowManager.getDefaultDisplay().getWidth();
        this.params = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
        this.params.height = (int) this.getResources().getDimension(R.dimen.chathead_size);
        this.params.width = (int) this.getResources().getDimension(R.dimen.chathead_size);
        this.iconWidth = this.params.width;
        this.iconHeight = this.params.height;
        WindowManager.LayoutParams paramsPopup = new WindowManager.LayoutParams(-2, -2, 2002, 8, -3);
        ViewGroup mPopupLayout = (ViewGroup) ((LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.main_popup, (ViewGroup) null);
        paramsPopup.windowAnimations = 16973826;
        this.mParentView = new FrameLayout(this);
        this.windowManager.addView(this.mParentView, paramsPopup);
        this.mParentView.addView(mPopupLayout);
        mPopupLayout.setVisibility(View.GONE);
        this.params.gravity = 51;
        this.params.x = this.screenWidth - this.iconWidth;
        this.params.y = -50 + (this.screenHeight / 2 - this.iconHeight / 2);
        this.params.alpha = 1.0f;
        this.windowManager.addView(this.chatHead, this.params);
        this.chatHead.setOnTouchListener(new View.OnTouchListener() {
            private float initialTouchX;
            private float initialTouchY;
            private int initialX;
            private int initialY;

            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case 0: {
                        EasyTouchService.this.params.alpha = 1.0f;
                        EasyTouchService.this.hideHandler.removeCallbacks(EasyTouchService.this.hideRunnable);
                        EasyTouchService.this.handler.postDelayed(EasyTouchService.this.mLongPressed, 800L);
                        this.initialX = EasyTouchService.this.params.x;
                        this.initialY = EasyTouchService.this.params.y;
                        this.initialTouchX = motionEvent.getRawX();
                        this.initialTouchY = motionEvent.getRawY();
                        return true;
                    }
                    case 1: {
                        EasyTouchService.this.hide();
                        EasyTouchService.this.handler.removeCallbacks(EasyTouchService.this.mLongPressed);
                        EasyTouchService.this.updatePositionAfterMove();
                        if (Math.abs(this.initialTouchX - motionEvent.getRawX()) >= 25.0f) {
                            return true;
                        }
                        if (Math.abs(this.initialTouchY - motionEvent.getRawY()) >= 25.0f) {
                            return true;
                        }
                        EasyTouchService.this.showPopup();
                        return true;
                    }
                    case 2: {
                        this.initialX = EasyTouchService.this.params.x;
                        this.initialY = EasyTouchService.this.params.y;
                        if (motionEvent.getRawX() < this.initialX - EasyTouchService.this.iconWidth / 2 || motionEvent.getRawY() < this.initialY - EasyTouchService.this.iconHeight / 2 || motionEvent.getRawX() > this.initialX + 1.2 * EasyTouchService.this.iconWidth) {
                            break;
                        }
                        EasyTouchService.this.handler.removeCallbacks(EasyTouchService.this.mLongPressed);
                        EasyTouchService.this.params.x = (int) (motionEvent.getRawX() - EasyTouchService.this.iconWidth / 2);
                        EasyTouchService.this.params.y = (int) (motionEvent.getRawY() - EasyTouchService.this.iconHeight);
                        try {
                            EasyTouchService.this.windowManager.updateViewLayout(EasyTouchService.this.chatHead, EasyTouchService.this.params);
                            return true;
                        } catch (Exception ex) {
                            return true;
                        }
                    }
                    default:
                        break;
                }
                return false;
            }
        });
        this.hideHandler = new Handler();
        this.hideRunnable = new Runnable() {
            @Override
            public void run() {
                try {
                    EasyTouchService.this.params.alpha = EasyTouchService.this.displayAlpha;
                    EasyTouchService.this.windowManager.updateViewLayout(EasyTouchService.this.chatHead, EasyTouchService.this.params);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };
        this.hide();

        initInterstitialAd();
    }

    public void onDestroy() {
        if (this.mApp != null) {
            this.mApp.saveList("list_favor", this.actionListFavor);
        }

        if (this.chatHead != null) {

            try {
                this.windowManager.removeView(this.chatHead);
                if (this.mParentView != null) {
                    this.windowManager.removeView(this.mParentView);
                }
                this.flashlightController.release();
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }

        }
        super.onDestroy();
    }

    public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2) {
        if ((paramIntent == null) || (!paramIntent.getAction().equals("com.touchpro.foregroundservice.action.startforeground"))) {
            if ((paramIntent != null) && (paramIntent.getAction().equals("com.touchpro.foregroundservice.action.stopforeground"))) {
                stopForeground(true);
                stopSelf();
            }
            return Service.START_STICKY;
        }
        this.mApp = ((EasyTouchApplication) getApplicationContext());
        this.isTipMain = this.mApp.isTipMain();
        this.isTipSetting = this.mApp.isTipSetting();
        this.isTipFavor = this.mApp.isTipFavor();
        this.mApp.loadDataList();
        this.actionListMain = this.mApp.getActionListMain();
        this.actionListFavor = this.mApp.getActionListFavor();
        this.actionListSetting = this.mApp.getActionListSetting();
        this.chatHead.setImageResource(((Integer) this.mApp.getThemeList().get(this.mApp.getDisplayTheme())).intValue());
        this.displayAnim = this.mApp.getDisplayAnim();
        this.displaySize = (50 + this.mApp.getDisplaySize());
        this.displayAlpha = ((10 + this.mApp.getDisplayAlpha()) / 100.0F);
        this.displayColor = this.mApp.getDisplayBackground();
        this.params.alpha = this.displayAlpha;
        this.params.width = ((int) (getResources().getDimension(R.dimen.chathead_size) * (this.displaySize / 100.0F)));
        this.params.height = this.params.width;
        this.iconHeight = this.params.height;
        this.iconWidth = this.params.width;
        initPopup();
        try {
            this.windowManager.updateViewLayout(this.chatHead, this.params);
            AppInfo localAppInfo = paramIntent.getParcelableExtra("add_app_key");
            int i = paramIntent.getIntExtra("pos", 0);
            if (localAppInfo != null) {
                ActionItem localActionItem = new ActionItem(2000, localAppInfo.getName(), 0);
                localActionItem.setPackageName(localAppInfo.getPackageName());
                showPopup();
                this.actionListFavor.remove(i);
                this.actionListFavor.add(i, localActionItem);
                this.adapterMain.notifyDataSetChanged();
                setListViewDisplay(3, true);
                this.mApp.saveList("list_favor", this.actionListFavor);
            }
            Intent localIntent = new Intent(this, MainActivity.class);
            localIntent.setFlags(268468224);
            PendingIntent localPendingIntent = PendingIntent.getActivity(this, 0, localIntent, 0);
            this.iconNotification = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
            this.notification = new NotificationCompat.Builder(this).setContentTitle(getResources().getString(R.string.app_name) + " is running").setTicker(getResources().getString(R.string.app_name) + " is running").setContentText("Touch to open").setSmallIcon(R.drawable.ic_small_noti_circle).setPriority(-2).setLargeIcon(Bitmap.createScaledBitmap(this.iconNotification, 128, 128, false)).setContentIntent(localPendingIntent).setOngoing(true).build();
            startForeground(101, this.notification);
        } catch (Exception localException1) {
            try {
                this.windowManager.addView(this.chatHead, this.params);
                return Service.START_STICKY;
            } catch (Exception localException2) {
                return Service.START_STICKY_COMPATIBILITY;
            }
        }
        return Service.START_STICKY;
    }

    public void refreashAll(final ActionItem actionItem) {
        for (final ActionItem actionItem2 : this.actionListSetting) {
            if (actionItem2 != null && actionItem2.equals(actionItem)) {
                actionItem2.setValue(actionItem.getValue());
            }
        }
        for (final ActionItem actionItem3 : this.actionListMain) {
            if (actionItem3 != null && actionItem3.equals(actionItem)) {
                actionItem3.setValue(actionItem.getValue());
            }
        }
    }

    public void refreshAction() {
        this.refreshSoundMode(this.actionListMain.get(8));
        this.refreshWifi(this.actionListSetting.get(0), true);
        this.refreshBluetooth(this.actionListSetting.get(1), true);
        this.refreshRotate(this.actionListSetting.get(2), true);
        this.refreshLocation(this.actionListSetting.get(3), true);
        this.refreshFlashlight(this.actionListSetting.get(5), true);
        this.refreshAirplane(this.actionListSetting.get(6), true);
    }

    protected void refreshAirplane(final ActionItem actionItem, final boolean b) {
        if (actionItem == null) {
            return;
        }
        if (b) {
            int value;
            if (this.airPlaneModeController.isAirplane()) {
                value = 1;
            } else {
                value = 0;
            }
            actionItem.setValue(value);
        } else {
            actionItem.setValue((1 + actionItem.getValue()) % 2);
        }
        this.refreashAll(actionItem);
        this.adapterDataSetChanged();
    }

    protected void refreshBluetooth(final ActionItem actionItem, final boolean b) {
        if (actionItem == null) {
            return;
        }
        if (b) {
            actionItem.setValue(this.blueToothController.isBluetoothEnable());
        } else {
            actionItem.setValue((1 + actionItem.getValue()) % 2);
        }
        this.refreashAll(actionItem);
        this.adapterDataSetChanged();
    }

    protected void refreshFlashlight(final ActionItem actionItem, final boolean b) {
        if (actionItem == null) {
            return;
        }
        if (b) {
            int value;
            if (this.flashlightController.isFlashOn()) {
                value = 1;
            } else {
                value = 0;
            }
            actionItem.setValue(value);
        } else {
            actionItem.setValue((1 + actionItem.getValue()) % 2);
        }
        this.refreashAll(actionItem);
        this.adapterDataSetChanged();
    }

    protected void refreshLocation(final ActionItem actionItem, final boolean b) {
        if (actionItem == null) {
            return;
        }
        if (b) {
            actionItem.setValue(this.locationController.isLocation());
        } else {
            actionItem.setValue((1 + actionItem.getValue()) % 2);
        }
        this.refreashAll(actionItem);
        this.adapterDataSetChanged();
    }

    protected void refreshRotate(final ActionItem actionItem, final boolean b) {
        if (actionItem == null) {
            return;
        }
        if (b) {
            actionItem.setValue(this.rotateController.isRotate());
        } else {
            actionItem.setValue((1 + actionItem.getValue()) % 2);
        }
        this.refreashAll(actionItem);
        this.adapterDataSetChanged();
    }

    public void refreshSoundMode(final ActionItem actionItem) {
        if (actionItem == null) {
            return;
        }
        actionItem.setValue(this.soundModeController.getRingerMode());
        this.adapterDataSetChanged();
    }

    protected void refreshWifi(final ActionItem actionItem, final boolean b) {
        if (actionItem == null) {
            return;
        }
        if (b) {
            actionItem.setValue(this.wifiController.isWifiEnable());
        } else {
            actionItem.setValue((1 + actionItem.getValue()) % 2);
        }
        this.refreashAll(actionItem);
        this.adapterDataSetChanged();
    }

    public void setAninationGridViewLeft(final ViewGroup viewGroup) {
        viewGroup.setLayoutAnimation(this.animControllerLeft);
    }

    public void setAninationGridViewRight(final ViewGroup viewGroup) {
        viewGroup.setLayoutAnimation(this.animControllerRight);
    }

    public void setListViewDisplay(final int n, final boolean b) {
        switch (n) {
            default: {
            }
            case 1: {
                this.actionListCurrent.clear();
                this.actionListCurrent.addAll(this.actionListMain);
                if (b) {
                    this.gvMain.setVisibility(View.VISIBLE);
                    this.gvFavor.setVisibility(View.GONE);
                    this.gvSetting.setVisibility(View.GONE);
                } else {
                    ViewAnimationUtil.setViewScaleIn(this.gvMain, this.displayAnim);
                    if (this.mCurrentType == 3) {
                        ViewAnimationUtil.setViewScaleOut(this.gvFavor, this.displayAnim);
                    } else if (this.mCurrentType == 2) {
                        ViewAnimationUtil.setViewScaleOut(this.gvSetting, this.displayAnim);
                    }
                }
                this.mCurrentType = 1;

                return;
            }
            case 2: {
                this.actionListCurrent.clear();
                this.actionListCurrent.addAll(this.actionListSetting);
                if (b) {
                    this.gvMain.setVisibility(View.GONE);
                    this.gvFavor.setVisibility(View.GONE);
                    this.gvSetting.setVisibility(View.VISIBLE);
                } else {
                    ViewAnimationUtil.setViewScaleIn(this.gvSetting, this.displayAnim);
                    ViewAnimationUtil.setViewScaleOut(this.gvMain, this.displayAnim);
                }
                this.mCurrentType = 2;

                return;
            }
            case 3: {
                this.actionListCurrent.clear();
                this.actionListCurrent.addAll(this.actionListFavor);
                if (b) {
                    this.gvMain.setVisibility(View.GONE);
                    this.gvFavor.setVisibility(View.VISIBLE);
                    this.gvSetting.setVisibility(View.GONE);
                } else {
                    ViewAnimationUtil.setViewScaleIn(this.gvFavor, this.displayAnim);
                    ViewAnimationUtil.setViewScaleOut(this.gvMain, this.displayAnim);
                }
                this.mCurrentType = 3;
            }
        }
    }

    public void showPopup() {
        if (this.popupTouch != null && !this.popupTouch.isShowing()) {
            this.refreshAction();
            this.setListViewDisplay(1, true);
            this.popupTouch.show();
            if (this.chatHead != null) {
                this.windowManager.removeView(this.chatHead);
            }
        }
    }

    public void updatePositionAfterMove() {
        int i = this.params.x + (this.iconWidth / TYPE_SETTING);
        int j = this.params.y + (this.iconHeight / TYPE_SETTING);
        if (i > this.screenWidth / TYPE_SETTING || j > this.screenHeight / TYPE_SETTING) {
            if (i <= this.screenWidth / TYPE_SETTING || j > this.screenHeight / TYPE_SETTING) {
                if (i <= this.screenWidth / TYPE_SETTING || j <= this.screenHeight / TYPE_SETTING) {
                    if (i <= this.screenWidth / TYPE_SETTING && j > this.screenHeight / TYPE_SETTING) {
                        if (this.screenHeight - j <= i) {
                            this.params.y = this.screenHeight;
                        } else {
                            this.params.x = 0;
                        }
                    }
                } else if (this.screenHeight - j <= this.screenWidth - i) {
                    this.params.y = this.screenHeight;
                } else {
                    this.params.x = this.screenWidth;
                }
            } else if (this.screenWidth - i <= j) {
                this.params.x = this.screenWidth;
            } else {
                this.params.y = 0;
            }
        } else if (i <= j) {
            this.params.x = 0;
        } else {
            this.params.y = 0;
        }
        try {
            this.windowManager.updateViewLayout(this.chatHead, this.params);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
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
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    // Check the LogCat to get your test device ID
                    .addTestDevice("9E847D99F08C0028B6613E597754B38A")
                    .build();
            mInterstitialAd.loadAd(adRequest);
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
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
}