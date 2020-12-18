package com.mercadolibre.android.ui.drawee;

import android.net.Uri;
import android.os.Build;
import androidx.annotation.NonNull;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.soloader.SoLoader;
import com.mercadolibre.android.ui.R;
import com.mercadolibre.android.ui.drawee.state.BinaryState;
import com.mercadolibre.android.ui.drawee.state.EnableState;
import com.mercadolibre.android.ui.drawee.state.LevelState;
import com.mercadolibre.android.ui.drawee.state.State;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.LOLLIPOP)
public class StateDraweeViewTest {

    @BeforeClass
    public static void beforeClass() {
        SoLoader.setInTestMode();
    }

    @Before
    public void setUp() {
        if (!Fresco.hasBeenInitialized()) {
            Fresco.initialize(RuntimeEnvironment.application);
        }
    }

    @Test
    public void test_ViewCantBeSettedImageByNormalMeans() {
        try {
            new StateDraweeView(RuntimeEnvironment.application).setImageURI("test");
            fail();
        } catch (Exception e) {
            Assert.assertEquals("This class doesnt support setting images, please use #setState", e.getMessage());
        }

        try {
            new StateDraweeView(RuntimeEnvironment.application).setImageResource(0);
            fail();
        } catch (Exception e) {
            Assert.assertEquals("This class doesnt support setting images, please use #setState", e.getMessage());
        }

        try {
            new StateDraweeView(RuntimeEnvironment.application).setImageBitmap(null);
            fail();
        } catch (Exception e) {
            Assert.assertEquals("This class doesnt support setting images, please use #setState", e.getMessage());
        }

        try {
            new StateDraweeView(RuntimeEnvironment.application).setImageDrawable(null);
            fail();
        } catch (Exception e) {
            Assert.assertEquals("This class doesnt support setting images, please use #setState", e.getMessage());
        }

        try {
            new StateDraweeView(RuntimeEnvironment.application).setImageURI("test", RuntimeEnvironment.application);
            fail();
        } catch (Exception e) {
            Assert.assertEquals("This class doesnt support setting images, please use #setState", e.getMessage());
        }

        try {
            new StateDraweeView(RuntimeEnvironment.application).setImageURI(Uri.parse("test"));
            fail();
        } catch (Exception e) {
            Assert.assertEquals("This class doesnt support setting images, please use #setState", e.getMessage());
        }

        try {
            new StateDraweeView(RuntimeEnvironment.application).setImageURI(Uri.parse("test"), RuntimeEnvironment.application);
            fail();
        } catch (Exception e) {
            Assert.assertEquals("This class doesnt support setting images, please use #setState", e.getMessage());
        }
    }

    @Test
    public void test_ViewEnabledListener() {
        final StateDraweeView view = new StateDraweeView(RuntimeEnvironment.application);

        view.setEnabled(false);

        view.setOnEnabledListener(new StateDraweeView.OnEnabledListener() {
            @Override
            public void onEnableChange(@NonNull final View v, final boolean enabled) {
                Assert.assertTrue(enabled);
                Assert.assertEquals(view, v);
            }
        });

        view.setEnabled(true);
    }

    @Test
    public void test_ViewLevelListener() {
        final StateDraweeView view = new StateDraweeView(RuntimeEnvironment.application);

        view.setImageLevel(0);

        view.setOnLevelListener(new StateDraweeView.OnLevelListener() {
            @Override
            public void onLevelChange(@NonNull final View v, final int level) {
                Assert.assertEquals(1, level);
                Assert.assertEquals(view, v);
            }
        });

        view.setImageLevel(1);
    }

    @Test
    public void test_StateIsRetained() {
        State testState;

        final StateDraweeView view = new StateDraweeView(RuntimeEnvironment.application);
        view.setState(testState = new EnableState());

        Assert.assertEquals(testState, view.state);

        view.setState(testState = new LevelState(0, 0));

        Assert.assertEquals(testState, view.state);
    }

    @Test
    public void test_StateDetachesOldOnes() {
        final StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        State testState1 = Mockito.spy(EnableState.class);
        testState1.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);
        testState1.add(BinaryState.STATE_ON, R.drawable.ui_ic_clear);
        State testState2 = Mockito.spy(EnableState.class);
        testState2.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);
        testState1.add(BinaryState.STATE_ON, R.drawable.ui_ic_clear);

        view.setState(testState1);
        view.setState(testState2);

        Mockito.verify(testState1).detach(view);
    }

    @Test
    public void test_StateNotAttachesIfNotPossible() {
        final StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));

        when(view.isViewAttachedToWindow()).thenReturn(false);

        State testState1 = Mockito.spy(EnableState.class);
        testState1.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);
        testState1.add(BinaryState.STATE_ON, R.drawable.ui_ic_clear);

        view.setState(testState1);

        Mockito.verify(testState1, Mockito.times(0)).attach(view);
    }

    @Test
    public void test_StateAttachesNewOneIfPossibleImmediatly() {
        final StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));

        when(view.isViewAttachedToWindow()).thenReturn(true);

        State testState1 = Mockito.spy(EnableState.class);
        testState1.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);
        testState1.add(BinaryState.STATE_ON, R.drawable.ui_ic_clear);

        view.setState(testState1);

        Mockito.verify(testState1).attach(view);
    }

    @Test
    public void test_Attach() {
        final StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        State testState1 = Mockito.spy(EnableState.class);
        testState1.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);
        testState1.add(BinaryState.STATE_ON, R.drawable.ui_ic_clear);

        view.setState(testState1);
        view.onAttach();

        Mockito.verify(testState1).attach(view);
    }

    @Test
    public void test_Detach() {
        final StateDraweeView view = Mockito.spy(new StateDraweeView(RuntimeEnvironment.application));
        State testState1 = Mockito.spy(EnableState.class);
        testState1.add(BinaryState.STATE_OFF, R.drawable.ui_ic_clear);
        testState1.add(BinaryState.STATE_ON, R.drawable.ui_ic_clear);

        view.setState(testState1);
        view.onDetach();

        Mockito.verify(testState1).detach(view);
    }

}
