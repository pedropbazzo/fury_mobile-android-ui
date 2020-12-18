package com.mercadolibre.android.ui.legacy.widgets.image;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.mercadolibre.android.ui.legacy.R;
import com.mercadolibre.android.ui.legacy.utils.BitmapUtils;
import com.mercadolibre.android.ui.legacy.widgets.viewpager.indicators.CirclePageIndicator;

@Deprecated
public class MLImagePager extends RelativeLayout {
    private ProgressBar mProgressBar;
    private ViewPager mPager;
    private CirclePageIndicator mIndicator;
    private GestureDetector mGestureDetector;
    private boolean usingZoom = false;
    private boolean forceDimension = false;

    private String imageViewScaleType;

    public MLImagePager(Context context) {
        super(context);
        init();
    }

    public MLImagePager(Context context, boolean uZoom, boolean uForceDimension) {
        super(context);
        init();
        usingZoom = uZoom;
        forceDimension = uForceDimension;
    }

    public MLImagePager(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MLImage);
        usingZoom = a.getBoolean(R.styleable.MLImage_isZoomable, false);
        forceDimension = a.getBoolean(R.styleable.MLImage_forceDimension, false);
        imageViewScaleType = a.getString(R.styleable.MLImage_scaleType);
        a.recycle();

        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (forceDimension) {
            Point windowsSize = BitmapUtils.getDisplaySize(getContext());
            int width = windowsSize.x;
            int height = windowsSize.y;
            if (height > width) {
                height = (int) (width / (4f / 3f));
            } else {
                height = (int) (height * 0.5f);
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setUseZoom(boolean u) {
        usingZoom = u;
    }

    private void init() {
        if (!isInEditMode()) {
            mGestureDetector = new GestureDetector(getContext(), new SingleTapListener());
        }
        mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyle);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_IN_PARENT);
        mProgressBar.setLayoutParams(layoutParams);
        mProgressBar.setIndeterminate(true);


        if (usingZoom) {
            MLViewPager tmpPager = new MLViewPager(getContext());
            tmpPager.setPagerParent(this);
            mPager = tmpPager;
        } else {
            mPager = new ViewPager(getContext());
        }

        mPager.setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        mPager.setId(R.id.ml_view_pager_id);
        mPager.setOffscreenPageLimit(4);
        mPager.setVisibility(VISIBLE);

        mIndicator = new CirclePageIndicator(getContext());
        mIndicator.setId(R.id.circle_page_indicator);
        LayoutParams indicatorParams = new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        indicatorParams.addRule(ALIGN_PARENT_BOTTOM);
        mIndicator.setLayoutParams(indicatorParams);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getContext().getResources().getDisplayMetrics());
        mIndicator.setPadding(padding, padding, padding, padding);
        mIndicator.setVisibility(VISIBLE);

        addView(mPager);
        addView(mIndicator);
        addView(mProgressBar);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isClickable()) {
            if (mGestureDetector.onTouchEvent(ev)) {
                performClick();
                return true;
            }
        }
        return false;
    }

    public void setCurrentPicture(int pos) {
        mIndicator.setCurrentItem(pos);
        mIndicator.onPageSelected(pos);
    }

    public int getCurrentPicture() {
        return mPager.getCurrentItem();
    }

    public void setUpGallery(FragmentManager fm, String[] picturesURLs, int position) {
        setUpGallery(fm, picturesURLs, position, null);
    }

    public void setUpGallery(FragmentManager fm, String[] picturesURLs, int position, String[] backgroundColors) {
        if (backgroundColors != null) {
            setUpGalleryWithPosition(fm, picturesURLs, backgroundColors);
        } else {
            setUpGalleryWithPosition(fm, picturesURLs);
        }
        if (!noPager)
            setCurrentPicture(position);
    }

    public void release() {
        MLImagePageAdapter adapter = (MLImagePageAdapter) mPager.getAdapter();
        if (adapter == null)
            return;

        for (int i = 0; i < adapter.getCount(); i++) {
            MLImageFragment fragment = (MLImageFragment) adapter.getItem(i);
            fragment.releaseBitmap();
        }
    }

    private FragmentManager fManager;
    MLImagePageAdapter imageAdapter;

    public FragmentManager getFragmentManager() {
        return fManager;
    }

    private boolean noPager = false;

    public void setUpGalleryWithPosition(FragmentManager fm, String[] picturesURLs) {
        setUpGalleryWithPosition(fm, picturesURLs, null);
    }


    public void setUpGalleryWithPosition(FragmentManager fm, String[] picturesURLs, String[] backgroundColors) {
        fManager = fm;

        mProgressBar.setVisibility(GONE);
        if (picturesURLs != null && picturesURLs.length > 0) {
            noPager = false;

            if (picturesURLs.length > 1)
                mIndicator.setVisibility(VISIBLE);
            else
                mIndicator.setVisibility(GONE);

            if (imageAdapter == null)
                imageAdapter = new MLImagePageAdapter(fm, usingZoom, imageViewScaleType);
            imageAdapter.setURLs(picturesURLs);
            imageAdapter.setBackgroundColors(backgroundColors);

            mPager.setAdapter(imageAdapter);

            mIndicator.setViewPager(mPager);

            if (noPicImageView != null) {
                noPicImageView.setVisibility(View.GONE);
            }
        } else {
            noPager = true;

            mProgressBar.setVisibility(View.GONE);
            mPager.setVisibility(View.GONE);
            mIndicator.setVisibility(View.GONE);

            if (noPicImageView == null) {
                noPicImageView = new ImageView(getContext());
                noPicImageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1));
                noPicImageView.setImageResource(R.drawable.no_pic_p);
                noPicImageView.setScaleType(ScaleType.FIT_CENTER);
                this.addView(noPicImageView);
            } else
                noPicImageView.setVisibility(View.VISIBLE);
        }
    }

    public void showLoading() {
        if (mProgressBar != null)
            mProgressBar.setVisibility(View.VISIBLE);

        if (mPager != null)
            mPager.setVisibility(View.GONE);

        if (mIndicator != null)
            mIndicator.setVisibility(View.GONE);

        if (noPicImageView != null)
            noPicImageView.setVisibility(View.GONE);
    }

    private ImageView noPicImageView;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        int progressBarLeft = getWidth() / 2 - mProgressBar.getWidth() / 2;
        int progressBarTop = getHeight() / 2 - mProgressBar.getHeight() / 2;

        mProgressBar.layout(progressBarLeft, progressBarTop,
                progressBarLeft + mProgressBar.getWidth(), progressBarTop + mProgressBar.getHeight());
    }


    // Return true on single tap confirmed
    class SingleTapListener extends SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return true;
        }
    }

    public CirclePageIndicator getmIndicator() {
        return mIndicator;
    }

}
