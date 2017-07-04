package com.touchpro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.develop.touchpro.R;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("all")
public class ThemeAdapter extends ArrayAdapter {
    Context context;
    Integer[] data;
    ArrayList themeList;

    public ThemeAdapter(final Context context, final int n, final ArrayList themeList) {
        super(context, n, (List) themeList);
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
        tag.ivIcon.setImageDrawable(this.context.getResources().getDrawable((int) this.themeList.get(n)));
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