package com.mercadolibre.android.ui.example.ui.drawee;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.mercadolibre.android.ui.drawee.StateDraweeView;
import com.mercadolibre.android.ui.drawee.state.EnableState;
import com.mercadolibre.android.ui.drawee.state.FocusState;
import com.mercadolibre.android.ui.drawee.state.LevelState;
import com.mercadolibre.android.ui.drawee.state.PressState;
import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;

public class DraweeStateActivity extends BaseActivity {

    Button enableButton;
    Button levelButton;

    StateDraweeView enableDrawee;
    StateDraweeView focusDrawee;
    StateDraweeView touchDrawee;
    StateDraweeView levelDrawee;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawee_state);

        enableButton = (Button) findViewById(R.id.drawee_button_1);
        levelButton = (Button) findViewById(R.id.drawee_button_4);
        enableDrawee = (StateDraweeView) findViewById(R.id.drawee_state_1);
        focusDrawee = (StateDraweeView) findViewById(R.id.drawee_state_2);
        touchDrawee = (StateDraweeView) findViewById(R.id.drawee_state_3);
        levelDrawee = (StateDraweeView) findViewById(R.id.drawee_state_4);

        enableDrawee.setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_IN);

        enableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (enableDrawee.isEnabled()) {
                    enableDrawee.setEnabled(false);
                    enableButton.setText("Disabled");
                } else {
                    enableDrawee.setEnabled(true);
                    enableButton.setText("Enabled");
                }
            }
        });

        levelButton.setOnClickListener(new View.OnClickListener() {
            int level = 0;

            @Override
            public void onClick(final View v) {
                level = level == 0 ? 1 : 0;
                levelButton.setText("Level " + level);
                levelDrawee.setImageLevel(level);
            }
        });

        enableDrawee.setState(new EnableState()
            .add(EnableState.STATE_ON, R.drawable.webp_add)
            .add(EnableState.STATE_OFF, R.drawable.webp_remove));

        focusDrawee.setState(new FocusState()
            .add(FocusState.STATE_ON, R.drawable.webp_add)
            .add(FocusState.STATE_OFF, R.drawable.webp_remove));

        focusDrawee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                focusDrawee.requestFocus();
            }
        });

        touchDrawee.setState(new PressState()
            .add(PressState.STATE_PRESSED, R.drawable.webp_remove)
            .add(PressState.STATE_RELEASED, R.drawable.webp_add));

        levelDrawee.setState(new LevelState(0 /* Default level */, R.drawable.webp_add)
            .add(1, R.drawable.webp_remove));
    }
}
