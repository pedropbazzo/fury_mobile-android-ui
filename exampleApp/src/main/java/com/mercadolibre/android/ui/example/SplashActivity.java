package com.mercadolibre.android.ui.example;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mercadolibre.android.ui.example.legacy.MainLegacyActivity;
import com.mercadolibre.android.ui.example.ui.MainActivity;
import com.mercadolibre.android.ui.example.ui.fonts.FontsInitializer;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        FontsInitializer.initFonts();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    public void openLegacyComponents(final View view) {
        startActivity(new Intent(this, MainLegacyActivity.class));
    }

    public void openNewComponents(final View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
