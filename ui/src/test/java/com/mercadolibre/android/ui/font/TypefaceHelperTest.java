package com.mercadolibre.android.ui.font;

import android.graphics.Typeface;
import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class TypefaceHelperTest {

    private TypefaceHelper.TypefaceSetter typefaceSetter = mock(TypefaceHelper.TypefaceSetter.class);
    private Typeface expectedTypeface = mock(Typeface.class);
    private Typeface typeface;

    @Test
    public void testGetFontTypeface() {
        givenATypeface(Font.LIGHT);
        givenACustomTypefaceSetter();

        whenGettingTypeface(Font.LIGHT);

        thenTypefaceIsReturned();
    }

    private void givenACustomTypefaceSetter() {
        TypefaceHelper.attachTypefaceSetter(typefaceSetter);
    }


    private void whenGettingTypeface(Font font) {
        typeface = TypefaceHelper.geyFontTypeface(RuntimeEnvironment.application, font);
    }

    private void thenTypefaceIsReturned() {
        assertEquals(expectedTypeface, typeface);
    }

    private void givenATypeface(Font font) {
        when(typefaceSetter.getTypeface(RuntimeEnvironment.application, font)).thenReturn(expectedTypeface);
    }
}
