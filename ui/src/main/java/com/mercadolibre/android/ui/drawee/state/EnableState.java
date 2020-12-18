package com.mercadolibre.android.ui.drawee.state;

import androidx.annotation.NonNull;
import android.view.View;
import com.mercadolibre.android.ui.drawee.StateDraweeView;
import com.mercadolibre.android.ui.utils.facebook.fresco.FrescoImageController;

/**
 * State based on the view enabled/disabled
 */
public class EnableState extends BinaryState {

    @Override
    public void attach(@NonNull final StateDraweeView view) {
        view.setOnEnabledListener(new StateDraweeView.OnEnabledListener() {
            @Override
            public void onEnableChange(@NonNull View view1, final boolean enabled) {
                setDrawable((StateDraweeView) view1);
            }
        });
        setDrawable(view);
    }

    @Override
    public void detach(@NonNull final StateDraweeView view) {
        view.setOnEnabledListener(null);
    }

    /* default */ void setDrawable(@NonNull StateDraweeView view) {
        FrescoImageController.create()
            .load(getDrawable(view.isEnabled() ? STATE_ON : STATE_OFF))
            .into(view);
    }

}
