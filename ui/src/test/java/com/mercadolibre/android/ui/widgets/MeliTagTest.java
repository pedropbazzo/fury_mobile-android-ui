package com.mercadolibre.android.ui.widgets;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.soloader.SoLoader;
import com.mercadolibre.android.ui.R;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Test class for {@link MeliTag}.
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class MeliTagTest {

    private MeliTag meliTag;
    private Context context;
    private ConstraintLayout container;
    private TextView textView;
    private ImageView closeButton;

    @BeforeClass
    public static void before() {
        SoLoader.setInTestMode();
    }

    @Before
    public void setUp() {
        context = new ContextWrapper(RuntimeEnvironment.application);
        context.setTheme(R.style.Theme_MLTheme);
        Fresco.initialize(context);
        meliTag = new MeliTag(context);
        container = meliTag.getContainer();
        textView = meliTag.getTextView();
        closeButton = meliTag.getCloseButton();
    }

    @Test
    public void testNotNull() {
        assertNotNull(meliTag);
    }

    @Test
    public void testDefaultConfig() {
        assertNull(meliTag.getText());

        assertTrue(meliTag.isCloseButtonShown());

        final int currentTextColor = textView.getTextColors().getColorForState(textView.getDrawableState(), 0);
        assertEquals(context.getResources().getColor(R.color.ui_meli_black), currentTextColor);

        final ColorStateList currentContainerColorStateList = ReflectionHelpers.getField(meliTag, "containerColor");
        final int currentContainerColor = currentContainerColorStateList.getColorForState(container.getDrawableState(), 0);
        assertEquals(context.getResources().getColor(R.color.ui_meli_white), currentContainerColor);
    }

    @Test
    public void testSetText() {
        assertTrue(textView.getText().toString().isEmpty());
        final String testText = "testText";
        textView.setText(testText);
        assertFalse(textView.getText().toString().isEmpty());
        assertEquals(textView.getText(), testText);
    }

    @Test
    public void testSetTextColor_withColorStateList_shouldSetTextColor() {
        int currentTextColor = textView.getTextColors()
                .getColorForState(textView.getDrawableState(), 0);
        assertNotEquals(context.getResources().getColor(R.color.ui_meli_yellow), currentTextColor);

        meliTag.setTextColor(ContextCompat.getColorStateList(context, R.color.ui_meli_yellow));

        currentTextColor = textView.getTextColors()
                .getColorForState(textView.getDrawableState(), 0);
        assertEquals(context.getResources().getColor(R.color.ui_meli_yellow), currentTextColor);
    }

    @Test
    public void testSetTextColor_withHexColor_shouldSetTextColor() {
        final int color = Color.parseColor("#AAAAAA");

        int currentTextColor = textView.getTextColors()
                .getColorForState(textView.getDrawableState(), 0);
        assertNotEquals(color, currentTextColor);

        meliTag.setTextColor(color);

        currentTextColor = textView.getTextColors()
                .getColorForState(textView.getDrawableState(), 0);
        assertEquals(color, currentTextColor);
    }

    @Test
    public void testSetBackgroundColor() {
        final int color = Color.parseColor("#00FF00");

        meliTag.setBackgroundColor(color);

        ColorStateList colorStateList = ReflectionHelpers.getField(meliTag, "containerColor");
        int actualColor = colorStateList.getColorForState(container.getBackground().getState(), 0);
        assertEquals(actualColor, color);
    }

    @Test
    public void testSetCloseButtonShown() {
        assertEquals(closeButton.getVisibility(), View.VISIBLE);

        meliTag.setCloseButtonShown(false);
        assertEquals(closeButton.getVisibility(), View.GONE);

        meliTag.setCloseButtonShown(true);
        assertEquals(closeButton.getVisibility(), View.VISIBLE);
    }

}
