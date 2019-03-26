package com.mercadolibre.android.ui.widgets;

import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mercadolibre.android.ui.R;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.support.v4.SupportFragmentTestUtil;
import org.robolectric.util.ReflectionHelpers;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class MeliFullScreenDialogTest {
    private MeliFullScreenDialog meliFullScreenDialog;

    private View root;

    @Before
    public void setUp() {
        meliFullScreenDialog = new DummyMeliFullScreenDialog();
        SupportFragmentTestUtil.startFragment(meliFullScreenDialog);
        root = getRootView(meliFullScreenDialog);
    }

    @Test
    public void testNotNull() {
        assertNotNull(meliFullScreenDialog);
        assertNotNull(getRootView(meliFullScreenDialog));
    }

    @Test
    public void testTitle() {
        assertEquals(DummyMeliDialog.TITLE, meliFullScreenDialog.getTitle());
    }

    @Test
    public void testSecondaryExit() {
        final Button secondaryExit = root.findViewById(R.id.ui_melifullscreendialog_secondary_exit_button);
        assertNotNull(secondaryExit);
        assertEquals(View.VISIBLE, secondaryExit.getVisibility());
        assertEquals(DummyMeliDialog.SECONDARY_EXIT, secondaryExit.getText().toString());
    }

    @Test
    public void testContentViewHierarchy() {
        final ViewGroup contentContainer = root.findViewById(R.id.ui_melifullscreendialog_parent_container);
        assertNotNull(contentContainer);
        // Check toolbar
        final AppBarLayout appBarLayout = (AppBarLayout) contentContainer.getChildAt(0);
        assertNotNull(appBarLayout);
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) appBarLayout.getChildAt(0);
        assertNotNull(collapsingToolbarLayout);
        final Toolbar toolbar = (Toolbar) collapsingToolbarLayout.getChildAt(0);
        assertNotNull(toolbar);

        //Check content
        final NestedScrollView nestedScrollView = (NestedScrollView) contentContainer.getChildAt(1);
        assertNotNull(nestedScrollView);
        final ViewGroup containerInScroll = (ViewGroup) nestedScrollView.getChildAt(0);
        assertNotNull(containerInScroll);
        final FrameLayout frameLayout = (FrameLayout) containerInScroll.getChildAt(0);
        assertNotNull(frameLayout);
        final View errorView = frameLayout.getChildAt(0);
        assertNotNull(errorView);
        assertEquals(View.VISIBLE, errorView.getVisibility());
        assertTrue(errorView instanceof ErrorView);
        final Button secondaryExit = (Button) containerInScroll.getChildAt(1);
        assertNotNull(secondaryExit);
        assertEquals(View.VISIBLE, secondaryExit.getVisibility());
        assertEquals(DummyMeliDialog.SECONDARY_EXIT, secondaryExit.getText().toString());
    }

    private View getRootView(final MeliFullScreenDialog meliFullScreenDialog) {
        return ReflectionHelpers.getField(meliFullScreenDialog, "root");
    }
}
