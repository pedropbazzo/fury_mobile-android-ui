package com.mercadolibre.android.ui.example.ui.widgets.chip;

import android.os.Bundle;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.MeliChip;

public class ChipActivity extends BaseActivity {

    private MeliChip meliChip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip);

        meliChip = findViewById(R.id.melichip1);
    }
}
