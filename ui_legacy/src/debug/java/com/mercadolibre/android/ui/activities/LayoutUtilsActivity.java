package com.mercadolibre.android.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mercadolibre.android.ui.legacy.R;

public class LayoutUtilsActivity extends Activity {

    private TextView layoutUtilsTextView;
    private ScrollView scrollView;
    private TextView scrollTextView;
    private View viewBackground;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout_utils);
        layoutUtilsTextView = (TextView)findViewById(R.id.layout_utils_test);
        scrollView = (ScrollView)findViewById(R.id.layout_util_scrollview);
        scrollTextView = (TextView)findViewById(R.id.layout_utils_txt_scrollview);
        viewBackground = (View)findViewById(R.id.layout_utils_view_background);
    }


    public TextView getLayoutUtilsTextView() {
        return layoutUtilsTextView;
    }

    public ScrollView getScrollView() {
        return scrollView;
    }

    public TextView getScrollTextView() {
        return scrollTextView;
    }

    public View getViewBackground() {
        return viewBackground;
    }
}
