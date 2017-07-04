package com.touchpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.touchpro.datamodel.AppInfo;
import com.develop.touchpro.R;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("all")
public class AppAdapter extends ArrayAdapter {
    ArrayList<AppInfo> appList;
    Context context;
    Integer[] data;

    public AppAdapter(final Context context, final int n, final ArrayList appList) {
        super(context, n, (List) appList);
        this.appList = appList;
        this.context = context;
    }

    public View getView(final int n, View inflate, final ViewGroup viewGroup) {
        AppViewHolder tag;
        if (inflate == null) {
            inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_app_layout, (ViewGroup) null);
            tag = new AppViewHolder(null);
            tag.tvName = (TextView) inflate.findViewById(R.id.custom_button_layout_text);
            tag.ivIcon = (ImageView) inflate.findViewById(R.id.custom_button_layout_img);
            inflate.setTag((Object) tag);
        } else {
            tag = (AppViewHolder) inflate.getTag();
        }
        final AppInfo appInfo = this.appList.get(n);
        tag.tvName.setText((CharSequence) appInfo.getName());
        tag.ivIcon.setImageDrawable(appInfo.getIcon());
        return inflate;
    }

    private static class AppViewHolder {
        ImageView ivIcon;
        TextView tvName;

        private AppViewHolder() {
            super();
        }

        AppViewHolder(final AppViewHolder appViewHolder) {
            this();
        }
    }
}