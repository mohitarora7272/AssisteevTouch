package com.touchpro.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.touchpro.easytouch.EasyTouchApplication;
@SuppressWarnings("all")
public class DatabaseManager {
    private SQLiteDatabase database;
    private DatabaseOpenHelper databaseHelper;
    private EasyTouchApplication mApp;

    public DatabaseManager(final Context context) {
        super();
        this.databaseHelper = new DatabaseOpenHelper(context);
        this.mApp = (EasyTouchApplication) context.getApplicationContext();
    }

    public void CloseDB() {
        if (this.database != null && this.database.isOpen()) {
            this.database.close();
        }
    }

    public Cursor SELECTSQL(final String s) {
        return this.database.rawQuery(s, (String[]) null);
    }

    public Cursor SelectAllAction() {
        return this.database.query("tbl_ActionList", (String[]) null, (String) null, (String[]) null, (String) null, (String) null, (String) null);
    }

    public int getDisplayAlpha() {
        final Cursor rawQuery = this.databaseHelper.getReadableDatabase().rawQuery("SELECT  * FROM tbl_display", (String[]) null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("alpha"));
        }
        return -1;
    }

    public int getDisplayAnim() {
        final Cursor rawQuery = this.databaseHelper.getReadableDatabase().rawQuery("SELECT  * FROM tbl_display", (String[]) null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("anim"));
        }
        return -1;
    }

    public int getDisplayBackground() {
        final Cursor rawQuery = this.databaseHelper.getReadableDatabase().rawQuery("SELECT  * FROM tbl_display", (String[]) null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("background"));
        }
        return -1;
    }

    public int getDisplaySize() {
        final Cursor rawQuery = this.databaseHelper.getReadableDatabase().rawQuery("SELECT  * FROM tbl_display", (String[]) null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("size"));
        }
        return -1;
    }

    public int getDisplayTheme() {
        final Cursor rawQuery = this.databaseHelper.getReadableDatabase().rawQuery("SELECT  * FROM tbl_display", (String[]) null);
        if (rawQuery.moveToFirst()) {
            return rawQuery.getInt(rawQuery.getColumnIndex("theme"));
        }
        return -1;
    }

    public String getListJson(final String s) {
        final Cursor rawQuery = this.databaseHelper.getReadableDatabase().rawQuery("SELECT  * FROM tbl_ActionList WHERE Name =? ", new String[]{s});
        if (rawQuery.moveToFirst()) {
            return rawQuery.getString(rawQuery.getColumnIndex("JSONData"));
        }
        return null;
    }

    public void initDisplay() {
        Log.d("TEST", "initDisplayTheme");
        this.database = this.databaseHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("theme", 0);
        contentValues.put("anim", 2);
        contentValues.put("size", 50);
        contentValues.put("alpha", 50);
        contentValues.put("background", this.mApp.getColorList()[12]);
        this.database.insertOrThrow("tbl_display", (String) null, contentValues);
    }

    public void insertList(final String s, final String s2) {
        this.database = this.databaseHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("Name", s);
        contentValues.put("JSONData", s2);
        this.database.insertOrThrow("tbl_ActionList", (String) null, contentValues);
    }

    public void updateDisplayAlpha(final int n) {
        this.database = this.databaseHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("alpha", n);
        this.database.update("tbl_display", contentValues, (String) null, (String[]) null);
    }

    public void updateDisplayAnim(final int n) {
        this.database = this.databaseHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("anim", n);
        this.database.update("tbl_display", contentValues, (String) null, (String[]) null);
    }

    public void updateDisplayBackground(final int n) {
        this.database = this.databaseHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("background", n);
        this.database.update("tbl_display", contentValues, (String) null, (String[]) null);
    }

    public void updateDisplaySize(final int n) {
        this.database = this.databaseHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("size", n);
        this.database.update("tbl_display", contentValues, (String) null, (String[]) null);
    }

    public void updateDisplayTheme(final int n) {
        this.database = this.databaseHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("theme", n);
        this.database.update("tbl_display", contentValues, (String) null, (String[]) null);
    }

    public void updateList(final String s, final String s2) {
        this.database = this.databaseHelper.getWritableDatabase();
        final ContentValues contentValues = new ContentValues();
        contentValues.put("JSONData", s2);
        this.database.update("tbl_ActionList", contentValues, "Name =?", new String[]{s});
    }
}