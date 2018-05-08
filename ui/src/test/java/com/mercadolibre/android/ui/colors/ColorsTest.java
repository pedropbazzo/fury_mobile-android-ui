package com.mercadolibre.android.ui.colors;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import com.mercadolibre.android.ui.R;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;

/**
 * Color tests.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class ColorsTest {

    @Test
    public void testColorPalette() {
        assertColors(R.color.ui_meli_yellow, "#FFDB15");
        assertColors(R.color.ui_meli_light_yellow, "#FFEA78");
        assertColors(R.color.ui_meli_black, "#333333");
        assertColors(R.color.ui_meli_dark_grey, "#666666");
        assertColors(R.color.ui_meli_grey, "#999999");
        assertColors(R.color.ui_meli_mid_grey, "#CCCCCC");
        assertColors(R.color.ui_meli_light_grey, "#EEEEEE");
        assertColors(R.color.ui_meli_white, "#FFFFFF");
        assertColors(R.color.ui_meli_blue, "#3483FA");
        assertColors(R.color.ui_meli_green, "#39B54A");
        assertColors(R.color.ui_meli_red, "#F04449");
        assertColors(R.color.ui_meli_orange, "#FBAB60");
    }

    @Test
    public void testContextualizedColors() {
        assertEquals(ContextCompat.getColor(RuntimeEnvironment.application, R.color.ui_meli_background),
                     ContextCompat.getColor(RuntimeEnvironment.application, R.color.ui_meli_light_grey));
        assertEquals(ContextCompat.getColor(RuntimeEnvironment.application, R.color.ui_meli_success), ContextCompat.getColor(RuntimeEnvironment.application, R.color.ui_meli_green));
        assertEquals(ContextCompat.getColor(RuntimeEnvironment.application, R.color.ui_meli_error), ContextCompat.getColor(RuntimeEnvironment.application, R.color.ui_meli_red));
        assertEquals(ContextCompat.getColor(RuntimeEnvironment.application, R.color.ui_meli_warning), ContextCompat.getColor(RuntimeEnvironment.application, R.color.ui_meli_orange));
    }

    private void assertColors(@ColorRes final int colorRes, final String colorString) {
        assertEquals(ContextCompat.getColor(RuntimeEnvironment.application, colorRes), Color.parseColor(colorString));
    }
}
