package com.mercadolibre.android.ui.example.ui.widgets.contextual_menu;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;
import com.mercadolibre.android.ui.utils.UIUtil;
import com.mercadolibre.android.ui.widgets.contextual_menu.ContextualMenu;
import com.mercadolibre.android.ui.widgets.contextual_menu.ContextualMenuInfo;
import com.mercadolibre.android.ui.widgets.contextual_menu.ContextualMenuListener;
import com.mercadolibre.android.ui.widgets.contextual_menu.ContextualMenuOption;

public class ContextualMenuActivity extends BaseActivity {
    /**
     * X/Y Position of the touch event
     */
    private PointF positionXY;
    private PopupWindow contextualMenuPopup;
    private ContextualMenu contextualMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contextual_menu);
        setContextualMenu();
    }

    private void setContextualMenu() {
        Button button = (Button) findViewById(R.id.ui_contextual_menu_button);

        assert button != null;
        // In order to show the Contextual Menu, you need to have the exact screen touch position
        button.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // We save the touch X/Y position
                positionXY = new PointF(event.getRawX(), event.getRawY());

                // If ContextualMenuPopup is showing, we handle the options touch event
                if (contextualMenuPopup != null) {
                    contextualMenu.handleTouchEvent(event);
                    return true;
                }
                return false;
            }
        });

        button.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // This click context allows you to send information that will help you use
                // properly this feature.
                ContextualMenuInfo clickContext = new ContextualMenuInfo();
                clickContext.setId("button");
                clickContext.setTouch(positionXY);

                ContextualMenuListener listener = new ContextualMenuListener() {
                    @Override
                    public void onMenuItemClick(ContextualMenuInfo clickContext) {
                        switch (clickContext.getChildAt()) {
                            case 0:
                                Toast.makeText(getApplicationContext(), "Option 1 selected", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(getApplicationContext(), "Option 2 selected", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }

                    @Override
                    public void onShowPinMenu() {

                    }

                    @Override
                    public void onHidePinMenu() {
                        if (contextualMenuPopup != null) {
                            contextualMenuPopup.dismiss();
                            contextualMenuPopup = null;
                        }
                    }
                };

                contextualMenu = new ContextualMenu(getApplicationContext(), clickContext, listener);
                setContextualMenuOptions(contextualMenu);

                if (contextualMenuPopup == null) {
                    contextualMenuPopup = new PopupWindow(contextualMenu, UIUtil.getPopupHeight(getBaseContext()), ViewGroup.LayoutParams.MATCH_PARENT);
                    contextualMenuPopup.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.NO_GRAVITY, 0, 0);
                }
                return false;
            }
        });
    }

    /**
     * Shows how to set an option in a Long Press Menu
     *
     * @param contextualMenu container
     */
    private void setContextualMenuOptions(ContextualMenu contextualMenu) {
        // You pass the icon, the tooltip and a hovered color if necessary
        ContextualMenuOption optionMenu = new ContextualMenuOption(this, R.drawable.ic_history_holo_light, "Option 1", 0);
        ContextualMenuOption optionMenu2 = new ContextualMenuOption(this, R.drawable.ic_history_holo_light, "Option 2", 0);

        contextualMenu.addIcons(optionMenu, optionMenu2);
    }
}