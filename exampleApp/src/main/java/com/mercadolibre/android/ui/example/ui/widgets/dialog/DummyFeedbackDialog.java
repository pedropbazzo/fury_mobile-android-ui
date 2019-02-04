package com.mercadolibre.android.ui.example.ui.widgets.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.ErrorView;
import com.mercadolibre.android.ui.widgets.dialogs.MeliDialog;

public class DummyFeedbackDialog extends MeliDialog {

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ErrorView errorView = (ErrorView) view.findViewById(R.id.dummy_errorview);
        errorView.setTitle("Title");
        errorView.setSubtitle("Subtitle");
    }

    @Override
    public int getContentView() {
        return R.layout.dummy_error_view;
    }

    @Override
    public String getActionString() {
        return "Apply";
    }

    @Override
    public View.OnClickListener getActionClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Apply", Toast.LENGTH_SHORT).show();
            }
        };
    }
}
