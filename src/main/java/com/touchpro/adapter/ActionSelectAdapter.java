package com.touchpro.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.touchpro.constant.ACTION;
import com.touchpro.datamodel.ActionItem;
import com.develop.touchpro.R;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("all")
public class ActionSelectAdapter extends ArrayAdapter {
    ArrayList<ActionItem> actionList;
    Context context;
    Integer[] data;
    PackageManager pm;

    public ActionSelectAdapter(final Context context, final int n, final ArrayList actionList) {
        super(context, n, (List) actionList);
        this.actionList = actionList;
        this.context = context;
        this.pm = context.getPackageManager();
    }

    public View getView(final int n, View inflate, final ViewGroup viewGroup) {
        ActionViewHolder tag;
        if (inflate == null) {
            inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_action_setting_layout, (ViewGroup) null);
            tag = new ActionViewHolder(null);
            tag.ivIcon = (ImageView) inflate.findViewById(R.id.custom_button_layout_img);
            tag.tvName = (TextView) inflate.findViewById(R.id.custom_button_layout_text);
            inflate.setTag((Object) tag);
        } else {
            tag = (ActionViewHolder) inflate.getTag();
        }
        final ActionItem actionItem = this.actionList.get(n);
        if (actionItem != null && !actionItem.equals(ACTION.BACK_SETTING)) {
            inflate.setVisibility(View.VISIBLE);
            tag.ivIcon.setVisibility(View.VISIBLE);
            tag.tvName.setVisibility(View.VISIBLE);
            tag.tvName.setText((CharSequence) this.actionList.get(n).getName());
            if (actionItem.getAction() == 2000) {
                new LoadImage(tag.ivIcon).execute((Object[]) new String[]{actionItem.getPackageName()});
            } else {
                tag.ivIcon.setImageResource(this.actionList.get(n).getIconID());
            }
            if (actionItem.getName().equals("")) {
                tag.tvName.setVisibility(View.GONE);
            } else {
                tag.tvName.setVisibility(View.VISIBLE);
            }
            switch (actionItem.getAction()) {
                default: {
                    return inflate;
                }
                case 1004:
                case 1005:
                case 1006:
                case 1007:
                case 1010: {
                    tag.ivIcon.setImageLevel(1);
                    return inflate;
                }
                case 1008: {
                    tag.ivIcon.setImageLevel(actionItem.getValue());
                    tag.tvName.setText((CharSequence) "Rote Screen");
                    return inflate;
                }
                case 1012: {
                    tag.ivIcon.setImageLevel(actionItem.getValue());
                    tag.tvName.setText((CharSequence) "Sound Mode");
                    return inflate;
                }
            }
        } else {
            if (actionItem != null && actionItem.equals(ACTION.BACK_SETTING)) {
                inflate.setVisibility(View.GONE);
                tag.ivIcon.setVisibility(View.GONE);
                tag.tvName.setVisibility(View.GONE);
                return inflate;
            }
            inflate.setVisibility(View.VISIBLE);
            tag.ivIcon.setVisibility(View.VISIBLE);
            tag.tvName.setVisibility(View.VISIBLE);
            tag.tvName.setText((CharSequence) "None");
            tag.ivIcon.setImageResource(ACTION.ADD_APP.getIconID());
            return inflate;
        }
    }

    private static class ActionViewHolder {
        ImageView ivIcon;
        TextView tvName;

        private ActionViewHolder() {
            super();
        }

        ActionViewHolder(final ActionViewHolder actionViewHolder) {
            this();
        }
    }

    class LoadImage extends AsyncTask {
        private ImageView imv;
        final ActionSelectAdapter this$0;

        public LoadImage(final ImageView imv) {
            super();
            this.this$0 = ActionSelectAdapter.this;
            this.imv = imv;
        }

        protected Drawable doInBackground(final String[] array) {
            try {
                return ActionSelectAdapter.this.pm.getApplicationIcon(array[0]);
            } catch (Exception ex) {
                return null;
            }
        }

        protected Object doInBackground(final Object[] array) {
            return this.doInBackground((String[]) array);
        }

        protected void onPostExecute(final Drawable imageDrawable) {
            if (imageDrawable != null && this.imv != null) {
                this.imv.setImageDrawable(imageDrawable);
                return;
            }
            this.imv.setImageResource(R.drawable.action_add);
        }

        protected void onPostExecute(final Object o) {
            this.onPostExecute((Drawable) o);
        }
    }
}