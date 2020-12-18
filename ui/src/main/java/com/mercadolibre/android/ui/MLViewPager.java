package com.mercadolibre.android.ui;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <p>A {@link ViewPager} that fixes issues in the original {@code ViewPager}.
 * This should be used instead of the original one in all cases.</p>
 *
 * <p>It currently fixes <a href='https://code.google.com/p/android/issues/detail?id=66620'>issue 66620</a>.</p>
 */
public class MLViewPager extends ViewPager {
    /**
     * Default constructor to use by code
     *
     * @param context      the android context
     */
    public MLViewPager(final Context context) {
        super(context);
    }

    /**
     * Default constructor to use by XML
     *
     * @param context      the android context
     * @param attrs        an attribute set
     */
    public MLViewPager(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onInterceptTouchEvent(final MotionEvent event) {
        return shouldHandleTouchEvents() && super.onInterceptTouchEvent(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTouchEvent(final MotionEvent ev) {
        return shouldHandleTouchEvents() && super.onTouchEvent(ev);
    }

    /**
     * Fixes <a href='https://code.google.com/p/android/issues/detail?id=66620'>issue 66620</a> by checking if we have
     * any children.
     */
    private boolean shouldHandleTouchEvents() {
        return getChildCount() != 0;
    }
}
