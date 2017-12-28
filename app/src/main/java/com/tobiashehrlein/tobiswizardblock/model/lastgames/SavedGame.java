package com.tobiashehrlein.tobiswizardblock.model.lastgames;

import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class SavedGame implements DisplayableItem {

    private String gameName;
    private int players;
    private String dateTime;

    public SavedGame(String gameName, int players, String dateTime) {
        this.gameName = gameName;
        this.players = players;
        this.dateTime = dateTime;
    }

    public String getGameName() {
        return gameName;
    }

    public int getPlayers() {
        return players;
    }

    public String getDateTime() {
        return dateTime;
    }
}
