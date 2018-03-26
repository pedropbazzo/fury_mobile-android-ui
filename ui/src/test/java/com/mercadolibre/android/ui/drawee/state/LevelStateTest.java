package com.mercadolibre.android.ui.drawee.state;

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
public class LevelStateTest extends AbstractRobolectricTest{

    @Before
    public void setUp() {
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(RuntimeEnvironment.application);
        }
    }

    @Test
    public void test_IsValidKeyIsOnlyForPositiveNumbers() {
        try {
            new LevelState(0, 0)
                .add(1, 0)
                .add(90000, 0);
        } catch (Exception e) {
            fail();
        }

        try {
            new LevelState(-1, 0);
            fail();
        } catch (Exception e) {
            // Silent
        }
    }

    @Test
    public void test_Attach() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        LevelState state = new LevelState(0, R.drawable.ui_ic_clear);

        state.attach(view);

        Mockito.verify(view).setOnLevelListener(Mockito.any(StateDraweeView.OnLevelListener.class));
    }

    @Test
    public void test_Detach() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        LevelState state = new LevelState(0, R.drawable.ui_ic_clear);

        state.detach(view);

        Mockito.verify(view).setOnLevelListener(null);
    }

    @Test
    public void test_SetDrawable() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        LevelState state = new LevelState(0, R.drawable.ui_ic_clear);

        state.setDrawable(view, 0);

        Mockito.verify(view).setController(Mockito.any(DraweeController.class));
    }

}
