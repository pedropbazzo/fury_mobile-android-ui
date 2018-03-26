package com.mercadolibre.android.ui.font;

import com.mercadolibre.android.testing.AbstractRobolectricTest;

import org.junit.Before;
import org.junit.Test;

import java.util.EnumMap;

import static org.junit.Assert.assertEquals;

public class FontTest extends AbstractRobolectricTest {

    @Before
    public void initFonts(){
        final EnumMap<Font, String> fontsHashMap = new EnumMap<>(Font.class);
        fontsHashMap.put(Font.BOLD,"Roboto-Bold.ttf");
        fontsHashMap.put(Font.BLACK,"Roboto-Black.ttf");
        fontsHashMap.put(Font.EXTRA_BOLD,"RobotoCondensed-Bold.ttf");
        fontsHashMap.put(Font.LIGHT,"Roboto-Light.ttf");
        fontsHashMap.put(Font.MEDIUM,"Roboto-Medium.ttf");
        fontsHashMap.put(Font.REGULAR,"Roboto-Regular.ttf");
        fontsHashMap.put(Font.SEMI_BOLD,"Roboto-Medium.ttf");
        fontsHashMap.put(Font.THIN,"Roboto-Thin.ttf");

        Font.FontConfig.setFonts(fontsHashMap);
    }
    @Test
    public void testFontType_fontName() throws Exception {
        assertEquals("Roboto-Black.ttf", Font.BLACK.getFontName());
        assertEquals("Roboto-Bold.ttf", Font.BOLD.getFontName());
        assertEquals("RobotoCondensed-Bold.ttf", Font.EXTRA_BOLD.getFontName());
        assertEquals("Roboto-Light.ttf", Font.LIGHT.getFontName());
        assertEquals("Roboto-Regular.ttf", Font.REGULAR.getFontName());
        assertEquals("Roboto-Medium.ttf", Font.SEMI_BOLD.getFontName());
        assertEquals("Roboto-Thin.ttf", Font.THIN.getFontName());
        assertEquals("Roboto-Medium.ttf", Font.MEDIUM.getFontName());
    }

    @Test
    public void testFontType_fontPath() throws Exception {
        assertEquals("Roboto-Black.ttf", Font.BLACK.getFontPath());
        assertEquals("Roboto-Bold.ttf", Font.BOLD.getFontPath());
        assertEquals("RobotoCondensed-Bold.ttf", Font.EXTRA_BOLD.getFontPath());
        assertEquals("Roboto-Light.ttf", Font.LIGHT.getFontPath());
        assertEquals("Roboto-Regular.ttf", Font.REGULAR.getFontPath());
        assertEquals("Roboto-Medium.ttf", Font.SEMI_BOLD.getFontPath());
        assertEquals("Roboto-Thin.ttf", Font.THIN.getFontPath());
        assertEquals("Roboto-Medium.ttf", Font.MEDIUM.getFontPath());
    }

}
