package com.touchpro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.touchpro.adapter.ColorAdapter;
import com.touchpro.datamodel.DialogSelectListener;
import com.touchpro.easytouch.EasyTouchApplication;
import com.develop.touchpro.R;
@SuppressWarnings("all")
public class BackgoundSelectDialog extends Dialog {
    private DialogSelectListener dialogSelectListener;
    private EasyTouchApplication mApp;

    @SuppressWarnings("ResourceType")
    public BackgoundSelectDialog(Context context, int i) {
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
        setContentView(R.layout.color_activity_layout);
        GridView gridview = (GridView) findViewById(R.id.theme_activity_gv_icon);
        gridview.setAdapter(new ColorAdapter(context, 0, this.mApp.getColorList()));
        gridview.setOnItemClickListener(new OnItemClickListener() {
            final BackgoundSelectDialog this$0;

            {
                this.this$0 = BackgoundSelectDialog.this;
            }

            public void onItemClick(AdapterView adapterview, View view, int j, long l) {
                BackgoundSelectDialog.this.dialogSelectListener.onSelected(j);
                BackgoundSelectDialog.this.dismiss();
            }
        });
    }

    public void setDialogSelectListener(DialogSelectListener dialogselectlistener) {
        this.dialogSelectListener = dialogselectlistener;
    }
}