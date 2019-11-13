package com.mercadolibre.android.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mercadolibre.android.ui.R;

public class MeliChip extends RelativeLayout {

    private TextView textView;

    public MeliChip(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MeliChip(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MeliChip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeliChip(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull final Context context, @Nullable final AttributeSet attrs
            , final int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.ui_layout_chip, this);
        textView = findViewById(R.id.ui_chip_text);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MeliChip
                , defStyleAttr, 0);

        final String text = a.getString(R.styleable.MeliChip_text);

        textView.setText(text);

        a.recycle();
    }
}
