package com.touchpro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.touchpro.adapter.ThemeAdapter;
import com.touchpro.datamodel.DialogSelectListener;
import com.touchpro.easytouch.EasyTouchApplication;
import com.develop.touchpro.R;
@SuppressWarnings("all")
public class IconSelectDialog extends Dialog {
    private DialogSelectListener dialogSelectListener;
    private EasyTouchApplication mApp;

    @SuppressWarnings("ResourceType")
    public IconSelectDialog(Context context, int i) {
        super(context, i);
        if (getWindow() != null) {
            getWindow().setBackgroundDrawableResource(17170445);
        }
        getWindow().getAttributes().windowAnimations = R.style.DialogActivityAnimation;
        requestWindowFeature(1);
        setContentView(R.layout.theme_activity_layout);
        getWindow().setType(2003);
        setCanceledOnTouchOutside(true);
        show();
        this.mApp = (EasyTouchApplication) context.getApplicationContext();
        setContentView(R.layout.theme_activity_layout);
        GridView gridview = (GridView) findViewById(R.id.theme_activity_gv_icon);
        gridview.setAdapter(new ThemeAdapter(context, 0, this.mApp.getThemeList()));
        gridview.setOnItemClickListener(new OnItemClickListener() {
            final IconSelectDialog this$0;

            {
                this.this$0 = IconSelectDialog.this;
            }

            public void onItemClick(AdapterView adapterview, View view, int j, long l) {
                IconSelectDialog.this.dialogSelectListener.onSelected(j);
                IconSelectDialog.this.dismiss();
            }
        });
    }

    public void setDialogSelectListener(DialogSelectListener dialogselectlistener) {
        this.dialogSelectListener = dialogselectlistener;
    }
}