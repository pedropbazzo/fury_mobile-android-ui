package com.mercadolibre.android.ui.legacy.widgets.image;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;

import java.math.BigDecimal;

/**
 * Zoomable controller that calculates transformation based on touch events.
 */
@Deprecated
class ZoomableController implements Detector.Gesture<Detector> {

    /**
     * Internal constants
     */
    private final static int RADIAN_PI = 180;

    private final static int SCALE_TIME_TOTAL = 500; // In millis!
    private final static int SCALE_TIME_STEP = 40;

    /**
     * Scales for limiting the zoom capabilities
     */
    protected final static float MIN_SCALE = 1.0f;
    protected final static float MAX_SCALE = 4.0f;
    private final static float INVALID_SCALE = -1.0f;

    /**
     * Maximum temporal values to store of previous events
     */
    private final static int MAX_TEMPORAL_VALUES = 9;

    /**
     * Internal boolean to know if user is zooming
     */
    private boolean pinchZoom = false;

    /**
     * Gesture detector to delegate user input and react accordingly
     */
    private @NonNull TransformGestureDetector gestureDetector;

    /**
     * Listener of the gesture to receive callbacks
     */
    private @Nullable TransformChangeListener transformChangeListener;

    /**
     * Internal booleans for available features
     */
    protected boolean enabled = false;
    protected boolean rotationEnabled = false;
    protected boolean scaleEnabled = true;
    protected boolean translationEnabled = true;

    /**
     * Internal values to update the scaling smoothly
     */
    private Handler handler;
    private Interpolator interpolator = new AccelerateDecelerateInterpolator();

    /**
     * Internal boolean for boundaries we keep track of
     */
    protected final @NonNull RectF viewBounds = new RectF(); //is the height and width of the imageview
    protected final @NonNull RectF imageBounds = new RectF(); //is the height and width of the actual image inside the imageview
    protected final @NonNull RectF transformedImageBounds = new RectF(); //is the height and width of the image after it is scaled

    /**
     * Transformations
     */
    protected final @NonNull Matrix previousTransform = new Matrix();
    protected final @NonNull Matrix activeTransform = new Matrix();

    /**
     * The temporal values we store from previous events (like a history)
     */
    protected final float[] temporalValues = new float[MAX_TEMPORAL_VALUES];

    protected float saveScale = 1f;

    /**
     * Constructor
     * @param gestureDetector detector which will handle the input from user
     */
    public ZoomableController(@NonNull TransformGestureDetector gestureDetector) {
        this.gestureDetector = gestureDetector;
        this.gestureDetector.setGestureListener(this);
    }

    /**
     * Set transform listener to receive callbacks from events
     * @param transformChangeListener listener
     */
    public void setTransformChangeListener(@NonNull TransformChangeListener transformChangeListener) {
        this.transformChangeListener = transformChangeListener;
    }

    /**
     * Rests the controller.
     * Resets the Matrixes and the Gesture Detecotor
     */
    public void reset() {
        gestureDetector.reset();
        previousTransform.reset();
        activeTransform.reset();
    }

    /**
     * Sets whether the controller is enabled or not.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) {
            reset();
        }
    }

    /**
     * Returns whether the controller is enabled or not.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the image bounds before zoomable transformation is applied.
     */
    public void setImageBounds(@NonNull RectF imageBounds) {
        this.imageBounds.set(imageBounds);
    }

    /**
     * Sets the view bounds.
     */
    public void setViewBounds(@NonNull RectF viewBounds) {
        this.viewBounds.set(viewBounds);
    }

    /**
     * Gets the zoomable transformation
     * Internal matrix is exposed for performance reasons and is not to be modified by the callers.
     */
    public Matrix getTransform() {
        return activeTransform;
    }

