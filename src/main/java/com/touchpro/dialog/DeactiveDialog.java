package com.touchpro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.touchpro.easytouch.MainActivity;
import com.develop.touchpro.R;
@SuppressWarnings("all")
public class DeactiveDialog extends Dialog {
    private MainActivity mContext;

    public DeactiveDialog(final MainActivity mContext, final int n) {
        super((Context) mContext, n);
        this.mContext = mContext;
        this.requestWindowFeature(1);
        this.setContentView(R.layout.deactive_dialog);
        final TextView textView = (TextView) this.findViewById(R.id.btOK);
        final TextView textView2 = (TextView) this.findViewById(R.id.btCancel);
        textView.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            final DeactiveDialog this$0 = DeactiveDialog.this;

            public void onClick(final View view) {
                DeactiveDialog.this.dismiss();
            }
        });
        textView2.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            final DeactiveDialog this$0 = DeactiveDialog.this;

            public void onClick(final View view) {
                DeactiveDialog.this.mContext.deactiveApp();
                DeactiveDialog.this.dismiss();
            }
        });
        this.show();
    }
}