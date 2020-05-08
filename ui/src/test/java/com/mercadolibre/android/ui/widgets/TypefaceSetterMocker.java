package com.mercadolibre.android.ui.widgets;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.TextView;
import com.mercadolibre.android.ui.font.Font;
import com.mercadolibre.android.ui.font.TypefaceHelper;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class TypefaceSetterMocker implements TypefaceHelper.TypefaceSetter {
    @Mock
    Typeface typefaceBlack;
    @Mock
    Typeface typefaceBold;
    @Mock
    Typeface typefaceExtraBold;
    @Mock
    Typeface typefaceLight;
    @Mock
    Typeface typefaceRegular;
    @Mock
    Typeface typefaceSemiBold;
    @Mock
    Typeface typefaceMedium;
    @Mock
    Typeface typefaceThin;

    static TypefaceSetterMocker init() {
        TypefaceSetterMocker mocker = new TypefaceSetterMocker();
        TypefaceHelper.attachTypefaceSetter(mocker);
        return mocker;
    }

    private TypefaceSetterMocker() {
        MockitoAnnotations.initMocks(this);
    }

    @Override
    public <T extends TextView> void setTypeface(@NonNull T view, @NonNull Font font) {
        view.setTypeface(getTypeface(font));
    }

    @Override
    public void setTypeface(@NonNull Context context, @NonNull Paint paint, @NonNull Font font) {
        paint.setTypeface(getTypeface(font));
    }

    @Nullable
    @Override
    public Typeface getTypeface(@NonNull Context context, @NonNull Font font) {
        return getTypeface(font);
    }

    private Typeface getTypeface(Font font) {
        switch (font) {
        case BOLD:
            return typefaceBold;
        case BLACK:
            return typefaceBlack;
        case EXTRA_BOLD:
            return typefaceExtraBold;
        case LIGHT:
            return typefaceLight;
        case REGULAR:
            return typefaceRegular;
        case SEMI_BOLD:
            return typefaceSemiBold;
        case MEDIUM:
            return typefaceMedium;
        case THIN:
            return typefaceThin;
        default:
            return null;
        }
    }
}