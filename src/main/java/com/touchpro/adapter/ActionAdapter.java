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

import com.develop.touchpro.R;
import com.touchpro.constant.ACTION;
import com.touchpro.datamodel.ActionItem;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("all")
public class ActionAdapter extends ArrayAdapter {
    ArrayList<ActionItem> actionList;
    Context context;
    Integer[] data;
    PackageManager pm;

    public ActionAdapter(final Context context, final int n, final ArrayList<ActionItem> actionList) {
        super(context, n, (List) actionList);
        this.actionList = actionList;
        this.context = context;
        this.pm = context.getPackageManager();
    }

    public View getView(final int n, View inflate, final ViewGroup viewGroup) {
        ActionViewHolder tag;
        if (inflate == null) {
            inflate = ((LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_action_layout, (ViewGroup) null);
            tag = new ActionViewHolder(null);
            tag.ivIcon = (ImageView) inflate.findViewById(R.id.custom_button_layout_img);
            tag.tvName = (TextView) inflate.findViewById(R.id.custom_button_layout_text);
            inflate.setTag((Object) tag);
        } else {
            tag = (ActionViewHolder) inflate.getTag();
        }
        final ActionItem actionItem = (ActionItem) this.actionList.get(n);
        if (actionItem == null) {
            inflate.setVisibility(View.GONE);
            tag.ivIcon.setVisibility(View.GONE);
            tag.tvName.setVisibility(View.GONE);
            return inflate;
        }
        inflate.setVisibility(View.VISIBLE);
        tag.ivIcon.setVisibility(View.VISIBLE);
        tag.tvName.setVisibility(View.VISIBLE);
        tag.tvName.setText(this.actionList.get(n).getName());
        if (actionItem.getAction() == 2000) {
            new LoadImage(tag, n).execute((Object[]) new String[]{actionItem.getPackageName()});
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
                tag.ivIcon.setImageLevel(actionItem.getValue());
                return inflate;
            }
            case 1008: {
                tag.ivIcon.setImageLevel(actionItem.getValue());
                if (actionItem.getValue() == 1) {
                    tag.tvName.setText((CharSequence) "Auto-rotate");
                    return inflate;
                }
                tag.tvName.setText((CharSequence) "Potrait");
                return inflate;
            }
            case 1012: {
                tag.ivIcon.setImageLevel(actionItem.getValue());
                if (actionItem.getValue() == 0) {
                    tag.tvName.setText((CharSequence) "Silent");
                    return inflate;
                }
                if (actionItem.getValue() == 1) {
                    tag.tvName.setText((CharSequence) "Vibrate");
                    return inflate;
                }
                tag.tvName.setText((CharSequence) "Normal");
                return inflate;
            }
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
        private ActionViewHolder holder;
        private int position;
        final ActionAdapter this$0;

        public LoadImage(final ActionViewHolder holder, final int position) {
            super();
            this.this$0 = ActionAdapter.this;
            this.holder = holder;
            this.position = position;
        }

        protected Drawable doInBackground(final String[] array) {
            try {
                return ActionAdapter.this.pm.getApplicationIcon(array[0]);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }

        protected Object doInBackground(final Object[] array) {
            return this.doInBackground((String[]) array);
        }

        protected void onPostExecute(final Drawable imageDrawable) {
            if (imageDrawable != null && this.holder.ivIcon != null) {
                this.holder.ivIcon.setImageDrawable(imageDrawable);
                this.holder.tvName.setVisibility(View.VISIBLE);
                return;
            }
            this.holder.ivIcon.setImageResource(R.drawable.action_add);
            this.holder.tvName.setVisibility(View.GONE);
            ActionAdapter.this.actionList.remove(this.position);
            ActionAdapter.this.actionList.add(this.position, ACTION.ADD_APP);
        }

        protected void onPostExecute(final Object o) {
            this.onPostExecute((Drawable) o);
        }
    }
}