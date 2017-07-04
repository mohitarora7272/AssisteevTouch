package com.touchpro.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
@SuppressWarnings("all")
public class SquareButton extends Button {
    public SquareButton(final Context context) {
        super(context);
    }

    public SquareButton(final Context context, final AttributeSet set) {
        super(context, set);
    }

    public SquareButton(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
    }

    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
        final int measuredWidth = this.getMeasuredWidth();
        this.setMeasuredDimension(measuredWidth, measuredWidth);
    }
}