package com.touchpro.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
@SuppressWarnings("all")
public class SquareLinearLayout extends LinearLayout {
    public SquareLinearLayout(final Context context) {
        super(context);
    }

    public SquareLinearLayout(final Context context, final AttributeSet set) {
        super(context, set);
    }

    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
        final int measuredWidth = this.getMeasuredWidth();
        this.setMeasuredDimension(measuredWidth, measuredWidth);
    }
}