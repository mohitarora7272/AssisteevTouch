package com.touchpro.easytouch;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.develop.touchpro.R;
import com.touchpro.adapter.AppAdapter;
import com.touchpro.datamodel.AppInfo;
import com.touchpro.service.EasyTouchService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

@SuppressWarnings("all")
public class AllAppActivity extends Activity {
    public static final String ADD_APP_KEY = "add_app_key";
    private AppAdapter adapter;
    private ArrayList appList;

    public AllAppActivity() {
        super();
        this.appList = new ArrayList();
    }

    protected void onCreate(final Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(R.layout.all_app_layout);
        GridView gvApp = (GridView) this.findViewById(R.id.app_app_gv_icon);
        this.adapter = new AppAdapter(this, R.layout.custom_action_layout, this.appList);
        gvApp.setAdapter(this.adapter);
        new AppLoader().execute((Object[]) new Void[0]);
        gvApp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView adapterView, final View view, final int n, final long n2) {
                final Intent intent = new Intent(AllAppActivity.this, (Class) EasyTouchService.class);
                final Bundle bundle = new Bundle();
                bundle.putParcelable("add_app_key", (Parcelable) AllAppActivity.this.appList.get(n));
                intent.putExtras(bundle);
                intent.putExtra("pos", AllAppActivity.this.getIntent().getIntExtra("pos", 0));
                intent.setAction("com.touchpro.foregroundservice.action.startforeground");
                AllAppActivity.this.startService(intent);
                AllAppActivity.this.finish();
            }
        });
    }

    protected void onResume() {
        super.onResume();
    }

    private class AppLoader extends AsyncTask {
        final AllAppActivity this$0;

        AppLoader() {
            super();
            this.this$0 = AllAppActivity.this;
        }

        protected Object doInBackground(final Object[] array) {
            return this.doInBackground((Void[]) array);
        }

        Void doInBackground(final Void[] array) {
            final PackageManager packageManager = AllAppActivity.this.getApplicationContext().getPackageManager();
            new ArrayList();
            final Intent intent = new Intent("android.intent.action.MAIN", (Uri) null);
            intent.addCategory("android.intent.category.LAUNCHER");
            final Iterator iterator = ((ArrayList) AllAppActivity.this.getApplicationContext().getPackageManager().queryIntentActivities((Intent) intent, 0)).iterator();
            while (((Iterator<ResolveInfo>) iterator).hasNext()) {
                final ResolveInfo resolveInfo = ((Iterator<ResolveInfo>) iterator).next();
                final AppInfo appInfo = new AppInfo();
                appInfo.setName(resolveInfo.loadLabel(packageManager).toString());
                appInfo.setIcon(resolveInfo.loadIcon(packageManager));
                appInfo.setPackageName(resolveInfo.activityInfo.packageName);
                AllAppActivity.this.appList.add(appInfo);
                this.publishProgress((Object[]) new Void[0]);
            }
            return null;
        }

        protected void onPostExecute(final Object o) {
            this.onPostExecute((Void) o);
        }

        void onPostExecute(final Void void1) {
            super.onPostExecute((Object) void1);
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected void onProgressUpdate(final Object[] array) {
            this.onProgressUpdate((Void[]) array);
        }

        void onProgressUpdate(final Void[] array) {
            super.onProgressUpdate((Object[]) array);
            AllAppActivity.this.adapter.notifyDataSetChanged();
        }
    }

    class SortAppName implements Comparator {
        final AllAppActivity this$0;

        SortAppName() {
            super();
            this.this$0 = AllAppActivity.this;
        }

        int compare(final AppInfo appInfo, final AppInfo appInfo2) {
            return appInfo.getName().compareTo(appInfo2.getName());
        }

        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((AppInfo) o, (AppInfo) o2);
        }
    }
}