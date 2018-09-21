package com.tobiashehrlein.tobiswizardblock.ui.viewhandler.delegates;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.hannesdorfmann.adapterdelegates3.AdapterDelegate;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.ui.viewholder.LastGameHolder;

import java.util.List;

import static com.tobiapplications.thutils.NullPointerUtils.isNull;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class SavedGameDelegate extends AdapterDelegate<List<DisplayableItem>> {

    private final FragmentNavigationListener listener;

    public SavedGameDelegate(FragmentNavigationListener listener) {
        this.listener = listener;
    }

    @Override
    protected boolean isForViewType(@NonNull List<DisplayableItem> items, int position) {
        return items.get(position) instanceof WizardGame;
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new LastGameHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.holder_last_games, parent, false), listener);
    }

    @Override
    protected void onBindViewHolder(@NonNull List<DisplayableItem> items, int position, @NonNull RecyclerView.ViewHolder holder, @NonNull List<Object> payloads) {
        LastGameHolder lastGameHolder = (LastGameHolder) holder;

        WizardGame savedGame = (WizardGame) items.get(position);

        if (isNull(lastGameHolder, savedGame)) {
            return;
        }

        GameSettings settings = savedGame.getGameSettings();
        lastGameHolder.setGameName(settings.getGameName());
        lastGameHolder.setPlayerCount(settings.getPlayerNames().size());
        lastGameHolder.setGameTime(savedGame.getGameDate());
    }
}
