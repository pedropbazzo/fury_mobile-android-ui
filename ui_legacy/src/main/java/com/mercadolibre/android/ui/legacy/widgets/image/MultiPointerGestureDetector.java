package com.mercadolibre.android.ui.legacy.widgets.image;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;

/**
 * Component that detects and tracks multiple pointers based on touch events.
 *
 * Each time a pointer gets pressed or released, the current gesture (if any) will end, and a new
 * one will be started (if there are still pressed pointers left). It is guaranteed that the number
 * of pointers within the single gesture will remain the same during the whole gesture.
 */
@Deprecated
class MultiPointerGestureDetector implements Detector {

    /**
     * Max number of pointers ("fingers") to detect and react to at the same time
     * This means we cant zoom with 3 fingers or more.
     */
    public static final int MAX_POINTERS = 2;

    /**
     * internal boolean to know if the user is currently zooming or doing a gesture
     */
    private boolean gestureInProgress;

    /**
     * Number of current "fingers"
     */
    private int pointerCount;

    /**
     * Internal array with the pointer id of the motion event.
     * This stores for each "finger" during a gesture, the last pointer id it had the motion event of him.
     */
    private final int ids[] = new int[MAX_POINTERS];

    /**
     * Internal coordinates of the gesture
     */
    private final float startX[] = new float[MAX_POINTERS];
    private final float startY[] = new float[MAX_POINTERS];
    private final float currentX[] = new float[MAX_POINTERS];
    private final float currentY[] = new float[MAX_POINTERS];

    /**
     * Listener for sending gesture callbacks if someone wants to hook on us
     */
    private @Nullable Gesture<Detector> listener = null;

    /**
     * Constructor
     */
    public MultiPointerGestureDetector() {
        reset();
    }

    /**
     * Default constructor
     * @return new MultiPointerGestureDetector
     */
    public static @NonNull MultiPointerGestureDetector newInstance() {
        return new MultiPointerGestureDetector();
    }

    /**
     * Sets a gesture listener for sending callbacks of the gestures done
     * @param gesture contract
     */
    @Override
    public void setGestureListener(@NonNull final Gesture<Detector> gesture) {
        listener = gesture;
    }

    /**
     * Resets the component to the initial state.
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public void reset() {
        gestureInProgress = false;
        pointerCount = 0;
        for (int i = 0; i < MAX_POINTERS; i++) {
            ids[i] = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH ?
                -1 : MotionEvent.INVALID_POINTER_ID;
        }
    }

    /**
     * This method can be overridden in order to perform threshold check or something similar.
     * @return whether or not to start a new gesture
     */
    protected boolean shouldStartGesture() {
        return true;
    }

    private void startGesture() {
        if (!gestureInProgress) {
            gestureInProgress = true;
            if (listener != null) {
                listener.onGestureBegin(this);
            }
        }
    }

    private void stopGesture() {
        if (gestureInProgress) {
            gestureInProgress = false;
            if (listener != null) {
                listener.onGestureEnd(this);
            }
        }
    }

    /**
     * Gets the index of the i-th pressed pointer.
     * Normally, the index will be equal to i, except in the case when the pointer is released.
     * @return index of the specified pointer or -1 if not found (i.e. not enough pointers are down)
     */
    private int getPressedPointerIndex(@NonNull MotionEvent event, int i) {
        final int count = event.getPointerCount();
        final int action = event.getActionMasked();
        final int index = event.getActionIndex();
        if (action == MotionEvent.ACTION_UP ||
            action == MotionEvent.ACTION_POINTER_UP) {
            if (i >= index) {
                i++;
            }
        }
        return (i < count) ? i : -1;
    }

    /**
     * Update the internals, and start or update the gesture depending on the state
     * @param event of type ACTION_MOVE
     */
    private void updateGesture(@NonNull final MotionEvent event) {
        // update pointers
        for (int i = 0; i < MAX_POINTERS; i++) {
            int index = event.findPointerIndex(ids[i]);
            if (index != -1) {
                currentX[i] = event.getX(index);
                currentY[i] = event.getY(index);
            }
        }
        // start a new gesture if not already started
        if (!gestureInProgress && shouldStartGesture()) {
            startGesture();
        }
        // notify listener
        if (gestureInProgress && listener != null) {
            listener.onGestureUpdate(this);
        }
    }

    /**
     * We stop the gesture and, if there are existing gestures (from another finger), we reset it and start it again
     * @param event motionEvent
     */
    private void closeGesture(@NonNull final MotionEvent event) {
        // we'll restart the current gesture (if any) whenever the number of pointers changes
        // NOTE: we only restart existing gestures here, new gestures are started in ACTION_MOVE
        boolean wasGestureInProgress = gestureInProgress;
        stopGesture();
        reset();
        // update pointers
        for (int i = 0; i < MAX_POINTERS; i++) {
            int index = getPressedPointerIndex(event, i);
            if (index == -1) {
                break;
            }
            ids[i] = event.getPointerId(index);
            currentX[i] = startX[i] = event.getX(index);
            currentY[i] = startY[i] = event.getY(index);
            pointerCount++;
        }
        // restart the gesture (if any) if there are still pointers left
        if (wasGestureInProgress && pointerCount > 0) {
            startGesture();
        }
    }

    /**
     * Handles the given motion event.
     * @param event event to handle
     * @return true always, because we will always handle it one way or another
     */
    public boolean onTouchEvent(@NonNull final MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_MOVE: {
                updateGesture(event);
                break;
            }

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP: {
                closeGesture(event);
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                stopGesture();
                reset();
                break;
            }
        }
        return true;
    }

    /**
     * Restarts the current gesture
     */
    public void restartGesture() {
        if (!gestureInProgress) {
            return;
        }
        stopGesture();
        for (int i = 0; i < MAX_POINTERS; i++) {
            startX[i] = currentX[i];
            startY[i] = currentY[i];
        }
        startGesture();
    }

    /**
     * Gets the number of pointers in the current gesture
     */
    public int getCount() {
        return pointerCount;
    }

    /**
     * Gets the start X coordinates for the all pointers
     * Mutable array is exposed for performance reasons and is not to be modified by the callers.
     */
    public float[] getStartX() {
        return startX;
    }

    /**
     * Gets the start Y coordinates for the all pointers
     * Mutable array is exposed for performance reasons and is not to be modified by the callers.
     */
    public float[] getStartY() {
        return startY;
    }

    /**
     * Gets the current X coordinates for the all pointers
     * Mutable array is exposed for performance reasons and is not to be modified by the callers.
     */
    public float[] getCurrentX() {
        return currentX;
    }

    /**
     * Gets the current Y coordinates for the all pointers
     * Mutable array is exposed for performance reasons and is not to be modified by the callers.
     */
    public float[] getCurrentY() {
        return currentY;
    }

}
