package com.mercadolibre.android.ui.example.ui.widgets.dialog;

import android.view.View;

public class DummyFeedBackDialogWithTitle extends DummyBigErrorDialog {

    public interface DummyInterface {
        void dismissed();
    }

    private DummyInterface listener;

    public DummyFeedBackDialogWithTitle() {
        super();
    }

    public void setListener(final DummyInterface listener) {
        this.listener = listener;
    }

    @Override
    public String getTitle() {
        return "Título que ocupa más de una línea en casi cualquier teléfono";
    }

    @Override
    public View.OnClickListener getSecondaryExitClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.dismissed();
            }
        };
    }

    @Override
    public String getSecondaryExitString() {
        return "Exit";
    }
}
