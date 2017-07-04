package com.touchpro.view;

import com.develop.touchpro.R;
@SuppressWarnings("all")
public final class styleable {
    public static final int[] AdsAttrs;
    public static final int AdsAttrs_adSize = 0;
    public static final int AdsAttrs_adSizes = 1;
    public static final int AdsAttrs_adUnitId = 2;
    public static final int[] CircularImageView;
    public static final int[] CircularImageViewStyle;
    public static final int CircularImageViewStyle_circularImageViewDefault = 0;
    public static final int CircularImageView_civ_border = 0;
    public static final int CircularImageView_civ_borderColor = 1;
    public static final int CircularImageView_civ_borderWidth = 2;
    public static final int CircularImageView_civ_selector = 3;
    public static final int CircularImageView_civ_selectorColor = 4;
    public static final int CircularImageView_civ_selectorStrokeColor = 5;
    public static final int CircularImageView_civ_selectorStrokeWidth = 6;
    public static final int CircularImageView_civ_shadow = 7;
    public static final int CircularImageView_civ_shadowColor = 11;
    public static final int CircularImageView_civ_shadowDx = 9;
    public static final int CircularImageView_civ_shadowDy = 10;
    public static final int CircularImageView_civ_shadowRadius = 8;
    public static final int[] MapAttrs;
    public static final int MapAttrs_cameraBearing = 1;
    public static final int MapAttrs_cameraTargetLat = 2;
    public static final int MapAttrs_cameraTargetLng = 3;
    public static final int MapAttrs_cameraTilt = 4;
    public static final int MapAttrs_cameraZoom = 5;
    public static final int MapAttrs_mapType = 0;
    public static final int MapAttrs_uiCompass = 6;
    public static final int MapAttrs_uiRotateGestures = 7;
    public static final int MapAttrs_uiScrollGestures = 8;
    public static final int MapAttrs_uiTiltGestures = 9;
    public static final int MapAttrs_uiZoomControls = 10;
    public static final int MapAttrs_uiZoomGestures = 11;
    public static final int MapAttrs_useViewLifecycle = 12;
    public static final int MapAttrs_zOrderOnTop = 13;
    public static final int[] WalletFragmentOptions;
    public static final int WalletFragmentOptions_appTheme = 0;
    public static final int WalletFragmentOptions_environment = 1;
    public static final int WalletFragmentOptions_fragmentMode = 3;
    public static final int WalletFragmentOptions_fragmentStyle = 2;
    public static final int[] WalletFragmentStyle;
    public static final int WalletFragmentStyle_buyButtonAppearance = 3;
    public static final int WalletFragmentStyle_buyButtonHeight = 0;
    public static final int WalletFragmentStyle_buyButtonText = 2;
    public static final int WalletFragmentStyle_buyButtonWidth = 1;
    public static final int WalletFragmentStyle_maskedWalletDetailsBackground = 6;
    public static final int WalletFragmentStyle_maskedWalletDetailsButtonBackground = 8;
    public static final int WalletFragmentStyle_maskedWalletDetailsButtonTextAppearance = 7;
    public static final int WalletFragmentStyle_maskedWalletDetailsHeaderTextAppearance = 5;
    public static final int WalletFragmentStyle_maskedWalletDetailsLogoImageType = 10;
    public static final int WalletFragmentStyle_maskedWalletDetailsLogoTextColor = 9;
    public static final int WalletFragmentStyle_maskedWalletDetailsTextAppearance = 4;

    static {
        AdsAttrs = new int[]{R.attr.adSize, R.attr.adSizes, R.attr.adUnitId};
        CircularImageView = new int[]{R.attr.civ_border, R.attr.civ_borderColor, R.attr.civ_borderWidth, R.attr.civ_selector, R.attr.civ_selectorColor, R.attr.civ_selectorStrokeColor, R.attr.civ_selectorStrokeWidth, R.attr.civ_shadow, R.attr.civ_shadowRadius, R.attr.civ_shadowDx, R.attr.civ_shadowDy, R.attr.civ_shadowColor};
        int[] iArr = new int[WalletFragmentStyle_buyButtonWidth];
        iArr[WalletFragmentStyle_buyButtonHeight] = R.attr.circularImageViewDefault;
        CircularImageViewStyle = iArr;
        MapAttrs = new int[]{R.attr.mapType, R.attr.cameraBearing, R.attr.cameraTargetLat, R.attr.cameraTargetLng, R.attr.cameraTilt, R.attr.cameraZoom, R.attr.uiCompass, R.attr.uiRotateGestures, R.attr.uiScrollGestures, R.attr.uiTiltGestures, R.attr.uiZoomControls, R.attr.uiZoomGestures, R.attr.useViewLifecycle, R.attr.zOrderOnTop};
        WalletFragmentOptions = new int[]{R.attr.appTheme, R.attr.environment, R.attr.fragmentStyle, R.attr.fragmentMode};
        WalletFragmentStyle = new int[]{R.attr.buyButtonHeight, R.attr.buyButtonWidth, R.attr.buyButtonText, R.attr.buyButtonAppearance, R.attr.maskedWalletDetailsTextAppearance, R.attr.maskedWalletDetailsHeaderTextAppearance, R.attr.maskedWalletDetailsBackground, R.attr.maskedWalletDetailsButtonTextAppearance, R.attr.maskedWalletDetailsButtonBackground, R.attr.maskedWalletDetailsLogoTextColor, R.attr.maskedWalletDetailsLogoImageType};
    }
}