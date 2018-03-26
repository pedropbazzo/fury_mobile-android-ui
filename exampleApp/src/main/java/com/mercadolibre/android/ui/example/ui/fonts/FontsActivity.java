package com.mercadolibre.android.ui.example.ui.fonts;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.font.Font;
import com.mercadolibre.android.ui.font.TypefaceHelper;

public class FontsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonts);

        setUpComponents();
    }

    @SuppressWarnings("HardcodedText")
    private void setUpComponents() {
        TextView tv1 = new TextView(this);
        Button btn = new Button(this);
        ToggleButton tb = new ToggleButton(this);
        EditText et = new EditText(this);
        Switch sw = new Switch(this);
        TextView tv2 = new TextView(this);
        TextView tv3 = new TextView(this);
        TextView tv4 = new TextView(this);

        tv1.setText("This component should have a Bold font");
        btn.setText("This component should have a Black font");
        tb.setText("This component should have a Regular font");
        et.setText("This component should have a ExtraBold font");
        sw.setText("This component should have a SemiBold font");
        tv2.setText("This component should have a Light font");
        tv3.setText("This component should have a Thin font");
        tv4.setText("This component should have a Medium font");

        TypefaceHelper.setTypeface(tv1, Font.BOLD);
        TypefaceHelper.setTypeface(btn, Font.BLACK);
        TypefaceHelper.setTypeface(tb, Font.REGULAR);
        TypefaceHelper.setTypeface(et, Font.EXTRA_BOLD);
        TypefaceHelper.setTypeface(sw, Font.SEMI_BOLD);
        TypefaceHelper.setTypeface(tv2, Font.LIGHT);
        TypefaceHelper.setTypeface(tv3, Font.THIN);
        TypefaceHelper.setTypeface(tv4, Font.MEDIUM);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ui_example_app_code_created_views_container);
        linearLayout.addView(tv1);
        linearLayout.addView(btn);
        linearLayout.addView(tb);
        linearLayout.addView(et);
        linearLayout.addView(sw);
        linearLayout.addView(tv2);
        linearLayout.addView(tv3);
        linearLayout.addView(tv4);
    }

}
