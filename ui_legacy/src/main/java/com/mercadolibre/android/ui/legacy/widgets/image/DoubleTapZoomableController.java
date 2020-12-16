package com.mercadolibre.android.ui.legacy.widgets.image;

import android.content.Context;
import android.graphics.PointF;
import androidx.annotation.NonNull;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Controller that detects double tapping zoom + has inner access to pinch to zoom features (with use of
 * TransformGestureDetector).
 */
@Deprecated
class DoubleTapZoomableController extends ZoomableController
    implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    /**
     * Internal gesture detector to know user input and react to it
     */
    private GestureDetector gestureDetector;

    /**
     * Constructor.
     * @param gestureDetector that can react to scaling/translations/etc
     * @param context Context
     */
    public DoubleTapZoomableController(@NonNull TransformGestureDetector gestureDetector, @NonNull Context context) {
        super(gestureDetector);
        this.gestureDetector = new GestureDetector(context, this);
        this.gestureDetector.setOnDoubleTapListener(this);
    }

    /**
     * Constructor with default params
     * @param context Context
     * @return DoubleTapZoomableController with default params
     */
    public static DoubleTapZoomableController newInstance(Context context) {
        return new DoubleTapZoomableController(TransformGestureDetector.newInstance(), context);
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {
        return false;
    }

    /**
     * Zoom in or out (depending on view zoom status) on double tap
     * @param event triggering the double tap
     * @return true (since we will always consume it)
     */
    @Override
    public boolean onDoubleTap(MotionEvent event) {
        if (getScaleFactor() > MIN_SCALE) {
            zoomToImagePoint(0.01f, new PointF(event.getX(), event.getY()));
        }
        else {
            zoomToImagePoint(MAX_SCALE, new PointF(event.getX(), event.getY()));
        }
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent event) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent event, MotionEvent event1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
    }

    @Override
    public boolean onFling(MotionEvent event, MotionEvent event1, float v, float v1) {
        return false;
    }

    /**
     * Inform the gesture only if its enabled the view
     * @param event motionEvent
     * @return if event was consumed or not
     */
    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (isEnabled()) {
            gestureDetector.onTouchEvent(event);
            return super.onTouchEvent(event);
        }
        return false;
    }

}

