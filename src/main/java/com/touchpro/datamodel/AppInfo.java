package com.touchpro.datamodel;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
@SuppressWarnings("all")
public class AppInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR;
    private Drawable icon;
    private String name;
    private String packageName;

    static {
        CREATOR = (Parcelable.Creator) new Parcelable.Creator() {
            public AppInfo createFromParcel(final Parcel parcel) {
                final AppInfo appInfo = new AppInfo();
                AppInfo.access$0(appInfo, parcel.readString());
                AppInfo.access$1(appInfo, parcel.readString());
                return appInfo;
            }

            public AppInfo[] newArray(final int n) {
                return null;
            }
        };
    }

    public AppInfo() {
        super();
    }

    public AppInfo(final String name, final String packageName, final Drawable icon) {
        super();
        this.name = name;
        this.packageName = packageName;
        this.icon = icon;
    }

    static /* synthetic */ void access$0(final AppInfo appInfo, final String name) {
        appInfo.name = name;
    }

    static /* synthetic */ void access$1(final AppInfo appInfo, final String packageName) {
        appInfo.packageName = packageName;
    }

    public int describeContents() {
        return 0;
    }

    public Drawable getIcon() {
        return this.icon;
    }

    public String getName() {
        return this.name;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setIcon(final Drawable icon) {
        this.icon = icon;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPackageName(final String packageName) {
        this.packageName = packageName;
    }

    public void writeToParcel(final Parcel parcel, final int n) {
        parcel.writeString(this.name);
        parcel.writeString(this.packageName);
    }
}