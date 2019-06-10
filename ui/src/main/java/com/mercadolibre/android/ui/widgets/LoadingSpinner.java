package com.mercadolibre.android.ui.widgets;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.StyleableRes;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.mercadolibre.android.ui.R;

/**
 * A custom indeterminate loading view.
 * The loading is achieved with a compound animation by drawing an arc that grows and then shrinks and a rotation.
 *
 * @since 22/3/16
 */
public class LoadingSpinner extends View {

    public static final int FULL_CIRCLE = 360;
    public static final int QUARTER_CIRCLE = 90;

    private Paint primaryColor;
    private Paint secondaryColor;

    private int sweepAngle;
    private int startAngle;

    private int strokeSize;

    private RectF viewBounds;
    private Paint currentColor;

    private ValueAnimator sweepAnim;
    private ValueAnimator startAnim;
    private ValueAnimator finalAnim;

    /**
     * Default constructor to use by code
     *
     * @param context the android context
     */
    public LoadingSpinner(final Context context) {
        this(context, null, 0);
    }

    /**
     * Default constructor to use by XML
     *
     * @param context the android context
     * @param attrs   an attribute set
     */
    public LoadingSpinner(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * Default constructor to use by XML
     *
     * @param context      the android context
     * @param attrs        an attribute set
     * @param defStyleAttr the default style attributes
     */
    public LoadingSpinner(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(final AttributeSet attrs, final int defStyleAttr) {
        bindAttrs(attrs, defStyleAttr);
        updateColor();
        createAnimators();
    }

    private void bindAttrs(AttributeSet attrs, int defStyleAttr) {
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.LoadingSpinner, defStyleAttr, 0);
        final int primaryColorInt = loadColor(a, R.styleable.LoadingSpinner_ui_primaryColor, R.color.ui_meli_blue);
        final int secondaryColorInt = loadColor(a, R.styleable.LoadingSpinner_ui_secondaryColor, R.color.ui_meli_yellow);
        strokeSize = a.getDimensionPixelSize(R.styleable.LoadingSpinner_ui_stroke, getResources().getDimensionPixelSize(R.dimen.ui_spinner_stroke));
        primaryColor = createPaint(Paint.Style.STROKE, strokeSize, primaryColorInt);
        secondaryColor = createPaint(Paint.Style.STROKE, strokeSize, secondaryColorInt);
        a.recycle();
    }

    /**
     * Load the color from xml attributes or fallback to the default if not found
     *
     * @param a            attributes typed array
     * @param index        the styleable index
     * @param defaultColor the default color resource id
     * @return the color to use
     */
    @ColorInt
    private int loadColor(final TypedArray a, @StyleableRes final int index, @ColorRes final int defaultColor) {
        int loadedColor = ContextCompat.getColor(getContext(), defaultColor);
        final ColorStateList colorList = a.getColorStateList(index);

        if (colorList != null) {
            loadedColor = colorList.getDefaultColor();
        }

        return loadedColor;
    }

    /**
     * Configure the animations that interpolate the arc values to achieve the loading effect
     */
    private void setupListeners() {
        cleanListeners();

        // - Head and tail start together, when the head finishes the full spin the tail catches up - //
        sweepAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                sweepAngle = (int) animation.getAnimatedValue();
            }
        });

        startAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                startAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });


        finalAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator animation) {
                startAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });

        finalAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                super.onAnimationEnd(animation);
                updateColor();
                sweepAnim.start();
                startAnim.start();
            }
        });

        sweepAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(final Animator animation) {
                super.onAnimationEnd(animation);
                finalAnim.start();
            }
        });

    }

    /**
     * Update the color to use in this round.
     * The color changes each round between the primary and secondary color
     */
    @SuppressWarnings("PMD.CompareObjectsWithEquals")
    /* default */ void updateColor() {
        currentColor = currentColor == primaryColor ? secondaryColor : primaryColor;
    }

    /**
     * Create an animator that will interpolate the angles of the circle
     *
     * @param startAngle the start value of the angle in degrees. Eg: 0
     * @param endAngle   the end value of the angle in degrees. Eg: 270
     * @param duration   the duration of the animation
     * @return an animator that will interpolate the angles between startAngle and endAngle
     */
    private ValueAnimator createAnimator(final int startAngle, final int endAngle, final int duration) {
        final ValueAnimator animator = ValueAnimator.ofInt(startAngle, endAngle);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(duration);
        return animator;
    }

    /**
     * Create the paint that will be used to draw the view
     *
     * @param style       the paint style
     * @param strokeWidth the stroke width
     * @param hex         the color to paint
     * @return the paint to apply
     */
    private Paint createPaint(final Paint.Style style, final int strokeWidth, @ColorInt final int hex) {
        final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(style);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(hex);

        return paint;
    }

    /**
     * When the view size changes, calculate its new size and start rotating from the center
     * {@inheritDoc}
     */
    @Override
    protected void onSizeChanged(final int width, final int height, final int oldWidth, final int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        viewBounds = new RectF(strokeSize, strokeSize, width - strokeSize, height - strokeSize);

        if (this.getAnimation() != null) {
            this.getAnimation().cancel();
        }

        final RotateAnimation rotateAnimation = new RotateAnimation(0f, FULL_CIRCLE, viewBounds.centerX(), viewBounds.centerY());
        rotateAnimation.setDuration(getResources().getInteger(R.integer.ui_spinner_rotation_time));
        rotateAnimation.setRepeatCount(-1);
        rotateAnimation.setRepeatMode(Animation.RESTART);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        startAnimation(rotateAnimation);
    }

    /**
     * Draw the arc of the loading progress
     * {@inheritDoc}
     */
    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        if (getVisibility() == VISIBLE) {
            canvas.drawArc(viewBounds, startAngle, sweepAngle - startAngle, false, currentColor);
        }
    }

    /**
     * Call this on activity start to begin animations
     */
    public void onStart() {
        if (!startAnim.isRunning()) {
            setupListeners();
            sweepAnim.start();
            startAnim.start();
        }
    }

    /**
     * Call this on activity stop to finish animations
     */
    public void onStop() {
        cleanListeners();
    }

    /**
     * Clean the animators so as not to leak memory
     *
     * @param animator the animator to clean
     */
    private void cleanAnimator(final ValueAnimator animator) {
            animator.cancel();
            animator.removeAllListeners();
            animator.removeAllUpdateListeners();
    }

    /**
     * Set the primary color of the loading wheel. Default is meli blue.
     *
     * @param colorId the color resource id
     */
    public void setPrimaryColor(@ColorRes final int colorId) {
        primaryColor = createPaint(Paint.Style.STROKE, strokeSize, ContextCompat.getColor(getContext(), colorId));
        currentColor = primaryColor;
    }

    /**
     * Set the secondary color of the loading wheel. Default is meli yellow.
     *
     * @param colorId the color resource id
     */
    public void setSecondaryColor(@ColorRes final int colorId) {
        secondaryColor = createPaint(Paint.Style.STROKE, strokeSize, ContextCompat.getColor(getContext(), colorId));
    }

    /**
     * Set the stroke size in pixels.
     * This has to be called before {@link #setPrimaryColor(int)} and {@link #setSecondaryColor(int)}
     * to have any effect.
     *
     * @param strokeSize The new stroke size in pixels.
     */
    public void setStrokeSize(final int strokeSize) {
        this.strokeSize = strokeSize;
    }

    @Override
    public String toString() {
        return "LoadingSpinner{"
                + "primaryColor=" + primaryColor
                + ", secondaryColor=" + secondaryColor
                + ", sweepAngle=" + sweepAngle
                + ", startAngle=" + startAngle
                + ", strokeSize=" + strokeSize
                + ", viewBounds=" + viewBounds
                + ", currentColor=" + currentColor
                + ", sweepAnim=" + sweepAnim
                + ", startAnim=" + startAnim
                + ", finalAnim=" + finalAnim
                + '}';
    }

    private void cleanListeners() {
        cleanAnimator(sweepAnim);
        cleanAnimator(startAnim);
        cleanAnimator(finalAnim);
    }

    private void createAnimators() {
        final int duration = getResources().getInteger(R.integer.ui_spinner_spinning_time);
        sweepAnim = createAnimator(0, FULL_CIRCLE, duration);
        startAnim = createAnimator(0, QUARTER_CIRCLE, duration);
        finalAnim = createAnimator(QUARTER_CIRCLE, FULL_CIRCLE, duration);
    }
}