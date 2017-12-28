package com.tobiashehrlein.tobiswizardblock.ui.viewhandler.delegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.model.lastgames.Divider;
import com.tobiashehrlein.tobiswizardblock.ui.viewholder.DividerHolder;

import java.util.List;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class DividerDelegate extends AdapterDelegate<List<DisplayableItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof Divider;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new DividerHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_divider, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        // not needed
    }
}
