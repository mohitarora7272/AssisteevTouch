package com.touchpro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.touchpro.easytouch.MainActivity;
import com.develop.touchpro.R;
@SuppressWarnings("all")
public class UninstallDialog extends Dialog {
    private MainActivity mContext;

    public UninstallDialog(final MainActivity mContext, final int n) {
        super((Context) mContext, n);
        this.mContext = mContext;
        this.requestWindowFeature(1);
        this.setContentView(R.layout.uninstall_dialog);
        final TextView textView = (TextView) this.findViewById(R.id.btCancel);
        final TextView textView2 = (TextView) this.findViewById(R.id.btOK);
        textView.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            final UninstallDialog this$0 = UninstallDialog.this;

            public void onClick(final View view) {
                UninstallDialog.this.dismiss();
            }
        });
        textView2.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            final UninstallDialog this$0 = UninstallDialog.this;

            public void onClick(final View view) {
                UninstallDialog.this.mContext.uninstallApp();
                UninstallDialog.this.dismiss();
            }
        });
        this.show();
    }
}