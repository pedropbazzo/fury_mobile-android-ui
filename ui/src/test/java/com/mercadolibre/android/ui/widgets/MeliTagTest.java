package com.mercadolibre.android.ui.widgets;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
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
    private SimpleDraweeView thumbnail;
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
        thumbnail = meliTag.getThumbnail();
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
        assertEquals(context.getResources().getColor(R.color.ui_transparent), currentContainerColor);
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
    public void testSetThumbnailShown() {
        assertEquals(thumbnail.getVisibility(), View.VISIBLE);

        meliTag.setThumbnailShown(false);
        assertEquals(thumbnail.getVisibility(), View.GONE);

        meliTag.setThumbnailShown(true);
        assertEquals(thumbnail.getVisibility(), View.VISIBLE);
    }

    @Test
    public void testSetCloseButtonShown() {
        assertEquals(closeButton.getVisibility(), View.VISIBLE);

        meliTag.setCloseButtonShown(false);
        assertEquals(closeButton.getVisibility(), View.GONE);

        meliTag.setCloseButtonShown(true);
        assertEquals(closeButton.getVisibility(), View.VISIBLE);
    }

    @Test
    public void testSetCloseButtonShown_withThumbnailVisible_textViewMarginsAreCorrect() {
        float density = context.getResources().getDisplayMetrics().density;
        int marginStart = (int) (context.getResources().getDimension(R.dimen.ui_tag_background_margin) / density);
        final int marginEndForCloseButtonShown = 0;
        final int marginEndForCloseButtonNotShown = (int) (context.getResources().getDimension(R.dimen.ui_tag_background_radius) / density);

        assertEquals(marginStart, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginStart());
        assertEquals(marginEndForCloseButtonShown, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginEnd());
        meliTag.setCloseButtonShown(false);
        assertEquals(marginStart, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginStart());
        assertEquals(marginEndForCloseButtonNotShown, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginEnd());
    }

    @Test
    public void testSetCloseButtonShown_withThumbnailNotVisible_textViewMarginsAreCorrect() {
        meliTag.setThumbnailShown(false);
        float density = context.getResources().getDisplayMetrics().density;
        int marginStart = (int) (context.getResources().getDimension(R.dimen.ui_tag_background_radius) / density);
        final int marginEndForCloseButtonShown = 0;
        final int marginEndForCloseButtonNotShown = (int) (context.getResources().getDimension(R.dimen.ui_tag_background_radius) / density);

        assertEquals(marginStart, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginStart());
        assertEquals(marginEndForCloseButtonShown, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginEnd());
        meliTag.setCloseButtonShown(false);
        assertEquals(marginStart, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginStart());
        assertEquals(marginEndForCloseButtonNotShown, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginEnd());
    }

    @Test
    public void testSetThumbnailShown_withCloseButtonVisible_textViewMarginsAreCorrect() {
        float density = context.getResources().getDisplayMetrics().density;
        int marginEnd = 0;
        final int marginStartForThumbnailShown = 8;
        final int marginStartForThumbnailNotShown = (int) (context.getResources().getDimension(R.dimen.ui_tag_background_radius) / density);

        assertEquals(marginEnd, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginEnd());
        assertEquals(marginStartForThumbnailShown, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginStart());
        meliTag.setThumbnailShown(false);
        assertEquals(marginEnd, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginEnd());
        assertEquals(marginStartForThumbnailNotShown, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginStart());
    }

    @Test
    public void testSetThumbnailShown_withCloseButtonNotVisible_textViewMarginsAreCorrect() {
        meliTag.setCloseButtonShown(false);
        float density = context.getResources().getDisplayMetrics().density;
        int marginEnd = (int) (context.getResources().getDimension(R.dimen.ui_tag_background_radius) / density);
        final int marginStartForThumbnailShown = 8;
        final int marginStartForThumbnailNotShown = (int) (context.getResources().getDimension(R.dimen.ui_tag_background_radius) / density);

        assertEquals(marginEnd, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginEnd());
        assertEquals(marginStartForThumbnailShown, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginStart());
        meliTag.setThumbnailShown(false);
        assertEquals(marginEnd, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginEnd());
        assertEquals(marginStartForThumbnailNotShown, ((ConstraintLayout.LayoutParams) textView.getLayoutParams()).getMarginStart());
    }

}
