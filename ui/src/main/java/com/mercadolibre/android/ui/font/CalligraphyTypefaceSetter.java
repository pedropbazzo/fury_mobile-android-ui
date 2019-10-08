package com.mercadolibre.android.ui.font;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Switch;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.TypefaceUtils;

/**
 * This TypefaceSetter implements Calligraphy library.
 */
public class CalligraphyTypefaceSetter implements TypefaceHelper.TypefaceSetter {
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
    @Nullable
    public Typeface getTypeface(@NonNull Context context, @NonNull Font font) {
        return createTypeface(context, font);
    }

    @Nullable
    private Typeface createTypeface(@NonNull Context context, @NonNull Font font) {
        return TypefaceUtils.load(context.getAssets(), font.getFontPath());
    }

}
