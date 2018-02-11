package com.tobiashehrlein.tobiswizardblock.utils;

import com.tobiapplications.thutils.DateUtils;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

    public void initializeGame(String gameName, RealmList<String> playerNames, int settingsType) {
        realm.beginTransaction();
        wizardGame = realm.createObject(WizardGame.class);
        GameSettings gameSettings = realm.createObject(GameSettings.class);
        gameSettings.setPlayerNames(playerNames);
        gameSettings.setSettingsType(settingsType);
        gameSettings.setGameName(gameName);
        wizardGame.setGameSettings(gameSettings);
        wizardGame.setGameDate(DateUtils.getDate("dd MMM. yyyy - HH:mm"));
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

        savedGames.addAll(realmResults);

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

    public Map<Highscore, Integer> getHighscores() {
        List<Highscore> highscores;

        RealmResults<Highscore> realmResults = realm.where(Highscore.class).findAll();
        highscores = realm.copyFromRealm(realmResults);
        Collections.sort(highscores, (o1, o2) -> o1.getScore() - o2.getScore());
        Collections.reverse(highscores);

        if (highscores.size() <= MAX_HIGHSCORES) {
            return getHighscoreIntegerMap(highscores);
        }

        return clearNotTopTenListAndReturnTopTen(highscores, realmResults);
    }

    private Map<Highscore, Integer> clearNotTopTenListAndReturnTopTen(List<Highscore> highscores, RealmResults<Highscore> realmResults) {
        highscores.removeAll(getNotTopTenList(highscores));

        realm.beginTransaction();
        realmResults.deleteAllFromRealm();

        for (Highscore topTen : highscores) {
            Highscore newScore = realm.createObject(Highscore.class);
            newScore.setScore(topTen.getScore());
            newScore.setPlayerName(topTen.getPlayerName());
        }

        realm.commitTransaction();


        return getHighscoreIntegerMap(highscores);
    }

    private List<Highscore> getNotTopTenList(List<Highscore> highscores) {
        return highscores.subList(MAX_HIGHSCORES, highscores.size());
    }

    private Map<Highscore, Integer> getHighscoreIntegerMap(List<Highscore> highscores) {
        Map<Highscore, Integer> scoreRankingMap = new HashMap<>();
        if (isNullOrEmpty(highscores)) {
            return scoreRankingMap;
        }

        int rank = 1;

        scoreRankingMap.put(highscores.get(0), rank);

        for (int i = 1; i < highscores.size(); i++) {
            if (highscores.get(i).getScore() != highscores.get(i - 1).getScore()) {
                rank++;
            }
            scoreRankingMap.put(highscores.get(i), rank);
        }

        scoreRankingMap = MapUtils.sortByValue(scoreRankingMap);
        return scoreRankingMap;
    }

    public Map<String, Integer> getWinner() {
        Round lastRound = wizardGame.getLastRound();
        RealmList<Integer> totalPoints = lastRound.getPointsTotal();
        Map<String, Integer> winners = new LinkedHashMap<>();
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

        for (int i = 0; i < playerNames.size(); i++) {
            Highscore newScore = realm.createObject(Highscore.class);
            newScore.setScore(totalPoints.get(i));
            newScore.setPlayerName(playerNames.get(i));
        }

        realm.commitTransaction();
    }

    public void deleteAllHighscore() {
        realm.beginTransaction();

        RealmResults<Highscore> realmResults = realm.where(Highscore.class).findAll();
        realmResults.deleteAllFromRealm();

        realm.commitTransaction();
    }

    public void deleteCurrentGameFromLastGameList() {
        realm.beginTransaction();

        WizardGame currentGame = realm.where(WizardGame.class).equalTo("gameDate", wizardGame.getGameDate()).findFirst();
        letVoid(currentGame, game -> game.deleteFromRealm());

        realm.commitTransaction();
    }

    public void deleteThisSavedGame(WizardGame savedGame) {
        realm.beginTransaction();

        WizardGame currentGame = realm.where(WizardGame.class).equalTo("gameDate", savedGame.getGameDate()).findFirst();
        letVoid(currentGame, game -> game.deleteFromRealm());

        realm.commitTransaction();
    }

    public void restoreGame(WizardGame savedGame) {
        realm.beginTransaction();
        wizardGame = realm.createObject(WizardGame.class);
        GameSettings gameSettings = realm.createObject(GameSettings.class);
        gameSettings.setPlayerNames(savedGame.getGameSettings().getPlayerNames());
        gameSettings.setSettingsType(savedGame.getGameSettings().getSettings());
        gameSettings.setGameName(savedGame.getGameSettings().getGameName());
        wizardGame.setGameSettings(gameSettings);
        wizardGame.setGameDate(savedGame.getGameDate());
        wizardGame.setResults(savedGame.getResults());
        realm.commitTransaction();
    }
}
