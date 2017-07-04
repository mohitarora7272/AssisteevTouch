package com.touchpro.controller;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.widget.Toast;

import java.util.List;
@SuppressWarnings("all")
public class ClearRamController {
    private ActivityManager activityManager;
    private long availMem;
    private Context mContext;
    private ActivityManager.MemoryInfo memoryInfo;
    private List<ActivityManager.RunningAppProcessInfo> runningAppList;

    public ClearRamController(final Context mContext) {
        super();
        this.mContext = mContext;
        this.activityManager = (ActivityManager) this.mContext.getSystemService(Context.ACTIVITY_SERVICE);
    }

    static void setMemoryInfo(final ClearRamController clearRamController, final ActivityManager.MemoryInfo memoryInfo) {
        clearRamController.memoryInfo = memoryInfo;
    }

    static void setAvailableMem(final ClearRamController clearRamController, final long availMem) {
        clearRamController.availMem = availMem;
    }

    static void setRunningList(final ClearRamController clearRamController, final List runningAppList) {
        clearRamController.runningAppList = runningAppList;
    }

    @SuppressLint({"NewApi"})
    public void cleanRam() {
        if (Build.VERSION.SDK_INT >= 11) {
            new MemoryCleaner().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            return;
        }
        new MemoryCleaner().execute(new Void[0]);
    }

    public void killApplication(final String s) {
        this.activityManager.killBackgroundProcesses(s);
    }

    class MemoryCleaner extends AsyncTask<Void, Void, Float> {

        @Override
        protected Float doInBackground(Void... array) {
            ClearRamController.setMemoryInfo(ClearRamController.this, new ActivityManager.MemoryInfo());
            ClearRamController.this.activityManager.getMemoryInfo(ClearRamController.this.memoryInfo);
            ClearRamController.setAvailableMem(ClearRamController.this, Long.valueOf(ClearRamController.this.memoryInfo.availMem));
            ClearRamController.setRunningList(ClearRamController.this, ClearRamController.this.activityManager.getRunningAppProcesses());
            for (int i = 0; i < ClearRamController.this.runningAppList.size(); ++i) {
                final String processName = ClearRamController.this.runningAppList.get(i).processName;
                if (!ClearRamController.this.mContext.getPackageName().equalsIgnoreCase(processName)) {
                    ClearRamController.this.killApplication(processName);
                }
            }
            ClearRamController.this.activityManager.getMemoryInfo(ClearRamController.this.memoryInfo);
            return (float) ((Long.valueOf(ClearRamController.this.memoryInfo.availMem) - ClearRamController.this.availMem) / 1048576L);
        }
        
        /*protected Object doInBackground(final Object[] array) {
            return this.doInBackground((Void[])array);
        }*/

        @Override
        protected void onPostExecute(Float n) {
            super.onPostExecute(n);
            if (Math.abs(n) > 50.0f) {
                Toast.makeText(ClearRamController.this.mContext, (CharSequence) ("Freed RAM: " + String.format("%.0f", Math.abs(n)) + ".5MB"), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ClearRamController.this.mContext, (CharSequence) "Phone has been boosted.", Toast.LENGTH_SHORT).show();
            }

        }
        
       /* protected void onPostExecute(final Object o) {
            this.onPostExecute((Float)o);
        }*/
    }
}