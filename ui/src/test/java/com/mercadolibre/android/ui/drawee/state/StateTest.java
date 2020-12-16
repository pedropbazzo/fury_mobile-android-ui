package com.mercadolibre.android.ui.drawee.state;

import android.content.res.Resources;
import android.os.Build;
import androidx.annotation.NonNull;

import com.mercadolibre.android.ui.R;
import com.mercadolibre.android.ui.drawee.StateDraweeView;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class StateTest {

    @Test
    public void test_IsValidKeyIsTakenIntoAccount() {
        State state = newState();
        try {
            state.add(-1, 0);
        } catch (Exception e) {
            Assert.assertEquals("Invalid key: -1 used for: " + state.getClass().getName() +
                " when adding drawable: 0", e.getMessage());
        }
    }

    @Test
    public void test_GetDrawable() {
        Assert.assertEquals(R.drawable.ui_ic_view_feedback_default,
            newState().add(9, R.drawable.ui_ic_view_feedback_default).getDrawable(9));
    }

    @Test
    public void test_GetDrawableNonExistent() {
        try {
            newState()
                .add(9, R.drawable.ui_ic_view_feedback_default)
                .getDrawable(20);
            fail();
        } catch (Resources.NotFoundException e) {
            Assert.assertEquals("Drawable resource not found for key: 20", e.getMessage());
        }
    }

    @Test
    public void test_AddKey() {
        State state = newState();
        state.add(0, R.drawable.ui_ic_view_feedback_default);
        state.add(1, R.drawable.ui_ic_view_feedback_default);
        state.add(2, R.drawable.ui_ic_view_feedback_default);

        Assert.assertEquals(3, state.states.size());
    }

    private State newState() {
        return new State() {
            @Override
            protected boolean isValidKey(final int key) {
                return key >= 0;
            }

            @Override
            public void attach(@NonNull final StateDraweeView view) {
                // Do nothing
            }

            @Override
            public void detach(@NonNull final StateDraweeView view) {
                // Do nothing
            }
        };
    }

}
