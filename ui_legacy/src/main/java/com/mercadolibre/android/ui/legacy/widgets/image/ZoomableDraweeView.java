package com.mercadolibre.android.ui.legacy.widgets.image;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.MotionEvent;

import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;

/**
 * DraweeView that has zoomable capabilities.
 *
 * Once the image loads, pinch-to-zoom and translation gestures are enabled.
 */
@Deprecated
class ZoomableDraweeView extends MLImageView implements ZoomableController.TransformChangeListener {

    /**
     * Internal bounds to keep track of
     */
    private final @NonNull RectF imageBounds = new RectF();
    private final @NonNull RectF viewBounds = new RectF();

    /**
     * Internal listener for knowing the state of the image to load
     */
    private final @NonNull ControllerListener controllerListener = new BaseControllerListener<Object>() {
        @Override
        public void onFinalImageSet(
            String id,
            @Nullable Object imageInfo,
            @Nullable Animatable animatable) {
            ZoomableDraweeView.this.onFinalImageSet();
        }

        @Override
        public void onRelease(String id) {
            ZoomableDraweeView.this.onRelease();
        }
    };

    /**
     * Internal controller for managing the zoom
     */
    private @NonNull ZoomableController zoomableController = DoubleTapZoomableController.newInstance(getContext());

    public ZoomableDraweeView(Context context) {
        super(context);
        init();
    }

    public ZoomableDraweeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomableDraweeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Initialize the view
     */
    private void init() {
        zoomableController.setTransformChangeListener(this);
        setScaleType(ScaleType.FIT_CENTER);
    }

    /**
     * Get the controller capable of managing the zoom
     * @return ZoomableController instance
     */
    public @NonNull ZoomableController getZoomableController() {
        return zoomableController;
    }

    /**
     * Set a drawee controller, for manipulating the image properties
     * @param controller for the drawee view
     */
    @Override
    public void setController(@Nullable DraweeController controller) {
        setControllers(controller);
    }

    /**
     * Sets internal logic for the controller, adding listeners to know information
     * about the image loading/failure
     * @param controller to attach
     */
    private void setControllersInternal(
        @Nullable DraweeController controller) {
        removeControllerListener(getController());
        addControllerListener(controller);
        super.setController(controller);
    }

    /**
     * Sets the controllers for the normal and huge image.
     * <p/>
     * <p> IMPORTANT: in order to avoid a flicker when switching to the huge image, the huge image
     * controller should have the normal-image-uri set as its low-res-uri.
     *
     * @param controller          controller to be initially used
     */
    public void setControllers(@Nullable DraweeController controller) {
        setControllersInternal(null);
        zoomableController.setEnabled(false);
        setControllersInternal(controller);
    }

    /**
     * Remove a listener capable of knowing image loading status from the controller
     * @param controller to remove
     */
    private void removeControllerListener(@Nullable DraweeController controller) {
        if (controller instanceof AbstractDraweeController) {
            ((AbstractDraweeController) controller).removeControllerListener(controllerListener);
        }
    }

    /**
     * Add a listener capable of knowing image loading status from the controller
     * @param controller to add
     */
    private void addControllerListener(@Nullable DraweeController controller) {
        if (controller instanceof AbstractDraweeController) {
            ((AbstractDraweeController) controller).addControllerListener(controllerListener);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        canvas.concat(zoomableController.getTransform());
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (zoomableController.onTouchEvent(event)) {
            if (zoomableController.getScaleFactor() > 1.0f) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
            return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected Pair<Integer, Integer> getResizeBounds() {
        Pair<Integer, Integer> pair = super.getResizeBounds();
        return new Pair<>(pair.first * 4, pair.second * 4);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        updateZoomableControllerBounds();
    }

    /**
     * Enable the zoomable controller and update it with the information of the loaded image.
     * Its package protected to avoid creation of synthetic methods
     */
    void onFinalImageSet() {
        if (!zoomableController.isEnabled()) {
            updateZoomableControllerBounds();
            zoomableController.setEnabled(true);
        }
    }

    /**
     * Disable the zoomable controller.
     * Its package protected to avoid creation of synthetic methods
     */
    void onRelease() {
        zoomableController.setEnabled(false);
    }

    /**
     * Call for transforming the underlying image.
     * This invalidates the view, making it redraw in a future looper message
     * @param transform the new matrix
     */
    @Override
    public void onTransformChanged(Matrix transform) {
        invalidate();
    }

    /**
     * Update the zoomable controller with the loaded image information
     */
    private void updateZoomableControllerBounds() {
        getHierarchy().getActualImageBounds(imageBounds);
        viewBounds.set(0, 0, getWidth(), getHeight());
        zoomableController.setImageBounds(imageBounds);
        zoomableController.setViewBounds(viewBounds);
    }

}
