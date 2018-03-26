package com.mercadolibre.android.ui.example.ui.fonts;

import com.mercadolibre.android.ui.font.Font;


import java.util.EnumMap;

/**
 * This class is used to initialize the fonts on the app.
 * If not set defatults fonts will be used with no bold, semibold, etc.
 */

public final class FontsInitializer {

    private static final String BOLD = "Roboto-Bold.ttf";
    private static final String BLACK = "Roboto-Black.ttf";
    private static final String EXTRA_BOLD = "RobotoCondensed-Bold.ttf";
    private static final String LIGHT = "Roboto-Light.ttf";
    private static final String MEDIUM = "Roboto-Medium.ttf";
    private static final String REGULAR = "Roboto-Regular.ttf";
    private static final String SEMI_BOLD = "Roboto-Medium.ttf";
    private static final String THIN = "Roboto-Thin.ttf";

    private FontsInitializer(){
        //DO NOTHING
    }

    public static void initFonts(){
        final EnumMap<Font, String> enumMap = new EnumMap<>(Font.class);
        enumMap.put(Font.BOLD,BOLD);
        enumMap.put(Font.BLACK,BLACK);
        enumMap.put(Font.EXTRA_BOLD,EXTRA_BOLD);
        enumMap.put(Font.LIGHT,LIGHT);
        enumMap.put(Font.MEDIUM,MEDIUM);
        enumMap.put(Font.REGULAR,REGULAR);
        enumMap.put(Font.SEMI_BOLD,SEMI_BOLD);
        enumMap.put(Font.THIN,THIN);

        Font.FontConfig.setFonts(enumMap);
    }
}
