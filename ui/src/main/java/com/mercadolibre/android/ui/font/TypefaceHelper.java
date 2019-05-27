package com.mercadolibre.android.ui.font;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.provider.FontsContractCompat;
import android.widget.Switch;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * This class is used as a wrapper for our custom font.
 * If you code create a View that supports typeface you should call one of this methods.
 */
public final class TypefaceHelper {

    @NonNull
    private static TypefaceSetter typefaceSetter;

    static {
        typefaceSetter = new TypefaceSetter() {
            @Override
            public <T extends TextView> void setTypeface(@NonNull final T view, @NonNull final Font font) {
                final Typeface typeface = createTypeface(view.getContext(), font);
                view.setTypeface(typeface);

                if (view instanceof Switch) {
                    ((Switch) view).setSwitchTypeface(typeface);
                }
            }

            @Override
            public void setTypeface(@NonNull final Context context, @NonNull final Paint paint, @NonNull final Font font) {
                final Typeface typeface = createTypeface(context, font);
                paint.setTypeface(typeface);
            }

            @Override
            @SuppressWarnings("PMD.LinguisticNaming")
            public void getTypeface(@NonNull final Context context, @NonNull final Font font, @NonNull final ResourcesCompat.FontCallback fontCallback) {
                final Typeface typeface = createTypeface(context, font);

                if (typeface == null) {
                    fontCallback.onFontRetrievalFailed(FontsContractCompat.FontRequestCallback.FAIL_REASON_FONT_NOT_FOUND);
                } else {
                    fontCallback.onFontRetrieved(typeface);
                }
            }

            @Nullable
            private Typeface createTypeface(@NonNull Context context, @Nullable Font font) {
                if (font == null) {
                    return null;
                }
                return TypefaceUtils.load(context.getAssets(), font.getFontPath());
            }
        };
    }

    private TypefaceHelper() {
        //Do nothing, this class should not be instantiated
    }

    /**
     * Attach a typeface setter to this helper class
     * @param typefaceSetter field
     */
    public static void attachTypefaceSetter(@NonNull final TypefaceSetter typefaceSetter) {
        TypefaceHelper.typefaceSetter = typefaceSetter;
    }

    /**
     * Sets the typeface to the given {@link T}
     * @param <T>   A generic for the textview
     * @param view  The view to which apply the font
     * @param font  The {@link Font} the text should have
     */
    public static <T extends TextView> void setTypeface(@NonNull final T view, @NonNull final Font font) {
        typefaceSetter.setTypeface(view, font);
    }

    /**
     * Sets the typeface to the given {@link Paint}
     *
     * @param context A context to obtain the font
     * @param paint   The paint to which apply the font
     * @param font    The {@link Font} the text should have
     */
    public static void setTypeface(@NonNull final Context context, @NonNull final Paint paint, @NonNull final Font font) {
        typefaceSetter.setTypeface(context, paint, font);
    }

    /**
     * Get a typeface associated to the font passed. The typeface will be sent through the
     * font callback passed as param
     *
     * @param context to use
     * @param font to retrieve its typeface
     * @param fontCallback to call when the typeface is retrieved
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    public static void getTypeface(@NonNull final Context context, @NonNull final Font font, @NonNull final ResourcesCompat.FontCallback fontCallback) {
        typefaceSetter.getTypeface(context, font, fontCallback);
    }

    /**
     * Setter for typeface
     */
    public interface TypefaceSetter {

        /**
         * Set a typeface to a view
         * @param view to set the typeface to
         * @param font to set
         * @param <T> extends TextView
         */
        <T extends TextView> void setTypeface(@NonNull final T view, @NonNull final Font font);

        /**
         * Set a typeface to the paint
         * @param context to use
         * @param paint to set the typeface
         * @param font to set
         */
        void setTypeface(@NonNull final Context context, @NonNull final Paint paint, @NonNull final Font font);

        /**
         * Get a typeface associated to the font passed. The typeface will be sent through the
         * font callback passed as param
         *
         * @param context to use
         * @param font to retrieve its typeface
         * @param fontCallback to call when the typeface is retrieved
         */
        @SuppressWarnings("PMD.LinguisticNaming")
        void getTypeface(@NonNull final Context context, @NonNull final Font font, @NonNull final ResourcesCompat.FontCallback fontCallback);

    }

}
