package com.mercadolibre.android.ui.legacy.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import android.view.Display;
import android.view.MenuItem;
import android.view.WindowManager;

import com.mercadolibre.android.ui.legacy.R;

public class BitmapUtils {

    /**
     * Returns the current window display size as a Point.
     * Point.x will have the width and point.y will have its height
     *
     * @param context to get boundaries information
     * @return point instance with the display size
     */
    public static Point getDisplaySize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    /**
     * Returns the drawable associated with the resource @resId, with the color specified in @colorFilter.
     *
     * @param ctx         current context.
     * @param resId       resource (bitmap) to use. This cannot be null
     * @param colorFilter color to be used in the bitmap.
     * @return drawable with the resource and color filter.
     */
    public static Drawable getTintedResource(Context ctx, Integer resId, Integer colorFilter) {
        if (resId == null) {
            return null;
        }
        Drawable d = ctx.getResources().getDrawable(resId).mutate();
        return getTintedDrawable(ctx, d, colorFilter);
    }

    /**
     * Returns the drawable associated with the resource @resId, with the color specified in @colorFilter.
     *
     * @param ctx                 context
     * @param d                   drawable to apply the tint to.
     * @param colorFilterResource color to be used in the bitmap.
     * @return drawable with the resource and color filter.
     */
    public static Drawable getTintedDrawable(Context ctx, Drawable d, Integer colorFilterResource) {
        if (d == null) {
            return null;
        }
        if (colorFilterResource != null) {
            d.setColorFilter(ctx.getResources().getColor(colorFilterResource), Mode.SRC_IN);
        }
        return d;
    }

    public static final void tintMenuIconAsGrey(Context ctx, MenuItem item) {
        if (ctx != null && item != null) {
            Drawable drawable = item.getIcon();
            item.setIcon(BitmapUtils.getTintedDrawable(ctx, drawable, R.color.icons_grey));
        }
    }

    public static final void tintMenuIcon(Context ctx, MenuItem item, int colorResourceId) {
        if (ctx != null && item != null) {
            Drawable drawable = item.getIcon();
            item.setIcon(getTintedDrawable(ctx, drawable, colorResourceId));
        }
    }

    /**
     * Copied from <a href="https://github.com/mercadolibre/mobile-android/blob/develop/Mercadolibre/src/main/java/com/mercadolibre/util/MercadolibreUtils.java">MercadoLibreUtils.java</a>
     * from the application's main repository on 8/4/15.
     *
     * @param context            The execution context.
     * @param drawableResourceId The drawable resource ID to use as base drawable.
     * @param text               The text to append to the given drawable.
     * @return A {@link Bitmap} object containing both the {@code drawableResourceId} and the {@code text}.
     */
    public static Bitmap drawTextToBitmap(Context context, @DrawableRes int drawableResourceId, String text) {
        Resources resources = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, drawableResourceId);

        if (text != null) {
            // Set default bitmap config if none
            Bitmap.Config bitmapConfig = bitmap.getConfig();
            if (bitmapConfig == null) {
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
            }

            // Resource bitmaps are immutable, so we need to convert it to mutable one
            bitmap = bitmap.copy(bitmapConfig, true);

            // new antialiased Paint
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

            paint.setColor(Color.rgb(255, 255, 255));

            // Text size in pixels
            paint.setTextSize((int) (12 * resources.getDisplayMetrics().density));

            // Draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width()) / 2;
            int y = (bitmap.getHeight() + bounds.height()) / 2;

            new Canvas(bitmap).drawText(text, x, y, paint);
        }

        return bitmap;
    }
}
