package com.mercadolibre.android.ui.example.ui.widgets;

import android.os.Bundle;
import android.view.View;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.ErrorView;

public class ErrorViewActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_view);

        setUpErrorView();
    }

    private void setUpErrorView() {
        final ErrorView errorView = (ErrorView) findViewById(R.id.error_view);

        if (errorView != null) {
            errorView.setImage(R.drawable.error_view_network);
            errorView.setTitle("Error view title");
            errorView.setSubtitle("Error view subtitle");
            errorView.setButton("Button's label", new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    //nothing to do
                }
            });
        }
    }
}
