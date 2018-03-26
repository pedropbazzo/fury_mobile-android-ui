package com.mercadolibre.android.ui.legacy.widgets.image;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

@Deprecated
class MLImagePageAdapter extends FragmentPagerAdapter {
    private boolean usingZoom = false;
    private final String imageViewScaleType;
    private String[] mUrls;
    private String[] backgroundColors;

    public MLImagePageAdapter(final FragmentManager fm, final boolean z,
                              final String imageViewScaleType) {
        super(fm);
        usingZoom = z;
        this.imageViewScaleType = imageViewScaleType;
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setURLs(String[] u) {
        mUrls = u;
        this.notifyDataSetChanged();
    }

    public void setBackgroundColors(String[] u) {
        backgroundColors = u;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mUrls.length;
    }

    @Override
    public Fragment getItem(int position) {

        MLImageFragment fragment;
        if (backgroundColors != null) {
            fragment = MLImageFragment.newInstance(mUrls[position], usingZoom,
                    imageViewScaleType, backgroundColors[position]);
        } else {
            fragment = MLImageFragment.newInstance(mUrls[position], imageViewScaleType, usingZoom);
        }
        fragment.setRetainInstance(true);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        FragmentManager manager = ((Fragment) object).getFragmentManager();
        if (manager == null)
            return;
        FragmentTransaction trans = manager.beginTransaction();
        trans.remove((Fragment) object);
        trans.commitAllowingStateLoss();
        super.destroyItem(container, position, object);
    }
}