package com.mercadolibre.android.ui.widgets.contextual_menu;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.mercadolibre.android.ui.R;

/**
 * An icon view to be shown in the {@link ContextualMenu}.
 * The background color can be set programatically.
 * Also supports tinting the icon when hovered.
 */
@SuppressWarnings("ViewConstructor")
public final class ContextualMenuOption extends LinearLayout {

    private final ImageView image;
    private String tooltip;
    private Drawable icon;
    private int hoveredTintColor;
    private boolean usesHoveredTint;

    /**
     * @param context          the context
     * @param icon             the icon that will be displayed
     * @param tooltip          the text for the label of the option
     * @param hoveredTintColor if we want to define a different hover color to the option
     */
    public ContextualMenuOption(final Context context, final int icon, final String tooltip, final int hoveredTintColor) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.ui_layout_contextual_menu_icon, this);
        this.image = (ImageView) findViewById(R.id.ui_contextual_menu_icon);
        this.tooltip = tooltip;
        setIcon(icon);
        if (hoveredTintColor != 0) {
            setHoveredTintColor(hoveredTintColor);
        }
    }

    public int getHoveredTintColor() {
        return hoveredTintColor;
    }

    /**
     * Set a tint color for when the option is hovered
     *
     * @param hoveredTintColor the tint color
     */
    public void setHoveredTintColor(final int hoveredTintColor) {
        usesHoveredTint = true;
        this.hoveredTintColor = hoveredTintColor;
    }

    public Drawable getIcon() {
        return icon;
    }

    /**
     * Set an icon for the option
     *
     * @param iconId the icon
     */
    public void setIcon(final int iconId) {
        final Drawable drawable = ContextCompat.getDrawable(getContext(), iconId);
        drawable.mutate();
        this.icon = drawable;
        image.setImageDrawable(drawable);
    }

    /**
     * Set if the option is currently being hovered
     * @param isHovered is the option hovered
     */
    public void setHovered(final boolean isHovered) {
        if (usesHoveredTint) {
            icon.setColorFilter(isHovered ? new PorterDuffColorFilter(hoveredTintColor, PorterDuff.Mode.SRC_IN) : null);
            image.setImageDrawable(icon);
        }
    }

    /**
     * Set a fill color for the option
     * @param color the color
     */
    public void setFillColor(final int color) {
        final GradientDrawable background = (GradientDrawable) image.getBackground();
        background.setColor(color);
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(final String tooltip) {
        this.tooltip = tooltip;
    }

    public ImageView getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "ContextualMenuOption{"
                + "image=" + image
                + ", tooltip='" + tooltip + '\''
                + ", icon=" + icon
                + ", hoveredTintColor=" + hoveredTintColor
                + ", usesHoveredTint=" + usesHoveredTint
                + '}';
    }
}
