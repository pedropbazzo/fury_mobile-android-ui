package com.mercadolibre.android.ui.widgets.contextual_menu;

import android.graphics.PointF;
import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class ContextualMenuInfo {

    /**
     * The type of object we are implementing this feature. I.E: "item", "button".
     */
    private String id;

    /**
     * Option icon selected.
     */
    private int childAt;

    /**
     * The X/Y position of the touch on the screen
     */
    @NonNull
    private PointF touch = new PointF();

    /**
     * Allows you to pass information that will help you implementing this feature.
     * For instance, if you want to use this feature in a RecyclerView, you may want to pass the
     * position of the item touched in order to show it properly.
     */
    private HashMap<String, Object> clickContext = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Map<String, Object> getClickContext() {
        return clickContext;
    }

    public void setClickContext(final HashMap<String, Object> clickContext) {
        this.clickContext = clickContext;
    }

    public PointF getTouch() {
        return this.touch;
    }

    public void setTouch(final PointF touch) {
        this.touch = touch;
    }

    public void setChildAt(final int childAt) {
        this.childAt = childAt;
    }

    public int getChildAt() {
        return childAt;
    }

    @Override
    public String toString() {
        return "ContextualMenuInfo{"
                + "id='" + id + '\''
                + ", childAt=" + childAt
                + ", touch=" + touch
                + ", clickContext=" + clickContext
                + '}';
    }
}

