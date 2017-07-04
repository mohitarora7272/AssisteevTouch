package com.touchpro.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.touchpro.animation.ActivityAnim;
import com.touchpro.easytouch.MainActivity;
import com.develop.touchpro.R;
@SuppressWarnings("all")
public class ExitDialog extends Dialog {
    private MainActivity mContext;

    public ExitDialog(final MainActivity mContext, final int n) {
        super((Context) mContext, n);
        this.mContext = mContext;
        this.requestWindowFeature(1);
        this.setContentView(R.layout.rate_dialog);
        this.setCanceledOnTouchOutside(false);
        final TextView textView = (TextView) this.findViewById(R.id.btCancle);
        final TextView textView2 = (TextView) this.findViewById(R.id.btExit);
        final TextView textView3 = (TextView) this.findViewById(R.id.btRate);
        textView.setTypeface(MainActivity.type_Roboto_Medium);
        textView2.setTypeface(MainActivity.type_Roboto_Medium);
        textView3.setTypeface(MainActivity.type_Roboto_Medium);
        ((TextView) this.findViewById(R.id.tvRateTitle)).setTypeface(MainActivity.type_Roboto_Medium);
        ((TextView) this.findViewById(R.id.tvRateContent)).setTypeface(MainActivity.type_Roboto_Regular);
        textView.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public void onClick(final View view) {
                ExitDialog.this.dismiss();
            }
        });
        textView2.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public void onClick(final View view) {
                ExitDialog.this.mContext.finish();
            }
        });
        textView3.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            public void onClick(final View view) {
                ExitDialog.this.mContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + ExitDialog.this.mContext.getPackageName())));
                ActivityAnim.slideIn(ExitDialog.this.mContext);
                Toast.makeText((Context) ExitDialog.this.mContext, (CharSequence) "Thank for your rating and comment :)", Toast.LENGTH_LONG).show();
                ExitDialog.this.mContext.finish();
            }
        });
        this.show();
    }
}