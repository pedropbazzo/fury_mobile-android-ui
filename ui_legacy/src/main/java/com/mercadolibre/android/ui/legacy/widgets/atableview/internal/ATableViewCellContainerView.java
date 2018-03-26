package com.mercadolibre.android.ui.legacy.widgets.atableview.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableViewCell;
import com.mercadolibre.android.ui.legacy.widgets.atableview.view.ATableViewCell.ATableViewCellSelectionStyle;

@Deprecated
public class ATableViewCellContainerView extends LinearLayout {

    public ATableViewCellContainerView(Context context) {
        super(context);
    }

    public ATableViewCellContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private static ATableViewCell getContainerCell(View view) {
        ViewParent parent = view.getParent();
        while (parent != null) {
            if (parent instanceof ATableViewCell) return (ATableViewCell) parent;
            parent = parent.getParent();
        }

        return null;
    }

    @Override
    public void setPressed(boolean pressed) {
        if (pressed) {
            ATableViewCell cell = getContainerCell(this);
            if (cell != null && cell.getSelectionStyle() == ATableViewCellSelectionStyle.None) {
                return;
            }
        }

        super.setPressed(pressed);
    }
}
