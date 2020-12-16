package com.mercadolibre.android.ui.drawee.state;

import androidx.annotation.NonNull;
import android.view.View;
import com.mercadolibre.android.ui.drawee.StateDraweeView;
import com.mercadolibre.android.ui.utils.facebook.fresco.FrescoImageController;

/**
 * State based on the view focus
 */
public class FocusState extends BinaryState {

    @Override
    public void attach(@NonNull final StateDraweeView view) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(final View v, final boolean hasFocus) {
                setDrawable((StateDraweeView) v);
            }
        });
        setDrawable(view);
    }

    @Override
    public void detach(@NonNull final StateDraweeView view) {
        view.setOnFocusChangeListener(null);
    }

    /* default */ void setDrawable(@NonNull StateDraweeView view) {
        FrescoImageController.create()
            .load(getDrawable(view.isFocused() ? STATE_ON : STATE_OFF))
            .into(view);
    }

}
