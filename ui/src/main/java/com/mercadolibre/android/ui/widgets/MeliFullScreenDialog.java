package com.mercadolibre.android.ui.widgets;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mercadolibre.android.ui.R;
import com.mercadolibre.android.ui.widgets.MeliDialog;

/**
 * Created by Mauro Rodriguez on 02/04/2019
 */
public abstract class MeliFullScreenDialog extends DialogFragment {
    private View root;
    private ViewGroup contentContainer;
    /* default */ Button secondaryExitButton;
    /* default */ View closeButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.MeliDialogFullScreen);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final View onCreateView(@NonNull final LayoutInflater inflater,
                                   @Nullable final ViewGroup container,
                                   @Nullable final Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.ui_layout_melifullscreendialog, container, false);
        contentContainer = root.findViewById(R.id.ui_melidialog_content_container);
        setupView();

        return root;
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
     * Sets the view up.
     */
    private void setupView() {
        setUpToolbar();
        setupAnimationOnBackPressed();
        setupSecondaryExitButton();
        setupContentView();
    }

    /**
     * Sets the toolbar.
     */
    private void setUpToolbar() {
        Toolbar toolbar = root.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ui_ic_clear_fullscreen);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View.OnClickListener dismissListener = getOnDismissListener();
                if (dismissListener != null) {
                    dismissListener.onClick(null);
                }
                dismiss();
            }
        });

        activity.setTitle(getTitle());
    }

    /**
     * Sets the content view up.
     */
    private void setupContentView() {
        final int contentView = getContentView();
        if (contentView <= 0) {
            return;
        }
        final View content = LayoutInflater.from(getActivity()).inflate(contentView, contentContainer, false);
        contentContainer.addView(content);
    }

    /**
     * Sets the secondary exit button up.
     */
    private void setupSecondaryExitButton() {
        if (shouldShowSecondaryExit()) {
            secondaryExitButton = root.findViewById(R.id.ui_melidialog_secondary_exit_button);
            secondaryExitButton.setText(getSecondaryExitString());
            secondaryExitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (getSecondaryExitClickListener() != null) {
                        getSecondaryExitClickListener().onClick(v);
                        dismiss();
                    }
                }
            });
            secondaryExitButton.setVisibility(View.VISIBLE);
        }
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
     * Sets custom behaviour for the back button to play the out animation when the dialog is dismissed.
     */
    private void setupAnimationOnBackPressed() {
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(final DialogInterface dialog, final int keyCode, final KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
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

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public String toString() {
        return "MeliFullScreenDialog{"
                + "root=" + root
                + ", contentContainer=" + contentContainer
                + ", secondaryExitButton=" + secondaryExitButton
                + ", closeButton=" + closeButton
                + '}';
    }
}
