package com.mercadolibre.android.ui.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;

public final class UIUtil {

    private UIUtil() {
        //This class should not be instanced
    }

    /**
     * Gets the screen size.
     *
     * @param context the app context.
     * @return the size.
     */
    public static Point getScreenSize(@NonNull final Context context) {
        final WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        final Display display = wm.getDefaultDisplay();

        final Point size = new Point();
        display.getSize(size);
        return size;
    }

    /**
     * Gets the Status Bar height
     *
     * @param context a context to obtain resources from
     * @return the status bar height
     */
    public static int getStatusBarHeight(@NonNull final Context context) {
        int statusBarHeight = 0;
        final int resource = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resource > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resource);
        }
        return statusBarHeight;
    }

    /**
     * Gets the Popup height without the StatusBar height
     *
     * @param context a context to obtain the window manager
     * @return the popup height
     */
    public static int getPopupHeight(@NonNull final Context context) {
        // There's a bug in API 21 where a popup window will show the status bar as black, no matter what the activity specifies
        // This is fixed in latter versions. Therefore, we create the popup with the height of the status bar substracted
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {

            final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            final Display display = windowManager.getDefaultDisplay();
            final Point size = new Point();
            display.getSize(size);

            final int statusBarHeight = getStatusBarHeight(context);

            return size.y - statusBarHeight;
        }
        return ViewGroup.LayoutParams.MATCH_PARENT;
    }
}
