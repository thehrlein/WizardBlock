package com.tobiashehrlein.tobiswizardblock.ui.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.HolderLastGamesBinding;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class LastGameHolder extends RecyclerView.ViewHolder {

    private HolderLastGamesBinding bind;
    private Context context;

    public LastGameHolder(View itemView) {
        super(itemView);
        bind = HolderLastGamesBinding.bind(itemView);
        context = bind.getRoot().getContext();
        itemView.setOnClickListener(v -> loadThisGame());
    }

    private void loadThisGame() {

    }

    public void setGameName(String gameName) {
        bind.gameName.setText(gameName);
    }

    public void setPlayerCount(int players) {
        bind.players.setText(context.getString(R.string.player_count, players));
    }

    public void setGameTime(String dateTime) {
        bind.gamedate.setText(dateTime);
    }
}
