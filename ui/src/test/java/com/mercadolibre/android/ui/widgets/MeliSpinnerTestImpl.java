package com.mercadolibre.android.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;

/**
 * This is a subclass of {@MeliSpinner} that stubs the {@link #start()} method (read below),
 * created as a workaround to a robolectric issue.
 */
public class MeliSpinnerTestImpl extends MeliSpinner {

    public MeliSpinnerTestImpl(final Context context) {
        super(context);
    }

    public MeliSpinnerTestImpl(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public MeliSpinnerTestImpl(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void start() {
        /** Stub this method to do nothing due to a problem with robolectric and indefinite
         * animations.
         * @see https://github.com/robolectric/robolectric/issues/1947
         * TODO: See if they fix this in Robolectric 3.1.
         */
    }
}
