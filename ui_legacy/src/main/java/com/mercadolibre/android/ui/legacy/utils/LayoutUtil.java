package com.mercadolibre.android.ui.legacy.utils;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ScrollView;

import com.mercadolibre.android.ui.legacy.R;

public class LayoutUtil {
    private static final int FADE_DURATION = 250;

    public static void setStateToAllControlsRecursive(View view, boolean state) {
        if (view instanceof ViewGroup) {
            ViewGroup vgroup = (ViewGroup) view;
            int count = vgroup.getChildCount();

            for (int i = 0; i < count; i++) {
                View tmpView = vgroup.getChildAt(i);
                setStateToAllControlsRecursive(tmpView, state);
            }
            vgroup.setEnabled(state);
        } else {
            view.setEnabled(state);
        }
    }

    public static void fadeLayoutToLowAlpha(View view) {
        fadeLayoutToLowAlpha(view, 0.15f);
    }

    public static void fadeLayoutToLowAlpha(View view, float lowAlpha) {
        Animation anim = new AlphaAnimation(1f, lowAlpha);
        anim.setDuration(FADE_DURATION);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

    public static void fadeLayoutToFullAlpha(View view) {
        Animation anim = new AlphaAnimation(0.15f, 1f);
        anim.setDuration(FADE_DURATION);
        anim.setFillAfter(true);
        view.startAnimation(anim);
    }

    public static void scrollToView(ScrollView sv, View v) {
        sv.scrollTo(0, v.getTop());
    }

    public static void focusAndScrollToView(ScrollView sv, View v) {
        v.requestFocus();
        LayoutUtil.scrollToView(sv, v);
    }

    public static StateListDrawable getBackgroundDrawable(Resources res, int color) {
        ColorDrawable pressed = new ColorDrawable(res.getColor(R.color.background_color));

        StateListDrawable drawable = new StateListDrawable();
        drawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        drawable.addState(new int[]{android.R.attr.state_focused}, pressed);
        drawable.addState(new int[]{}, new ColorDrawable(color));

        return drawable;
    }

}
