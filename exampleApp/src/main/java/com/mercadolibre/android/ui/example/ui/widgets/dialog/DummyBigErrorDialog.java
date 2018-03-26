package com.mercadolibre.android.ui.example.ui.widgets.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.ErrorView;

public class DummyBigErrorDialog extends DummyFeedbackDialog {

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ErrorView errorView = (ErrorView) view.findViewById(R.id.dummy_errorview);

        errorView.setImage(R.drawable.logo_mercadolibre_new);
        errorView.setTitle("Title");
        errorView.setSubtitle("Subtitle");
        errorView.setButton("Button", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Feedback Button", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
