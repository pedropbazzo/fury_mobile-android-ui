package com.mercadolibre.android.ui.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;

import com.mercadolibre.android.ui.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mercadolibre.android.ui.widgets.MeliButton.State.DISABLED;
import static com.mercadolibre.android.ui.widgets.MeliButton.State.NORMAL;
import static com.mercadolibre.android.ui.widgets.MeliButton.Type.ACTION_PRIMARY;
import static com.mercadolibre.android.ui.widgets.MeliButton.Type.ACTION_SECONDARY;
import static com.mercadolibre.android.ui.widgets.MeliButton.Type.OPTION_PRIMARY;

/**
 * MeliButton is the button that we use.
 * It has default colors and font, but you can overwrite both.
 * You can change colors and font but you can't change text size.
 */
public final class MeliButton extends AppCompatButton {

    /**
     * The button state (Normal or Disabled)
     */
    @State
    private int state;
    /**
     * The type of button (ActionPrimary, ActionSecondary or OptionPrimary)
     */
    @Type
    private int type;

    /**
     * Default constructor without attrs, defaults will be used: type=ActionPrimary state=Normal.
     *
     * @param context The context
     */
    public MeliButton(@NonNull final Context context) {
        this(context, null, 0);
    }

    /**
     * Constructor with attrs if state or type are not defined,
     * defaults will be used: type=ActionPrimary state=Normal.
     *
     * @param context The context
     * @param attrs   The AttributeSet
     */
    public MeliButton(@NonNull final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Constructor with attrs if state or type are not defined,
     * defaults will be used: type=ActionPrimary state=Normal.
     *
     * @param context      The context
     * @param attrs        The AttributeSet
     * @param defStyleAttr the StyleAttr
     */
    public MeliButton(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        configureButton(context, attrs, defStyleAttr);
    }

    private void configureButton(@NonNull final Context context, @Nullable final AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MeliButton, defStyleAttr, 0);
        final int buttonType = a.getInt(R.styleable.MeliButton_type, ACTION_PRIMARY);
        final int buttonState = a.getInt(R.styleable.MeliButton_state, NORMAL);

        final int textAppearance = a.getInt(R.styleable.MeliButton_textAppearance, R.style.MLFont_Regular);

        setTextAppearance(context, textAppearance);
        setGravity(Gravity.CENTER);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimensionPixelSize(R.dimen.ui_fontsize_medium));

        setType(buttonType);
        setState(buttonState);
    }

    private void configureState(@State final int buttonState) {
        if (NORMAL == buttonState) {
            setEnabled(true);
            setClickable(true);
        } else if (DISABLED == buttonState) {
            setEnabled(false);
            setClickable(false);
        }
    }

    private void configureType(@Type final int buttonType) {
        final Context context = getContext();
        Drawable buttonBackground = null;
        ColorStateList textColorState = null;

        switch (buttonType) {
            case ACTION_SECONDARY:
                buttonBackground = ContextCompat.getDrawable(context, R.drawable.ui_secondary_action_button);
                textColorState = ContextCompat.getColorStateList(context,R.color.ui_secondary_action_button_text_color);
                break;
            case OPTION_PRIMARY:
                buttonBackground = ContextCompat.getDrawable(context, R.drawable.ui_option_button);
                textColorState = ContextCompat.getColorStateList(context,R.color.ui_primary_option_button_text_color);
                break;
            case ACTION_PRIMARY:
            default:
                buttonBackground = ContextCompat.getDrawable(context, R.drawable.ui_primary_action_button);
                textColorState = ContextCompat.getColorStateList(context,R.color.ui_primary_action_button_text_color);
        }
        setTextColor(textColorState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(buttonBackground);
        } else {
            setBackgroundDrawable(buttonBackground);
        }
    }

    /*
        This was modified to reach exactly 48dp in height.
     */
    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        final int maxHeight = getResources().getDimensionPixelSize(R.dimen.ui_button_height);
        final int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(maxHeight, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
    }

    /**
     * Gets the button state {@link #state}
     *
     * @return the button's state Button.state
     */
    @State
    public int getState() {
        return state;
    }

    /**
     * Sets the button state {@link #state}
     *
     * @param state Button.state
     */
    public void setState(@State final int state) {
        this.state = state;
        configureState(state);
    }

    /**
     * Gets the button type {@link #type}
     *
     * @return the button's type
     */
    @Type
    public int getType() {
        return type;
    }

    /**
     * Sets the button type {@link #type}
     *
     * @param type Button.type
     */
    public void setType(@Type final int type) {
        this.type = type;
        configureType(type);
    }

    /**
     * Possible button types
     */
    @SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.CommentDefaultAccessModifier"})
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({ACTION_PRIMARY, ACTION_SECONDARY, OPTION_PRIMARY})
    public @interface Type {
        int ACTION_PRIMARY = 0;
        int ACTION_SECONDARY = 1;
        int OPTION_PRIMARY = 2;
    }

    /**
     * Possible button states
     */
    @SuppressWarnings({"PMD.RedundantFieldInitializer", "PMD.CommentDefaultAccessModifier"})
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({NORMAL, DISABLED})
    public @interface State {
        int NORMAL = 0;
        int DISABLED = 1;
    }
}