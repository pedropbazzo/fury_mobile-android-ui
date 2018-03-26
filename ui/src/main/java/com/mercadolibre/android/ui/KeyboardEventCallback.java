package com.mercadolibre.android.ui;

/**
 * Callback methods for keyboard opening/closing events.
 *
 * @since 6/3/16
 */
public interface KeyboardEventCallback {

    /**
     * Method called when soft keyboard shows.
     */
    void onKeyboardShown();

    /**
     * Method called when soft keyboard hides.
     */
    void onKeyboardHidden();
}
