package com.mercadolibre.android.ui.example.ui.widgets.chip;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.MeliChip;

public class ChipActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chip);

        MeliChip clickableMeliChip = findViewById(R.id.melichip7);
        MeliChip closeableMeliChip = findViewById(R.id.melichip8);
        MeliChip meliChipWithEverything = findViewById(R.id.melichip9);

        final Context context = this;

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        };

        clickableMeliChip.setOnClickListener(onClickListener);
        meliChipWithEverything.setOnClickListener(onClickListener);

        View.OnClickListener onCloseListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Closed", Toast.LENGTH_SHORT).show();
            }
        };

        closeableMeliChip.setOnCloseListener(onCloseListener);
        meliChipWithEverything.setOnCloseListener(onCloseListener);
    }
}
