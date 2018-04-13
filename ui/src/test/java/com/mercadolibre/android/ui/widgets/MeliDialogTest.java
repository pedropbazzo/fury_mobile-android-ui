package com.mercadolibre.android.ui.widgets;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mercadolibre.android.ui.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Test class for {@link MeliDialog}.
 *
 * @since 14/4/16
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class MeliDialogTest {

    private MeliDialog meliDialog;

    private View root;

    @Before
    public void setUp() {
        meliDialog = new DummyMeliDialog();
        startFragment(meliDialog);
        root = getRootView(meliDialog);
    }

    @Test
    public void testNotNull() {
        assertNotNull(meliDialog);
        assertNotNull(getRootView(meliDialog));
    }

    @Test
    public void testTitle() {
        final TextView title = root.findViewById(R.id.ui_melidialog_title);
        assertNotNull(title);
        assertEquals(View.VISIBLE, title.getVisibility());
        assertEquals(DummyMeliDialog.TITLE, title.getText().toString());
    }

    @Test
    public void testActionButton() {
        final TextView actionButton = root.findViewById(R.id.ui_melidialog_action_button);
        assertNotNull(actionButton);
        assertEquals(View.VISIBLE, actionButton.getVisibility());
        assertEquals(DummyMeliDialog.ACTION_BUTTON, actionButton.getText().toString());
    }

    @Test
    public void testSecondaryExit() {
        final Button secondaryExit = root.findViewById(R.id.ui_melidialog_secondary_exit_button);
        assertNotNull(secondaryExit);
        assertEquals(View.VISIBLE, secondaryExit.getVisibility());
        assertEquals(DummyMeliDialog.SECONDARY_EXIT, secondaryExit.getText().toString());
    }

    @Test
    public void testCloseButton() {
        final View closeButton = root.findViewById(R.id.ui_melidialog_close_button);
        assertNotNull(closeButton);
        assertEquals(View.VISIBLE, closeButton.getVisibility());
    }

    @Test
    public void testContentViewHierarchy() {
        final ViewGroup contentContainer = root.findViewById(R.id.ui_melidialog_content_container);
        assertNotNull(contentContainer);
        // Check that a ScrollView has been added.
        final ViewGroup scrollView = (ViewGroup) contentContainer.getChildAt(0);
        assertNotNull(scrollView);
        assertTrue(scrollView instanceof ScrollView);
        // Check that our view has been added.
        final View errorView = scrollView.getChildAt(0);
        assertNotNull(errorView);
        assertEquals(View.VISIBLE, errorView.getVisibility());
        assertTrue(errorView instanceof ErrorView);
    }

    private View getRootView(final MeliDialog meliDialog) {
        return ReflectionHelpers.getField(meliDialog, "root");
    }

}
