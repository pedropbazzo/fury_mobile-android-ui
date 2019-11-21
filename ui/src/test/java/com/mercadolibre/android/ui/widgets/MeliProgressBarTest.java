package com.mercadolibre.android.ui.widgets;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.v7.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ProgressBar;
import com.mercadolibre.android.ui.R;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link MeliProgressBar}.
 */
@RunWith(RobolectricTestRunner.class)
// TODO: remove this when migrated to Robolectric 4, as current version does not support API level 28.
@Config(sdk = Build.VERSION_CODES.O)
public class MeliProgressBarTest {

    private MeliProgressBar meliProgressBar;
    private ProgressBar progressBarMock;
    private ObjectAnimator animatorMock;

    @Before
    public void setUp() {
        progressBarMock = mock(ProgressBar.class);
        animatorMock = mock(ObjectAnimator.class);
        meliProgressBar = new MeliProgressBar(new ContextThemeWrapper(RuntimeEnvironment.application, R.style.Theme_MLTheme));
    }


    @After
    public void tearDown() {
        progressBarMock = null;
        animatorMock = null;
    }

    @Test
    public void notNull() {
        assertNotNull(meliProgressBar);
    }

    @Test
    public void defaultInitialization() {
        assertEquals("", 0, meliProgressBar.getProgress());
        assertEquals("", 0, meliProgressBar.progressBar.getProgress());
        assertEquals("", 10000, meliProgressBar.progressBar.getMax());
        assertEquals("", "", meliProgressBar.textView.getText().toString());
        assertEquals("", View.GONE, meliProgressBar.textView.getVisibility());
    }

    @Test
    public void start_withDefaultSettings() {
        meliProgressBar.progressBar = progressBarMock;
        meliProgressBar.animator = null;
        meliProgressBar.start(null);

        verify(progressBarMock, times(2)).setProgress(0);
    }

    @Test
    public void start_withDefaultSettings_andPreviousAnimator() {
        meliProgressBar.progressBar = progressBarMock;
        meliProgressBar.animator = animatorMock;
        meliProgressBar.start(null);

        verify(animatorMock).cancel();
        verify(progressBarMock, times(2)).setProgress(0);
    }

    @Test
    public void start_withText() {
        meliProgressBar.progressBar = progressBarMock;
        meliProgressBar.animator = animatorMock;

        meliProgressBar.setText("Text");

        assertEquals("", "Text", meliProgressBar.textView.getText().toString());
        assertEquals("", View.INVISIBLE, meliProgressBar.textView.getVisibility());

        meliProgressBar.start(null);
        assertEquals("", View.VISIBLE, meliProgressBar.textView.getVisibility());
    }

    @Test
    public void finish_withDefaultSettings() {
        meliProgressBar.animator = animatorMock;
        meliProgressBar.progressBar.setProgress(10);

        meliProgressBar.finish(null);
        verify(animatorMock).cancel();
        assertNotEquals("", animatorMock, meliProgressBar.animator);
    }

    @Test
    public void finish_withDefaultSettings_andNoProgress() {
        meliProgressBar.animator = null;
        meliProgressBar.progressBar.setProgress(0);

        meliProgressBar.finish(null);
        assertNull("", meliProgressBar.animator);
    }

    @Test
    public void restart_withDefaultSettings() {
        when(progressBarMock.getProgress()).thenReturn(10);
        meliProgressBar.progressBar = progressBarMock;
        meliProgressBar.animator = null;

        meliProgressBar.restart();
        verify(progressBarMock).setProgress(0);
        assertEquals("", View.GONE, meliProgressBar.textView.getVisibility());
    }

    @Test
    public void restart_withDefaultSettings_andPreviousAnimatorAndText() {
        when(progressBarMock.getProgress()).thenReturn(10);
        meliProgressBar.progressBar = progressBarMock;
        meliProgressBar.animator = animatorMock;
        meliProgressBar.setText("Text");

        meliProgressBar.restart();
        verify(animatorMock).cancel();
        verify(progressBarMock).setProgress(0);
        assertEquals("", View.INVISIBLE, meliProgressBar.textView.getVisibility());
    }
}
