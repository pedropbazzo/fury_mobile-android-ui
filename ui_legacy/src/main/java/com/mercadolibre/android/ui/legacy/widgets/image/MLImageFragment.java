package com.mercadolibre.android.ui.legacy.widgets.image;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import com.mercadolibre.android.ui.legacy.R;

@Deprecated
public class MLImageFragment extends Fragment {

    MLImageView mImageView;

    public static MLImageFragment newInstance(String url) {
        MLImageFragment fragment = new MLImageFragment();

        fragment.mUrl = url;

        return fragment;
    }

    public static MLImageFragment newInstance(String url, boolean useGestureImageView) {
        MLImageFragment fragment = new MLImageFragment(useGestureImageView);

        fragment.mUrl = url;

        return fragment;
    }

    public static MLImageFragment newInstance(String url, boolean useGestureImageView,
                                              String backgroundColor) {
        MLImageFragment fragment = new MLImageFragment(useGestureImageView);

        fragment.mUrl = url;
        fragment.backgroundColor = backgroundColor;

        return fragment;
    }

    public static MLImageFragment newInstance(final String url, final String imageViewScaleType,
                                              final boolean useGestureImageView) {
        final MLImageFragment fragment = new MLImageFragment(useGestureImageView);

        fragment.mUrl = url;
        fragment.imageViewScaleType = imageViewScaleType;

        return fragment;
    }

    public static MLImageFragment newInstance(final String url, final boolean useGestureImageView,
                                              final String imageViewScaleType, final String backgroundColor) {
        final MLImageFragment fragment = new MLImageFragment(useGestureImageView);

        fragment.mUrl = url;
        fragment.backgroundColor = backgroundColor;
        fragment.imageViewScaleType = imageViewScaleType;

        return fragment;
    }

    String mUrl;
    private String backgroundColor;
    private boolean useGestureImageView = false;
    private String imageViewScaleType;

    public MLImageFragment() {
    }

    @SuppressLint("ValidFragment")
    public MLImageFragment(boolean useGesture) {
        useGestureImageView = useGesture;
    }

    public void releaseBitmap() {
    }

    public ImageView getImageView() {
        return mImageView;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mUrl = savedInstanceState.getString("url");
            useGestureImageView = savedInstanceState.getBoolean("useZoom");
        }

        Log.d("MLImagePageAdapter", "onCreateView");

        if (useGestureImageView) {
            mImageView = new ZoomableDraweeView(getActivity());
        } else {
            mImageView = new CustomScaleImageView(getActivity());
            try {
                if (TextUtils.isEmpty(imageViewScaleType)
                        || ImageView.ScaleType.MATRIX == ImageView.ScaleType.valueOf(imageViewScaleType)) {
                    ((CustomScaleImageView) mImageView).setCustomScaleType(CustomScaleImageView.CustomScaleType.FIT_HEIGHT_CROP_WIDTH);
                } else {
                    mImageView.setImageScaleType(ImageView.ScaleType.valueOf(imageViewScaleType));
                }
            } catch (final IllegalArgumentException e) {
                Log.e("MLImageFragment", "Incorrect scale type", e);
                ((CustomScaleImageView) mImageView).setCustomScaleType(CustomScaleImageView.CustomScaleType.FIT_HEIGHT_CROP_WIDTH);
            }
        }

        mImageView.setId(R.id.view_pager_image_view);
        LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        mImageView.setLayoutParams(params);
        if (!TextUtils.isEmpty(backgroundColor)) {
            mImageView.setBackgroundColor(Color.parseColor(backgroundColor));
        }

        final LinearLayout layout = new LinearLayout(getActivity());
        layout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        layout.setGravity(Gravity.CENTER);
        layout.addView(mImageView);

        layout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                layout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                //avoid calling Glide with null context.
                if (getActivity() != null) {
                    mImageView.loadImage(mUrl, getActivity());
                }
            }
        });
        return layout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("url", mUrl);
        outState.putBoolean("useZoom", useGestureImageView);
        super.onSaveInstanceState(outState);
    }
}
