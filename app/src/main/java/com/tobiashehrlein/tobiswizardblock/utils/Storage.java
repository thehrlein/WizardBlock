package com.tobiashehrlein.tobiswizardblock.utils;

import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;

import io.realm.RealmList;

import static com.tobiapplications.thutils.NullPointerUtils.isNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.let;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

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

    public void saveInput(RealmList<Integer> input, boolean isTippMode) {
        if (isTippMode) {
            setAnnouncedTipps(input);
        } else {
            setMadeStitches(input);
        }
    }

    private void setAnnouncedTipps(RealmList<Integer> announcedTipps) {
        Round round = new Round();
        round.setAnnouncedTipps(announcedTipps);
        wizardGame.addRound(round);
    }

    private void setMadeStitches(RealmList<Integer> madeStitches) {
        Round round = wizardGame.getLastRound();
        round.setMadeStitches(madeStitches);
        wizardGame.addMadeStitches(round);
    }

    public void savePlayerNames(RealmList<String> newPlayerName) {
        wizardGame.getGameSettings().setPlayerNames(newPlayerName);
    }

    public void clearLastInput() {
        Round round = let(wizardGame, WizardGame::getLastRound);
        if (isNull(round)) {
            return;
        }

        if (isNullOrEmpty(round.getMadeStitches())) {
            letVoid(wizardGame, WizardGame::clearLastRound);
        } else {
            round.getMadeStitches().clear();
            round.getPointsAdded().clear();
            round.getPointsTotal().clear();
        }
    }
}
