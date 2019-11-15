package com.mercadolibre.android.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mercadolibre.android.ui.R;

public class MeliChip extends RelativeLayout {

    private ConstraintLayout container;
    private TextView textView;
    private ImageView thumbnail;
    private ImageView closeButton;
    private String text;
    private boolean mustShowCloseButton;
    private int thumbnailResourceId;
    private int closeButtonResourceId;
    private ColorStateList textColor;
    private ColorStateList containerColor;

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
        container = findViewById(R.id.ui_chip_container);
        textView = findViewById(R.id.ui_chip_text);
        thumbnail = findViewById(R.id.ui_chip_thumbnail);
        closeButton = findViewById(R.id.ui_chip_close_button);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MeliChip
                , defStyleAttr, 0);

        final String text = a.getString(R.styleable.MeliChip_text);
        mustShowCloseButton = a.getBoolean(R.styleable.MeliChip_showCloseButton, true);
        thumbnailResourceId = a.getResourceId(R.styleable.MeliChip_thumbnailDrawable, R.drawable.ui_chip_avatar);
        closeButtonResourceId = a.getResourceId(R.styleable.MeliChip_closeButtonDrawable, R.drawable.ui_ic_chip_close);
        textColor = a.getColorStateList(R.styleable.MeliChip_textColor);
        if (textColor == null) {
            textColor = ContextCompat.getColorStateList(context, R.color.ui_textfield_text_color);
        }
        containerColor = a.getColorStateList(R.styleable.MeliChip_containerColor);
        if (containerColor == null) {
            containerColor = ContextCompat.getColorStateList(context, R.color.white);
        }

        textView.setText(text);

        Bitmap thumbnailBitmap = BitmapFactory.decodeResource(getResources(), thumbnailResourceId);
        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), thumbnailBitmap);
        roundedBitmapDrawable.setCornerRadius(Math.max(thumbnailBitmap.getWidth(), thumbnailBitmap.getHeight()) / 2.0f);
        thumbnail.setImageDrawable(roundedBitmapDrawable);

        closeButton.setImageResource(closeButtonResourceId);

        if (mustShowCloseButton) {
            closeButton.setVisibility(VISIBLE);
        } else {
            closeButton.setVisibility(GONE);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(0, 0, 50, 0);
            textView.setLayoutParams(layoutParams);
        }
        textView.setTextColor(textColor);
        Drawable chipBackgroundDrawable = getResources().getDrawable(R.drawable.ui_chip_background);
        chipBackgroundDrawable.setColorFilter(containerColor.getColorForState(container.getDrawableState(), R.color.white)
                , PorterDuff.Mode.SRC_ATOP);

        container.setBackground(chipBackgroundDrawable);

        a.recycle();
    }

}
