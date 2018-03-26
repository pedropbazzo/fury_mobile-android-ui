package com.mercadolibre.android.ui.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mercadolibre.android.ui.R;
import com.mercadolibre.android.ui.font.Font;
import com.mercadolibre.android.ui.font.TypefaceHelper;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.mercadolibre.android.ui.widgets.MeliSnackbar.Type.ERROR;
import static com.mercadolibre.android.ui.widgets.MeliSnackbar.Type.MESSAGE;
import static com.mercadolibre.android.ui.widgets.MeliSnackbar.Type.SUCCESS;

/**
 * Snackbar wrapper that allows extra customization.
 *
 * @since 24/2/16.
 */
public final class MeliSnackbar {

    private final Snackbar snackbar;

    /**
     * Predefined types to configure the MeliSnackbar.
     */

    /**
     * @deprecated
     * DO NOT CHANGE ORDER
     */
    @Deprecated
    public enum Type {
        MESSAGE,
        SUCCESS,
        ERROR,
    }

    @SuppressWarnings("PMD.RedundantFieldInitializer")
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SnackbarType.MESSAGE, SnackbarType.SUCCESS, SnackbarType.ERROR})

    public @interface SnackbarType {
        /* default */ int MESSAGE = 0;
        /* default */ int SUCCESS = 1;
        /* default */ int ERROR = 2;
    }

    private MeliSnackbar(@NonNull final Snackbar snackbar, @SnackbarType final int type) {
        this.snackbar = snackbar;
        @ColorRes final int snackbarColor;
        switch (type){
            case SnackbarType.SUCCESS:
                snackbarColor = R.color.ui_components_success_color;
                break;
            case SnackbarType.ERROR:
                snackbarColor = R.color.ui_components_error_color;
                break;
            case SnackbarType.MESSAGE:
            default:
                snackbarColor=R.color.ui_components_black_color;
        }

        changeSnackbarBackground(snackbarColor);
        configureView();
    }

    /**
     * @deprecated instead use:  make(View, CharSequence, int, @SnackbarType int) {
     * Make a Snackbar to display a message.
     * <p>
     * Having a {@link android.support.design.widget.CoordinatorLayout} in your view hierarchy allows
     * Snackbar to enable certain features, such as swipe-to-dismiss and automatically moving of
     * widgets like {@link android.support.design.widget.FloatingActionButton}.
     *
     * @param view     The view o find a parent from.
     * @param text     The text to show. Can be formatted text.
     * @param duration How long to display the message. One of Snackbar.LENGTH_SHORT,
     *                 Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE or a custom duration in milliseconds.
     * @param type     The {@link MeliSnackbar.Type}.
     * @return The MeliSnackbar created.
     */
    @Deprecated
    @NonNull
    public static MeliSnackbar make(@NonNull final View view,
                                    @NonNull final CharSequence text,
                                    @BaseTransientBottomBar.Duration final int duration,
                                    final Type type) {
        return new MeliSnackbar(Snackbar.make(view, text, duration), type.ordinal());
    }

    /**
     * Make a Snackbar to display a message.
     * <p>
     * Having a {@link android.support.design.widget.CoordinatorLayout} in your view hierarchy allows
     * Snackbar to enable certain features, such as swipe-to-dismiss and automatically moving of
     * widgets like {@link android.support.design.widget.FloatingActionButton}.
     *
     * @param view     The view o find a parent from.
     * @param text     The text to show. Can be formatted text.
     * @param duration How long to display the message. One of Snackbar.LENGTH_SHORT,
     *                 Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE or a custom duration in milliseconds.
     * @param type     The {@link SnackbarType}.
     * @return The MeliSnackbar created.
     */
    @NonNull
    public static MeliSnackbar make(@NonNull final View view,
                                    @NonNull final CharSequence text,
                                    @BaseTransientBottomBar.Duration final int duration,
                                    @SnackbarType final int type) {
        return new MeliSnackbar(Snackbar.make(view, text, duration), type);
    }

    /**
     * Make a Snackbar to display a message.
     * <p>
     * Having a {@link android.support.design.widget.CoordinatorLayout} in your view hierarchy allows
     * Snackbar to enable certain features, such as swipe-to-dismiss and automatically moving of
     * widgets like {@link android.support.design.widget.FloatingActionButton}.
     *
     * @param view     The view to find a parent from.
     * @param text     The text to show. Can be formatted text.
     * @param duration How long to display the message. One of Snackbar.LENGTH_SHORT,
     *                 Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE or a custom duration in milliseconds.
     * @return The MeliSnackbar created.
     */
    @NonNull
    public static MeliSnackbar make(@NonNull final View view,
                                    @NonNull final CharSequence text,
                                    @BaseTransientBottomBar.Duration final int duration) {
        return make(view, text, duration, SnackbarType.MESSAGE);
    }

    /**
     * @deprecated instead use:  make(View, int, int, @SnackbarType int)
     * Make a Snackbar to display a message.
     * <p>
     * Having a {@link android.support.design.widget.CoordinatorLayout} in your view hierarchy allows
     * Snackbar to enable certain features, such as swipe-to-dismiss and automatically moving of
     * widgets like {@link android.support.design.widget.FloatingActionButton}.
     *
     * @param view     The view o find a parent from.
     * @param resId    The resource id of the string resource to use. Can be formatted text.
     * @param duration How long to display the message. One of Snackbar.LENGTH_SHORT,
     *                 Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE or a custom duration in milliseconds.
     * @param type     The {@link MeliSnackbar.Type}.
     * @return The MeliSnackbar created.
     */
    @Deprecated
    @NonNull
    public static MeliSnackbar make(@NonNull final View view,
                                    @StringRes final int resId,
                                    @BaseTransientBottomBar.Duration final int duration,
                                    final Type type) {
        return make(view, view.getResources().getText(resId), duration, type.ordinal());
    }

    /**
     * Make a Snackbar to display a message.
     * <p>
     * Having a {@link android.support.design.widget.CoordinatorLayout} in your view hierarchy allows
     * Snackbar to enable certain features, such as swipe-to-dismiss and automatically moving of
     * widgets like {@link android.support.design.widget.FloatingActionButton}.
     *
     * @param view     The view o find a parent from.
     * @param resId    The resource id of the string resource to use. Can be formatted text.
     * @param duration How long to display the message. One of Snackbar.LENGTH_SHORT,
     *                 Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE or a custom duration in milliseconds.
     * @param type     The {@link SnackbarType}.
     * @return The MeliSnackbar created.
     */

    @NonNull
    public static MeliSnackbar make(@NonNull final View view,
                                    @StringRes final int resId,
                                    @BaseTransientBottomBar.Duration final int duration,
                                    @SnackbarType final int type) {
        return make(view, view.getResources().getText(resId), duration, type);
    }

    /**
     * Make a Snackbar to display a message.
     * <p>
     * Having a {@link android.support.design.widget.CoordinatorLayout} in your view hierarchy allows
     * Snackbar to enable certain features, such as swipe-to-dismiss and automatically moving of
     * widgets like {@link android.support.design.widget.FloatingActionButton}.
     *
     * @param view     The view o find a parent from.
     * @param resId    The resource id of the string resource to use. Can be formatted text.
     * @param duration How long to display the message. One of Snackbar.LENGTH_SHORT,
     *                 Snackbar.LENGTH_LONG, Snackbar.LENGTH_INDEFINITE or a custom duration in milliseconds.
     * @return The MeliSnackbar created.
     */
    @NonNull
    public static MeliSnackbar make(@NonNull final View view,
                                    @StringRes final int resId,
                                    @BaseTransientBottomBar.Duration final int duration) {
        return make(view, resId, duration, SnackbarType.MESSAGE);
    }

    /**
     * Update the text in this Snackbar.
     *
     * @param text The new text.
     * @return The snackbar.
     */
    @NonNull
    public MeliSnackbar setText(final CharSequence text) {
        snackbar.setText(text);
        return this;
    }

    /**
     * Update the text in this Snackbar.
     *
     * @param resId The new text.
     * @return The snackbar.
     */
    @NonNull
    public MeliSnackbar setText(@StringRes final int resId) {
        snackbar.setText(resId);
        return this;
    }

    /**
     * Set the action to be displayed in this Snackbar.
     *
     * @param text     Text to display.
     * @param listener Callback to be invoked when the action is clicked.
     * @return The snackbar.
     */
    @NonNull
    public MeliSnackbar setAction(final CharSequence text, final View.OnClickListener listener) {
        snackbar.setAction(text, listener);
        return this;
    }

    /**
     * Set the action to be displayed in this Snackbar.
     *
     * @param resId    String resource to display.
     * @param listener Callback to be invoked when the action is clicked.
     * @return The snackbar.
     */
    @NonNull
    public MeliSnackbar setAction(@StringRes final int resId, final View.OnClickListener listener) {
        snackbar.setAction(resId, listener);
        return this;
    }

    /**
     * Sets a callback to be called then the snackbar changes its visibility.
     *
     * @param callback callback to be called then the visibility changes.
     * @return the snackbar
     */
    public MeliSnackbar setCallback(final Snackbar.Callback callback) {
        snackbar.setCallback(callback);
        return this;
    }

    /**
     * Sets the text color.
     * @deprecated textColor can't change any more
     * @param colorRes Color resource.
     * @return The snackbar.
     */
    @Deprecated
    @NonNull
    public MeliSnackbar setTextColor(@ColorRes final int colorRes) {
        final TextView textView = getTextView();
        textView.setTextColor(ContextCompat.getColor(textView.getContext(), colorRes));
        return this;
    }

    /**
     * Set the text size to the given value, interpreted as "scaled
     * pixel" units.  This size is adjusted based on the current density and
     * user font size preference.
     * @deprecated textSize can't change any more
     * @param textSize The scaled pixel size.
     * @return The snackbar.
     */
    @Deprecated
    @NonNull
    public MeliSnackbar setTextSize(final float textSize) {
        final TextView textView = getTextView();
        textView.setTextSize(textSize);
        return this;
    }

    /**
     * Sets the text color of the action specified in setAction method.
     * @deprecated actionTextColor can't change any more
     * @param colors ColorStateList.
     * @return The snackbar.
     */
    @Deprecated
    @NonNull
    public MeliSnackbar setActionTextColor(final ColorStateList colors) {
        snackbar.setActionTextColor(colors);
        return this;
    }

    /**
     * Sets the text color of the action specified in setAction method.
     * @deprecated actionTextColor can't change any more
     * @param colorRes Color resource.
     * @return The snackbar.
     */
    @Deprecated
    @NonNull
    public MeliSnackbar setActionTextColor(@ColorRes final int colorRes) {
        snackbar.setActionTextColor(ContextCompat.getColor(snackbar.getView().getContext(), colorRes));
        return this;
    }

    /**
     * Sets the text size of the action specified in setAction method to the given value,
     * interpreted as "scaled pixel" units. This size is adjusted based on the current density and
     * user font size preference.
     * @deprecated actionTextSize can't change any more
     * @param textSize The scaled pixel size.
     * @return The snackbar.
     */
    @Deprecated
    @NonNull
    public MeliSnackbar setActionTextSize(final float textSize) {
        final Button actionButton = getActionButton();
        actionButton.setTextSize(textSize);
        return this;
    }

    /**
     * Set the background color in this Snackbar.
     * @deprecated backgroundColor can't change any more
     * @param colorRes The background color resource.
     * @return The snackbar.
     */
    @Deprecated
    @NonNull
    public MeliSnackbar setBackgroundColor(@ColorRes final int colorRes) {
        snackbar.getView().setBackgroundColor(ContextCompat.getColor(snackbar.getView().getContext(), colorRes));
        return this;
    }

    /**
     * Show the snackbar.
     */
    public void show() {
        snackbar.show();
    }

    /**
     * Dismiss the snackbar.
     */
    public void dismiss() {
        snackbar.dismiss();
    }

    /**
     * Returns whether this {@link MeliSnackbar} is currently being shown.
     *
     * @return true if the snackbar is being shown, false otherwise
     */
    public boolean isShown() {
        return snackbar.isShown();
    }

    /**
     * Returns this {@link MeliSnackbar} view tag.
     *
     * @return the tag
     */
    public Object getTag() {
        return snackbar.getView() == null ? null : snackbar.getView().getTag();
    }

    /**
     * Sets the tag associated with {@link MeliSnackbar} view.
     *
     * @param object the tag
     */
    public void setTag(final Object object) {
        snackbar.getView().setTag(object);
    }

    /**
     * Get snackbar's text TextView.
     *
     * @return Snackbar's TextView.
     */
    private TextView getTextView() {
        return (TextView) snackbar.getView().findViewById(R.id.snackbar_text);
    }

    /**
     * Get snackbar's action button.
     *
     * @return Snackbar's action button.
     */
    private Button getActionButton() {
        return (Button) snackbar.getView().findViewById(R.id.snackbar_action);
    }

    private void changeSnackbarBackground(@ColorRes final int color){
        final View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(snackBarView.getContext(),color));
    }

    /**
     * Configure the view, font and font sizes.
     */
    private void configureView() {
        // Set the font.
        TypefaceHelper.setTypeface(getTextView(), Font.LIGHT);
        TypefaceHelper.setTypeface(getActionButton(), Font.REGULAR);

        final TextView textView = getTextView();
        final Button actionButton = getActionButton();
        final Context context = textView.getContext();
        final Resources res = context.getResources();

        textView.setTextColor(ContextCompat.getColor(context, R.color.ui_components_white_color));
        snackbar.setActionTextColor(ContextCompat.getColor(context, R.color.ui_components_white_color));

        final float textSize = context.getResources().getDimension(R.dimen.ui_fontsize_small);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        actionButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // More line spacing. In older version the text does not center.
            textView.setLineSpacing(res.getDimensionPixelSize(R.dimen.ui_snackbar_text_linespacing), 1);
        }

        // New paddings for text and button.
        final int padding = res.getDimensionPixelSize(R.dimen.ui_snackbar_padding);
        textView.setPadding(padding, 0, padding, 0);
        actionButton.setPadding(padding, padding, padding, padding);

        // Remove the 16dp snackbar padding.
        snackbar.getView().setPadding(0, 0, 0, 0);
    }
}
