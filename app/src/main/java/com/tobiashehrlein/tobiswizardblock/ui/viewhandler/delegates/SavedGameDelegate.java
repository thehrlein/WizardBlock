package com.tobiashehrlein.tobiswizardblock.ui.viewhandler.delegates;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.model.lastgames.SavedGame;
import com.tobiashehrlein.tobiswizardblock.ui.viewholder.LastGameHolder;

import java.util.List;

import static com.tobiapplications.thutils.NullPointerUtils.isNull;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class SavedGameDelegate extends AdapterDelegate<List<DisplayableItem>> {

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof SavedGame;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new LastGameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_last_games, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        LastGameHolder lastGameHolder = (LastGameHolder) holder;

        SavedGame savedGame = (SavedGame) items.get(position);

        if (isNull(lastGameHolder, savedGame)) {
            return;
        }

        lastGameHolder.setGameName(savedGame.getGameName());
        lastGameHolder.setPlayerCount(savedGame.getPlayers());
        lastGameHolder.setGameTime(savedGame.getDateTime());
    }
}
