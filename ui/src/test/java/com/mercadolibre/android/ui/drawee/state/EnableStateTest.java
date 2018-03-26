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
public class EnableStateTest extends AbstractRobolectricTest{

    @Before
    public void setUp() {
    
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(RuntimeEnvironment.application);
        }
    }

    @Test
    public void test_IsValidKeyIsOnlyForPositiveNumbers() {
        try {
            new EnableState()
                .add(BinaryState.STATE_OFF, 0)
                .add(BinaryState.STATE_ON, 0);
        } catch (Exception e) {
            fail();
        }

        try {
            new EnableState()
                .add(-1, 0);
            fail();
        } catch (Exception e) {
            // Silent
        }
    }

    @Test
    public void test_Attach() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        EnableState state = new EnableState();
        state.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);
        state.add(BinaryState.STATE_ON, R.drawable.ui_ic_clear);

        state.attach(view);

        Mockito.verify(view).setOnEnabledListener(Mockito.any(StateDraweeView.OnEnabledListener.class));
    }

    @Test
    public void test_Detach() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        EnableState state = new EnableState();

        state.detach(view);

        Mockito.verify(view).setOnEnabledListener(null);
    }

    @Test
    public void test_SetDrawable() {
        StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        EnableState state = new EnableState();
        state.add(BinaryState.STATE_ON, R.drawable.ui_ic_clear);
        state.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);

        state.setDrawable(view);

        Mockito.verify(view).setController(Mockito.any(DraweeController.class));
    }

}
