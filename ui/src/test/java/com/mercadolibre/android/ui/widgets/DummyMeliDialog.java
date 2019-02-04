package com.mercadolibre.android.ui.widgets;

import android.view.View;

import com.mercadolibre.android.ui.R;
import com.mercadolibre.android.ui.widgets.dialogs.MeliDialog;

/**
 * Dummy {@link MeliDialog} meant for testing purposes.
 */
public class DummyMeliDialog extends MeliDialog {

    public static final String TITLE = "Title";

    public static final String ACTION_BUTTON = "Action button";

    public static final String SECONDARY_EXIT = "Secondary exit";

    public static final View.OnClickListener LISTENER = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {
            // Do nothing;
        }
    };

    @Override
    public int getContentView() {
        return R.layout.ui_layout_melidialog_test;
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public String getActionString() {
        return ACTION_BUTTON;
    }

    @Override
    public View.OnClickListener getActionClickListener() {
        return LISTENER;
    }

    @Override
    public String getSecondaryExitString() {
        return SECONDARY_EXIT;
    }

    @Override
    public View.OnClickListener getSecondaryExitClickListener() {
        return LISTENER;
    }
}
