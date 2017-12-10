package com.tobiashehrlein.tobiswizardblock.model;

import com.google.auto.value.AutoValue;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Tobias Hehrlein on 06.12.2017.
 */

@AutoValue
public abstract class GameSettings implements Serializable {

    public abstract String getGamename();
    public abstract List<String> getPlayerNames();
    public abstract Settings getSettings();

    public static GameSettings create(String gamename, List<String> playerNames, Settings settings) {
        return new AutoValue_GameSettings(gamename, playerNames, settings);
    }
}
