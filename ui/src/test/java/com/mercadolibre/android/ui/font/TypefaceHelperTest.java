package com.mercadolibre.android.ui.font;

import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class TypefaceHelperTest {

    private TypefaceHelper.TypefaceSetter typefaceSetter = mock(TypefaceHelper.TypefaceSetter.class);
    private Typeface expectedTypeface = mock(Typeface.class);
    private ResourcesCompat.FontCallback fontCallback = mock(ResourcesCompat.FontCallback.class);
    private Typeface typeface;

    @Test
    public void testGetFontTypeface() {
        givenATypeface(Font.LIGHT);
        givenACustomTypefaceSetter();

        whenGettingTypefaceSync(Font.LIGHT);

        thenTypefaceIsReturned();
    }

    @Test
    public void testGetTypefaceAsync() {
        givenATypeface(Font.BLACK);
        givenACustomTypefaceSetter();

        whenGettingTypefaceAsync(Font.BLACK);

        thenTypefaceIsReturnedAsync(fontCallback);
    }

    private void givenACustomTypefaceSetter() {
        TypefaceHelper.attachTypefaceSetter(typefaceSetter);
    }

    private void whenGettingTypefaceAsync(Font font) {
        TypefaceHelper.getTypeface(RuntimeEnvironment.application, font, fontCallback);
    }

    private void whenGettingTypefaceSync(Font font) {
        typeface = TypefaceHelper.geyFontTypeface(RuntimeEnvironment.application, font);
    }

    private void thenTypefaceIsReturnedAsync(ResourcesCompat.FontCallback fontCallback) {
        verify(fontCallback, only()).onFontRetrieved(expectedTypeface);
    }

    private void thenTypefaceIsReturned() {
        assertEquals(expectedTypeface, typeface);
    }

    private void givenATypeface(Font font) {
        when(typefaceSetter.getTypeface(RuntimeEnvironment.application, font)).thenReturn(expectedTypeface);
    }
}
