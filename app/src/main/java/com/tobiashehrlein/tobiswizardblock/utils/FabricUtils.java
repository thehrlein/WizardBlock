package com.tobiashehrlein.tobiswizardblock.utils;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static com.tobiapplications.thutils.NullPointerUtils.isNotNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;

/**
 * Created by Tobias Hehrlein on 11.02.2018.
 */

public final class FabricUtils {

    public static final String OPEN_WIZARD_BLOCK_PLAY_STORE = "Open Wizard Block Play Store";
    public static final String OPEN_MOVIEBASE_PLAY_STORE = "Open MovieBase Play Store";
    public static final String HIGHSCORES_OPENED = "Highscores opened";
    public static final String SAVED_GAMES_OPENED = "Saved Games opened";
    public static final String NEW_GAME_STARTED = "New game started";

    private FabricUtils() {
        // no construction possible
    }

    public static final String GAME_FINISHED = "GameFinished";
    public static final String GAMENAME = "Gamename";
    public static final String GAMEDATE = "Gamedate";
    public static final String WINNERSCORE = "Winnerscore";
    public static final String WINNER = "Winner";
    public static final String UNKNOWN_WINNER = "Unknown Winner";

    public static void gameFinished(WizardGame wizardGame, Map<String, Integer> winners) {
        String gameName = "Unknown Gamename";
        String gameDate = "Unknown Gamedate";
        int winnerScore = getWinnerScore(winners);
        String winner = getWinner(winners);
        if (isNotNull(wizardGame)) {
            gameDate = wizardGame.getGameDate();
            if (isNotNull(wizardGame.getGameSettings())) {
                gameName = wizardGame.getGameSettings().getGameName();
            }
        }

        Answers.getInstance().logCustom(new CustomEvent(GAME_FINISHED)
            .putCustomAttribute(GAMENAME, gameName)
            .putCustomAttribute(GAMEDATE, gameDate)
            .putCustomAttribute(WINNERSCORE, winnerScore)
            .putCustomAttribute(WINNER, winner));
    }

    private static String getWinner(Map<String, Integer> winners) {
        if (isNullOrEmpty(winners)) {
            return UNKNOWN_WINNER;
        }

        Set<String> keys = winners.keySet();
        if (keys.isEmpty()) {
            return UNKNOWN_WINNER;
        }

        Iterator<String> iterator = keys.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }

        return UNKNOWN_WINNER;
    }

    private static int getWinnerScore(Map<String, Integer> winners) {
        if (isNullOrEmpty(winners)) {
            return 0;
        }

        Collection<Integer> values = winners.values();
        if (values.isEmpty()) {
            return 0;
        }
        Iterator<Integer> iterator = values.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }

        return 0;
    }

    public static void openWizardBlockPlayStore() {
        Answers.getInstance().logCustom(new CustomEvent(OPEN_WIZARD_BLOCK_PLAY_STORE));
    }

    public static void openMovieBasePlayStore() {
        Answers.getInstance().logCustom(new CustomEvent(OPEN_MOVIEBASE_PLAY_STORE));
    }

    public static void highscoresOpened() {
        Answers.getInstance().logCustom(new CustomEvent(HIGHSCORES_OPENED));
    }

    public static void savedGamesOpened() {
        Answers.getInstance().logCustom(new CustomEvent(SAVED_GAMES_OPENED));
    }

    public static void newGameStarted() {
        Answers.getInstance().logCustom(new CustomEvent(NEW_GAME_STARTED));
    }
}
