package com.mercadolibre.android.ui.legacy.widgets.image;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

import com.mercadolibre.android.testing.AbstractRobolectricTest;

import org.junit.Test;
import org.mockito.Mockito;

public class MultiPointerGestureDetectorTest extends AbstractRobolectricTest {

    @Test
    public void testTouchEvents() {
        final MultiPointerGestureDetector detector = MultiPointerGestureDetector.newInstance();
        Detector.Gesture listener = new Detector.Gesture() {
            @Override
            public void onGestureBegin(@NonNull final Detector detector) {

            }

            @Override
            public void onGestureUpdate(@NonNull final Detector detector) {

            }

            @Override
            public void onGestureEnd(@NonNull final Detector detector) {

            }
        };
        listener = Mockito.spy(listener);
        detector.setGestureListener(listener);

        //Simulate a pinch to zoom
        detector.onTouchEvent(MotionEvent.obtain(21, 21, MotionEvent.ACTION_MOVE, 2, 2, 2));
        detector.onTouchEvent(MotionEvent.obtain(21, 21, MotionEvent.ACTION_MOVE, 20, 20, 23));

        //Gesture should only begin once
        Mockito.verify(listener).onGestureBegin(Mockito.any(Detector.class));
        //The update can be called more than once.
        Mockito.verify(listener, Mockito.atLeastOnce()).onGestureUpdate(Mockito.any(Detector.class));

        //Finish gesture
        detector.onTouchEvent(MotionEvent.obtain(3, 4, MotionEvent.ACTION_UP, 4, 4, 4));

        //Check it only finished once.
        Mockito.verify(listener).onGestureEnd(Mockito.any(Detector.class));
    }

}
