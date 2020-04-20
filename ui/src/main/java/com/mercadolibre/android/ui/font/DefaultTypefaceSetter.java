package com.mercadolibre.android.ui.font;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;


/**
 * This TypefaceSetter implements Calligraphy library.
 */
public class DefaultTypefaceSetter implements TypefaceHelper.TypefaceSetter {
    @Override
    public <T extends TextView> void setTypeface(@NonNull final T view, @NonNull final Font font) {
        //DONOTHING
    }

    @Override
    public void setTypeface(@NonNull final Context context, @NonNull final Paint paint,
        @NonNull final Font font) {
        //DONOTHING
    }

    @Override
    @Nullable
    public Typeface getTypeface(@NonNull Context context, @NonNull Font font) {
        return null;
    }

}
