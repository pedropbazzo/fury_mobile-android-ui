package com.mercadolibre.android.ui.widgets;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercadolibre.android.testing.AbstractRobolectricTest;
import com.mercadolibre.android.ui.R;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ErrorViewTest extends AbstractRobolectricTest {

    private static final String TEST_TEXT = "testText";

    private ErrorView errorView;
    private ImageView image;
    private TextView title;
    private TextView subtitle;
    private Button button;

    @Before
    public void setUp() {

        errorView = spy(new ErrorView(RuntimeEnvironment.application));

        image = errorView.findViewById(R.id.ui_error_view_image);
        title = errorView.findViewById(R.id.ui_error_view_title);
        subtitle = errorView.findViewById(R.id.ui_error_view_subtitle);
        button = errorView.findViewById(R.id.ui_error_view_button);
    }

    @Test
    public void testCustomImage() {
        assertEquals("The ErrorView image should be gone", View.GONE, image.getVisibility());

        errorView.setImage(0);
        assertEquals("The ErrorView image should be gone", View.GONE, image.getVisibility());

        errorView.setImage(R.drawable.ui_button_style_primary);
        assertEquals("The ErrorView image should be visible", View.VISIBLE, image.getVisibility());
    }

    @Test
    public void testCustomTitle() {
        assertEquals("The ErrorView title should be gone", View.GONE, title.getVisibility());

        errorView.setTitle(null);
        assertEquals("The ErrorView title should be gone", View.GONE, title.getVisibility());

        errorView.setTitle(TEST_TEXT);
        assertEquals("The ErrorView title should be visible", View.VISIBLE, title.getVisibility());
    }

    @Test
    public void testCustomSubtitle() {
        assertEquals("The ErrorView subtitle should be gone", View.GONE, subtitle.getVisibility());

        errorView.setSubtitle(null);
        assertEquals("The ErrorView subtitle should be gone", View.GONE, subtitle.getVisibility());

        errorView.setSubtitle(TEST_TEXT);
        assertEquals("The ErrorView subtitle should be visible", View.VISIBLE, subtitle.getVisibility());
    }

    @Test
    public void testCustomButton() {
        assertEquals("The ErrorView button should be gone", View.GONE, button.getVisibility());

        errorView.setButton(null, null);
        assertEquals("The ErrorView button should be gone", View.GONE, button.getVisibility());

        errorView.setButton(TEST_TEXT, mock(View.OnClickListener.class));
        assertEquals("The ErrorView button should be visible", View.VISIBLE, button.getVisibility());
    }

}