package com.touchpro.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
@SuppressWarnings("all")
public class SquareLinearLayoutLand extends LinearLayout {
    public SquareLinearLayoutLand(final Context context) {
        super(context);
    }

    public SquareLinearLayoutLand(final Context context, final AttributeSet set) {
        super(context, set);
    }

    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
        final int measuredHeight = this.getMeasuredHeight();
        this.setMeasuredDimension((int) (1.05 * measuredHeight), measuredHeight);
    }
}