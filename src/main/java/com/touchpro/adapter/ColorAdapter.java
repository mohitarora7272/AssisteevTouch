package com.touchpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.develop.touchpro.R;
@SuppressWarnings("all")
public class ColorAdapter extends ArrayAdapter {
    Context context;
    Integer[] themeList;

    public ColorAdapter(final Context context, final int n, final Integer[] themeList) {
        super(context, n, (Object[]) themeList);
        this.themeList = themeList;
        this.context = context;
    }

    public View getView(final int n, View inflate, final ViewGroup viewGroup) {
        AppViewHolder tag;
        if (inflate == null) {
            inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.theme_item_layout, (ViewGroup) null);
            tag = new AppViewHolder(null);
            tag.ivIcon = (ImageView) inflate.findViewById(R.id.theme_item_layout_img);
            inflate.setTag((Object) tag);
        } else {
            tag = (AppViewHolder) inflate.getTag();
        }
        tag.ivIcon.setBackgroundColor((int) this.themeList[n]);
        return inflate;
    }

    private static class AppViewHolder {
        ImageView ivIcon;

        private AppViewHolder() {
            super();
        }

        AppViewHolder(final AppViewHolder appViewHolder) {
            this();
        }
    }
}