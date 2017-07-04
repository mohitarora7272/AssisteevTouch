package com.touchpro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.touchpro.adapter.ActionSelectAdapter;
import com.touchpro.constant.ACTION;
import com.develop.touchpro.R;
@SuppressWarnings("all")
public class SelectActionDialog extends Dialog {
    private OnActionSelected mOnActionSelected;

    @SuppressWarnings("ResourceType")
    public SelectActionDialog(final Context context) {
        super(context);
        this.requestWindowFeature(1);
        if (getWindow() != null) {
            this.getWindow().setBackgroundDrawable((Drawable) new ColorDrawable(0));
        }
        this.setContentView(R.layout.select_action_layout);
        final GridView gridView = (GridView) this.findViewById(R.id.select_action_gv_icon);
        Log.d("TEST", "Action list : " + ACTION.getActionList().size());
        gridView.setAdapter((ListAdapter) new ActionSelectAdapter(context, 0, ACTION.getActionList()));
        gridView.setOnItemClickListener((AdapterView.OnItemClickListener) new AdapterView.OnItemClickListener() {
            final SelectActionDialog this$0 = SelectActionDialog.this;

            public void onItemClick(final AdapterView adapterView, final View view, final int n, final long n2) {
                SelectActionDialog.this.mOnActionSelected.onActionSelected(n);
                SelectActionDialog.this.dismiss();
            }
        });
        this.show();
    }

    public void setOnActionSelected(final OnActionSelected mOnActionSelected) {
        this.mOnActionSelected = mOnActionSelected;
    }

    public interface OnActionSelected {
        void onActionSelected(int p0);
    }
}