package com.mercadolibre.android.ui.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.os.ParcelableCompat;
import android.support.v4.os.ParcelableCompatCreatorCallbacks;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mercadolibre.android.ui.R;

/**
 * A view that shows MercadoLibre's full screen error
 *
 * Comments: If you want to center the content you should specify "match_parent" to layout_width and layout_height
 * in your xml file. Otherwise it might be shown on the top of the screen.
 */
public class ErrorView extends LinearLayout {

    private ImageView image;
    private TextView title;
    private TextView subtitle;
    private Button button;
    private OnClickListener buttonClickListener;

    @DrawableRes
    private int imageRes;

    /**
     * Error View Constructor
     *
     * @param context the Context
     */
    public ErrorView(final Context context) {
        this(context, null);
    }

    /**
     * Error View Constructor
     *
     * @param context the Context
     * @param attrs the attributes
     */
    public ErrorView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Error View Constructor
     *
     * @param context the Context
     * @param attrs the attributes
     * @param defStyleAttr the style attribute
     */
    public ErrorView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * Error View Constructor
     *
     * @param context the Context
     * @param attrs the attributes
     * @param defStyleAttr the style attribute
     * @param defStyleRes the style resource
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ErrorView(final Context context, final AttributeSet attrs, final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.ui_layout_errorview, this);

        //sets the linearLayout's orientation according to the device's current orientation
        final int linearLayoutOrientation = getResources().getConfiguration().orientation;
        final int padding = (int) context.getResources().getDimension(R.dimen.ui_error_view_padding);
        if (linearLayoutOrientation == Configuration.ORIENTATION_PORTRAIT) {
            setOrientation(VERTICAL);
            setPadding(padding, 0, padding, 0);
        } else {
            setOrientation(HORIZONTAL);
            setPadding(0, 0, padding, 0);
        }

        setClickable(true);

        //sets background color
        setBackgroundColor(ContextCompat.getColor(getContext(), R.color.gray_light_3));

        //sets gravity
        setGravity(Gravity.CENTER);

