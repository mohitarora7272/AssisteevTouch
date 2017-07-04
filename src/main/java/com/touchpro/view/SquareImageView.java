package com.touchpro.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
@SuppressWarnings("all")
public class SquareImageView extends ImageView {
    public SquareImageView(final Context context) {
        super(context);
    }

    public SquareImageView(final Context context, final AttributeSet set) {
        super(context, set);
    }

    protected void onMeasure(final int n, final int n2) {
        super.onMeasure(n, n2);
        final int measuredHeight = this.getMeasuredHeight();
        this.setMeasuredDimension(measuredHeight, measuredHeight);
    }
}