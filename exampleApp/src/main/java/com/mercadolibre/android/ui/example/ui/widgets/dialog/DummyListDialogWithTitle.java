package com.mercadolibre.android.ui.example.ui.widgets.dialog;

import android.view.View;
import android.widget.Toast;

public class DummyListDialogWithTitle extends DummyListDialog {

    @Override
    public String getTitle() {
        return "RecyclerView list";
    }

    @Override
    public View.OnClickListener getSecondaryExitClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Secondary exit", Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public String getSecondaryExitString() {
        return "Exit";
    }
}
