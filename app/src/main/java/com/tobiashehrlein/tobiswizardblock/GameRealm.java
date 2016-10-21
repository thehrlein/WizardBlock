package com.tobiashehrlein.tobiswizardblock;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Tobias on 15.06.2016.
 */
public class GameRealm extends RealmObject
{
    private String gameName;
    private String gameDate;
    private int countPlayers;
    private RealmList<RealmString> playerNames;
    private RealmList<RealmInt> tippsResults;
    private RealmList<RealmInt> scoreAddTotal;
    private int currentGameRound;
    private int maxGameRounds;
    private boolean stitchesEqualsTipps;

    public GameRealm()
    {

    }


    public String getGameName()
    {
        return gameName;
    }

    public void setGameName(String gameName)
    {
        this.gameName = gameName;
    }

    public String getGameDate()
    {
        return gameDate;
    }

    public void setGameDate(String gameDate)
    {
        this.gameDate = gameDate;
    }

    public int getCountPlayers()
    {
        return countPlayers;
    }

    public void setCountPlayers(int countPlayers)
    {
        this.countPlayers = countPlayers;
    }

    public RealmList<RealmString> getPlayerNames()
    {
        return playerNames;
    }

    public void setPlayerNames(RealmList<RealmString> playerNames)
    {
        this.playerNames = playerNames;
    }

    public RealmList<RealmInt> getTippsResults()
    {
        return tippsResults;
    }

    public void setTippsResults(RealmList<RealmInt> tippsResults)
    {
        this.tippsResults = tippsResults;
    }

    public RealmList<RealmInt> getScoreAddTotal()
    {
        return scoreAddTotal;
    }

    public void setScoreAddTotal(RealmList<RealmInt> scoreAddTotal)
    {
        this.scoreAddTotal = scoreAddTotal;
    }

    public int getCurrentGameRound()
    {
        return currentGameRound;
    }

    public void setCurrentGameRound(int currentGameRound)
    {
        this.currentGameRound = currentGameRound;
    }

    public int getMaxGameRounds()
    {
        return maxGameRounds;
    }

    public void setMaxGameRounds(int maxGameRounds)
    {
        this.maxGameRounds = maxGameRounds;
    }

    public boolean isStitchesEqualsTipps()
    {
        return stitchesEqualsTipps;
    }

    public void setStitchesEqualsTipps(boolean stitchesEqualsTipps)
    {
        this.stitchesEqualsTipps = stitchesEqualsTipps;
    }
}
