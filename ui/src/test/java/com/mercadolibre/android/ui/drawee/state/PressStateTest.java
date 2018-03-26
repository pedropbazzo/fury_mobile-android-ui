package com.mercadolibre.android.ui.drawee.state;

import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.mercadolibre.android.testing.AbstractRobolectricTest;
import com.mercadolibre.android.ui.R;
import com.mercadolibre.android.ui.drawee.StateDraweeView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.robolectric.RuntimeEnvironment;

import static org.junit.Assert.fail;

@SuppressWarnings("PMD.EmptyCatchBlock")
public class PressStateTest extends AbstractRobolectricTest{

    @Before
    public void setUp() {
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(RuntimeEnvironment.application);
        }
    }

    @Test
    public void test_IsValidKeyIsOnlyForClassConstants() {
        try {
            new PressState()
                .add(PressState.STATE_RELEASED, 0)
                .add(PressState.STATE_PRESSED, 0);
        } catch (Exception e) {
            fail();
        }

        try {
            new PressState()
                .add(-1, 0);
            fail();
        } catch (Exception e) {
            // Default
        }
    }

    @Test
    public void test_Attach() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        PressState state = new PressState();
        state.add(PressState.STATE_RELEASED, R.drawable.ui_ic_clear);

        state.attach(view);

        Mockito.verify(view).setOnTouchListener(Mockito.any(View.OnTouchListener.class));
    }

    @Test
    public void test_Detach() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        PressState state = new PressState();

        state.detach(view);

        Mockito.verify(view).setOnTouchListener(null);
    }

    @Test
    public void test_SetDrawable() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        PressState state = new PressState();
        state.add(PressState.STATE_PRESSED, R.drawable.ui_ic_clear);
        state.add(PressState.STATE_RELEASED, R.drawable.ui_ic_clear);

        state.setDrawable(view, PressState.STATE_RELEASED);

        Mockito.verify(view).setController(Mockito.any(DraweeController.class));
    }

}
