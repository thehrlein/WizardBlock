package com.tobiashehrlein.tobiswizardblock.model;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 12.12.2017.
 */

public class WizardGame extends RealmObject {

    public static final int WIN_EXTRA_POINTS = 20;
    public static final int NORMAL_POINTS = 10;
    private GameSettings gameSettings;
    private RealmList<Round> results;

    public WizardGame() {

    }

    public WizardGame(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        results = new RealmList<>();
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
        return results.get(results.size() - 1);
    }

    public void addRound(Round round) {
        results.add(round);
    }

    public void addMadeStitches(Round round) {
        if (results == null || results.isEmpty()) {
            throw new IllegalStateException("Replacing Round not possible - results null or empty");
        }

        results.remove(results.size() - 1);
        calculateResults(round);
        results.add(round);
    }

    private void calculateResults(Round round) {
        RealmList<Integer> tippsList = round.getAnnouncedTipps();
        RealmList<Integer> stitchesList = round.getMadeStitches();
        RealmList<Integer> pointsAddList = round.getPointsAdded();
        RealmList<Integer> pointsTotalList = round.getPointsTotal();

        for (int i = 0; i < round.getMadeStitches().size(); i++) {
            int tipp = tippsList.get(i);
            int stitch = stitchesList.get(i);

            int pointsAdd;
            if (tipp != stitch) {
                pointsAdd = getPointsToSubtract(tipp, stitch);
            } else {
                pointsAdd = getPointsToAdd(stitch);
            }

            pointsAddList.add(pointsAdd);
            pointsTotalList.add(getLastRoundTotalPoints(i) + pointsAdd);
        }
    }

    private int getLastRoundTotalPoints(int i) {
        if (results == null || results.isEmpty()) {
            return 0;
        }
        Round round = results.get(results.size() - 1);
        if (round == null) {
            return 0;
        }

        RealmList<Integer> totalPoints = round.getPointsTotal();
        if (totalPoints == null || totalPoints.isEmpty() || i >= totalPoints.size()) {
            return 0;
        }
        return totalPoints.get(i);
    }

    private int getPointsToAdd(int stitch) {
        return (stitch * NORMAL_POINTS) + WIN_EXTRA_POINTS;
    }

    private int getPointsToSubtract(int tipp, int stitch) {
        int points = (tipp - stitch) * NORMAL_POINTS;
        if (points > 0) {
            points = points * -1;
        }

        return points;
    }

    public void clearLastRound() {
        Round round = results.get(results.size() - 1);
        results.remove(results.indexOf(round));
    }
}
