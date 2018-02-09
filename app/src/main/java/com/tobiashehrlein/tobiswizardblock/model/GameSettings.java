package com.tobiashehrlein.tobiswizardblock.model;

import io.realm.RealmList;
import io.realm.RealmObject;

import com.tobiashehrlein.tobiswizardblock.model.settings.SettingsFactory;

/**
 * Created by Tobias Hehrlein on 06.12.2017.
 */

public class GameSettings extends RealmObject {

    private String gameName;
    private RealmList<String> playerNames;
    private @SettingsFactory.SettingsType int settingsType;

    public GameSettings() {

    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setSettingsType(int settingsType) {
        this.settingsType = settingsType;
    }

    public String getGameName() {
        return gameName;
    }

    public RealmList<String> getPlayerNames() {
        return playerNames;
    }

    public @SettingsFactory.SettingsType int getSettings() {
        return settingsType;
    }

    public void setPlayerNames(RealmList<String> playerNames) {
        this.playerNames = playerNames;
    }
}
