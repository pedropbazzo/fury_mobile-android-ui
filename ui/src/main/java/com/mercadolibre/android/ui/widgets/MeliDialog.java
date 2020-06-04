package com.mercadolibre.android.ui.widgets;

import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.DimenRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.mercadolibre.android.ui.KeyboardEventCallback;
import com.mercadolibre.android.ui.KeyboardEventListener;
import com.mercadolibre.android.ui.R;

/**
 * Base class for MercadoLibre's dialogs.
 *
 * @since 6/4/16
 */
@SuppressWarnings("PMD.GodClass")
public abstract class MeliDialog extends DialogFragment implements KeyboardEventCallback {

    /**
     * The animation duration.
     */
    /* default */ static final long TRANSLATE_ANIMATION_DURATION = 250;
    /* default */ static final long FADE_ANIMATION_DURATION = 350;

    /**
     * The animation translation in dp.
     */
    /* default */ static final int Y_TRANSLATION = 30;

    /* default */ static final float VISIBLE = 1f;

    @SuppressWarnings({ "PMD.RedundantFieldInitializer" })
    /* default */ static final float INVISIBLE = 0f;

    private View root;

    /* default */ View dialogContainer;

    private ViewGroup contentContainer;

    private View titleContainer;

    /* default */ Button secondaryExitButton;

    /* default */ View closeButton;

    /* default */ TextView actionButton;

