package com.mercadolibre.android.ui.widgets.contextual_menu;

public interface ContextualMenuListener {
    /**
     * Contextual Menu clicked item
     *
     * @param clickContext the item that was clicked
     */
    void onMenuItemClick(ContextualMenuInfo clickContext);

    /**
     * Callback when the menu is shown
     */
    void onShowPinMenu();

    /**
     * Callback when the menu is closed
     */
    void onHidePinMenu();
}