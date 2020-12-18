package com.mercadolibre.android.ui.widgets;

import android.os.Build;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.core.widget.NestedScrollView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.mercadolibre.android.ui.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class FullScreenModalTest {

    private FullScreenModal fullScreenModal;
    private View root;

    @Before
    public void setUp() {
        FragmentActivity activity = Robolectric.setupActivity(FragmentActivity.class);
        fullScreenModal = new DummyFullScreenModal();
        fullScreenModal.show(activity.getSupportFragmentManager(), null);
        root = fullScreenModal.getView();
    }

    @Test
    public void testNotNull() {
        assertNotNull(fullScreenModal);
        assertNotNull(root);
    }

    @Test
    public void testTitle() {
        assertEquals(DummyMeliDialog.TITLE, fullScreenModal.getTitle());
    }

    @Test
    public void testSecondaryExit() {
        final Button secondaryExit = root.findViewById(R.id.ui_fullscreenmodal_secondary_exit_button);
        assertNotNull(secondaryExit);
        assertEquals(View.VISIBLE, secondaryExit.getVisibility());
        assertEquals(DummyMeliDialog.SECONDARY_EXIT, secondaryExit.getText().toString());
    }

    @Test
    public void testContentViewHierarchy() {
        final ViewGroup contentContainer = root.findViewById(R.id.ui_fullscreenmodal_parent_container);
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

}
