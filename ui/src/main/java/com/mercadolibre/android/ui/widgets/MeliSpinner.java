package com.mercadolibre.android.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.mercadolibre.android.ui.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * MercadoLibre's Spinner class.
 *
 * @since 28/4/16
 */
@SuppressWarnings("PMD.LinguisticNaming")
public class MeliSpinner extends FrameLayout {

    /* default */ static final float INVISIBLE = 0f;
    /* default */ static final float VISIBLE = 1f;
    /* default */ static final int ANIM_DURATION = 300;
    private static final int SMALL = 0;
    private static final int LARGE = 1;
    private static final int NO_MODE = -1;
    /* default */ final int textDelay;
    private final LoadingSpinner spinner;
    /* default */ TextView textView;
    /* default */ int size;
    /* default */ int textFadeInDuration;
    @ColorRes
    /* default */int primaryColor;
    @ColorRes
    /* default */int secondaryColor;

    /**
     * MeliSpinner Constructor
     *
     * @param context the Context
     */
    public MeliSpinner(final Context context) {
        this(context, null);
    }

    /**
     * MeliSpinner Constructor
     *
     * @param context the Context
     * @param attrs   the attributes
     */
    public MeliSpinner(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * MeliSpinner Constructor
     *
     * @param context      the Context
     * @param attrs        the attributes
     * @param defStyleAttr the default style attributes
     */
    @SuppressWarnings("PMD.ConstructorCallsOverridableMethod")
    public MeliSpinner(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.ui_layout_spinner, this);

        spinner = (LoadingSpinner) findViewById(R.id.ui_spinner);
        textView = (TextView) findViewById(R.id.ui_spinner_text);

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MeliSpinner, defStyleAttr, 0);
        final String text = a.getString(R.styleable.MeliSpinner_spinnerText);
        textDelay = a.getInt(R.styleable.MeliSpinner_textDelay, 0);
        configureText(text);

        textFadeInDuration = a.getInt(R.styleable.MeliSpinner_textFadeInDuration, ANIM_DURATION);

        // Deprecated mode, supported for compatibility until removal
        final int modeValue = a.getInt(R.styleable.MeliSpinner_spinnerMode, NO_MODE);
        if (modeValue == NO_MODE) {
            size = a.getInt(R.styleable.MeliSpinner_size, LARGE);
            primaryColor = a.getResourceId(R.styleable.MeliSpinner_primaryColor, R.color.ui_components_spinner_primary_color);
            secondaryColor = a.getResourceId(R.styleable.MeliSpinner_secondaryColor, R.color.ui_components_spinner_secondary_color);
            configureSpinner(primaryColor, secondaryColor);
        } else {
            final SpinnerMode mode = SpinnerMode.getEnum(modeValue);
            setSpinnerMode(mode);
        }

        a.recycle();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onAttachedToWindow() {
        spinner.onStart();
        super.onAttachedToWindow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onDetachedFromWindow() {
        spinner.onStop();
        super.onDetachedFromWindow();
    }

    /**
     * Set the spinner size
     * @param size the size
     */
    public void setSize(@SpinnerSize final int size) {
        this.size = size;
        configureSpinner(primaryColor, secondaryColor);
    }

    /**
     * Set the primary color
     * @param primaryColor the color
     */
    public void setPrimaryColor(@ColorRes final int primaryColor){
        this.primaryColor=primaryColor;
        configureSpinner(primaryColor,secondaryColor);
    }


    /**
     * Set de secondary color
     * @param secondaryColor the color
     */
    public void setSecondaryColor(@ColorRes final int secondaryColor){
        this.secondaryColor=secondaryColor;
        configureSpinner(primaryColor,secondaryColor);
    }

    /**
     * Set the spinner text.
     *
     * @param text the text.
     * @return Self
     */
    public MeliSpinner setText(final CharSequence text) {
        configureText(text);
        return this;
    }

    /**
     * Sets the {@link SpinnerMode}.
     *
     * @param mode the new SpinnerMode.
     * @return Self
     * @deprecated Configure size, primaryColor and secondaryColor directly from style.
     */
    @Deprecated
    public final MeliSpinner setSpinnerMode(final SpinnerMode mode) {
        size = mode == SpinnerMode.BIG_WHITE || mode == SpinnerMode.BIG_YELLOW ? LARGE : SMALL;
        configureSpinner(mode.primaryColor, mode.secondaryColor);
        return this;
    }

    /**
     * Starts the spinner.
     * @deprecated No longer necessary. Spinner start/stop automatically.
     */
    @Deprecated
    public void start() {
    }

    /**
     * Stops the spinner.
     * @deprecated No longer necessary. Spinner start/stop automatically.
     */
    @Deprecated
    public void stop() {
    }

    private void configureSpinner(@ColorRes final int primaryColor, @ColorRes final int secondaryColor) {
        @DimenRes final int stroke;
        @DimenRes final int spinnerSize;

        if (size == LARGE) {
            stroke = R.dimen.ui_spinner_stroke;
            spinnerSize = R.dimen.ui_spinner_size;
        } else {
            stroke = R.dimen.ui_spinner_small_stroke;
            spinnerSize = R.dimen.ui_spinner_small_size;
        }

        spinner.setStrokeSize(getResources().getDimensionPixelSize(stroke));
        spinner.setPrimaryColor(primaryColor);
        spinner.setSecondaryColor(secondaryColor);
        final ViewGroup.LayoutParams params = spinner.getLayoutParams();
        final int size = getResources().getDimensionPixelSize(spinnerSize);
        params.height = size;
        params.width = size;
        spinner.setLayoutParams(params);
        if (shouldShowText() && !TextUtils.isEmpty(textView.getText())) {
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }
    }

    private void configureText(@Nullable final CharSequence text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        textView.setText(text);
        if (shouldShowText()) {
            ViewCompat.setAlpha(textView, INVISIBLE);
            textView.setVisibility(View.VISIBLE);
            textView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    ViewCompat.animate(textView).alpha(VISIBLE).setDuration(textFadeInDuration).start();
                }
            }, textDelay);
        }
    }

    private boolean shouldShowText() {
        return size != SMALL;
    }

    /**
     * The available spinner modes.
     *
     * @deprecated Use the size, primaryColor and secondaryColor attributes directly,
     * or the appropriate predefined styles @style/ui_meli_spinner.*
     */
    @Deprecated
    public enum SpinnerMode {
        BIG_YELLOW(R.color.ui_components_spinner_primary_color, R.color.ui_components_spinner_secondary_color),
        BIG_WHITE(R.color.ui_components_spinner_primary_color, R.color.ui_components_spinner_alternate_color),
        SMALL_BLUE(R.color.ui_components_spinner_primary_color, R.color.ui_components_spinner_primary_color),
        SMALL_WHITE(R.color.ui_components_spinner_alternate_color, R.color.ui_components_spinner_alternate_color);

        @ColorRes
        public final int primaryColor;

        @ColorRes
        public final int secondaryColor;

        SpinnerMode(@ColorRes final int primaryColor, @ColorRes final int secondaryColor) {
            this.primaryColor = primaryColor;
            this.secondaryColor = secondaryColor;
        }

        /**
         * Returns a SpinnerMode enum. Defaults to BIG_YELLOW.
         *
         * @param val an integer.
         * @return the corresponding SpinnerMode.
         */
        public static SpinnerMode getEnum(final int val) {
            for (final SpinnerMode e : SpinnerMode.values()) {
                if (e.ordinal() == val) {
                    return e;
                }
            }
            return BIG_YELLOW;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SMALL, LARGE})
    public @interface SpinnerSize {
    }
}