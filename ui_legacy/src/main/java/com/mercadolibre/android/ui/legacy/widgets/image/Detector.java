package com.mercadolibre.android.ui.legacy.widgets.image;

import android.support.annotation.NonNull;

/**
 * Contract for detectors.
 */
@Deprecated
public interface Detector {

    /**
     * Set a gesture listener to receive Gesture responses
     * @param gesture contract
     */
    void setGestureListener(@NonNull final Gesture<Detector> gesture);

    /**
     * Contract for gestures of detectors
     */
    @Deprecated
    interface Gesture<T extends Detector> {
        /**
         * Responds to the beginning of a gesture.
         * @param detector detector
         */
        void onGestureBegin(@NonNull T detector);

        /**
         * Responds to the update of a gesture in progress.
         * @param detector detector
         */
        void onGestureUpdate(@NonNull T detector);

        /**
         * Responds to the end of a gesture.
         * @param detector detector
         */
        void onGestureEnd(@NonNull T detector);
    }

}