    /**
     * Notifies controller of the received touch event.
     */
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        if (enabled) {
            return gestureDetector.onTouchEvent(event);
        }
        return false;
    }

    /**
     * Update the gesture from the callback received from a transform gesture detector
     *
     * Package protected to avoid creation of synthetic methods
     *
     * @param transformGestureDetector detector
     */
    void updateGesture(TransformGestureDetector transformGestureDetector) {
        pinchZoom = true;
        activeTransform.set(previousTransform);

        if (rotationEnabled) {
            float angle = transformGestureDetector.getRotation() * (float) (RADIAN_PI / Math.PI);
            activeTransform.postRotate(angle, transformGestureDetector.getPivotX(), transformGestureDetector.getPivotY());
        }

        if (scaleEnabled) {
            float scale = transformGestureDetector.getScale();
            activeTransform.postScale(scale, scale, transformGestureDetector.getPivotX(), transformGestureDetector.getPivotY());
        }
        limitScale(transformGestureDetector.getPivotX(), transformGestureDetector.getPivotY());

        if (translationEnabled) {
            activeTransform.postTranslate(transformGestureDetector.getTranslationX(), transformGestureDetector.getTranslationY());
        }
        limitTranslation();

        if (transformChangeListener != null) {
            transformChangeListener.onTransformChanged(activeTransform);
        }
    }

    @Override
    public void onGestureBegin(@NonNull Detector detector) {
    }

    @Override
    public void onGestureUpdate(@NonNull Detector detector) {
        updateGesture((TransformGestureDetector) detector);
    }

    @Override
    public void onGestureEnd(@NonNull Detector detector) {
        previousTransform.set(activeTransform);
        pinchZoom = false;
    }

    /**
     * Gets the current scale factor.
     */
    public float getScaleFactor() {
        activeTransform.getValues(temporalValues);
        return temporalValues[Matrix.MSCALE_X];
    }

    /**
     * Doesnt allow the image to be scaled to smaller than MIN_SCALE which is 1.0f
     *
     * Package protected to avoid creation of synthetic methods
     *
     * @param pivotX float
     * @param pivotY float
     */
    float limitScale(float pivotX, float pivotY) {
        float scale = INVALID_SCALE;
        float currentScale = getScaleFactor();
        if (currentScale < MIN_SCALE) {
            scale = MIN_SCALE / currentScale;
            activeTransform.postScale(scale, scale, pivotX, pivotY);
        }
        if (currentScale > MAX_SCALE) {
            scale = MAX_SCALE / currentScale;
            activeTransform.postScale(scale, scale, pivotX, pivotY);
        }
        return scale;
    }

    /**
     * Limit the translation with the possibilities
     * Package protected to avoid creation of synthetic methods
     */
    void limitTranslation() {
        RectF bounds = transformedImageBounds;
        bounds.set(imageBounds);
        activeTransform.mapRect(bounds);

        float offsetLeft = getOffset(bounds.left, bounds.width(), viewBounds.width());
        float offsetTop = getOffset(bounds.top, bounds.height(), viewBounds.height());
        if (offsetLeft != bounds.left || offsetTop != bounds.top) {
            activeTransform.postTranslate(offsetLeft - bounds.left, offsetTop - bounds.top);
            gestureDetector.restartGesture();
        }
    }

    private float getOffset(float offset, float imageDimension, float viewDimension) {
        float diff = viewDimension - imageDimension;
        final int maxDifference = 0;

        //If the image doesnt fit in the view, use as offset half of the difference (because half for each side)
        //Else, if the image does fit or is tinier than the view, get the limit.
        return (diff > maxDifference) ? diff / 2 : limit(offset, diff, maxDifference);
    }

    private float limit(float value, float min, float max) {
        return Math.min(Math.max(min, value), max);
    }

    /**
     * zoom the image with a scale, in a certain point.
     * CAREFUL: Use wisely since you can put a scale > MAX or < MIN and produce undesired effects
     * @param scale to zoom
     * @param imagePoint to act is pivot
     */
    protected void zoomToImagePoint(float scale, PointF imagePoint) {
        if (transformChangeListener != null) {
            transformChangeListener.onTransformChanged(activeTransform);
        }

        startScaleAnimation(SCALE_TIME_TOTAL, getScaleFactor(), scale, imagePoint);
    }

    /**
     * Scale smoothly with animation
     */
    private void startScaleAnimation(final int totalTime, final float startingZoom, final float endZoom, final PointF imagePoint) {
        float initialZoom;
        if (startingZoom > MIN_SCALE) {
            initialZoom = MIN_SCALE;
        }
        else {
            initialZoom = startingZoom;
        }
        final float startZoom = initialZoom;

        if (handler == null) {
            handler = new Handler();
        }

        final int step = SCALE_TIME_STEP;

        handler.postDelayed(new Runnable() {
            int time = 0;
            long startTime = System.currentTimeMillis();
            float zoom = startZoom;

            @Override
            public void run() {
                time += step;
                if (totalTime >= time) {
                    if (!pinchZoom) {
                        handler.postDelayed(this, step);
                        activeTransform.set(previousTransform);
                        float t = interpolate(startTime, totalTime);
                        final int decimalsToRound = 2;
                        float deltaScale = round(calculateDeltaScale(t, zoom, endZoom), decimalsToRound);
                        zoom = deltaScale;
                        activeTransform.postScale(deltaScale, deltaScale, imagePoint.x, imagePoint.y);

                        limitScale(imagePoint.x, imagePoint.y);
                        limitTranslation();

                        if (transformChangeListener != null) {
                            transformChangeListener.onTransformChanged(activeTransform);
                        }
                    }
                }
                else {
                    previousTransform.set(activeTransform);
                }
            }
        }, step);
    }

    /**
     * Round a number
     *
     * Package protected to avoid creation of synthetic methods
     */
    float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    /**
     * Calculate scale according to the target zoom with a start in a given time
     */
    private float calculateDeltaScale(float t, float startZoom, float targetZoom) {
        float zoom = startZoom + t * (targetZoom - startZoom);
        return zoom / saveScale;
    }

    private float interpolate(long startTime, float ZOOM_TIME) {
        long currTime = System.currentTimeMillis();

        final float minimumElapsed = 1f;
        float elapsed = (currTime - startTime) / ZOOM_TIME;

        elapsed = Math.min(minimumElapsed, elapsed);

        return interpolator.getInterpolation(elapsed);
    }

    /**
     * TransformChangeListener interface.
     */
    public interface TransformChangeListener {

        /**
         * Notifies the view that the transform changed.
         *
         * @param transform the new matrix
         */
        void onTransformChanged(Matrix transform);
    }

}