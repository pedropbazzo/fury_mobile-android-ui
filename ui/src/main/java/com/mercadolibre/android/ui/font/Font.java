package com.mercadolibre.android.ui.font;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.EnumMap;

/**
 * The font types that we support
 * Use the FontConfig to initialize all fonts
 */
public enum Font {
    BLACK,
    BOLD,
    EXTRA_BOLD,
    LIGHT,
    REGULAR,
    SEMI_BOLD,
    MEDIUM,
    THIN;

    /**
     * Getter for the fontName
     *
     * @return the fontName
     */
    @Nullable
    public String getFontName() {
        return getFont();
    }

    /**
     * Getter for the path formed with the fontName
     *
     * @return the path of the font
     */
    @Nullable
    public String getFontPath() {
        return getFont();
    }

    @Nullable
    private String getFont() {
        final EnumMap fonts = FontConfig.getFonts();
        return fonts == null ? null : fonts.get(this).toString();
    }

    public static final class FontConfig {

        private static EnumMap<Font,String> fontsMap;

        private FontConfig(){
            //DO NOTHING
        }

        /* default */ @Nullable static EnumMap<Font,String> getFonts() {
            return fontsMap;
        }

        /**
         * Sets the fonts to use
         * @param fontsMap with Font as Key and String with path to the font as value
         */
        public static void setFonts(@NonNull final EnumMap<Font,String> fontsMap) {
            FontConfig.fontsMap = fontsMap;
        }
    }
}
