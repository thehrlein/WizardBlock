package com.tobiashehrlein.tobiswizardblock.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 06.12.2017.
 */

public class GameSettings extends RealmObject {

    private String gameName;
    private RealmList<String> playerNames;
    private Settings settings;

    public GameSettings() {

    }

    public GameSettings(String gameName, RealmList<String> playerNames, Settings settings) {
        this.gameName = gameName;
        this.playerNames = playerNames;
        this.settings = settings;
    }

    public String getGameName() {
        return gameName;
    }

    public RealmList<String> getPlayerNames() {
        return playerNames;
    }

    public Settings getSettings() {
        return settings;
    }
}
