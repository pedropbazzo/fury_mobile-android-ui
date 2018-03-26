package com.mercadolibre.android.ui.legacy.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public abstract class DimensionUtils {

    /**
     * Converts the given {@code px} size to a {@code sp} size.
     * <p>
     * This code snippet was taken from <a href="http://stackoverflow.com/a/9219417/1898043">http://stackoverflow.com/a/9219417/1898043</a>
     *
     * @param context The context to get valid display metrics from runtime.
     * @param px      The size to convert.
     * @return The same size given in {@code px} but using {@code sp} unit.
     */
    public static float px2sp(Context context, float px) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return px / scaledDensity;
    }

    /**
     * Converts the given {@code pd} size to a {@code px} size.
     * <p>
     * This code snippet was taken from <a href="https://github.com/lawloretienne/QuickReturn/blob/master/library/src/main/java/com/etiennelawlor/quickreturn/library/utils/QuickReturnUtils.java">https://github.com/lawloretienne/QuickReturn/blob/master/library/src/main/java/com/etiennelawlor/quickreturn/library/utils/QuickReturnUtils.java</a>
     *
     * @param context The context to get a valid {@link android.view.WindowManager}.
     * @param dp      The size to convert.
     * @return The same size given in {@code dp} but using {@code px} unit.
     */
    public static int dp2px(Context context, int dp) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getMetrics(displaymetrics);

        return (int) (dp * displaymetrics.density + 0.5f);
    }
}
