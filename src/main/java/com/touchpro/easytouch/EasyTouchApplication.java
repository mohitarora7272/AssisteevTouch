package com.touchpro.easytouch;

import android.app.Application;
import android.content.SharedPreferences;

import com.develop.touchpro.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.touchpro.constant.ACTION;
import com.touchpro.database.DatabaseManager;
import com.touchpro.datamodel.ActionItem;

import java.lang.reflect.Type;
import java.util.ArrayList;
@SuppressWarnings("all")
public class EasyTouchApplication extends Application {
    private static final String KEY_PRE = "share_pre";
    private static final String KEY_PRE_LAUNCH = "share_pre_launch";
    private static final String KEY_PRE_TIP_FAVOR = "share_pre_favor";
    private static final String KEY_PRE_TIP_MAIN = "share_pre_main";
    private static final String KEY_PRE_TIP_SETTING = "share_pre_setting";
    private ArrayList actionListFavor;
    private ArrayList actionListMain;
    private ArrayList actionListSetting;
    private DatabaseManager database;
    private int displayAlpha;
    private int displayAnim;
    private int displayBackground;
    private int displaySize;
    private int displayTheme;
    SharedPreferences.Editor editor;
    private boolean isTipFavor;
    private boolean isTipMain;
    private boolean isTipSetting;
    private int launchTime;
    SharedPreferences preferences;

    public EasyTouchApplication() {
        super();
        this.actionListMain = new ArrayList(9);
        this.actionListSetting = new ArrayList(9);
        this.actionListFavor = new ArrayList(9);
    }

    public ArrayList getActionListFavor() {
        return this.actionListFavor;
    }

    public ArrayList getActionListMain() {
        return this.actionListMain;
    }

    public ArrayList getActionListSetting() {
        return this.actionListSetting;
    }

    public Integer[] getColorList() {
        final int[] intArray = this.getResources().getIntArray(R.array.backgoundlist);
        final Integer[] array = new Integer[intArray.length];
        for (int length = intArray.length, i = 0, n = 0; i < length; ++i, ++n) {
            array[n] = intArray[i];
        }
        return array;
    }

    public int getDisplayAlpha() {
        this.displayAlpha = this.database.getDisplayAlpha();
        return this.displayAlpha;
    }

    public int getDisplayAnim() {
        this.displayAnim = this.database.getDisplayAnim();
        return this.displayAnim;
    }

    public int getDisplayBackground() {
        return this.displayBackground = this.database.getDisplayBackground();
    }

    public int getDisplaySize() {
        this.displaySize = this.database.getDisplaySize();
        return this.displaySize;
    }

    public int getDisplayTheme() {
        this.displayTheme = this.database.getDisplayTheme();
        return this.displayTheme;
    }

    public int getLaunchTime() {
        return this.launchTime = this.preferences.getInt("share_pre_launch", 0);
    }

    public int getResID(final String s) {
        return this.getResources().getIdentifier(s, "drawable", this.getPackageName());
    }

    public ArrayList getThemeList() {
        final ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(R.drawable.theme_white);
        list.add(R.drawable.theme_blue);
        list.add(R.drawable.theme_classic);
        list.add(R.drawable.theme_blue_ocean);
        for (int i = 1; i <= 46; ++i) {
            list.add(this.getResID("ic_touch_" + String.format("%02d", i)));
        }
        return list;
    }

