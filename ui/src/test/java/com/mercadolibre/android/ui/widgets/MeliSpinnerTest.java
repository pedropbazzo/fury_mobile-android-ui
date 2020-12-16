package com.mercadolibre.android.ui.widgets;

import android.os.Build;
import androidx.appcompat.view.ContextThemeWrapper;
import android.view.View;
import android.widget.TextView;

import com.mercadolibre.android.ui.R;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;

/**
 * Test class for {@link MeliSpinner}.
 *
 * @since 3/5/16
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class MeliSpinnerTest {

    private static final String TEXT = "TEXT";

    private MeliSpinner spinner;

    @Before
    public void setUp() {
        spinner = new MeliSpinnerTestImpl(new ContextThemeWrapper(RuntimeEnvironment.application, R.style.Theme_MLTheme));
    }

    @Test
    public void testDefaultInitialization() {
        spinner.setSpinnerMode(MeliSpinner.SpinnerMode.BIG_YELLOW);
        final TextView textView = getTextView(spinner);
        Assert.assertNotNull(textView);
        Assert.assertEquals(View.GONE, textView.getVisibility());
        final LoadingSpinner loadingSpinner = getLoadingSpinner(spinner);
        Assert.assertNotNull(loadingSpinner);
        Assert.assertEquals(View.VISIBLE, loadingSpinner.getVisibility());
    }

    @Test
    public void testSpinnerText() {
        final TextView textView = getTextView(spinner);
        Assert.assertNotNull(textView);
        Assert.assertEquals(View.GONE, textView.getVisibility());
        spinner.setText(TEXT);
        // Now text should be visible.
        Assert.assertEquals(View.VISIBLE, textView.getVisibility());
        Assert.assertEquals(TEXT, textView.getText().toString());
        spinner.setSpinnerMode(MeliSpinner.SpinnerMode.SMALL_WHITE);
        // Now text shouldn't be visible.
        Assert.assertEquals(View.GONE, textView.getVisibility());
    }

    @Test
    public void testSpinnerMode() {
        final TextView textView = getTextView(spinner);
        Assert.assertNotNull(textView);
        Assert.assertEquals(View.GONE, textView.getVisibility());
        spinner.setText(TEXT);

        spinner.setSpinnerMode(MeliSpinner.SpinnerMode.BIG_YELLOW);
        Assert.assertEquals(View.VISIBLE, textView.getVisibility());

        spinner.setSpinnerMode(MeliSpinner.SpinnerMode.SMALL_WHITE);
        Assert.assertEquals(View.GONE, textView.getVisibility());

        spinner.setSpinnerMode(MeliSpinner.SpinnerMode.BIG_WHITE);
        Assert.assertEquals(View.VISIBLE, textView.getVisibility());

        spinner.setSpinnerMode(MeliSpinner.SpinnerMode.SMALL_BLUE);
        Assert.assertEquals(View.GONE, textView.getVisibility());
    }

    private TextView getTextView(final MeliSpinner spinner) {
        return ReflectionHelpers.getField(spinner, "textView");
    }

    private LoadingSpinner getLoadingSpinner(final MeliSpinner spinner) {
        return ReflectionHelpers.getField(spinner, "spinner");
    }
}
