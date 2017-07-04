package com.touchpro.controller;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.widget.Toast;

import com.touchpro.datamodel.AppInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
@SuppressWarnings("all")
public class LaunchAppController {
    Context mContext;
    PackageManager pm;

    public LaunchAppController(final Context mContext) {
        super();
        this.mContext = mContext;
        this.pm = this.mContext.getPackageManager();
    }

    public ArrayList getListApp() {
        final ArrayList<Object> list = new ArrayList<Object>();
        new ArrayList();
        final Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
        intent.addCategory("android.intent.category.LAUNCHER");
        final Iterator iterator = ((ArrayList) this.mContext.getPackageManager().queryIntentActivities((Intent) intent, 0)).iterator();
        while (((Iterator<ResolveInfo>) iterator).hasNext()) {
            final ResolveInfo resolveInfo = ((Iterator<ResolveInfo>) iterator).next();
            final AppInfo appInfo = new AppInfo();
            appInfo.setName(resolveInfo.loadLabel(this.pm).toString());
            appInfo.setPackageName(resolveInfo.resolvePackageName);
            list.add(appInfo);
        }
        Collections.sort(list, new SortAppName());
        return list;
    }

    public boolean launchApp(final String s) {
        final Intent launchIntentForPackage = this.pm.getLaunchIntentForPackage(s);
        boolean b = false;
        if (launchIntentForPackage == null) {
            return b;
        }
        try {
            this.mContext.startActivity(launchIntentForPackage);
            b = true;
            return b;
        } catch (Exception ex) {
            Toast.makeText(this.mContext, (CharSequence) "Not found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    class SortAppName implements Comparator {
        final LaunchAppController launchApp;

        SortAppName() {
            super();
            this.launchApp = LaunchAppController.this;
        }

        public int compare(final AppInfo appInfo, final AppInfo appInfo2) {
            return appInfo.getName().compareTo(appInfo2.getName());
        }

        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((AppInfo) o, (AppInfo) o2);
        }
    }
}