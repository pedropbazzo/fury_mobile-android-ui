package com.mercadolibre.android.ui.example.ui.widgets.chip;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.MeliChip;

public class ChipActivity extends AppCompatActivity {

    private MeliChip meliChip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip);

        meliChip = findViewById(R.id.melichip);
    }
}
