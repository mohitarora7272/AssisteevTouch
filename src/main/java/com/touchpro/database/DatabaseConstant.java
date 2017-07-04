package com.touchpro.database;

import com.develop.touchpro.R;
@SuppressWarnings("all")
public class DatabaseConstant {
    public static final int ANIM_NORMAL = 2;
    public static final int ANIM_QUICK = 3;
    public static final int ANIM_SMOOTH = 1;
    public static final String CL_ALPHA = "alpha";
    public static final String CL_ANIM = "anim";
    public static final String CL_BACKGROUND = "background";
    public static final String CL_JSON = "JSONData";
    public static final String CL_NAME = "Name";
    public static final String CL_SIZE = "size";
    public static final String CL_THEME = "theme";
    public static final String DB_NAME = "DataBase.db";
    public static final int DB_VERSION = 18;
    public static final int DEFAULT_ALPHA = 50;
    public static final int DEFAULT_ANIM = 2;
    public static int DEFAULT_BACKGROUND = 0;
    public static final int DEFAULT_SIZE = 50;
    public static final String LIST_FAVOR = "list_favor";
    public static final String LIST_MAIN = "list_main";
    public static final String LIST_SETTING = "list_setting";
    public static final String TAG = "TEST";
    public static final String TB_DISPLAY = "tbl_display";
    public static final String TB_NAME = "tbl_ActionList";

    static {
        DEFAULT_BACKGROUND = Integer.valueOf(R.color.c13);
    }
}