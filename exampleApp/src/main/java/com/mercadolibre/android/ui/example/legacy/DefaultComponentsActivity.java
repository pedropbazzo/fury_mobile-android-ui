package com.mercadolibre.android.ui.example.legacy;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.mercadolibre.android.ui.example.BaseActivity;
import com.mercadolibre.android.ui.example.R;

public class DefaultComponentsActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components);
    }


    private void initListeners() {
        OnClickListener onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isEnabled())
                    v.setSelected(!v.isSelected());
            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.option_1) {
            Toast.makeText(this, "Option 1 clicked!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.option_2) {
            Toast.makeText(this, "Option 2 clicked!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.option_3) {
            Toast.makeText(this, "Option 3 clicked!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.option_4) {
            Toast.makeText(this, "Option 4 clicked!", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.option_5) {
            Toast.makeText(this, "Option 5 clicked!", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
