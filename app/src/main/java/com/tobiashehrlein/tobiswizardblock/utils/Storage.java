package com.tobiashehrlein.tobiswizardblock.utils;

import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 12.12.2017.
 */

public class Storage {

    private static Storage instance;
    private WizardGame wizardGame;

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }

        return instance;
    }

    private Storage() {

    }


    public void setGameSettings(GameSettings gameSettings) {
        wizardGame = new WizardGame(gameSettings);
    }

    public WizardGame getWizardGame() {
        return wizardGame;
    }

    public void setAnnouncedTipps(RealmList<Integer> announcedTipps) {
        Round round = wizardGame.getLastRound();
        round.setAnnouncedTipps(announcedTipps);
        wizardGame.addRound(round);
    }
}