    public void initDataList() {
        this.actionListMain.add(0, null);
        this.actionListMain.add(1, ACTION.LOCK);
        this.actionListMain.add(2, null);
        this.actionListMain.add(3, ACTION.FAVOR);
        this.actionListMain.add(4, ACTION.CLEAN_RAM);
        this.actionListMain.add(5, ACTION.SETTING);
        this.actionListMain.add(6, null);
        this.actionListMain.add(7, ACTION.HOME);
        this.actionListMain.add(8, null);
        this.actionListSetting.add(0, ACTION.WIFI);
        this.actionListSetting.add(1, ACTION.BLUETOOTH);
        this.actionListSetting.add(2, ACTION.ROTE);
        this.actionListSetting.add(3, ACTION.LOCATION);
        this.actionListSetting.add(4, ACTION.BACK_SETTING);
        this.actionListSetting.add(5, ACTION.VOLUME_UP);
        this.actionListSetting.add(6, ACTION.AIRPLANE);
        this.actionListSetting.add(7, ACTION.FLASHLIGHT);
        this.actionListSetting.add(8, ACTION.VOLUME_DOWN);
        this.actionListFavor.add(0, ACTION.ADD_APP);
        this.actionListFavor.add(1, ACTION.ADD_APP);
        this.actionListFavor.add(2, ACTION.ADD_APP);
        this.actionListFavor.add(3, ACTION.ADD_APP);
        this.actionListFavor.add(4, ACTION.BACK_FAVOR);
        this.actionListFavor.add(5, ACTION.ADD_APP);
        this.actionListFavor.add(6, ACTION.ADD_APP);
        this.actionListFavor.add(7, ACTION.ADD_APP);
        this.actionListFavor.add(8, ACTION.ADD_APP);
    }

    public boolean isTipFavor() {
        return this.isTipFavor = this.preferences.getBoolean("share_pre_favor", false);
    }

    public boolean isTipMain() {
        return this.isTipMain = this.preferences.getBoolean("share_pre_main", false);
    }

    public boolean isTipSetting() {
        return this.isTipSetting = this.preferences.getBoolean("share_pre_setting", false);
    }

    public void loadDataList() {
        final Type type = new TypeToken<ArrayList<ActionItem>>() {
        }.getType();
        final String listJson = this.database.getListJson("list_main");
        final String listJson2 = this.database.getListJson("list_favor");
        final String listJson3 = this.database.getListJson("list_setting");
        if (listJson == null) {
            this.initDataList();
            this.database.insertList("list_main", new Gson().toJson(this.actionListMain));
            this.database.insertList("list_setting", new Gson().toJson(this.actionListSetting));
            this.database.insertList("list_favor", new Gson().toJson(this.actionListFavor));
            return;
        }
        this.actionListMain = new Gson().fromJson(listJson, type);
        this.actionListFavor = new Gson().fromJson(listJson2, type);
        this.actionListSetting = new Gson().fromJson(listJson3, type);
    }

    public void onCreate() {
        super.onCreate();
        this.preferences = this.getSharedPreferences("share_pre", 0);
        this.editor = this.preferences.edit();
        this.getLaunchTime();
        this.database = new DatabaseManager(this);
        if (this.getDisplayTheme() == -1) {
            this.database.initDisplay();
        }
    }

    public void saveList(final String s, final ArrayList list) {
        this.database.updateList(s, new Gson().toJson(list));
    }

    public void setDisplayAlpha(final int displayAlpha) {
        this.displayAlpha = displayAlpha;
        this.database.updateDisplayAlpha(displayAlpha);
    }

    public void setDisplayAnim(final int displayAnim) {
        this.displayAnim = displayAnim;
        this.database.updateDisplayAnim(displayAnim);
    }

    public void setDisplayBackground(final int displayBackground) {
        this.displayBackground = displayBackground;
        this.database.updateDisplayBackground(displayBackground);
    }

    public void setDisplaySize(final int displaySize) {
        this.displaySize = displaySize;
        this.database.updateDisplaySize(displaySize);
    }

    public void setDisplayTheme(final int displayTheme) {
        this.displayTheme = displayTheme;
        this.database.updateDisplayTheme(displayTheme);
    }

    public void setLaunchTime() {
        this.launchTime = 1 + this.getLaunchTime();
        this.editor.putInt("share_pre_launch", this.launchTime);
        this.editor.commit();
    }

    public void setTipFavor(final boolean isTipFavor) {
        this.isTipFavor = isTipFavor;
        this.editor.putBoolean("share_pre_favor", isTipFavor);
        this.editor.commit();
    }

    public void setTipMain(final boolean isTipMain) {
        this.isTipMain = isTipMain;
        this.editor.putBoolean("share_pre_main", isTipMain);
        this.editor.commit();
    }

    public void setTipSetting(final boolean isTipSetting) {
        this.isTipSetting = isTipSetting;
        this.editor.putBoolean("share_pre_setting", isTipSetting);
        this.editor.commit();
    }
}