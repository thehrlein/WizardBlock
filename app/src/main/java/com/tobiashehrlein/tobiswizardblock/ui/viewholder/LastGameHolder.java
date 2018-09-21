package com.tobiashehrlein.tobiswizardblock.ui.viewholder;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.HolderLastGamesBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.ui.fragments.gameblock.GameBlockFragment;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class LastGameHolder extends RecyclerView.ViewHolder {

    private HolderLastGamesBinding bind;
    private FragmentNavigationListener listener;
    private Context context;
    private String gameDate;

    public LastGameHolder(View itemView, FragmentNavigationListener listener) {
        super(itemView);
        this.listener = listener;
        bind = HolderLastGamesBinding.bind(itemView);
        context = bind.getRoot().getContext();
        itemView.setOnClickListener(v -> loadThisGame());
    }

    private void loadThisGame() {
        Storage.getInstance().initializeGameWithThisDate(gameDate);
        GameBlockFragment gameBlockFragment = GameBlockFragment.newInstance();
        letVoid(listener, l -> l.replaceFragment(gameBlockFragment, true));
    }

    public void setGameName(String gameName) {
        bind.gameName.setText(gameName);
    }

    public void setPlayerCount(int players) {
        bind.players.setText(context.getString(R.string.player_count, players));
    }

    public void setGameTime(String dateTime) {
        this.gameDate = dateTime;
        String displayDate = context.getString(R.string.clock, dateTime);
        bind.gamedate.setText(displayDate);
    }

    public String getGameName() {
        return bind.gameName.getText().toString();
    }

    public View getForegroundLayout() {
        return bind.viewForeground;
    }
}
