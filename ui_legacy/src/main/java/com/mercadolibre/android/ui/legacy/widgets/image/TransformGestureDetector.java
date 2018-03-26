package com.mercadolibre.android.ui.legacy.widgets.image;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

/**
 * Component that detects translation, scale and rotation based on touch events.
 * <p>
 * This class notifies its listeners whenever a gesture begins, updates or ends.
 * The instance of this detector is passed to the listeners, so it can be queried
 * for pivot, translation, scale or rotation.
 */
@Deprecated
class TransformGestureDetector implements Detector, Detector.Gesture<Detector> {

    /**
     * Internal values for default transformations
     */
    private static final int DEFAULT_ROTATION = 0;
    private static final int DEFAULT_SCALE = 1;

    /**
     * Parent detector (we just hook on him and receive his callbacks)
     */
    private final MultiPointerGestureDetector parentDetector;

    /**
     * Callback in case someone wants to hook on us and receive our callbacks
     */
    private Detector.Gesture<Detector> listener = null;

    /**
     * Constructor
     * @param multiPointerGestureDetector the detector from which we hook to receive gesture events
     */
    public TransformGestureDetector(@NonNull MultiPointerGestureDetector multiPointerGestureDetector) {
        parentDetector = multiPointerGestureDetector;
        parentDetector.setGestureListener(this);
    }

    /**
     * Factory method that creates a new instance of TransformGestureDetector
     */
    public static @NonNull TransformGestureDetector newInstance() {
        return new TransformGestureDetector(MultiPointerGestureDetector.newInstance());
    }

    /**
     * Sets the listener.
     * @param listener listener to set
     */
    @Override
    public void setGestureListener(@NonNull Detector.Gesture<Detector> listener) {
        this.listener = listener;
    }

    /**
     * Resets the component to the initial state.
     */
    public void reset() {
        parentDetector.reset();
    }

    /**
     * Handles the given motion event.
     * @param event event to handle
     * @return whether or not the event was handled
     */
    public boolean onTouchEvent(@NonNull final MotionEvent event) {
        return parentDetector.onTouchEvent(event);
    }

    @Override
    public void onGestureBegin(@NonNull Detector detector) {
        if (listener != null) {
            listener.onGestureBegin(this);
        }
    }

    @Override
    public void onGestureUpdate(@NonNull Detector detector) {
        if (listener != null) {
            listener.onGestureUpdate(this);
        }
    }

    @Override
    public void onGestureEnd(@NonNull Detector detector) {
        if (listener != null) {
            listener.onGestureEnd(this);
        }
    }

    private float calcAverage(float[] arr, int len) {
        float sum = 0;
        for (int i = 0; i < len; i++) {
            sum += arr[i];
        }
        return (len > 0) ? sum / len : 0;
    }

    /**
     * Restarts the current gesture
     */
    public void restartGesture() {
        parentDetector.restartGesture();
    }

    /**
     * Gets the X coordinate of the pivot point
     */
    public float getPivotX() {
        return calcAverage(parentDetector.getStartX(), parentDetector.getCount());
    }

    /**
     * Gets the Y coordinate of the pivot point
     */
    public float getPivotY() {
        return calcAverage(parentDetector.getStartY(), parentDetector.getCount());
    }

    /**
     * Gets the X component of the translation
     */
    public float getTranslationX() {
        return calcAverage(parentDetector.getCurrentX(), parentDetector.getCount()) -
            calcAverage(parentDetector.getStartX(), parentDetector.getCount());
    }

    /**
     * Gets the Y component of the translation
     */
    public float getTranslationY() {
        return calcAverage(parentDetector.getCurrentY(), parentDetector.getCount()) -
            calcAverage(parentDetector.getStartY(), parentDetector.getCount());
    }

    /**
     * Gets the scale
     */
    public float getScale() {
        if (parentDetector.getCount() < MultiPointerGestureDetector.MAX_POINTERS) {
            return DEFAULT_SCALE; // Scale 1 == nothing
        } else {
            float startDeltaX = parentDetector.getStartX()[1] - parentDetector.getStartX()[0];
            float startDeltaY = parentDetector.getStartY()[1] - parentDetector.getStartY()[0];
            float currentDeltaX = parentDetector.getCurrentX()[1] - parentDetector.getCurrentX()[0];
            float currentDeltaY = parentDetector.getCurrentY()[1] - parentDetector.getCurrentY()[0];
            float startDist = (float) Math.hypot(startDeltaX, startDeltaY);
            float currentDist = (float) Math.hypot(currentDeltaX, currentDeltaY);
            return currentDist / startDist;
        }
    }

    /**
     * Gets the rotation in radians
     */
    public float getRotation() {
        if (parentDetector.getCount() < MultiPointerGestureDetector.MAX_POINTERS) {
            return DEFAULT_ROTATION; // Rotation 0 == no rotation
        } else {
            float startDeltaX = parentDetector.getStartX()[1] - parentDetector.getStartX()[0];
            float startDeltaY = parentDetector.getStartY()[1] - parentDetector.getStartY()[0];
            float currentDeltaX = parentDetector.getCurrentX()[1] - parentDetector.getCurrentX()[0];
            float currentDeltaY = parentDetector.getCurrentY()[1] - parentDetector.getCurrentY()[0];
            float startAngle = (float) Math.atan2(startDeltaY, startDeltaX);
            float currentAngle = (float) Math.atan2(currentDeltaY, currentDeltaX);
            return currentAngle - startAngle;
        }
    }

}
