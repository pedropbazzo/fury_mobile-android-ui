package com.mercadolibre.android.ui.widgets.animationManager;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.DialogFragment;

import com.mercadolibre.android.ui.R;

/**
 * Enables or disables the animations of a DialogFragment
 * @since 23/04/2019
 */
public class DialogAnimationManager implements DialogAnimationEvents {
    /* default */ final DialogFragment dialogFragment;
    private final int animations;
    private final int animationDuration;
    private static final int defaultAnimation = R.style.DefaultModalAnimation;

    public DialogAnimationManager(@NonNull DialogFragment dialogFragment, @StyleRes int animations, int animationDuration) {
        this.dialogFragment = dialogFragment;
        this.animations = animations;
        this.animationDuration = animationDuration;
    }

    @Override
    public void enableAnimations() {
        dialogFragment.getDialog().getWindow().setWindowAnimations(animations);
    }

    @Override
    public void restoreAnimations() {
        if (dialogFragment.getDialog() != null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialogFragment.getDialog().getWindow().setWindowAnimations(R.style.FullscreenModalAnimation);
                }
            }, animationDuration);
        }
    }

    /**
     * Sets a default animation to prevent animate when stopping the app
     */
    @Override
    public void disableAnimations() {
        if (dialogFragment.getDialog() != null) {
            dialogFragment.getDialog().getWindow().setWindowAnimations(defaultAnimation);
        }
    }
}
