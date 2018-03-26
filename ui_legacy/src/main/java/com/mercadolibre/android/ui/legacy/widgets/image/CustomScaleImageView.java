package com.mercadolibre.android.ui.legacy.widgets.image;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;

/**
 * No one should use this ever again.
 */
@Deprecated
class CustomScaleImageView extends MLImageView {

    private CustomScaleType mCustomScaleType;

    public CustomScaleImageView(Context context) {
        super(context, null);
    }

    public CustomScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public CustomScaleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected boolean setFrame(int l, int t, int r, int b) {
        if (getScaleType() != ScaleType.MATRIX ||
                getDrawable() == null ||
                mCustomScaleType != CustomScaleType.FIT_HEIGHT_CROP_WIDTH) {
            return super.setFrame(l, t, r, b);
        }

        Matrix matrix = getImageMatrix();

        int viewWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int viewHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        int drawableWidth = getDrawable().getIntrinsicWidth();
        int drawableHeight = getDrawable().getIntrinsicHeight();

        float scaleHeight = (float) viewHeight / (float) drawableHeight;

        matrix.reset();

        matrix.postScale(scaleHeight, scaleHeight);

        float tx = (viewWidth - (drawableWidth * scaleHeight)) * 0.5f;
        matrix.postTranslate(tx, 0);

        setImageMatrix(matrix);

        return super.setFrame(l, t, r, b);
    }

    public CustomScaleType getCustomScaleType() {
        return mCustomScaleType;
    }

    public void setCustomScaleType(CustomScaleType mCustomScaleType) {
        setImageScaleType(ScaleType.MATRIX);
        this.mCustomScaleType = mCustomScaleType;
    }

    public enum CustomScaleType {
        FIT_HEIGHT_CROP_WIDTH
    }
}
