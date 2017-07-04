package com.touchpro.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
@SuppressWarnings("all")
public class CircleImageView extends ImageView {
    private static final int SHADOW_COLOR = -16777216;
    private static final float SHADOW_DX = 0.0f;
    private static final float SHADOW_DY = 2.0f;
    private static final boolean SHADOW_ENABLED = false;
    private static final float SHADOW_RADIUS = 4.0f;
    private static final String TAG;
    private int borderWidth;
    private int canvasSize;
    private boolean hasBorder;
    private boolean hasSelector;
    private Bitmap image;
    private boolean isSelected;
    private Paint paint;
    private Paint paintBorder;
    private Paint paintSelectorBorder;
    private ColorFilter selectorFilter;
    private int selectorStrokeWidth;
    private BitmapShader shader;
    private int shadowColor;
    private float shadowDx;
    private float shadowDy;
    private boolean shadowEnabled;
    private float shadowRadius;

    static {
        TAG = CircleImageView.class.getSimpleName();
    }

    public CircleImageView(final Context context) {
        this(context, null, 0);
    }

    public CircleImageView(final Context context, final AttributeSet set) {
        this(context, set, 0);
    }

    public CircleImageView(final Context context, final AttributeSet set, final int n) {
        super(context, set, n);
        this.init(context, set, n);
    }

    @SuppressLint("NewApi")
    public CircleImageView(final Context context, final AttributeSet set, final int n, final int n2) {
        super(context, set, n, n2);
        this.init(context, set, n);
    }

    @SuppressLint({"NewApi"})
    @SuppressWarnings("ResourceType")
    private void init(final Context context, final AttributeSet set, final int n) {
        (this.paint = new Paint()).setAntiAlias(true);
        (this.paintBorder = new Paint()).setAntiAlias(true);
        this.paintBorder.setStyle(Paint.Style.STROKE);
        (this.paintSelectorBorder = new Paint()).setAntiAlias(true);
        if (Build.VERSION.SDK_INT >= 11) {
            this.setLayerType(1, (Paint) null);
        }
        final TypedArray obtainStyledAttributes = context.obtainStyledAttributes(set, styleable.CircularImageView, n, 0);
        this.hasBorder = obtainStyledAttributes.getBoolean(0, false);
        this.hasSelector = obtainStyledAttributes.getBoolean(3, false);
        this.shadowEnabled = obtainStyledAttributes.getBoolean(7, false);
        if (this.hasBorder) {
            this.setBorderWidth(obtainStyledAttributes.getDimensionPixelOffset(2, (int) (0.5f + 2.0f * context.getResources().getDisplayMetrics().density)));
            this.setBorderColor(obtainStyledAttributes.getColor(1, -1));
        }
        if (this.hasSelector) {
            final int n2 = (int) (0.5f + 2.0f * context.getResources().getDisplayMetrics().density);
            this.setSelectorColor(obtainStyledAttributes.getColor(4, 0));
            this.setSelectorStrokeWidth(obtainStyledAttributes.getDimensionPixelOffset(6, n2));
            this.setSelectorStrokeColor(obtainStyledAttributes.getColor(5, -16776961));
        }
        if (this.shadowEnabled) {
            this.shadowRadius = obtainStyledAttributes.getFloat(8, 4.0f);
            this.shadowDx = obtainStyledAttributes.getFloat(9, 0.0f);
            this.shadowDy = obtainStyledAttributes.getFloat(10, 2.0f);
            this.shadowColor = obtainStyledAttributes.getColor(11, -16777216);
            this.setShadowEnabled(true);
        }
        obtainStyledAttributes.recycle();
    }

    private int measureHeight(final int n) {
        final int mode = View.MeasureSpec.getMode(n);
        final int size = View.MeasureSpec.getSize(n);
        int canvasSize;
        if (mode == 1073741824) {
            canvasSize = size;
        } else if (mode == Integer.MIN_VALUE) {
            canvasSize = size;
        } else {
            canvasSize = this.canvasSize;
        }
        return canvasSize + 2;
    }

    private int measureWidth(final int n) {
        final int mode = View.MeasureSpec.getMode(n);
        final int size = View.MeasureSpec.getSize(n);
        if (mode == 1073741824) {
            return size;
        }
        if (mode == Integer.MIN_VALUE) {
            return size;
        }
        return this.canvasSize;
    }

    private void updateShadow() {
        float shadowRadius;
        if (this.shadowEnabled) {
            shadowRadius = this.shadowRadius;
        } else {
            shadowRadius = 0.0f;
        }
        this.paintBorder.setShadowLayer(shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor);
        this.paintSelectorBorder.setShadowLayer(shadowRadius, this.shadowDx, this.shadowDy, this.shadowColor);
    }

    public boolean dispatchTouchEvent(final MotionEvent motionEvent) {
        if (!this.isClickable()) {
            this.isSelected = false;
            return super.onTouchEvent(motionEvent);
        }
        switch (motionEvent.getAction()) {
            case 0: {
                this.isSelected = true;
                break;
            }
            case 1:
            case 3:
            case 4:
            case 8: {
                this.isSelected = false;
                break;
            }
        }
        this.invalidate();
        return super.dispatchTouchEvent(motionEvent);
    }

