package com.mercadolibre.android.ui.drawee.state;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class BinaryState extends State {

    public static final int STATE_OFF = 0;
    public static final int STATE_ON = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_ON, STATE_OFF})
    public @interface State {}

    @Override
    protected boolean isValidKey(@State final int key) {
        return key == STATE_OFF || key == STATE_ON;
    }

}
