package com.mercadolibre.android.ui.drawee.state;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.view.View;
import com.mercadolibre.android.ui.drawee.StateDraweeView;
import com.mercadolibre.android.ui.utils.facebook.fresco.FrescoImageController;

/**
 * State based on the view enabled/disabled
 */
public class LevelState extends State {

    private final int defaultLevel;

    public LevelState(int defaultLevel, @DrawableRes int defaultRes) {
        super();
        this.defaultLevel = defaultLevel;
        add(defaultLevel, defaultRes);
    }

    @Override
    protected boolean isValidKey(final int key) {
        return key >= 0;
    }

    @Override
    public void attach(@NonNull final StateDraweeView view) {
        view.setOnLevelListener(new StateDraweeView.OnLevelListener() {
            @Override
            public void onLevelChange(@NonNull View view1, int level) {
                setDrawable((StateDraweeView) view1, level);
            }
        });
        setDrawable(view, defaultLevel);
    }

    @Override
    public void detach(@NonNull final StateDraweeView view) {
        view.setOnLevelListener(null);
    }

    /* default */ void setDrawable(@NonNull StateDraweeView view, int level) {
        FrescoImageController.create()
            .load(getDrawable(level))
            .into(view);
    }

    @Override
    public String toString() {
        return "LevelState{" +
            "defaultLevel=" + defaultLevel +
            '}';
    }
}
