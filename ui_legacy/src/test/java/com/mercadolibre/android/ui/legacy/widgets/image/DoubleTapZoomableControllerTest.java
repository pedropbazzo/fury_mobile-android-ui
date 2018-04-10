package com.mercadolibre.android.ui.legacy.widgets.image;

import android.graphics.PointF;
import android.view.MotionEvent;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class DoubleTapZoomableControllerTest extends AbstractRobolectricTest {

    @Test
    public void checkDoubleTapZooms() {
        final boolean[] called = { false };
        final DoubleTapZoomableController controller = Mockito.mock(DoubleTapZoomableController.class);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) {
                Assert.assertEquals(4f, invocation.getArguments()[0]);
                called[0] = true;
                return null;
            }
        }).when(controller).zoomToImagePoint(Mockito.anyFloat(), Mockito.any(PointF.class));

        Mockito.doCallRealMethod().when(controller).onDoubleTap(Mockito.any(MotionEvent.class));

        controller.onDoubleTap(MotionEvent.obtain(4, 4, MotionEvent.ACTION_DOWN, 2, 2, 2));

        Assert.assertTrue(called[0]);
    }

}
