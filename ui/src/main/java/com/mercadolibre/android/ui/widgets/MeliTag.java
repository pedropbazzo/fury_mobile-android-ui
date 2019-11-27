package com.mercadolibre.android.ui.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.common.util.UriUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mercadolibre.android.ui.R;

public class MeliTag extends RelativeLayout {

    private ConstraintLayout container;
    private TextView textView;
    private SimpleDraweeView thumbnail;
    private ImageView closeButton;
    private String text;
    private boolean thumbnailShown;
    private boolean closeButtonShown;
    private ColorStateList textColor;
    private ColorStateList containerColor;

    private final int marginBig = (int) getResources().getDimension(R.dimen.ui_tag_background_radius);
    private final int marginSmall = (int) getResources().getDimension(R.dimen.ui_tag_background_margin);

    /**
     * MeliTag constructor for code usage
     *
     * @param context the context
     */
    public MeliTag(Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * MeliTag constructor for XML usage
     *
     * @param context the context
     * @param attrs   the attributes
     */
    public MeliTag(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * MeliTag constructor for XML usage
     *
     * @param context      the context
     * @param attrs        the attributes
     * @param defStyleAttr the default attributes
     */
    public MeliTag(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        LayoutInflater.from(context).inflate(R.layout.ui_layout_tag, this);
        container = findViewById(R.id.ui_tag_container);
        textView = findViewById(R.id.ui_tag_text);
        thumbnail = findViewById(R.id.ui_tag_thumbnail);
        closeButton = findViewById(R.id.ui_tag_close_button);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MeliTag
                , defStyleAttr, 0);

        text = a.getString(R.styleable.MeliTag_text);
        thumbnailShown = a.getBoolean(R.styleable.MeliTag_showThumbnail, true);
        closeButtonShown = a.getBoolean(R.styleable.MeliTag_showCloseButton, true);
        int thumbnailResourceId = a.getResourceId(R.styleable.MeliTag_thumbnailDrawable, R.drawable.ui_tag_avatar);
        int closeButtonResourceId = a.getResourceId(R.styleable.MeliTag_closeButtonDrawable, R.drawable.ui_ic_tag_close);
        textColor = a.getColorStateList(R.styleable.MeliTag_textColor);
        if (textColor == null) {
            textColor = ContextCompat.getColorStateList(context, R.color.ui_meli_black);
        }
        containerColor = a.getColorStateList(R.styleable.MeliTag_containerColor);
        if (containerColor == null) {
            containerColor = ContextCompat.getColorStateList(context, R.color.ui_transparent);
        }

        setText(text);

        setThumbnailDrawable(thumbnailResourceId);

        setCloseButtonDrawable(closeButtonResourceId);

        setThumbnailShown(thumbnailShown);

        setCloseButtonShown(closeButtonShown);

        setTextColor(textColor);

        setBackgroundColor(containerColor.getColorForState(container.getDrawableState(), R.color.ui_transparent));

        a.recycle();
    }

    /**
     * Getter for the text
     *
     * @return the tag's text
     */
    public String getText() {
        return text;
    }

    /**
     * Setter for the text
     *
     * @param text the text to be set in the {@link MeliTag}
     */
    final public void setText(String text) {
        this.text = text;
        textView.setText(text);
    }

    /**
     * Returns whether the thumbnail is currently being shown.
     *
     * @return true if the thumbnail is being shown, false otherwise
     */
    public boolean isThumbnailShown() {
        return thumbnailShown;
    }

    /**
     * Setter for the visibility of the thumbnail
     *
     * @param thumbnailShown indicates whether the close button must be shown or not
     */
    final public void setThumbnailShown(boolean thumbnailShown) {
        this.thumbnailShown = thumbnailShown;
        final ConstraintSet constraintSet = new ConstraintSet();

        if (thumbnailShown) {
            thumbnail.setVisibility(VISIBLE);
            constraintSet.clone(container);
            constraintSet.clear(textView.getId(), ConstraintSet.LEFT);
            constraintSet.connect(textView.getId(), ConstraintSet.START, thumbnail.getId(), ConstraintSet.END, marginSmall);
            setEndConstraint(constraintSet);
        } else {
            thumbnail.setVisibility(GONE);
            constraintSet.clone(container);
            constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, marginBig);
            setEndConstraint(constraintSet);
        }
        constraintSet.applyTo(container);
    }

