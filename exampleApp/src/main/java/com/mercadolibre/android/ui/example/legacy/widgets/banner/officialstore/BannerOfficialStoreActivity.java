package com.mercadolibre.android.ui.example.legacy.widgets.banner.officialstore;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercadolibre.android.ui.example.R;

public class BannerOfficialStoreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_official_store);

        ((TextView) findViewById(R.id.ui_widget_banner_officialstore_brandname)).setText("Quick Silver");
        ((ImageView) findViewById(R.id.ui_widget_banner_officialstore_brandlogo)).setImageDrawable(getResources()
                .getDrawable(R.drawable.ic_bpp));
    }

}
