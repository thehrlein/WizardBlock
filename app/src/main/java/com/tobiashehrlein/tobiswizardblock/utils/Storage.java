package com.tobiashehrlein.tobiswizardblock.utils;

import com.tobiapplications.thutils.DateUtils;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;
import com.tobiashehrlein.tobiswizardblock.model.lastgames.SavedGame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static final int MAX_HIGHSCORES = 10;
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
        RealmResults<WizardGame> realmResults = getAllSavedWizardGames();
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

    private RealmResults<WizardGame> getAllSavedWizardGames() {
        return realm.where(WizardGame.class).findAll();
    }

    public void initializeGameWithThisDate(String gameDate) {
        RealmResults<WizardGame> realmResults = getAllSavedWizardGames();
        for (WizardGame game : realmResults) {
            if (game.getGameDate().equals(gameDate)) {
                wizardGame = game;
                break;
            }
        }
    }

    public List<Highscore> getHighscores() {
        List<Highscore> highscores;

        RealmResults<Highscore> realmResults = realm.where(Highscore.class).findAll();
        highscores = realm.copyFromRealm(realmResults);
        Collections.sort(highscores, (o1, o2) -> o1.getScore() > o2.getScore() ? -1 : 1);

        if (highscores.size() <= MAX_HIGHSCORES) {
            return highscores;
        }

        return highscores.subList(0, MAX_HIGHSCORES);
    }

    public Map<String, Integer> getWinner() {
        Round lastRound = wizardGame.getLastRound();
        RealmList<Integer> totalPoints = lastRound.getPointsTotal();
        Map<String, Integer> winners = new HashMap<>();
        RealmList<String> playerNames = wizardGame.getGameSettings().getPlayerNames();
        winners.put(playerNames.first(), totalPoints.first());

        for (int i = 1; i < totalPoints.size(); i++) {
            int winnerScore = winners.values().iterator().next();
            int currentScore = totalPoints.get(i);
            String currentPlayerName = playerNames.get(i);
            if (currentScore > winnerScore) {
                winners.clear();
                winners.put(currentPlayerName, currentScore);
            } else if (currentScore == winnerScore) {
                winners.put(currentPlayerName, currentScore);
            }
        }

        return winners;
    }

    public void saveHighscores() {
        Round lastRound = wizardGame.getLastRound();
        RealmList<Integer> totalPoints = lastRound.getPointsTotal();
        RealmList<String> playerNames = wizardGame.getGameSettings().getPlayerNames();

        if (totalPoints.size() != playerNames.size()) {
            return;
        }

        realm.beginTransaction();

        List<Highscore> highscores = new ArrayList<>();
        for (int i = 0; i < playerNames.size(); i++) {
            Highscore newScore = realm.createObject(Highscore.class);
            newScore.setScore(totalPoints.get(i));
            newScore.setPlayerName(playerNames.get(i));
            highscores.add(newScore);
        }

        realm.commitTransaction();
    }

    public void deleteAllHighscore() {
        realm.beginTransaction();

        RealmResults<Highscore> realmResults = realm.where(Highscore.class).findAll();
        realmResults.deleteAllFromRealm();

        realm.commitTransaction();
    }
}
