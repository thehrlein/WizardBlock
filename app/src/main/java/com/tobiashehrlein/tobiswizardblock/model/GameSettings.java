package com.tobiashehrlein.tobiswizardblock.model;

import io.realm.RealmList;
import io.realm.RealmObject;

import com.tobiashehrlein.tobiswizardblock.model.settings.Settings;
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

    public GameSettings(String gameName, RealmList<String> playerNames, @SettingsFactory.SettingsType int settingsType) {
        this.gameName = gameName;
        this.playerNames = playerNames;
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
}
