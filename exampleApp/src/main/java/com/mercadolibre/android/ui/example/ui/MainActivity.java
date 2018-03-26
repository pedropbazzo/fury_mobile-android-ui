package com.mercadolibre.android.ui.example.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.example.ui.drawee.DraweeStateActivity;
import com.mercadolibre.android.ui.example.ui.fonts.FontSizeActivity;
import com.mercadolibre.android.ui.example.ui.fonts.FontsActivity;
import com.mercadolibre.android.ui.example.ui.widgets.ErrorViewActivity;
import com.mercadolibre.android.ui.example.ui.widgets.WidgetsActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void openDraweeStateActivity(View view) {
        startActivity(new Intent(this, DraweeStateActivity.class));
    }

    public void openFontsActivity(View view) {
        startActivity(new Intent(this, FontsActivity.class));
    }

    public void openFontSizeActivity(View view) {
        startActivity(new Intent(this, FontSizeActivity.class));
    }

    public void openColorsActivity(View view) {
        startActivity(new Intent(this, ColorsActivity.class));
    }

    public void openWidgetsActivity(View view) {
        startActivity(new Intent(this, WidgetsActivity.class));
    }

    public void openLineSpacingActivity(View view) {
        startActivity(new Intent(this, LineSpacingActivity.class));
    }

    public void openErrorViewActivity(View view) {
        startActivity(new Intent(this, ErrorViewActivity.class));
    }
}
