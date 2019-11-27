package com.mercadolibre.android.ui.example.ui.widgets.tag;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.widgets.MeliTag;

public class TagActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        MeliTag clickableMeliTag = findViewById(R.id.melitag_clickable);
        MeliTag closeableMeliTag = findViewById(R.id.melitag_closeable);
        MeliTag meliTagWithEverything = findViewById(R.id.melitag_everything);

        final Context context = this;

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        };

        clickableMeliTag.setOnClickListener(onClickListener);
        meliTagWithEverything.setOnClickListener(onClickListener);

        View.OnClickListener onCloseListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Closed", Toast.LENGTH_SHORT).show();
            }
        };

        closeableMeliTag.setOnCloseListener(onCloseListener);
        meliTagWithEverything.setOnCloseListener(onCloseListener);
    }
}
