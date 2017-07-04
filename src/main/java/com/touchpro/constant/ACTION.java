package com.touchpro.constant;

import com.touchpro.datamodel.ActionItem;
import com.develop.touchpro.R;

import java.util.ArrayList;
import java.util.HashMap;
@SuppressWarnings("all")
public class ACTION {
    public static ActionItem ADD_APP;
    public static final int ADD_APP_KEY = 1013;
    public static ActionItem AIRPLANE;
    public static final int AIRPLANE_KEY = 1006;
    public static ActionItem BACK_FAVOR;
    public static final int BACK_FAVOR_KEY = 1014;
    public static ActionItem BACK_SETTING;
    public static final int BACK_SETTING_KEY = 1011;
    public static ActionItem BLUETOOTH;
    public static final int BLUETOOTH_KEY = 1007;
    public static ActionItem CLEAN_RAM;
    public static final int CLEAN_RAM_KEY = 1009;
    public static ActionItem FAVOR;
    public static final int FAVOR_KEY = 1003;
    public static ActionItem FLASHLIGHT;
    public static final int FLASHLIGHT_KEY = 1010;
    public static ActionItem HOME;
    public static final int HOME_KEY = 1000;
    public static final int LAUNCH_APP = 2000;
    public static ActionItem LOCATION;
    public static final int LOCATION_KEY = 1004;
    public static ActionItem LOCK;
    public static final int LOCK_KEY = 1002;
    public static ActionItem ROTE;
    public static final int ROTE_KEY = 1008;
    public static ActionItem SETTING;
    public static final int SETTING_KEY = 1001;
    public static ActionItem SOUND_MODE;
    public static final int SOUND_MODE_KEY = 1012;
    public static ActionItem VOLUME_DOWN;
    public static final int VOLUME_DOWN_KEY = 1016;
    public static ActionItem VOLUME_UP;
    public static final int VOLUME_UP_KEY = 1015;
    public static ActionItem WIFI;
    public static final int WIFI_KEY = 1005;

    static {
        ADD_APP = new ActionItem(ADD_APP_KEY, "", R.drawable.action_add);
        AIRPLANE = new ActionItem(AIRPLANE_KEY, "Airplane", R.drawable.airplane_list, 1);
        BACK_FAVOR = new ActionItem(BACK_FAVOR_KEY, "", R.drawable.ic_back_new);
        BACK_SETTING = new ActionItem(BACK_SETTING_KEY, "", R.drawable.ic_back_new);
        BLUETOOTH = new ActionItem(BLUETOOTH_KEY, "Exit", R.drawable.bluetooth_list, 1);
        CLEAN_RAM = new ActionItem(CLEAN_RAM_KEY, "Clean Now !", R.drawable.ic_clear_ram);
        FAVOR = new ActionItem(FAVOR_KEY, "Shortcut", R.drawable.ic_star_white);
        FLASHLIGHT = new ActionItem(FLASHLIGHT_KEY, "Flashlight", R.drawable.flashlight_list, 0);
        HOME = new ActionItem(HOME_KEY, "Home", R.drawable.ic_home_white);
        LOCATION = new ActionItem(LOCATION_KEY, "Location", R.drawable.location_list, 1);
        LOCK = new ActionItem(LOCK_KEY, "Lock", R.drawable.ic_screen_lock_portrait_white);
        ROTE = new ActionItem(ROTE_KEY, "Auto Rotate", R.drawable.rote_list, 1);
        SETTING = new ActionItem(SETTING_KEY, "Setting", R.drawable.ic_settings_applications_white);
        SOUND_MODE = new ActionItem(SOUND_MODE_KEY, "Sound Mode", R.drawable.sound_mode_list, 2);
        VOLUME_DOWN = new ActionItem(VOLUME_DOWN_KEY, "Volume Down", R.drawable.ic_volume_down_white);
        VOLUME_UP = new ActionItem(VOLUME_UP_KEY, "Volume Up", R.drawable.ic_volume_up_white);
        WIFI = new ActionItem(WIFI_KEY, "Wi-Fi", R.drawable.wifi_list, 1);
    }

    public static ArrayList getActionList() {
        final ArrayList<ActionItem> list = new ArrayList<ActionItem>();
        list.add(null);
        list.add(ACTION.HOME);
        list.add(ACTION.SETTING);
        list.add(ACTION.LOCK);
        list.add(ACTION.FAVOR);
        list.add(ACTION.LOCATION);
        list.add(ACTION.WIFI);
        list.add(ACTION.AIRPLANE);
        list.add(ACTION.BLUETOOTH);
        list.add(ACTION.ROTE);
        list.add(ACTION.CLEAN_RAM);
        list.add(ACTION.FLASHLIGHT);
        list.add(ACTION.SOUND_MODE);
        list.add(ACTION.VOLUME_UP);
        list.add(ACTION.VOLUME_DOWN);
        return list;
    }

    public static HashMap getActionMap() {
        final HashMap<Integer, ActionItem> hashMap = new HashMap<Integer, ActionItem>();
        hashMap.put(1013, ACTION.ADD_APP);
        hashMap.put(1000, ACTION.HOME);
        hashMap.put(1001, ACTION.SETTING);
        hashMap.put(1002, ACTION.LOCK);
        hashMap.put(1003, ACTION.FAVOR);
        hashMap.put(1004, ACTION.LOCATION);
        hashMap.put(1005, ACTION.WIFI);
        hashMap.put(1006, ACTION.AIRPLANE);
        hashMap.put(1007, ACTION.BLUETOOTH);
        hashMap.put(1008, ACTION.ROTE);
        hashMap.put(1009, ACTION.CLEAN_RAM);
        hashMap.put(1010, ACTION.FLASHLIGHT);
        hashMap.put(1012, ACTION.SOUND_MODE);
        hashMap.put(1015, ACTION.VOLUME_UP);
        hashMap.put(1016, ACTION.VOLUME_DOWN);
        return hashMap;
    }
}