package com.mercadolibre.android.ui.example.ui;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mercadolibre.android.ui.example.R;

public class SimpleViewHolder extends RecyclerView.ViewHolder {

    private final TextView tv;

    public SimpleViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.ui_simple_list_item_text);
    }

    public void bind(final String text) {
        tv.setText(text);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tv.getContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
