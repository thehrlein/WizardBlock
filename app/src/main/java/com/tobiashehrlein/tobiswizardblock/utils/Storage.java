package com.tobiashehrlein.tobiswizardblock.utils;

import com.tobiapplications.thutils.DateUtils;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.model.lastgames.SavedGame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

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
    private Realm realm;

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }

        return instance;
    }

    private Storage() {
        realm = Realm.getDefaultInstance();
    }

    public void setGameSettings(String gameName, RealmList<String> playerNames, int settingsType) {
        realm.beginTransaction();
        wizardGame = realm.createObject(WizardGame.class);
        GameSettings gameSettings = realm.createObject(GameSettings.class);
        gameSettings.setPlayerNames(playerNames);
        gameSettings.setSettingsType(settingsType);
        gameSettings.setGameName(gameName);
        wizardGame.setGameSettings(gameSettings);
        wizardGame.setGameDate(DateUtils.getTodayString(DateUtils.TimeMode.MINUTES));
        realm.commitTransaction();
    }

    public WizardGame getWizardGame() {
        return wizardGame;
    }

    public void setWizardGame(WizardGame wizardGame) {
        this.wizardGame = wizardGame;
    }

    public void saveInput(RealmList<Integer> input, boolean isTippMode) {
        if (isTippMode) {
            setAnnouncedTipps(input);
        } else {
            setMadeStitches(input);
        }
    }

    private void setAnnouncedTipps(RealmList<Integer> announcedTipps) {
        realm.beginTransaction();
        Round round = realm.createObject(Round.class);
        round.setAnnouncedTipps(announcedTipps);
        wizardGame.addRound(round);
        realm.commitTransaction();
    }

    private void setMadeStitches(RealmList<Integer> madeStitches) {
        realm.beginTransaction();
        Round round = wizardGame.getLastRound();
        round.setMadeStitches(madeStitches);
        wizardGame.addMadeStitches(round);
        realm.commitTransaction();
    }

    public void savePlayerNames(RealmList<String> newPlayerName) {
        realm.beginTransaction();
        wizardGame.getGameSettings().setPlayerNames(newPlayerName);
        realm.commitTransaction();
    }

    public void clearLastInput() {
        Round round = let(wizardGame, WizardGame::getLastRound);
        if (isNull(round)) {
            return;
        }

        realm.beginTransaction();

        if (isNullOrEmpty(round.getMadeStitches())) {
            letVoid(wizardGame, WizardGame::clearLastRound);
        } else {
            round.getMadeStitches().clear();
            round.getPointsAdded().clear();
            round.getPointsTotal().clear();
        }

        realm.commitTransaction();
    }

    public List<DisplayableItem> getSavedGames() {
        RealmResults<WizardGame> realmResults = getWizardGames();
        List<DisplayableItem> savedGames = new ArrayList<>();
        if (realmResults.isEmpty()) {
            return savedGames;
        }
        for (WizardGame game : realmResults) {
            GameSettings settings = game.getGameSettings();
            savedGames.add(new SavedGame(settings.getGameName(), settings.getPlayerNames().size(), game.getGameDate()));
        }

        return savedGames;
    }

    private RealmResults<WizardGame> getWizardGames() {
        return realm.where(WizardGame.class).findAll();
    }

    public void initizalizeGameWithThisDate(String gameDate) {
        RealmResults<WizardGame> realmResults = getWizardGames();
        for (WizardGame game : realmResults) {
            if (game.getGameDate().equals(gameDate)) {
                wizardGame = game;
                break;
            }
        }
    }
}