    /**
     * Sets the constraint for the rightmost part of the tag according to the visibility of its
     * close button
     * @param constraintSet the {@link ConstraintSet} used to set the container's constraints
     */
    private void setEndConstraint(ConstraintSet constraintSet) {
        if (closeButtonShown) {
            constraintSet.connect(textView.getId(), ConstraintSet.END, closeButton.getId(), ConstraintSet.START);
        } else {
            constraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, marginBig);
        }
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
        final ConstraintSet constraintSet = new ConstraintSet();

        if (closeButtonShown) {
            closeButton.setVisibility(VISIBLE);
            constraintSet.clone(container);
            constraintSet.clear(textView.getId(), ConstraintSet.RIGHT);
            constraintSet.connect(textView.getId(), ConstraintSet.END, closeButton.getId(), ConstraintSet.START);
            setStartConstraint(constraintSet);
        } else {
            closeButton.setVisibility(GONE);
            constraintSet.clone(container);
            constraintSet.connect(textView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, marginBig);
            setStartConstraint(constraintSet);
        }
        constraintSet.applyTo(container);
    }

    /**
     * Sets the constraint for the leftmost part of the tag according to the visibility of its
     * thumbnail
     * @param constraintSet the {@link ConstraintSet} used to set the container's constraints
     */
    private void setStartConstraint(ConstraintSet constraintSet) {
        if (thumbnailShown) {
            constraintSet.connect(textView.getId(), ConstraintSet.START, thumbnail.getId(), ConstraintSet.END, marginSmall);
        } else {
            constraintSet.connect(textView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, marginBig);
        }
    }

    /**
     * Sets the tag's thumbnail from a drawable resource
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
     * Sets the tag's thumbnail from an image url
     *
     * @param url the url to get the tag's thumbnail from
     */
    final public void setThumbnailDrawable(String url) {
        Uri uri = Uri.parse(url);

        thumbnail.setImageURI(uri);
    }

    /**
     * Setter for the tag's close button drawable
     *
     * @param resourceId the drawable resource id for the close button
     */
    final public void setCloseButtonDrawable(int resourceId) {
        closeButton.setImageResource(resourceId);
    }

    /**
     * Sets the tag's text color from a ColorStateList
     *
     * @param textColor a {@link ColorStateList}
     */
    final public void setTextColor(ColorStateList textColor) {
        textView.setTextColor(textColor);
        this.textColor = textColor;
    }

    /**
     * Sets the tag's text color
     *
     * @param color the color to be used for the text
     */
    final public void setTextColor(int color) {
        textView.setTextColor(color);
        textColor = ColorStateList.valueOf(color);
    }

    /**
     * Sets the tag's background color
     *
     * @param color the color to be used for the background
     */
    @Override
    final public void setBackgroundColor(int color) {
        ((GradientDrawable) container.getBackground()).setColor(color);
        containerColor = ColorStateList.valueOf(color);
    }

    /**
     * Sets the listener for the tag's closing
     * @param l the {@link android.view.View.OnClickListener} for the close button
     */
    final public void setOnCloseListener(@Nullable OnClickListener l) {
        closeButton.setOnClickListener(l);
    }

    /**
     * Getter for the tag's {@link TextView}
     * @return the {@link TextView} used in the {@link MeliTag}
     */
    public TextView getTextView() { return textView; }

    /**
     * Getter for the tag's container
     * @return the {@link ConstraintLayout} used for the {@link MeliTag}
     */
    public ConstraintLayout getContainer() {
        return container;
    }

    /**
     * Getter for the tag's thumbnail
     * @return the {@link SimpleDraweeView} used for the {@link MeliTag}
     */
    public SimpleDraweeView getThumbnail() {
        return thumbnail;
    }

    /**
     * Getter for the tag's {@link ImageView} used as a button for closing
     * @return the {@link ImageView} used for closing the {@link MeliTag}
     */
    public ImageView getCloseButton() {
        return closeButton;
    }

}
