package com.mercadolibre.android.ui.example.ui.widgets.spinner;

import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.view.ContextThemeWrapper;
import android.view.View;
import android.widget.RelativeLayout;
import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.MeliSpinner;

public class SpinnerActivity extends BaseActivity {

    private RelativeLayout spinnerFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        spinnerFrame = (RelativeLayout) findViewById(R.id.spinner_frame);
    }

    public void showBigYellowSpinner(View view) {
        spinnerFrame.removeAllViews();
        MeliSpinner meliSpinner = new MeliSpinner(new ContextThemeWrapper(this, R.style.ui_meli_spinner));
        meliSpinner.setText("Texto de soporte hasta dos líneas");
        spinnerFrame.setBackgroundColor(ContextCompat.getColor(this, R.color.ui_meli_white));
        spinnerFrame.addView(meliSpinner);
    }

    public void showBigWhiteSpinner(View view) {
        spinnerFrame.removeAllViews();
        MeliSpinner meliSpinner = new MeliSpinner(new ContextThemeWrapper(this, R.style.ui_meli_spinner_Alternate));
        meliSpinner.setText("Texto de soporte hasta dos líneas");
        spinnerFrame.setBackgroundColor(ContextCompat.getColor(this, R.color.ui_meli_yellow));
        spinnerFrame.addView(meliSpinner);
    }

    public void showSmallBlueSpinner(View view) {
        spinnerFrame.removeAllViews();
        MeliSpinner meliSpinner = new MeliSpinner(new ContextThemeWrapper(this, R.style.ui_meli_spinner_SingleColorSmall));
        spinnerFrame.setBackgroundColor(ContextCompat.getColor(this, R.color.ui_meli_white));
        spinnerFrame.addView(meliSpinner);
    }

    public void showSmallWhiteSpinner(View view) {
        spinnerFrame.removeAllViews();
        MeliSpinner meliSpinner = new MeliSpinner(new ContextThemeWrapper(this, R.style.ui_meli_spinner_SingleColorSmall_Alternate));
        spinnerFrame.setBackgroundColor(ContextCompat.getColor(this, R.color.ui_meli_yellow));
        spinnerFrame.addView(meliSpinner);
    }
}