        image = (ImageView) findViewById(R.id.ui_error_view_image);
        title = (TextView) findViewById(R.id.ui_error_view_title);
        subtitle = (TextView) findViewById(R.id.ui_error_view_subtitle);
        button = (Button) findViewById(R.id.ui_error_view_button);
    }

    /**
     * Sets up a custom full screen error image.
     * If the param is null, it will not be shown in the view
     *
     * @param drawable the image resId
     */
    public void setImage(@DrawableRes final int drawable) {
        setImage(drawable, null);
    }

    /**
     * Sets up a custom full screen error image.
     * If the param is null, it will not be shown in the view
     *
     * @param drawable the image resId
     * @param contentDescription the image description
     */
    public void setImage(@DrawableRes final int drawable, @Nullable final String contentDescription) {
        if (drawable <= 0) {
            image.setVisibility(GONE);
        } else {
            image.setImageResource(drawable);
            image.setVisibility(VISIBLE);
        }
        image.setContentDescription(contentDescription);
        imageRes = drawable;
    }

    /**
     * Sets up a custom full screen error title.
     * If the param is null, it will not be shown in the view
     *
     * @param titleText the error view title
     */
    public void setTitle(@Nullable final String titleText) {
        if (titleText == null) {
            title.setText(null);
            title.setVisibility(GONE);
        } else {
            title.setText(titleText);
            title.setVisibility(VISIBLE);
        }
    }

    /**
     * Sets up a custom full screen error title.
     * If the param is 0, it will not be shown in the view
     *
     * @param resId the error view title resource
     */
    public void setTitle(@StringRes final int resId) {
        if (resId > 0) {
            title.setText(resId);
            title.setVisibility(VISIBLE);
        } else {
            title.setText(null);
            title.setVisibility(GONE);
        }
    }

    /**
     * Sets up a custom full screen error subtitle.
     * If the param is null, it will not be shown in the view
     *
     * @param subtitleText the error view subtitle
     */
    public void setSubtitle(@Nullable final String subtitleText) {
        if (subtitleText == null) {
            subtitle.setText(null);
            subtitle.setVisibility(GONE);
        } else {
            subtitle.setText(subtitleText);
            subtitle.setVisibility(VISIBLE);
        }
    }

    /**
     * Sets up a custom full screen error subtitle.
     * If the param is 0, it will not be shown in the view
     *
     * @param resId the error view subtitle resource
     */
    public void setSubtitle(@StringRes final int resId) {
        if (resId > 0) {
            subtitle.setText(resId);
            subtitle.setVisibility(VISIBLE);
        } else {
            subtitle.setText(null);
            subtitle.setVisibility(GONE);
        }
    }

    /**
     * Sets up a custom full screen error button.
     * If any param is null, the button will not be shown in the view
     *
     * @param buttonLabel the button's label
     * @param onClickListener the button's clickListener
     */
    public void setButton(@Nullable final String buttonLabel, @Nullable final OnClickListener onClickListener) {
        if (buttonLabel == null || onClickListener == null) {
            button.setText(null);
            button.setVisibility(GONE);
        } else {
            button.setText(buttonLabel);
            button.setOnClickListener(onClickListener);
            button.setVisibility(VISIBLE);
        }
        buttonClickListener = onClickListener;
    }

    /**
     * Sets up a custom full screen error button.
     * If any param is null, the button will not be shown in the view
     *
     * @param resId the button's label resource
     * @param onClickListener the button's clickListener
     */
    public void setButton(@StringRes final int resId, @Nullable final OnClickListener onClickListener) {
        if (resId <= 0 || onClickListener == null) {
            button.setText(null);
            button.setVisibility(GONE);
        } else {
            button.setText(resId);
            button.setOnClickListener(onClickListener);
            button.setVisibility(VISIBLE);
        }
        buttonClickListener = onClickListener;
    }

    /**
     * This method will be called if there's a configurationChanges set for a parent activity.
     * In that case we need to save the current state, initialize the view and restore the data
     * once again because the layout is not automatically changed and we are using a different
     * layout according to the current orientation.
     *
     * @param newConfig the new Configuration
     */
    @Override
    protected void onConfigurationChanged(final Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        final String titleText = title.getText().toString();
        final String subtitleText = subtitle.getText().toString();
        final String buttonLabel = button.getText().toString();

        removeAllViews();
        initView(getContext());

        setTitle(titleText);
        setSubtitle(subtitleText);
        setButton(buttonLabel, buttonClickListener);
        setImage(imageRes);
    }

    @Override
    protected void onRestoreInstanceState(final Parcelable state) {
        final SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());

        setImage(savedState.getDrawable());
        setTitle(savedState.getTitle().toString());
        setSubtitle(savedState.getSubtitle().toString());

        //we can't save the button's listener so we only restore the label and not it's visibility
        button.setText(savedState.getButtonLabel().toString());
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Parcelable parcelable = super.onSaveInstanceState();
        return new SavedState(parcelable, imageRes, title.getText(), subtitle.getText(), button.getText());
    }

    @Override
    @SuppressWarnings({"checkstyle:multiplestringliterals", "PMD.AvoidDuplicateLiterals"})
    public String toString() {
        return "ErrorView{"
                + "image=" + image
                + ", title=" + title
                + ", subtitle=" + subtitle
                + ", button=" + button
                + ", buttonClickListener=" + buttonClickListener
                + ", imageRes=" + imageRes
                + '}';
    }

    /**
     * Saves the state of the view
     */
    private static class SavedState extends BaseSavedState {

        private final int drawable;
        private final CharSequence title;
        private final CharSequence subtitle;
        private final CharSequence buttonLabel;

        public static final Parcelable.Creator<SavedState> CREATOR =
                ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() {
                    @Override
                    public SavedState createFromParcel(final Parcel source, final ClassLoader loader) {
                        return new SavedState(source);
                    }

                    @Override
                    public SavedState[] newArray(final int size) {
                        return new SavedState[size];
                    }
                });

        /**
         * Constructor used when reading from a parcel.
         *
         * @param source The parcel that contains the data
         */
        /* default */ SavedState(final Parcel source) {
            super(source);
            drawable = source.readInt();
            title = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            subtitle = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
            buttonLabel = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(source);
        }

        /**
         * Creates a new SavedState
         *
         * @param superState  The state of the superclass of this view
         * @param drawable    The drawable used in the view
         * @param title       The view title
         * @param subtitle    The view subtitle
         * @param buttonLabel The button label
         */
        /* default */ SavedState(final Parcelable superState, @DrawableRes final int drawable, final CharSequence title,
                   final CharSequence subtitle, final CharSequence buttonLabel) {
            super(superState);
            this.drawable = drawable;
            this.title = title;
            this.subtitle = subtitle;
            this.buttonLabel = buttonLabel;
        }

        @Override
        public void writeToParcel(final Parcel out, final int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(drawable);
            TextUtils.writeToParcel(title, out, flags);
            TextUtils.writeToParcel(subtitle, out, flags);
            TextUtils.writeToParcel(buttonLabel, out, flags);
        }

        @DrawableRes
        public int getDrawable() {
            return drawable;
        }

        public CharSequence getTitle() {
            return title;
        }

        public CharSequence getSubtitle() {
            return subtitle;
        }

        public CharSequence getButtonLabel() {
            return buttonLabel;
        }

        @Override
        @SuppressWarnings("checkstyle:multiplestringliterals")
        public String toString() {
            return "SavedState{"
                    + "drawable=" + drawable
                    + ", title=" + title
                    + ", subtitle=" + subtitle
                    + ", buttonLabel=" + buttonLabel
                    + '}';
        }
    }
}
