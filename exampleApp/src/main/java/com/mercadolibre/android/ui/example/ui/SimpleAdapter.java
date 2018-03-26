package com.mercadolibre.android.ui.example.ui;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mercadolibre.android.ui.example.R;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

    int itemCount;

    public SimpleAdapter(int itemCount) {
        this.itemCount = itemCount;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.bind("Dummy item " + position);
    }

    @Override
    public int getItemCount() {
        return itemCount;
    }
}
