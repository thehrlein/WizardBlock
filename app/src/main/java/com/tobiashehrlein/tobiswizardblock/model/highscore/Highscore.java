package com.tobiashehrlein.tobiswizardblock.model.highscore;

import io.realm.RealmObject;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class Highscore extends RealmObject {

    private String playerName;
    private int score;

    public Highscore() {

    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }
}