    public Bitmap drawableToBitmap(final Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            Log.i(CircleImageView.TAG, "Bitmap drawable!");
            return ((BitmapDrawable) drawable).getBitmap();
        }
        final int intrinsicWidth = drawable.getIntrinsicWidth();
        final int intrinsicHeight = drawable.getIntrinsicHeight();
        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            return null;
        }
        try {
            final Bitmap bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888);
            final Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (OutOfMemoryError outOfMemoryError) {
            Log.e(CircleImageView.TAG, "Encountered OutOfMemoryError while generating bitmap!");
            return null;
        }
    }

    public boolean isSelected() {
        return this.isSelected;
    }

    public void onDraw(final Canvas canvas) {
        if (this.image != null && this.image.getHeight() != 0 && this.image.getWidth() != 0) {
            final int canvasSize = this.canvasSize;
            int canvasSize2;
            if (this.getWidth() < this.getHeight()) {
                canvasSize2 = this.getWidth();
            } else {
                canvasSize2 = this.getHeight();
            }
            this.canvasSize = canvasSize2;
            if (canvasSize != this.canvasSize) {
                this.updateBitmapShader();
            }
            this.paint.setShader((Shader) this.shader);
            int n = this.canvasSize / 2;
            int n2;
            if (this.hasSelector && this.isSelected) {
                n2 = this.selectorStrokeWidth;
                n = (this.canvasSize - n2 * 2) / 2;
                this.paint.setColorFilter(this.selectorFilter);
                canvas.drawCircle((float) (n + n2), (float) (n + n2), n2 + (this.canvasSize - n2 * 2) / 2 - 4.0f, this.paintSelectorBorder);
            } else if (this.hasBorder) {
                n2 = this.borderWidth;
                n = (this.canvasSize - n2 * 2) / 2;
                this.paint.setColorFilter((ColorFilter) null);
                canvas.drawArc(new RectF((float) (0 + n2 / 2), (float) (0 + n2 / 2), (float) (this.canvasSize - n2 / 2), (float) (this.canvasSize - n2 / 2)), 360.0f, 360.0f, false, this.paintBorder);
            } else {
                this.paint.setColorFilter((ColorFilter) null);
                n2 = 0;
            }
            canvas.drawCircle((float) (n + n2), (float) (n + n2), (float) ((this.canvasSize - n2 * 2) / 2), this.paint);
        }
    }

    protected void onMeasure(final int n, final int n2) {
        this.setMeasuredDimension(this.measureWidth(n), this.measureHeight(n2));
    }

    public void setBorderColor(final int color) {
        if (this.paintBorder != null) {
            this.paintBorder.setColor(color);
        }
        this.invalidate();
    }

    public void setBorderWidth(final int borderWidth) {
        this.borderWidth = borderWidth;
        if (this.paintBorder != null) {
            this.paintBorder.setStrokeWidth((float) borderWidth);
        }
        this.requestLayout();
        this.invalidate();
    }

    public void setIconModeEnabled(final boolean b) {
    }

    public void setImageBitmap(final Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.image = bitmap;
        if (this.canvasSize > 0) {
            this.updateBitmapShader();
        }
    }

    public void setImageDrawable(final Drawable imageDrawable) {
        super.setImageDrawable(imageDrawable);
        this.image = this.drawableToBitmap(this.getDrawable());
        if (this.canvasSize > 0) {
            this.updateBitmapShader();
        }
    }

    public void setImageResource(final int imageResource) {
        super.setImageResource(imageResource);
        this.image = this.drawableToBitmap(this.getDrawable());
        if (this.canvasSize > 0) {
            this.updateBitmapShader();
        }
    }

    public void setImageURI(final Uri imageURI) {
        super.setImageURI(imageURI);
        this.image = this.drawableToBitmap(this.getDrawable());
        if (this.canvasSize > 0) {
            this.updateBitmapShader();
        }
    }

    public void setSelectorColor(final int n) {
        this.selectorFilter = (ColorFilter) new PorterDuffColorFilter(n, PorterDuff.Mode.SRC_ATOP);
        this.invalidate();
    }

    public void setSelectorStrokeColor(final int color) {
        if (this.paintSelectorBorder != null) {
            this.paintSelectorBorder.setColor(color);
        }
        this.invalidate();
    }

    public void setSelectorStrokeWidth(final int selectorStrokeWidth) {
        this.selectorStrokeWidth = selectorStrokeWidth;
        this.requestLayout();
        this.invalidate();
    }

    public void setShadow(final float shadowRadius, final float shadowDx, final float shadowDy, final int shadowColor) {
        this.shadowRadius = shadowRadius;
        this.shadowDx = shadowDx;
        this.shadowDy = shadowDy;
        this.shadowColor = shadowColor;
        this.updateShadow();
    }

    public void setShadowEnabled(final boolean shadowEnabled) {
        this.shadowEnabled = shadowEnabled;
        this.updateShadow();
    }

    public void updateBitmapShader() {
        if (this.image != null) {
            this.shader = new BitmapShader(this.image, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            if (this.canvasSize != this.image.getWidth() || this.canvasSize != this.image.getHeight()) {
                final Matrix localMatrix = new Matrix();
                final float n = this.canvasSize / this.image.getWidth();
                localMatrix.setScale(n, n);
                this.shader.setLocalMatrix(localMatrix);
            }
        }
    }
}