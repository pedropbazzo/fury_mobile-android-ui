package com.mercadolibre.android.ui.legacy.widgets.image;

import android.content.Context;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;

@Deprecated
class MLViewPager extends ViewPager {

    private boolean enabled;

    public MLViewPager(Context context) {
        super(context);
        this.enabled = true;
    }

    public MLViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = true;
    }

    private boolean checkInsideViewZoom() {
        int currentItem = getCurrentItem();

        FragmentManager manager = parent.getFragmentManager();
        MLImageFragment fragment = (MLImageFragment) manager.findFragmentByTag("android:switcher:" + this.getId() + ":" + currentItem);

        if (fragment == null)
            return false;

        if (!(fragment.getImageView() instanceof ZoomableDraweeView)) {
            return false;
        }
        ZoomableDraweeView imageView = (ZoomableDraweeView) fragment.getImageView();
        if (initialScales.get(currentItem) == null) {
            if (imageView.getDrawable() != null)
                initialScales.put(currentItem, imageView.getZoomableController().getScaleFactor());
            return false;
        }

        return imageView.getZoomableController().getScaleFactor() - initialScales.get(currentItem) > SCALE_PADDING_ALLOWED;

    }

    private SparseArray<Float> initialScales = new SparseArray<Float>();

    private static final float SCALE_PADDING_ALLOWED = 0.25f;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled && !checkInsideViewZoom()) {
            //bug: https://code.google.com/p/android/issues/detail?id=18990
            //workaround: try-catch the error.
            try {
                return super.onInterceptTouchEvent(event);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }

        return false;
    }

    public void setPagingEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private MLImagePager parent;

    public void setPagerParent(MLImagePager pnt) {
        parent = pnt;
    }

    public MLImagePager getPagerParent() {
        return parent;
    }
}
