package com.mercadolibre.android.ui.widgets.contextual_menu;

import android.graphics.PointF;
import android.support.v4.content.ContextCompat;

import com.mercadolibre.android.testing.AbstractRobolectricTest;
import com.mercadolibre.android.ui.R;

import org.junit.Before;
import org.junit.Test;
import org.robolectric.RuntimeEnvironment;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test class for {@link ContextualMenu}.
 */
public class ContextualMenuTest extends AbstractRobolectricTest {

    private ContextualMenu contextualMenu;

    @Before
    public void setUp() {
        final ContextualMenuInfo contextualMenuInfo = new ContextualMenuInfo();
        contextualMenuInfo.setId("test");
        contextualMenuInfo.setTouch(new PointF(0, 0));
        contextualMenu = new ContextualMenu(RuntimeEnvironment.application, contextualMenuInfo, null);
    }

    @Test
    public void testNotNull() {
        assertNotNull(contextualMenu);
    }

    @Test
    public void testCreateContextualMenuOption() {
        final int imageId = R.drawable.ic_image;
        final String tooltip = "Option 1";
        final int hoveredColor = 0;

        final ContextualMenuOption option = new ContextualMenuOption(RuntimeEnvironment.application, imageId, tooltip, hoveredColor);

        assertEquals(ContextCompat.getDrawable(RuntimeEnvironment.application, imageId), option.getIcon());
        assertEquals(hoveredColor, option.getHoveredTintColor());
        assertEquals(tooltip, option.getTooltip());
    }


    @Test
    public void testHovered0UsesDefaultColor() {
        int imageId = R.drawable.ic_image;
        String tooltip = "Option 1";
        int hoveredColor = 0;

        final ContextualMenuOption option = new ContextualMenuOption(RuntimeEnvironment.application, imageId, tooltip, hoveredColor);

        assertEquals(hoveredColor, option.getHoveredTintColor());
    }

    @Test
    public void testChangeHoveredColorToOption() {
        final int imageId = R.drawable.ic_image;
        final String tooltip = "Option 1";
        final int colorId = R.color.ui_meli_yellow;
        final int hoveredColor = 0;

        final ContextualMenuOption option = new ContextualMenuOption(RuntimeEnvironment.application, imageId, tooltip, hoveredColor);
        option.setHoveredTintColor(colorId);

        assertEquals(colorId, option.getHoveredTintColor());
    }

    @Test
    public void testChangeTooltipToOption() {
        final int imageId = R.drawable.ic_image;
        final String tooltip = "Option 1";
        final String tooltip2 = "Option 2";
        final int hoveredColor = 0;

        final ContextualMenuOption option = new ContextualMenuOption(RuntimeEnvironment.application, imageId, tooltip, hoveredColor);
        option.setTooltip(tooltip2);

        assertEquals(tooltip2, option.getTooltip());
    }

    @Test
    public void testSetListenerToContextualMenu() {
        contextualMenu.setContextualMenuClickListener(new ContextualMenuListener() {
            @Override
            public void onMenuItemClick(final ContextualMenuInfo clickContext) {
                //Do nothing
            }

            @Override
            public void onShowPinMenu() {
                //Do nothing
            }

            @Override
            public void onHidePinMenu() {
                //Do nothing
            }
        });

        assertNotNull(contextualMenu.getContextualMenuClickListener());
    }

    @Test
    public void testSetContextualMenuInfo() {
        final ContextualMenuInfo contextualMenuInfo = new ContextualMenuInfo();
        contextualMenuInfo.setId("item");
        contextualMenuInfo.setTouch(new PointF(0, 1));
        contextualMenuInfo.setClickContext(new HashMap<String, Object>());

        contextualMenu.setContextualMenuInfo(contextualMenuInfo);

        assertNotNull(contextualMenu.getContextualMenuInfo());
    }

    @Test
    public void testCreateContextualMenuInfoAndSet() {
        final String id = "item";
        final ContextualMenuInfo contextualMenuInfo = new ContextualMenuInfo();
        contextualMenuInfo.setId(id);
        contextualMenuInfo.setTouch(new PointF(0, 1));
        contextualMenuInfo.setClickContext(new HashMap<String, Object>());

        contextualMenu.setContextualMenuInfo(contextualMenuInfo);
        assertEquals(id, contextualMenuInfo.getId());
        assertEquals(0f, contextualMenuInfo.getTouch().x, 0);
        assertEquals(1f, contextualMenuInfo.getTouch().y, 0);
        assertNotNull(contextualMenuInfo.getClickContext());
    }

    @Test
    public void testAddOptionsToContextualMenu() {
        final int imageId = R.drawable.ic_image;
        final String tooltip = "Option 1";
        final int hoveredColor = 0;

        final ContextualMenuOption option1 = new ContextualMenuOption(RuntimeEnvironment.application, imageId, tooltip, hoveredColor);
        final ContextualMenuOption option2 = new ContextualMenuOption(RuntimeEnvironment.application, imageId, tooltip, hoveredColor);
        final ContextualMenuOption option3 = new ContextualMenuOption(RuntimeEnvironment.application, imageId, tooltip, hoveredColor);

        contextualMenu.addIcons(option1, option2, option3);

        assertEquals(4, contextualMenu.getChildCount());
    }

    @Test
    public void testResetContextualMenuOptionViewToDefault() {
        final int imageId = R.drawable.ic_image;
        final String tooltip = "Option 1";
        final int hoveredColor = 0;

        final ContextualMenuOption option = new ContextualMenuOption(RuntimeEnvironment.application, imageId, tooltip, hoveredColor);
        final float scale = 123;
        final float translate = 123;
        option.setScaleX(scale);
        option.setScaleY(scale);
        option.setTranslationX(translate);
        option.setTranslationY(translate);

        contextualMenu.resetContextualMenuOptionView(option);

        assertEquals(1f, option.getScaleX(), 0);
        assertEquals(1f, option.getScaleY(), 0);
        assertEquals(0f, option.getTranslationX(), 0);
        assertEquals(0f, option.getTranslationY(), 0);
    }

    @Test
    public void testSetCenteredContextualMenu() {
        final float x = 100f, y = 150f;
        final PointF point = new PointF(x, y);
        contextualMenu.showCenteredAt(point);

        assertEquals(x, contextualMenu.getCenter().x, 0);
        assertEquals(y, contextualMenu.getCenter().y, 0);
    }
}
