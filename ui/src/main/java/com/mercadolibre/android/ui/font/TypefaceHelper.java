package com.mercadolibre.android.ui.font;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;

/**
 * This class is used as a wrapper for our custom font.
 * If you code create a View that supports typeface you should call one of this methods.
 */
public final class TypefaceHelper {

    private TypefaceHelper() {
        //Do nothing, this class should not be instantiated
    }

    /**
     * Attach a typeface setter to this helper class
     * @param typefaceSetter field
     */
    public static void attachTypefaceSetter(@NonNull final TypefaceSetter typefaceSetter) {
        com.mercadolibre.android.andesui.font.TypefaceHelper.Companion.attachTypefaceSetter(typefaceSetter);
    }

    /**
     * Sets the typeface to the given {@link T}
     * @param <T>   A generic for the textview
     * @param view  The view to which apply the font
     * @param font  The {@link Font} the text should have
     */
    public static <T extends TextView> void setTypeface(@NonNull final T view, @NonNull final Font font) {
        com.mercadolibre.android.andesui.font.TypefaceHelper.Companion.setTypeface(
                view, com.mercadolibre.android.andesui.font.Font.valueOf(font.name())
        );
    }

    /**
     * Sets the typeface to the given {@link Paint}
     *
     * @param context A context to obtain the font
     * @param paint   The paint to which apply the font
     * @param font    The {@link Font} the text should have
     */
    public static void setTypeface(@NonNull final Context context, @NonNull final Paint paint, @NonNull final Font font) {
        com.mercadolibre.android.andesui.font.TypefaceHelper.Companion.setTypeface(
                context, paint, com.mercadolibre.android.andesui.font.Font.valueOf(font.name())
        );
    }

    /**
     * Get a typeface associated to the font passed. The typeface will be sent through the
     * font callback passed as param
     *
     * @param context to use
     * @param font to retrieve its typeface
     * @param fontCallback to call when the typeface is retrieved
     *
     * @deprecated use TypefaceHelper{@link #getFontTypeface(Context, Font)} instead
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Deprecated
    public static void getTypeface(@NonNull final Context context, @NonNull final Font font, @NonNull final ResourcesCompat.FontCallback fontCallback) {
        com.mercadolibre.android.andesui.font.TypefaceHelper.Companion.getTypeface(
                context, com.mercadolibre.android.andesui.font.Font.valueOf(font.name()), fontCallback
        );
    }

    /**
     * Get a typeface associated to the font passed.

     * @param context to use
     * @param font to use
     * @return associated typeface
     */
    @Nullable
    public static Typeface getFontTypeface(@NonNull final Context context, @NonNull Font font) {
        return com.mercadolibre.android.andesui.font.TypefaceHelper.Companion.getFontTypeface(
                context, com.mercadolibre.android.andesui.font.Font.valueOf(font.name())
        );
    }

    /**
     * Setter for typeface
     */
    public interface TypefaceSetter extends com.mercadolibre.android.andesui.font.TypefaceHelper.TypefaceSetter {}

}
