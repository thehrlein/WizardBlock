package com.tobiashehrlein.tobiswizardblock.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 12.12.2017.
 */

public class WizardGame extends RealmObject {

    private GameSettings gameSettings;
    private RealmList<Round> results;

    public WizardGame() {

    }

    public WizardGame(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public RealmList<Round> getResults() {
        return results;
    }

    public void setResults(RealmList<Round> results) {
        this.results = results;
    }

    public Round getLastRound() {
        if (results == null || results.isEmpty()) {
            results = new RealmList<>();
            return new Round();
        }

        return results.get(results.size() - 1);
    }

    public void addRound(Round round) {
        results.add(round);
    }
}
