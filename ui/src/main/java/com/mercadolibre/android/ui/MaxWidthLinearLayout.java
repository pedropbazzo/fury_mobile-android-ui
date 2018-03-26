package com.mercadolibre.android.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * LinearLayout with max width attribute.
 *
 * @since 22/3/16
 */
public class MaxWidthLinearLayout extends LinearLayout {

    private int maxWidth;

    /**
     * Default constructor to use by code
     *
     * @param context the android context
     */
    public MaxWidthLinearLayout(final Context context) {
        this(context, null);
    }

    /**
     * Default constructor to use by XML
     *
     * @param context the android context
     * @param attrs   an attribute set
     */
    public MaxWidthLinearLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Default constructor to use by XML
     *
     * @param context      the android context
     * @param attrs        an attribute set
     * @param defStyleAttr the default style attributes
     */
    public MaxWidthLinearLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaxWidthRelativeLayout, defStyleAttr, 0);
        maxWidth = a.getDimensionPixelSize(R.styleable.MaxWidthRelativeLayout_maxWidth, 0);
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        int tmpWidthMeasureSpec = widthMeasureSpec;
        if (maxWidth > 0) {
            final int widthSize = MeasureSpec.getSize(tmpWidthMeasureSpec);
            final int widthMode = MeasureSpec.getMode(tmpWidthMeasureSpec);

            switch (widthMode) {
                case MeasureSpec.AT_MOST:
                    tmpWidthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(widthSize, maxWidth), MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.UNSPECIFIED:
                    tmpWidthMeasureSpec = MeasureSpec.makeMeasureSpec(maxWidth, MeasureSpec.AT_MOST);
                    break;
                case MeasureSpec.EXACTLY:
                    tmpWidthMeasureSpec = MeasureSpec.makeMeasureSpec(Math.min(widthSize, maxWidth), MeasureSpec.EXACTLY);
                    break;
            }
        }

        super.onMeasure(tmpWidthMeasureSpec, heightMeasureSpec);
    }

    public int getMaxWidth() {
        return maxWidth;
    }

    public void setMaxWidth(final int maxWidth) {
        this.maxWidth = maxWidth;
    }

    @Override
    public String toString() {
        return "MaxWidthLinearLayout{"
                + "maxWidth=" + maxWidth
                + '}';
    }
}
