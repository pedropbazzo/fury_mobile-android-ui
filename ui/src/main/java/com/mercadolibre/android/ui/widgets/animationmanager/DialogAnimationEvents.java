package com.mercadolibre.android.ui.widgets.animationmanager;

import android.support.v4.app.DialogFragment;

public interface DialogAnimationEvents {
    /**
     * Sets the animations to the {@link DialogFragment}
     */
    void enableAnimations();

    /**
     * Waits until the animation ends to restore the animations
     */
    void restoreAnimations();

    /**
     * Sets a default animation to prevent animate the {@link DialogFragment}
     */
    void disableAnimations();
}
