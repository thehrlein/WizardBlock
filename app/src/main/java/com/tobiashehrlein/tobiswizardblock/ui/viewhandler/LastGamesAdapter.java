package com.tobiashehrlein.tobiswizardblock.ui.viewhandler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.ui.viewhandler.delegates.SavedGameDelegate;

import java.util.ArrayList;
import java.util.List;

import static com.tobiapplications.thutils.NullPointerUtils.isNotNullOrEmpty;
import static com.tobiapplications.thutils.NullPointerUtils.isNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class LastGamesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DisplayableItem> itemList;
    private AdapterDelegatesManager<List<DisplayableItem>> delegatesManager;

    public LastGamesAdapter(FragmentNavigationListener listener) {
        itemList = new ArrayList<>();

        delegatesManager = new AdapterDelegatesManager<>();
        delegatesManager.addDelegate(new SavedGameDelegate(listener));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        delegatesManager.onBindViewHolder(itemList, position, holder);
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(itemList, position);
    }

    @Override
    public int getItemCount() {
        if (isNullOrEmpty(itemList)) {
            return 0;
        }

        return itemList.size();
    }

    public void setSavedGames(List<DisplayableItem> savedGames) {
        if (isNullOrEmpty(savedGames)) {
            return;
        }

        itemList.addAll(savedGames);
        notifyDataSetChanged();
    }

    public DisplayableItem removeItem(int posToDelete) {
        if (isNullOrEmpty(itemList) || posToDelete > itemList.size()) {
            return null;
        }
        DisplayableItem deleteItem = itemList.get(posToDelete);
        itemList.remove(deleteItem);
        notifyItemRemoved(posToDelete);

        return deleteItem;
    }

    public void restoreItem(DisplayableItem item, int position) {
        if (isNull(itemList, item)) {
            return;
        }

        itemList.add(position, item);
        notifyItemInserted(position);
    }
}
