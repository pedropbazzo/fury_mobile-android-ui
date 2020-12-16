package com.mercadolibre.android.ui.drawee.state;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import com.mercadolibre.android.ui.drawee.StateDraweeView;
import com.mercadolibre.android.ui.utils.facebook.fresco.FrescoImageController;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * State based on the view focus
 */
public class PressState extends State {

    public static final int STATE_RELEASED = 0;
    public static final int STATE_PRESSED = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATE_PRESSED, STATE_RELEASED})
    public @interface State {}

    @Override
    protected boolean isValidKey(@State final int key) {
        return key == STATE_PRESSED || key == STATE_RELEASED;
    }

    @Override
    public void attach(@NonNull final StateDraweeView view) {
        setDrawable(view, STATE_RELEASED);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, final MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_BUTTON_PRESS:
                    case MotionEvent.ACTION_DOWN:
                        setDrawable((StateDraweeView) v, STATE_PRESSED);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_BUTTON_RELEASE:
                    case MotionEvent.ACTION_UP:
                        setDrawable((StateDraweeView) v, STATE_RELEASED);
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public void detach(@NonNull final StateDraweeView view) {
        view.setOnTouchListener(null);
    }

    /* default */ void setDrawable(@NonNull StateDraweeView view, int key) {
        FrescoImageController.create()
            .load(getDrawable(key))
            .into(view);
    }

}
