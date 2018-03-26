package com.mercadolibre.android.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * RelativeLayout with rounded corners.
 *
 * @since 19/04/2016
 */
public class RoundedRelativeLayout extends RelativeLayout {

    private int radius;

    /**
     * Default constructor to use by code
     *
     * @param context      the android context
     */
    public RoundedRelativeLayout(final Context context) {
        this(context, null);
    }

    /**
     * Default constructor to use by XML
     *
     * @param context      the android context
     * @param attrs        an attribute set
     */
    public RoundedRelativeLayout(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Default constructor to use by XML
     *
     * @param context      the android context
     * @param attrs        an attribute set
     * @param defStyleAttr the default style attributes
     */
    public RoundedRelativeLayout(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RoundedRelativeLayout, defStyleAttr, 0);
        radius = a.getDimensionPixelSize(R.styleable.RoundedRelativeLayout_ui_cornerRadius, 0);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            // Turn off hardware acceleration for this view, because clipping is not supported.
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void dispatchDraw(final Canvas canvas) {
        final Path path = new Path();
        path.addRoundRect(new RectF(0, 0, getWidth(), getHeight()), radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.dispatchDraw(canvas);
    }

    @Override
    public String toString() {
        return "RoundedRelativeLayout{"
                + "radius=" + radius
                + '}';
    }
}