    /* default */ boolean dismissed;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.MeliDialog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final View onCreateView(@NonNull final LayoutInflater inflater,
                                   @Nullable final ViewGroup container,
                                   @Nullable final Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ui_layout_melidialog, container, false);
        contentContainer = root.findViewById(R.id.ui_melidialog_content_container);
        titleContainer = root.findViewById(R.id.ui_melidialog_title_container);
        dialogContainer = root.findViewById(R.id.ui_melidialog_dialog_container);
        final boolean animate = savedInstanceState == null && shouldAnimate();
        setupView(animate);
        if (animate) {
            animateIn();
        }
        return root;
    }

    @Override
    public void onDestroy() {
        MeliDialog.super.dismissAllowingStateLoss();
        super.onDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dismiss() {
        if (!dismissed) {
            dismissed = true;
            if (root != null && animateOut()) {
                root.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isAdded()) {
                            MeliDialog.super.dismissAllowingStateLoss();
                        }
                    }
                }, FADE_ANIMATION_DURATION);
            } else {
                super.dismiss();
            }
        }
    }

    /**
     * Override to set the content view.
     *
     * @return An integer representing a layout id.
     */
    @LayoutRes
    public abstract int getContentView();

    /**
     * Override to set a title.
     *
     * @return A string to be set as a title, {@code null} if no title is set.
     */
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    @Nullable
    public String getTitle() {
        return null;
    }

    /**
     * Override to set the action string.
     * The action button won't be visible unless you override {@link MeliDialog#getActionClickListener()} too.
     *
     * @return A string to be set to the action button, {@code null} if no action is set.
     */
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    @Nullable
    public String getActionString() {
        return null;
    }

    /**
     * Override to set the action's click listener.
     * The action button won't be visible unless you override {@link MeliDialog#getActionString()} too.
     *
     * @return The action's OnClickListener, {@code null} if no action is set.
     */
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    @Nullable
    public View.OnClickListener getActionClickListener() {
        return null;
    }

    /**
     * Override to set the secondary exit string.
     * The secondary exit button won't be visible unless you override {@link MeliDialog#getSecondaryExitClickListener()} too.
     *
     * @return A string to be set to the secondary exit button.
     */
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    @Nullable
    public String getSecondaryExitString() {
        return null;
    }

    /**
     * Override to set the secondary exit's click listener.
     * The secondary exit button won't be visible unless you override {@link MeliDialog#getSecondaryExitString()} too.
     *
     * @return The secondary exit's OnClickListener.
     */
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    @Nullable
    public View.OnClickListener getSecondaryExitClickListener() {
        return null;
    }

    /**
     * Override to set a OnDismissListener.
     * If set, this listener will be called every time the dialog is dismissed.
     *
     * @return An OnClickListener to be called when the dialog is dismissed.
     */
    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    @Nullable
    public View.OnClickListener getOnDismissListener() {
        return null;
    }

    /**
     * Override to avoid the dialog from animating when showed and dismissed.
     * Dialogs are animated by default.
     *
     * @return {@code true} if the dialog should be animated, {@code false} otherwise.
     */
    public boolean shouldAnimate() {
        return true;
    }

    /**
     * Override to prevent the dialog from scrolling by default.
     *
     * @return {@code true} if the dialog should scroll by default, {@code false} otherwise.
     */
    public boolean shouldScroll() {
        return true;
    }

    /**
     * Sets the view up.
     */
    private void setupView(final boolean animate) {
        setupTitle();
        setupCloseAction(animate);
        setupAnimationOnBackPressed();
        setupSecondaryExitButton(animate);
        setupActionButton(animate);
        setupContentView();
        setupListenerForKeyboardEvents();
    }

    /**
     * Sets the title up.
     */
    private void setupTitle() {
        final String title = getTitle();
        if (TextUtils.isEmpty(title)) {
            titleContainer.setVisibility(View.GONE);
        } else {
            ((TextView) titleContainer.findViewById(R.id.ui_melidialog_title)).setText(title);
            titleContainer.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Sets the close button and background's action and listener.
     */
    private void setupCloseAction(final boolean animate) {
        closeButton = root.findViewById(R.id.ui_melidialog_close_button);
        final View background = root.findViewById(R.id.ui_melidialog_container);
        final View dialogViewContainer = root.findViewById(R.id.ui_melidialog_rounded_container);
        final View.OnClickListener dismissListener = getOnDismissListener();
        background.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                if (!dismissed && event.getAction() == MotionEvent.ACTION_UP) {
                    final Rect rect = new Rect();
                    dialogViewContainer.getGlobalVisibleRect(rect);
                    // Calculate whether touch is moved inside the dialog's view.
                    if (event.getRawY() >= rect.bottom || event.getRawY() <= rect.top
                            || event.getRawX() >= rect.right || event.getRawX() <= rect.left) {
                        if (dismissListener != null) {
                            dismissListener.onClick(v);
                        }
                        dismiss();
                        return true;
                    }
                }
                return false;
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (!dismissed) {
                    if (dismissListener != null) {
                        dismissListener.onClick(v);
                    }
                    dismiss();
                }
            }
        });
        if (animate) {
            closeButton.setAlpha(INVISIBLE);
        }
    }

    /**
     * Sets the content view up.
     */
    private void setupContentView() {
        final int contentView = getContentView();
        if (contentView <= 0) {
            return;
        }
        final View content = LayoutInflater.from(getContext()).inflate(contentView, contentContainer, false);
        final ViewGroup contentContainer = createScrollView(this.contentContainer, content);
        contentContainer.addView(content);
    }

    /**
     * Checks whether or not a {@link ScrollView} is necessary and creates it (or not).
     *
     * @param container The content container.
     * @param content   The content view.
     * @return A {@link ScrollView} if content is not already scrollable, the container otherwise.
     */
    @NonNull
    private ViewGroup createScrollView(final ViewGroup container, final View content) {
        // If content is already scrollable, don't add a ScrollView.
        if (!shouldAddScrollView(content)) {
            return container;
        }
        final ScrollView scrollView = new ScrollView(container.getContext());
        scrollView.setOverScrollMode(ScrollView.OVER_SCROLL_IF_CONTENT_SCROLLS);
        scrollView.setBackgroundDrawable(null);
        container.addView(scrollView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        return scrollView;
    }

    @SuppressWarnings("CPD-START")
    /**
     * Sets the action button up.
     */
    private void setupActionButton(final boolean animate) {
        if (shouldShowActionButton()) {
            actionButton = root.findViewById(R.id.ui_melidialog_action_button);
            actionButton.setText(getActionString());
            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (!dismissed) {
                        getActionClickListener().onClick(v);
                        dismiss();
                    }
                }
            });
            actionButton.setVisibility(View.VISIBLE);
            if (animate) {
                actionButton.setAlpha(INVISIBLE);
            }
        }
    }

    /**
     * Sets the secondary exit button up.
     */
    private void setupSecondaryExitButton(final boolean animate) {
        if (shouldShowSecondaryExit()) {
            secondaryExitButton = root.findViewById(R.id.ui_melidialog_secondary_exit_button);
            secondaryExitButton.setText(getSecondaryExitString());
            secondaryExitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (!dismissed) {
                        getSecondaryExitClickListener().onClick(v);
                        dismiss();
                    }
                }
            });
            secondaryExitButton.setVisibility(View.VISIBLE);
            if (animate) {
                secondaryExitButton.setAlpha(INVISIBLE);
            }

            setBottomPadding(R.dimen.ui_dialog_bottom_padding_with_secondary_exit);
        }
    }

    @SuppressWarnings("CPD-END")

    /**
     * Whether or not action button should be shown.
     *
     * @return {@code true} if it should be show, {@code false} otherwise.
     */
    /* default */ boolean shouldShowActionButton() {
        return !TextUtils.isEmpty(getActionString()) && getActionClickListener() != null;
    }

    /**
     * Whether or not secondary exit button should be shown.
     *
     * @return {@code true} if it should be show, {@code false} otherwise.
     */
    /* default */ boolean shouldShowSecondaryExit() {
        return !TextUtils.isEmpty(getSecondaryExitString()) && getSecondaryExitClickListener() != null;
    }

    /**
     * Whether or not a ScrollView should be added.
     *
     * @param content The content view.
     * @return {@code true} if it should be added, {@code false} otherwise.
     */
    /* default */ boolean shouldAddScrollView(@Nullable final View content) {
        return shouldScroll() && !(content instanceof ScrollView)
                && !(content instanceof ScrollingView) && !(content instanceof AbsListView);
    }

    /**
     * Reduce the bottom padding.
     */
    private void setBottomPadding(@DimenRes final int dimenRes) {
        final Resources res = getResources();
        final int sideMargin = res.getDimensionPixelSize(R.dimen.ui_dialog_side_padding);
        final int verticalMargin = res.getDimensionPixelSize(R.dimen.ui_dialog_vertical_padding);
        dialogContainer.setPadding(sideMargin,
                verticalMargin,
                sideMargin,
                res.getDimensionPixelSize(dimenRes));
    }

    /**
     * Sets custom behaviour for the back button to play the out animation when the dialog is dismissed.
     */
    private void setupAnimationOnBackPressed() {
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(final DialogInterface dialog, final int keyCode, final KeyEvent event) {
                if (!dismissed && keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (getOnDismissListener() != null) {
                        getOnDismissListener().onClick(getView());
                    }
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Listen for keyboard events.
     */
    private void setupListenerForKeyboardEvents() {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new KeyboardEventListener(root, this));
    }

    /**
     * In animation.
     */
    private void animateIn() {
        if (shouldAnimate()) {
            ViewCompat.setAlpha(root, INVISIBLE);
            ViewCompat.animate(root).alpha(VISIBLE).setDuration(FADE_ANIMATION_DURATION).start();

            final Animation anim = new TranslateAnimation(0, 0, getResources().getDimensionPixelSize(R.dimen.ui_dialog_y_translation), 0);
            anim.setDuration(TRANSLATE_ANIMATION_DURATION);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(final Animation animation) {
                    // Do nothing.
                }

                @Override
                public void onAnimationEnd(final Animation animation) {
                    if (shouldShowSecondaryExit()) {
                        ViewCompat.animate(secondaryExitButton).alpha(VISIBLE).setDuration(TRANSLATE_ANIMATION_DURATION).start();
                    }
                    if (shouldShowActionButton()) {
                        ViewCompat.animate(actionButton).alpha(VISIBLE).setDuration(TRANSLATE_ANIMATION_DURATION).start();
                    }
                    ViewCompat.animate(closeButton).alpha(VISIBLE).setDuration(TRANSLATE_ANIMATION_DURATION).start();
                }

                @Override
                public void onAnimationRepeat(final Animation animation) {
                    // Do nothing.
                }
            });
            dialogContainer.startAnimation(anim);
        }
    }

    /**
     * Out animation.
     *
     * @return Whether the view was animated or not.
     */
    private boolean animateOut() {
        if (shouldAnimate() && isVisible() && root != null) {
            ViewCompat.animate(root).alpha(INVISIBLE).setDuration(FADE_ANIMATION_DURATION).start();

            final Animation anim = new TranslateAnimation(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.ui_dialog_y_translation));
            anim.setDuration(TRANSLATE_ANIMATION_DURATION);
            dialogContainer.startAnimation(anim);
            return true;
        } else {
            return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyboardShown() {
        if (shouldShowSecondaryExit()) {
            setBottomPadding(R.dimen.ui_dialog_bottom_padding_with_keyboard_and_secondary_exit);
        } else {
            setBottomPadding(R.dimen.ui_dialog_bottom_padding_with_keyboard);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onKeyboardHidden() {
        if (shouldShowSecondaryExit()) {
            setBottomPadding(R.dimen.ui_dialog_bottom_padding_with_secondary_exit);
        } else {
            setBottomPadding(R.dimen.ui_dialog_vertical_padding);
        }
    }

    @Override
    public String toString() {
        return "MeliDialog{"
                + "root=" + root
                + ", dialogContainer=" + dialogContainer
                + ", contentContainer=" + contentContainer
                + ", titleContainer=" + titleContainer
                + ", secondaryExitButton=" + secondaryExitButton
                + ", closeButton=" + closeButton
                + ", actionButton=" + actionButton
                + ", dismissed=" + dismissed
                + '}';
    }

    /**
     * Gets contentDescription for close button (for accessibility)
     */

    @Nullable
    public String getCloseButtonContentDescription() {
        CharSequence closeButtonContentDescription = root.findViewById(R.id.ui_melidialog_close_button).getContentDescription();
        if (closeButtonContentDescription == null) {
            return null;
        } else {
            return closeButtonContentDescription.toString(); }
    }

    /**
     * Sets contentDescription for close button (for accessibility)
     */
    public void setCloseButtonContentDescription(String contentDescription) {
        closeButton = root.findViewById(R.id.ui_melidialog_close_button);
        closeButton.setContentDescription(contentDescription);
    }
}
