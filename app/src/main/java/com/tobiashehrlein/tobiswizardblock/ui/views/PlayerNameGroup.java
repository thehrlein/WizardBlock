package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 05.12.2017.
 */

public class PlayerNameGroup extends LinearLayout {

    private static final int MAX_PLAYERS = 6;
    private static final int MIN_PLAYERS = 3;
    private List<PlayerNameInput> players;

    public PlayerNameGroup(Context context) {
        super(context);
        init(context);
    }

    public PlayerNameGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerNameGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        players = new ArrayList<>();

        for (int i = 1; i <= MAX_PLAYERS; i++) {
            PlayerNameInput player = new PlayerNameInput(context);
            player.addPlayerNumber(i);

            players.add(player);
            addView(player);
        }

        setPlayerFieldsVisibleUntil(MIN_PLAYERS);
    }

    public void setPlayerFieldsVisibleUntil(int visibleCount) {
        for (int i = 0; i < players.size(); i++) {
            if (i < visibleCount) {
                players.get(i).setVisibility(View.VISIBLE);
            } else {
                players.get(i).setVisibility(View.GONE);
                players.get(i).clearText();
            }
        }

        setFocusUntil(visibleCount);
    }

    private void setFocusUntil(int visibleCount) {
        for (int i = 0; i < visibleCount; i++) {
            if (players.get(i).getInput().length() == 0) {
                players.get(i).requestFocus();
                break;
            }
        }
    }

    public RealmList<String> getPlayerNames(int currentPlayerCount) {
        RealmList<String> playerNames = new RealmList<>();
        if (players == null || players.isEmpty() || currentPlayerCount >= players.size()) {
            return playerNames;
        }

        for (int i = 0; i <= currentPlayerCount; i++) {
            playerNames.add(players.get(i).getInput());
        }

        return playerNames;
    }
}
