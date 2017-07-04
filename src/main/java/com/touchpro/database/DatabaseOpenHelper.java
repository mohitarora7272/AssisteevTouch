package com.touchpro.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
@SuppressWarnings("all")
public class DatabaseOpenHelper extends SQLiteOpenHelper {
    public DatabaseOpenHelper(final Context context) {
        super(context, "DataBase.db", (SQLiteDatabase.CursorFactory) null, 18);
    }

    public void onCreate(final SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE tbl_ActionList(Name TEXT, JSONData TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE tbl_display(theme INTEGER, anim INTEGER, size INTEGER, background INTEGER, alpha INTEGER)");
    }

    public void onUpgrade(final SQLiteDatabase sqLiteDatabase, final int n, final int n2) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS tbl_ActionList");
        this.onCreate(sqLiteDatabase);
    }
}