package com.mercadolibre.android.ui.font;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.widget.Switch;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * This class is used as a wrapper for our custom font.
 * If you code create a View that supports typeface you should call one of this methods.
 */
public final class TypefaceHelper {

    private TypefaceHelper() {
        //Do nothing, this class should not be instantiated
    }

    /**
     * Sets the typeface to the given {@link T}
     * @param <T>   A generic for the textview
     * @param view  The view to which apply the font
     * @param font  The {@link Font} the text should have
     */
    public static <T extends TextView> void setTypeface(@NonNull final T view, @NonNull final Font font) {
        final Typeface typeface = createTypeface(view.getContext(), font);
        view.setTypeface(typeface);

        if (view instanceof Switch) {
            ((Switch) view).setSwitchTypeface(typeface);
        }
    }

    /**
     * Sets the typeface to the given {@link Paint}
     *
     * @param context A context to obtain the font
     * @param paint   The paint to which apply the font
     * @param font    The {@link Font} the text should have
     */
    public static void setTypeface(@NonNull final Context context, @NonNull final Paint paint, @NonNull final Font font) {
        final Typeface typeface = createTypeface(context, font);
        paint.setTypeface(typeface);
    }

    /**
     * Crates a typeface given a {@link Font}
     * @param context   The context of execution
     * @param font  The {@link Font} that contains the path
     * @return          A Typeface from a custom font
     */
    private static Typeface createTypeface(final Context context, final Font font) {
        return TypefaceUtils.load(context.getAssets(), font.getFontPath());
    }
}
