package com.mercadolibre.android.ui.widgets;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.mercadolibre.android.ui.R;

/**
 * Determinate horizontal progress bar.
 */
@SuppressWarnings("PMD.LinguisticNaming")
public final class MeliProgressBar extends FrameLayout {

    private static final String PROGRESS_PROPERTY = "progress";

    private static final int MAX_PROGRESS_TIME_DEFAULT = 10_000;
    private static final int FINISH_ANIM_TIME_DEFAULT = 500;
    private static final int INITIAL_PROGRESS = 0;
    private static final int TEXT_ANIM_DURATION_DEFAULT = 300;
    private static final int TEXT_DELAY_DEFAULT = 0;

    /* default */ ProgressBar progressBar;
    /* default */ TextView textView;

    private int maxProgressTime;
    private int finishAnimTime;
    /* default */ ObjectAnimator animator;
    private int textFadeInDuration;
    private int textDelay;

    /**
     * Constructor for code usage.
     * @param context   context.
     */
    public MeliProgressBar(final Context context) {
        super(context);
        init(context, null, 0);
    }

    /**
     * Constructor for XML usage.
     * @param context   context.
     * @param attrs     attributes.
     */
    public MeliProgressBar(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    /**
     * Constructor for XML usage.
     * @param context       context.
     * @param attrs         attributes.
     * @param defStyleAttr  default attributes.
     */
    public MeliProgressBar(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * Constructor for XML usage.
     * @param context       context.
     * @param attrs         attributes.
     * @param defStyleAttr  default attributes.
     * @param defStyleRes   default style resource.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MeliProgressBar(final Context context, final AttributeSet attrs,
        final int defStyleAttr, final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(@NonNull final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.ui_layout_progress_bar, this);
        progressBar = findViewById(R.id.ui_progressBar);
        textView = findViewById(R.id.ui_progress_bar_text);

        final TypedArray a =
            context.obtainStyledAttributes(attrs, R.styleable.MeliProgressBar, defStyleAttr, 0);

        maxProgressTime = a.getInt(R.styleable.MeliProgressBar_maxProgressTime, MAX_PROGRESS_TIME_DEFAULT);
        finishAnimTime = a.getInt(R.styleable.MeliProgressBar_finishAnimTime, FINISH_ANIM_TIME_DEFAULT);
        progressBar.setProgress(INITIAL_PROGRESS);
        progressBar.setMax(maxProgressTime);

        final String text = a.getString(R.styleable.MeliProgressBar_progressBarText);
        textDelay = a.getInt(R.styleable.MeliProgressBar_progressBarTextDelay, TEXT_DELAY_DEFAULT);
        textFadeInDuration =
            a.getInt(R.styleable.MeliProgressBar_progressBarTextFadeInDuration, TEXT_ANIM_DURATION_DEFAULT);
        textView.setText(text);
        setTextInitialVisibility();

        a.recycle();
    }

    /**
     * Starts progress animation.
     * @param animatorListener  to get notified of animation events.
     */
    public void start(@Nullable final Animator.AnimatorListener animatorListener) {
        if (animator != null) {
            animator.cancel();
        }
        progressBar.setProgress(INITIAL_PROGRESS);
        animator = ObjectAnimator.ofInt(progressBar, PROGRESS_PROPERTY, 0, maxProgressTime);
        animator.setInterpolator(new LinearInterpolator());
        if (animatorListener != null) {
            animator.addListener(animatorListener);
        }
        animator.setDuration(maxProgressTime);
        showText();
        animator.start();
    }

    /**
     * Completes progress with an animation.
     * @param animatorListener  to get notified of animation events.
     */
    public void finish(@Nullable final Animator.AnimatorListener animatorListener) {
        final int progress = progressBar.getProgress();
        if (animator != null) {
            animator.cancel();
        }
        if (progress > INITIAL_PROGRESS) {
            animator = ObjectAnimator.ofInt(progressBar, PROGRESS_PROPERTY, progress, maxProgressTime);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(finishAnimTime);
            if (animatorListener != null) {
                animator.addListener(animatorListener);
            }
            animator.start();
        }
    }

    /**
     * Restarts the progress bar's progress and hides the text.
     * If there is an ongoing animation, it'll be cancelled.
     */
    public void restart() {
        if (animator != null) {
            animator.cancel();
        }
        setTextInitialVisibility();
        progressBar.setProgress(INITIAL_PROGRESS);
    }

    /**
     * Sets the maximum time for the progress bar.
     * @param maxProgressTime in milliseconds.
     * @return self.
     */
    public MeliProgressBar setMaxProgressTime(final int maxProgressTime) {
        this.maxProgressTime = maxProgressTime;
        progressBar.setMax(this.maxProgressTime);
        return this;
    }

    /**
     * Sets the time for the animation that completes the progress.
     * @param finishAnimTime in milliseconds.
     * @return self.
     */
    public MeliProgressBar setFinishAnimTime(final int finishAnimTime) {
        this.finishAnimTime = finishAnimTime;
        return this;
    }

    /**
     * Sets the progress bar text.
     * @param text the text.
     * @return self.
     */
    public MeliProgressBar setText(final CharSequence text) {
        textView.setText(text);
        if (getProgress() <= INITIAL_PROGRESS) {
            setTextInitialVisibility();
        } else {
            showText();
        }
        return this;
    }

    /**
     * Sets text fade in animation duration.
     * @param textFadeInDuration duration in milliseconds.
     * @return self.
     */
    public MeliProgressBar setTextFadeInDuration(final int textFadeInDuration) {
        this.textFadeInDuration = textFadeInDuration;
        return this;
    }

    /**
     * Sets the delay before showing up the text.
     * @param textDelay delay in milliseconds.
     * @return self.
     */
    public MeliProgressBar setTextDelay(final int textDelay) {
        this.textDelay = textDelay;
        return this;
    }

    /**
     * @return progress bar's current progress.
     */
    public int getProgress() {
        return progressBar == null ? INITIAL_PROGRESS : progressBar.getProgress();
    }

    private void showText() {
        if (!TextUtils.isEmpty(textView.getText())) {
            textView.setAlpha(0f);
            textView.setVisibility(View.VISIBLE);

            textView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.animate()
                        .alpha(1f)
                        .setDuration(textFadeInDuration)
                        .setListener(null);
                }
            }, textDelay);
        }
    }

    private void setTextInitialVisibility() {
        if (TextUtils.isEmpty(textView.getText())) {
            textView.setVisibility(GONE);
        } else {
            textView.setVisibility(INVISIBLE);
        }
    }

    @Override
    @NonNull
    public String toString() {
        return "MeliProgressBar{" +
            "progressBar=" + progressBar +
            ", textView=" + textView +
            ", maxProgressTime=" + maxProgressTime +
            ", finishAnimTime=" + finishAnimTime +
            ", animator=" + animator +
            ", textFadeInDuration=" + textFadeInDuration +
            ", textDelay=" + textDelay +
            '}';
    }
}
