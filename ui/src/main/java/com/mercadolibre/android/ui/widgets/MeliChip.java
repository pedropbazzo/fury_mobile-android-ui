package com.mercadolibre.android.ui.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mercadolibre.android.ui.R;

public class MeliChip extends RelativeLayout {

    private ConstraintLayout container;
    private TextView textView;
    private SimpleDraweeView thumbnail;
    private ImageView closeButton;
    private String text;
    private boolean closeButtonShown;
    private ColorStateList textColor;
    private ColorStateList containerColor;

    /**
     * MeliChip constructor for code usage
     *
     * @param context the context
     */
    public MeliChip(Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * MeliChip constructor for XML usage
     *
     * @param context the context
     * @param attrs   the attributes
     */
    public MeliChip(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * MeliChip constructor for XML usage
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr the default attributes
     */
    public MeliChip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Inits all component variables
     *
     * @param context      The android context
     * @param attrs        The attributes setted by XML
     * @param defStyleAttr The default style
     */
    private void init(@NonNull final Context context, @Nullable final AttributeSet attrs
            , final int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.ui_layout_chip, this);
        container = findViewById(R.id.ui_chip_container);
        textView = findViewById(R.id.ui_chip_text);
        thumbnail = findViewById(R.id.ui_chip_thumbnail);
        closeButton = findViewById(R.id.ui_chip_close_button);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MeliChip
                , defStyleAttr, 0);

        text = a.getString(R.styleable.MeliChip_text);
        closeButtonShown = a.getBoolean(R.styleable.MeliChip_showCloseButton, true);
        int thumbnailResourceId = a.getResourceId(R.styleable.MeliChip_thumbnailDrawable, R.drawable.ui_chip_avatar);
        int closeButtonResourceId = a.getResourceId(R.styleable.MeliChip_closeButtonDrawable, R.drawable.ui_ic_chip_close);
        textColor = a.getColorStateList(R.styleable.MeliChip_textColor);
        if (textColor == null) {
            textColor = ContextCompat.getColorStateList(context, R.color.ui_meli_black);
        }
        containerColor = a.getColorStateList(R.styleable.MeliChip_containerColor);
        if (containerColor == null) {
            containerColor = ContextCompat.getColorStateList(context, R.color.ui_meli_white);
        }

        setText(text);

        setThumbnailDrawable(thumbnailResourceId);

        setCloseButtonDrawable(closeButtonResourceId);

        setCloseButtonShown(closeButtonShown);

        setTextColor(textColor);

        setBackgroundColor(containerColor.getColorForState(container.getDrawableState(), R.color.ui_meli_white));

        a.recycle();
    }

    /**
     * Getter for the text
     *
     * @return the chip's text
     */
    public String getText() {
        return text;
    }

    /**
     * Setter for the text
     *
     * @param text the text to be set in the chip
     */
    final public void setText(String text) {
        this.text = text;
        textView.setText(text);
    }

    /**
     * Returns whether the close button is currently being shown.
     *
     * @return true if the close button is being shown, false otherwise
     */
    public boolean isCloseButtonShown() {
        return closeButtonShown;
    }

    /**
     * Setter for the visibility of the close button
     *
     * @param closeButtonShown indicates whether the close button must be shown or not
     */
    final public void setCloseButtonShown(boolean closeButtonShown) {
        this.closeButtonShown = closeButtonShown;

        if (closeButtonShown) {
            closeButton.setVisibility(VISIBLE);
        } else {
            closeButton.setVisibility(GONE);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.setMargins(0, 0, 50, 0);
            textView.setLayoutParams(layoutParams);
        }
    }

    /**
     * Sets the chip's thumbnail from a drawable resource
     *
     * @param resourceId the drawable resource id for the thumbnail
     */
    final public void setThumbnailDrawable(int resourceId) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resourceId))
                .build();

        thumbnail.setImageURI(uri);
    }

    /**
     * Sets the chip's thumbnail from an image url
     *
     * @param url the url to get the chip's thumbnail from
     */
    final public void setThumbnailDrawable(String url) {
        Uri uri = Uri.parse(url);

        thumbnail.setImageURI(uri);
    }

    /**
     * Setter for the chip's close button drawable
     *
     * @param resourceId the drawable resource id for the close button
     */
    final public void setCloseButtonDrawable(int resourceId) {
        closeButton.setImageResource(resourceId);
    }

    /**
     * Sets the chip's text color from a ColorStateList
     *
     * @param textColor a {@link ColorStateList}
     */
    final public void setTextColor(ColorStateList textColor) {
        textView.setTextColor(textColor);
        this.textColor = textColor;
    }

    /**
     * Sets the chip's text color
     *
     * @param color the color to be used for the text
     */
    final public void setTextColor(int color) {
        textView.setTextColor(color);
        textColor = ColorStateList.valueOf(color);
    }

    /**
     * Sets the chip's background color
     *
     * @param color the color to be used for the background
     */
    @Override
    final public void setBackgroundColor(int color) {
        Drawable chipBackgroundDrawable = getResources().getDrawable(R.drawable.ui_chip_background);
        chipBackgroundDrawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        container.setBackground(chipBackgroundDrawable);
        containerColor = ColorStateList.valueOf(color);
    }

    /**
     * Sets the listener for the chip's closing
     * @param l the {@link android.view.View.OnClickListener} for the close button
     */
    final public void setOnCloseListener(@Nullable OnClickListener l) {
        closeButton.setOnClickListener(l);
    }

    /**
     * Getter for the chip's {@link TextView}
     * @return the {@link TextView} used in the chip
     */
    public TextView getTextView() { return textView; }

    /**
     * Getter for the chip's container
     * @return the {@link ConstraintLayout} used for the chip
     */
    public ConstraintLayout getContainer() {
        return container;
    }

    /**
     * Getter for the chip's {@link ImageView} used as a button for closing
     * @return the {@link ImageView} used for closing the chip
     */
    public ImageView getCloseButton() {
        return closeButton;
    }

}
