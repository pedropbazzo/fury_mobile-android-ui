package com.mercadolibre.android.ui.drawee;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.util.AttributeSet;
import android.view.View;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mercadolibre.android.ui.drawee.state.State;

public class StateDraweeView extends SimpleDraweeView {

    private @Nullable OnEnabledListener enabledListener;
    private @Nullable OnLevelListener levelListener;
    private static final String MESSAGE = "This class doesnt support setting images, please use #setState";

    @VisibleForTesting
    @Nullable
    /* default */ State state;

    public StateDraweeView(@NonNull final Context context, @NonNull final State state) {
        super(context);
        this.state = state;
    }

    public StateDraweeView(final Context context, final GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public StateDraweeView(final Context context) {
        super(context);
    }

    public StateDraweeView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        checkAttrs(attrs);
    }

    public StateDraweeView(final Context context, final AttributeSet attrs, final int defStyle) {
        super(context, attrs, defStyle);
        checkAttrs(attrs);
    }

    public StateDraweeView(final Context context, final AttributeSet attrs, final int defStyleAttr,
        final int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        checkAttrs(attrs);
    }

    private void checkAttrs(@Nullable AttributeSet attrs) {
        if (attrs != null) {
            TypedArray gdhAttrs = getContext()
                .obtainStyledAttributes(attrs, com.facebook.drawee.R.styleable.SimpleDraweeView);

            try {
                if (gdhAttrs
                    .hasValue(com.facebook.drawee.R.styleable.SimpleDraweeView_actualImageUri) ||
                    gdhAttrs.hasValue(
                        com.facebook.drawee.R.styleable.SimpleDraweeView_actualImageResource)) {
                    throw new IllegalStateException(
                        MESSAGE);
                }
            } finally {
                gdhAttrs.recycle();
            }
        }
    }

    public void setState(@NonNull State state) {
        if (this.state != null) {
            this.state.detach(this);
        }

        this.state = state;

        if (isViewAttachedToWindow()) {
            this.state.attach(this);
        }
    }

    @VisibleForTesting
    boolean isViewAttachedToWindow() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return getWindowToken() != null;
        } else {
            return isAttachedToWindow();
        }
    }

    @Override
    protected void onAttach() {
        super.onAttach();
        if (state != null) {
            state.attach(this);
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        if (state != null) {
            state.detach(this);
        }
    }

    @Override
    public void setImageLevel(final int level) {
        super.setImageLevel(level);
        if (levelListener != null) {
            levelListener.onLevelChange(this, level);
        }
    }

    @Override
    public void setEnabled(final boolean enabled) {
        super.setEnabled(enabled);
        if (enabledListener != null) {
            enabledListener.onEnableChange(this, enabled);
        }
    }

    /**
     * When using this listener, always detach when you wont be having the view anymore, since it
     * keeps a strong reference to the parameter class
     *
     * @param listener strong reference to listener
     */
    public void setOnLevelListener(@Nullable OnLevelListener listener) {
        this.levelListener = listener;
    }

    /**
     * When using this listener, always detach when you wont be having the view anymore, since it
     * keeps a strong reference to the parameter class
     *
     * @param listener strong reference to listener
     */
    public void setOnEnabledListener(@Nullable OnEnabledListener listener) {
        this.enabledListener = listener;
    }

    /**
     * Deprecated methods, overriden for forcing #setState
     */

    @Override
    public void setImageDrawable(final Drawable drawable) {
        throw new IllegalStateException(MESSAGE);
    }

    @Override
    public void setImageBitmap(final Bitmap bm) {
        throw new IllegalStateException(MESSAGE);
    }

    @Override
    public void setImageResource(final int resId) {
        throw new IllegalStateException(MESSAGE);
    }

    @Override
    public void setImageURI(final Uri uri) {
        throw new IllegalStateException(MESSAGE);
    }

    @Override
    public void setActualImageResource(@DrawableRes final int resourceId) {
        throw new IllegalStateException(MESSAGE);
    }

    @Override
    public void setActualImageResource(@DrawableRes final int resourceId,
        @javax.annotation.Nullable final Object callerContext) {
        throw new IllegalStateException(MESSAGE);
    }

    @Override
    public void setImageURI(final Uri uri, @javax.annotation.Nullable final Object callerContext) {
        throw new IllegalStateException(MESSAGE);
    }

    @Override
    public void setImageURI(@javax.annotation.Nullable final String uriString) {
        throw new IllegalStateException(MESSAGE);
    }

    @Override
    public void setImageURI(@javax.annotation.Nullable final String uriString,
        @javax.annotation.Nullable final Object callerContext) {
        throw new IllegalStateException(MESSAGE);
    }

    public interface OnEnabledListener {
        void onEnableChange(@NonNull View view, boolean enabled);
    }

    public interface OnLevelListener {
        void onLevelChange(@NonNull View view, int level);
    }

    @Override
    public String toString() {
        return "StateDraweeView{" +
            "enabledListener=" + enabledListener +
            ", levelListener=" + levelListener +
            ", state=" + state +
            '}';
    }
}
