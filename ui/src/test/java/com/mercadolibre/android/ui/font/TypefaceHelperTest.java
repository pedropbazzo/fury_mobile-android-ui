package com.mercadolibre.android.ui.font;

import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Build;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.EnumMap;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.TypefaceUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP, manifest = "AndroidManifest.xml")
public class TypefaceHelperTest {

    @Before
    public void setUp() {
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
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder().build());
    }

    @Test
    public void testSetTypeface_withTextView() {
        verifyFontCanBeSet(new TextView(RuntimeEnvironment.application), Font.BLACK);
    }

    @Test
    public void testSetTypeface_withEditText() {
        verifyFontCanBeSet(new EditText(RuntimeEnvironment.application), Font.LIGHT);
    }

    @Test
    public void testSetTypeface_withButton() {
        verifyFontCanBeSet(new Button(RuntimeEnvironment.application), Font.REGULAR);
    }

    @Test
    public void testSetTypeface_withToggleButton() {
        verifyFontCanBeSet(new ToggleButton(RuntimeEnvironment.application), Font.BOLD);
    }

    @Test
    public void testSetTypeface_withSwitch() {
        verifyFontCanBeSet(new Switch(RuntimeEnvironment.application), Font.EXTRA_BOLD);
    }

    @Test
    public void testSetTypeface_withCheckBox() {
        verifyFontCanBeSet(new CheckBox(RuntimeEnvironment.application), Font.SEMI_BOLD);
    }

    @Test
    public void testSetTypeface_withRadioButton() {
        verifyFontCanBeSet(new RadioButton(RuntimeEnvironment.application), Font.LIGHT);
    }

    @Test
    public void testSetTypeface_withPaint() {
        final Paint paint = new Paint();

        assertNull(paint.getTypeface());
        TypefaceHelper.setTypeface(RuntimeEnvironment.application, paint, Font.LIGHT);

        Typeface expected = TypefaceUtils.load(RuntimeEnvironment.application.getAssets(), Font.LIGHT.getFontPath());
        assertEquals(expected, paint.getTypeface());
    }

    private <T extends TextView> void verifyFontCanBeSet(final T view, final Font font) {
        TypefaceHelper.setTypeface(view, font);
        final Typeface expected = TypefaceUtils.load(RuntimeEnvironment.application.getAssets(), font.getFontPath());
        assertEquals(expected, view.getTypeface());
    }
}
