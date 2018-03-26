package com.mercadolibre.android.ui;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Listener for soft keyboard opening/closing events.
 *
 * @since 6/3/16
 */
public class KeyboardEventListener implements ViewTreeObserver.OnGlobalLayoutListener {

    private final Rect windowVisibleDisplayFrame = new Rect();
    private int lastVisibleDecorViewHeight;
    private final View decorView;
    private final KeyboardEventCallback listener;

    /**
     * Constructor for {@link KeyboardEventListener}.
     *
     * @param decorView The view whose size is affected by the keyboard showing up.
     * @param listener  The {@link KeyboardEventCallback} listener.
     */
    public KeyboardEventListener(@NonNull final View decorView, @NonNull final KeyboardEventCallback listener) {
        this.decorView = decorView;
        this.listener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onGlobalLayout() {
        // Retrieve visible rectangle inside window.
        decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
        final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

        // Decide whether keyboard is visible from changing decor view height.
        if (lastVisibleDecorViewHeight != 0) {
            if (lastVisibleDecorViewHeight > visibleDecorViewHeight) {
                // Notify listener about keyboard being shown.
                listener.onKeyboardShown();
            } else if (lastVisibleDecorViewHeight < visibleDecorViewHeight) {
                // Notify listener about keyboard being hidden.
                listener.onKeyboardHidden();
            }
        }
        // Save current decor view height for the next call.
        lastVisibleDecorViewHeight = visibleDecorViewHeight;
    }

    @Override
    public String toString() {
        return "KeyboardEventListener{"
                + "windowVisibleDisplayFrame=" + windowVisibleDisplayFrame
                + ", lastVisibleDecorViewHeight=" + lastVisibleDecorViewHeight
                + ", decorView=" + decorView
                + ", listener=" + listener
                + '}';
    }
}
