package com.tobiashehrlein.tobiswizardblock;

import io.realm.RealmObject;

/**
 * Created by Tobias on 29.08.2016.
 */
public class HighScoreRealm extends RealmObject
{
    private int winnerScore;
    private String winnerName;
    private String gameDate;
    private String gameName;
    private int playerCount;

    public HighScoreRealm()
    {

    }


    public int getWinnerScore()
    {
        return winnerScore;
    }

    public void setWinnerScore(int winnerScore)
    {
        this.winnerScore = winnerScore;
    }

    public String getWinnerName()
    {
        return winnerName;
    }

    public void setWinnerName(String winnerName)
    {
        this.winnerName = winnerName;
    }

    public String getGameDate()
    {
        return gameDate;
    }

    public void setGameDate(String gameDate)
    {
        this.gameDate = gameDate;
    }

    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public int getPlayerCount()
    {
        return playerCount;
    }

    public void setPlayerCount(int playerCount)
    {
        this.playerCount = playerCount;
    }
}
