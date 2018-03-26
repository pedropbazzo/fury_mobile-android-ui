package com.mercadolibre.android.ui.drawee.state;

import android.content.res.Resources;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.SparseIntArray;
import com.mercadolibre.android.ui.drawee.StateDraweeView;

public abstract class State {

    @VisibleForTesting
    @NonNull
    /* default */ SparseIntArray states;

    public State() {
        states = new SparseIntArray();
    }

    public @NonNull State add(int key, @DrawableRes int drawable) {
        if (!isValidKey(key)) {
            throw new IllegalStateException("Invalid key: " + key + " used for: " + getClass().getName() +
                " when adding drawable: " + drawable);
        }
        states.put(key, drawable);
        return this;
    }

    protected @DrawableRes int getDrawable(int key) {
        int drawable = states.get(key, -1);

        if (drawable == -1) {
            throw new Resources.NotFoundException("Drawable resource not found for key: " + key);
        }

        return drawable;
    }

    protected abstract boolean isValidKey(int key);

    public abstract void attach(@NonNull StateDraweeView view);
    public abstract void detach(@NonNull StateDraweeView view);

    @Override
    public String toString() {
        return "State{" +
            "states=" + states +
            '}';
    }
}
