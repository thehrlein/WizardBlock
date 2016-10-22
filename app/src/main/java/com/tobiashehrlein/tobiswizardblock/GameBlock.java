package com.tobiashehrlein.tobiswizardblock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class GameBlock extends AppCompatActivity
{
    private static int currentPlayers;
    private static RealmList<RealmString> playerNamesRealmList;
    private TableLayout tableLayout;
    private TextView[] tvPlayers = new TextView[6];
    private TextView roundUeberschrift;
    private static int currentGameRound;
    private static boolean tippsSaved = false;
    private static boolean resultEntered = true;
    private final String OPEN_TIPPS = "Tipp";
    private final String OPEN_RESULTS = "Ergebnis";
    private TableLayout tippTableLayout;
    private TextView tvRound;
    private TableRow tableRow;
    private View vSeperator;
    private static boolean tippOrResultCanceled = false;
    private int maxGameRounds;
    private ScrollView svGameBlock;
    private TextView[][] myTippsTextView = new TextView[12][20];
    private TextView[][] myScoresTextView = new TextView[12][20];
    private RealmList<RealmInt> playerTippsOrResultRealmList;
    private RealmList<RealmInt> completeTippsResultsRealmList = new RealmList<>();
    private RealmList<RealmInt> playerScoresAddAndTotalRealmList;
    private RealmList<RealmInt> completeScoresRealmList = new RealmList<>();
    private boolean newGame;
    private boolean backFromResultsToCalculateScores;
    private String currentGameName;
    private String currentGameDate;
    private Realm myRealm;
    private boolean comefromlastgame;
    private RealmResults<GameRealm> results;
    private GameRealm gameRealm;
    private boolean dontIncreaseGameRound;
    private boolean backFromTipps;
    private static boolean changeTippsOrResult;
    private static boolean stitchesEqualTipps;
    private boolean showWinner = true;
    private int winnerScore;
    private String winner;
    private boolean displayActive;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_block);

        displayActive = Settings.isDisplayActive();
        setDisplayAlwaysActive(displayActive);

        Log.d("GameBlock", "onCreate displayActive: " + displayActive);

        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext())
                .name("myRealm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

        findMyViews();
        getMyIntent();

        myRealm = Realm.getDefaultInstance();
        playerTippsOrResultRealmList = new RealmList<>();
        playerScoresAddAndTotalRealmList = new RealmList<>();

        if (backFromTipps)
        {
            results = myRealm.where(GameRealm.class).findAll();
            gameRealm = results.last();
            completeTippsResultsRealmList = gameRealm.getTippsResults();
            completeScoresRealmList = gameRealm.getScoreAddTotal();
            stitchesEqualTipps = gameRealm.isStitchesEqualsTipps();
        /*  Log.d("GameBlock", "onCreate backFromTipps stitchesEqualTipps: " + stitchesEqualTipps);
            Log.d("GameBlock", "onCreate completeTippsSize: " + completeTippsResultsRealmList.size());
            Log.d("GameBlock", "onCreate backFromTipps: " + backFromTipps); */
            playerTippsOrResultRealmList = Tipps.getPlayerTippsAndResultRealmList();
            if (changeTippsOrResult)
            {
                deleteLastTippsFromCompleteList();
            }
            safeNewestTippsResultIntoComplete();
          //  showCompleteTippsResult();
            dontIncreaseGameRound = true;
            saveGame();
            if (changeTippsOrResult)
            {
                if (tippsSaved)
                {
                    writeTippsOnBlock(currentGameRound);
                }
                writeTippsOnBlock(currentGameRound - 1);
            }
            else
            {
                writeTippsOnBlock(currentGameRound);
            }
            if (!backFromResultsToCalculateScores)
            {
                writeTippsResultsOnBlock();
                writeScoresOnBlock();
            }

        }

        if (newGame)
        {
            setUpNewGame();
        }


        if (backFromResultsToCalculateScores)
        {
            dontIncreaseGameRound = false;
            results = myRealm.where(GameRealm.class).findAll();
            gameRealm = results.last();
            completeScoresRealmList = gameRealm.getScoreAddTotal();
            calculateScores();
            if (changeTippsOrResult)
            {
                dontIncreaseGameRound = true;
                deleteLastScoresFromCompleteList();

            }
            safeNewestScoresIntoComplete();

           // showCompleteScores();
            saveGame();
            writeTippsResultsOnBlock();
            writeScoresOnBlock();
        }

        if (comefromlastgame)
        {
            //showCompleteTippsResult();
           // showCompleteScores();

            if (completeTippsResultsRealmList.size() + currentPlayers == currentPlayers * 2 * currentGameRound)
            {
                writeTippsOnBlock(currentGameRound);
                tippsSaved = true;
                resultEntered = false;
            }
            writeTippsOnBlock(currentGameRound - 1);
            writeTippsResultsOnBlock();
            writeScoresOnBlock();
        }

        setPlayerNames();
        setUpFocus();
    }

    private void deleteLastScoresFromCompleteList()
    {
     //   Log.d("GameBlock", "deleteLastScoresFromCompleteList");
        myRealm.beginTransaction();
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();
        completeScoresRealmList = gameRealm.getScoreAddTotal();

        for (int i = 0; i < currentPlayers * 2; i++)
        {
            completeScoresRealmList.remove(completeScoresRealmList.size() - 1);
        }

        myRealm.commitTransaction();

     //   Log.d("GameBlock", "deleteLastScores completeSize: " + completeScoresRealmList.size());
    }

    private void deleteLastTippsFromCompleteList()
    {
     //   Log.d("GameBlock", "deleteLastTippsFromCompleteList");
        myRealm.beginTransaction();
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();
        completeTippsResultsRealmList = gameRealm.getTippsResults();

        for (int i = 0; i < currentPlayers; i++)
        {
            completeTippsResultsRealmList.remove(completeTippsResultsRealmList.size() - 1);
        }

        myRealm.commitTransaction();

     //   Log.d("GameBlock", "deleteLastTipps completeSize: " + completeTippsResultsRealmList.size());
    }

    private void findMyViews()
    {
        tableLayout = (TableLayout) findViewById(R.id.blockTable);
        tableLayout.setShrinkAllColumns(true);
        tableLayout.setStretchAllColumns(true);
        svGameBlock = (ScrollView) findViewById(R.id.svGameBlock);
        tvPlayers[0] = (TextView) findViewById(R.id.tvPlayerOne);
        tvPlayers[1] = (TextView) findViewById(R.id.tvPlayerTwo);
        tvPlayers[2] = (TextView) findViewById(R.id.tvPlayerThree);
        tvPlayers[3] = (TextView) findViewById(R.id.tvPlayerFour);
        tvPlayers[4] = (TextView) findViewById(R.id.tvPlayerFive);
        tvPlayers[5] = (TextView) findViewById(R.id.tvPlayerSix);
        roundUeberschrift = (TextView) findViewById(R.id.roundUeberschrift);
        tippTableLayout = new TableLayout(this);
        tippTableLayout.setShrinkAllColumns(true);
        tippTableLayout.setStretchAllColumns(true);
    }

    private void getMyIntent()
    {
        Intent intent = getIntent();
        newGame = intent.getBooleanExtra("newGame", true);
        comefromlastgame = intent.getBooleanExtra("comefromlastgame", false);
        backFromTipps = intent.getBooleanExtra("backFromTipps", false);
        backFromResultsToCalculateScores = intent.getBooleanExtra("backfromresults", false);
        changeTippsOrResult = intent.getBooleanExtra("changeTippsOrResult", false);

     /*   Log.d("GameBlock", "changeTippsOrResult was " + changeTippsOrResult);
        Log.d("Gameblock", "comefromlastgame: " + comefromlastgame);
        Log.d("GameBlock", "backFromTipps: " + backFromTipps);
        Log.d("Gameblock", "onCreate newGame: " + newGame);
        Log.d("Gameblock", "onCreate backfromresults: " + backFromResultsToCalculateScores);
        Log.d("GameBlock", "getIntent tippsaved is " + tippsSaved);
        Log.d("GameBlock", "getIntent resultsEntered is " + resultEntered); */
    }

    private void safeNewestTippsResultIntoComplete()
    {
     //   Log.d("GameBlock", "before saveNewestTippResultIntoComplete completeSize: " + completeTippsResultsRealmList.size());
        myRealm.beginTransaction();

        for (int i = 0; i < playerTippsOrResultRealmList.size(); i++)
        {
            RealmInt realmInt = new RealmInt();
            realmInt.setVal(playerTippsOrResultRealmList.get(i).getVal());
            completeTippsResultsRealmList.add(realmInt);
        }
        myRealm.commitTransaction();
      //  Log.d("GameBlock", "after safeNewestTippResultsIntoComplete completeSize: " + completeTippsResultsRealmList.size());
    }

    private void safeNewestScoresIntoComplete()
    {
     //   Log.d("GameBlock", "before saveNewestScoresIntoComplete completeSize: " + completeScoresRealmList.size());
        myRealm.beginTransaction();

        int helperScore = completeScoresRealmList.size();
     //   Log.d("GameBlock", "safeNewestScoresIntoComplete helperScore: " + helperScore);

        for (int i = 0; i < currentPlayers; i++)
        {
            RealmInt realmInt = new RealmInt();
            realmInt.setVal(playerScoresAddAndTotalRealmList.get(i).getVal());
            completeScoresRealmList.add(realmInt);
        }

        // add score to totalscore from lastround and save to completescorerealmlist
        for (int j = 0; j < currentPlayers; j++)
        {
            RealmInt realmInt = new RealmInt();
            if (helperScore == 0)
            {
              //  Log.d("GameBlock", "safeNewestScoresIntoComplete helperScore: " + helperScore);
                realmInt.setVal(playerScoresAddAndTotalRealmList.get(j).getVal());
            }
            else
            {
             //   Log.d("GameBlock", "safeNewestScoresIntoComplete helperScore: " + helperScore);
                realmInt.setVal(playerScoresAddAndTotalRealmList.get(j).getVal() + completeScoresRealmList.get(helperScore - currentPlayers).getVal());
                helperScore++;
            }
            completeScoresRealmList.add(realmInt);

        }

        myRealm.commitTransaction();
      //  Log.d("GameBlock", "after safeNewestScoresIntoComplete completeSize: " + completeScoresRealmList.size());
    }

    private void showCompleteTippsResult()
    {
        for (int i = 0; i < completeTippsResultsRealmList.size(); i++)
        {
            Log.d("GameBlock", "completeTippsResults " + i + ": " + completeTippsResultsRealmList.get(i).getVal());
        }
    }

    private void showCompleteScores()
    {
        for (int i = 0; i < completeScoresRealmList.size(); i++)
        {
            Log.d("GameBlock", "completeScores " + i + ": " + completeScoresRealmList.get(i).getVal());
        }
    }

    // New Game --> fetch data from saved gamerealm
    private void setUpNewGame()
    {
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();

    //    Log.d("Gameround", "setUpNewGame resultssize: " + results.size());
    //    Log.d("GameBlock", "setUpNewGame gameround: " + currentGameRound);

        playerNamesRealmList = gameRealm.getPlayerNames();
        currentPlayers = gameRealm.getCountPlayers();
        currentGameRound = gameRealm.getCurrentGameRound();
        maxGameRounds = gameRealm.getMaxGameRounds();
        currentGameName = gameRealm.getGameName();
        currentGameDate = gameRealm.getGameDate();
        completeTippsResultsRealmList = gameRealm.getTippsResults();
        completeScoresRealmList = gameRealm.getScoreAddTotal();
        stitchesEqualTipps = gameRealm.isStitchesEqualsTipps();

    //    Log.d("GameBlock", "setUpNewGame stitchesEqualTipps: " + stitchesEqualTipps);

        setTitle(getString(R.string.game_block) + ": \"" + gameRealm.getGameName()+"\"");
        setCustumActionBar();

    }

    private void setCustumActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        TextView textView = new TextView(GameBlock.this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                                   ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(layoutParams);
        textView.setText(getString(R.string.game_block) + ": \"" + gameRealm.getGameName()+"\"");
        textView.setTextSize(15);
        textView.setTextColor(getResources().getColor(R.color.colorLightGrey));
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(textView);

    }

    private void saveGame()
    {
        myRealm.beginTransaction();
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();
        currentGameDate = gameRealm.getGameDate();
        currentGameName = gameRealm.getGameName();
        maxGameRounds = gameRealm.getMaxGameRounds();
        currentGameRound = gameRealm.getCurrentGameRound();
        playerNamesRealmList = gameRealm.getPlayerNames(); // evtl nicht von gamerealm nochmal holen
     //   Log.d("GameBlock", "saveGame gameround: " + gameRealm.getCurrentGameRound());

        gameRealm = myRealm.createObject(GameRealm.class);
        gameRealm.setGameName(currentGameName);
        gameRealm.setGameDate(currentGameDate);
        gameRealm.setCountPlayers(currentPlayers);
        gameRealm.setPlayerNames(playerNamesRealmList);
        gameRealm.setTippsResults(completeTippsResultsRealmList);
        gameRealm.setScoreAddTotal(completeScoresRealmList);

     /*   Log.d("GameBlock", "saveGame completeTippsSize: " + completeTippsResultsRealmList.size());
        Log.d("GameBlock", "saveGame completeScoresSize: " + completeScoresRealmList.size());
        Log.d("GameBlock", "saveGame dontIncreaseGameRound: " + dontIncreaseGameRound); */
        if (dontIncreaseGameRound)
        {
            gameRealm.setCurrentGameRound(currentGameRound);
            // dontIncreaseGameRound = false;
        }
        else
        {
            gameRealm.setCurrentGameRound(currentGameRound + 1);
        }
        gameRealm.setMaxGameRounds(maxGameRounds);

      //  Log.d("GameBlock", "safeGame stitchesEqualTipps: " + stitchesEqualTipps);
        gameRealm.setStitchesEqualsTipps(stitchesEqualTipps);

        myRealm.commitTransaction();

        results = myRealm.where(GameRealm.class).findAll();
      /*  Log.d("GameBlock", "Größe SaveRealm: " + results.size());
        Log.d("GameBlock", "afterSaveGame gameround: " + gameRealm.getCurrentGameRound()); */
        currentGameRound = gameRealm.getCurrentGameRound();

        checkIfGameAlreadyInRealmDatabase();
    }

    private void checkIfGameAlreadyInRealmDatabase()
    {
        myRealm.beginTransaction();
        results = myRealm.where(GameRealm.class).findAll();
        GameRealm myGame = results.last();

        if (results.size() > 1)
        {
            for (int i = results.size()-2; i >= 0; i--)
            {
                if (myGame.getGameDate().equalsIgnoreCase(results.get(i).getGameDate()))
                {
                    results.remove(i);
                }
            }
        }
        myRealm.commitTransaction();
    }

    private void calculateScores()
    {
    //    Log.d("GameBlock", "calculateScores completeTippResultSize: " + completeTippsResultsRealmList.size());
        for (int i = completeTippsResultsRealmList.size() - (currentPlayers * 2); i < completeTippsResultsRealmList.size() - currentPlayers; i++)
        {
            int addScore;
            if (completeTippsResultsRealmList.get(i).getVal() == completeTippsResultsRealmList.get(i + currentPlayers).getVal())
            {
                addScore = 20 + (10 * completeTippsResultsRealmList.get(i).getVal());
            }
            else
            {
                addScore = (completeTippsResultsRealmList.get(i).getVal() - completeTippsResultsRealmList.get(i + currentPlayers).getVal()) * 10;
                if (addScore > 0)
                {
                    addScore = addScore * (-1);
                }
            }
       //     Log.d("GameBlock", "addScore: " + addScore);
            RealmInt realmInt = new RealmInt();
            realmInt.setVal(addScore);
            playerScoresAddAndTotalRealmList.add(realmInt);
        }
    }

    // write player names in first line and make textviews visible, if more then 3 player
    private void setPlayerNames()
    {
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();
        playerNamesRealmList = gameRealm.getPlayerNames();

        tvPlayers[0].setText(playerNamesRealmList.get(0).getVal());
        tvPlayers[1].setText(playerNamesRealmList.get(1).getVal());
        tvPlayers[2].setText(playerNamesRealmList.get(2).getVal());

        switch (currentPlayers)
        {
            case 6:
                tvPlayers[5].setText(playerNamesRealmList.get(5).getVal());;
                tvPlayers[5].setVisibility(View.VISIBLE);
                setUpPlayerTextViews(6);
            case 5:
                tvPlayers[4].setText(playerNamesRealmList.get(4).getVal());
                tvPlayers[4].setVisibility(View.VISIBLE);
                setUpPlayerTextViews(5);
            case 4:
                tvPlayers[3].setText(playerNamesRealmList.get(3).getVal());
                tvPlayers[3].setVisibility(View.VISIBLE);
                setUpPlayerTextViews(4);
        }

        if (currentPlayers > 3)
        {
            tvPlayers[0].setTextSize(10);
            tvPlayers[1].setTextSize(10);
            tvPlayers[2].setTextSize(10);
            tvPlayers[3].setTextSize(10);
            tvPlayers[4].setTextSize(10);
            tvPlayers[5].setTextSize(10);
            roundUeberschrift.setTextSize(10);
        }
    }

    // Make TextViews of Player (playerNumber) visible and find them
    private void setUpPlayerTextViews(int playerNumber)
    {
        for (int i = 1; i <= maxGameRounds; i++)
        {
            int tmp = i - 1;
            int playerID = (playerNumber * 2) - 2;

            // Tipps
            String id = "prt" + playerNumber + i;
            int tvTP = getResources().getIdentifier(id, "id", getPackageName());
            String idr = "prr" + playerNumber + i;
            int tvrTP = getResources().getIdentifier(idr, "id", getPackageName());

            myTippsTextView[playerID][tmp] = (TextView) findViewById(tvTP);
            myTippsTextView[playerID + 1][tmp] = (TextView) findViewById(tvrTP);
            myTippsTextView[playerID][tmp].setVisibility(View.VISIBLE);
            myTippsTextView[playerID + 1][tmp].setVisibility(View.VISIBLE);

            // AddScore
            String idA = "ra" + playerNumber + i;
            int tvAS = getResources().getIdentifier(idA, "id", getPackageName());

            myScoresTextView[playerID][tmp] = (TextView) findViewById(tvAS);
            myScoresTextView[playerID][tmp].setVisibility(View.VISIBLE);

            // Total Score
            String idT = "rt" + playerNumber + i;
            int tvTS = getResources().getIdentifier(idT, "id", getPackageName());

            myScoresTextView[playerID + 1][tmp] = (TextView) findViewById(tvTS);
            myScoresTextView[playerID + 1][tmp].setVisibility(View.VISIBLE);

            myTippsTextView[playerID][tmp].setTextSize(10);
            myTippsTextView[playerID + 1][tmp].setTextSize(10);
            myScoresTextView[playerID][tmp].setTextSize(10);
            myScoresTextView[playerID + 1][tmp].setTextSize(10);
        }
    }

    // make an alert to ask user if he really want to start a new game
    private void reallyWantToStartNewGame()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(R.string.really_want_to_start_new_game);
        builder1.setCancelable(true);


        builder1.setPositiveButton(
                R.string.start_a_new_game,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        startNewGame();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    // start a new game by going back to StartActivity
    private void startNewGame()
    {
        Intent intent = new Intent(this, StartActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    // open tipps activity with key tipps or result depending on openTippsOrResult
    private void openTippsOrResultActivity(String openTippsOrResult)
    {

      //  Log.d("GameBlock", "openTippsOrResult gameround: " + currentGameRound);


        results = myRealm.where(GameRealm.class).findAll();
        if (openTippsOrResult.equals(OPEN_TIPPS))
        {
            if (!tippOrResultCanceled)
            {
                //currentGameRound = currentGameRound + 1;
            }
            Intent intent = new Intent(this, Tipps.class);
            intent.putExtra("changeTippsOrResult", changeTippsOrResult);
            intent.putExtra("tippOrResult", OPEN_TIPPS);

            startActivity(intent);
        }
        else if (openTippsOrResult.equals(OPEN_RESULTS))
        {
            Intent intent = new Intent(this, Tipps.class);
            intent.putExtra("changeTippsOrResult", changeTippsOrResult);
            intent.putExtra("tippOrResult", OPEN_RESULTS);

            startActivity(intent);
        }
        else
        {
            Toast.makeText(GameBlock.this, R.string.error, Toast.LENGTH_SHORT).show();
        }

    }

    private void writeTippsOnBlock(int gameRound)
    {
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();
        completeTippsResultsRealmList = gameRealm.getTippsResults();
     //   Log.d("GameBlock", "writeTippsOnBlock completeTippsSize: " + completeTippsResultsRealmList.size());

        for (int i = 1; i <= gameRound; i++)
        {
        //    Log.d("GameRound", "writeTipps currentGameRound: " + currentGameRound);
        //    Log.d("GameRound", "writeTipps i: " + i);
            String idRound = "round" + i;

            int tvRoundId = getResources().getIdentifier(idRound, "id", getPackageName());
            tvRound = (TextView) findViewById(tvRoundId);
            tvRound.setText(String.valueOf(i) + ".");
            tvRound.setVisibility(View.VISIBLE);

            int j = i - 1;
            String idSeperator = "roundSeperator" + j;
            int tvSeperatorId = getResources().getIdentifier(idSeperator, "id", getPackageName());
            vSeperator = findViewById(tvSeperatorId);
            vSeperator.setVisibility(View.VISIBLE);

            String idP1 = "prt1" + i;
            int tvTP1 = getResources().getIdentifier(idP1, "id", getPackageName());
            String idP2 = "prt2" + i;
            int tvTP2 = getResources().getIdentifier(idP2, "id", getPackageName());
            String idP3 = "prt3" + i;
            int tvTP3 = getResources().getIdentifier(idP3, "id", getPackageName());
            String idP4 = "prt4" + i;
            int tvTP4 = getResources().getIdentifier(idP4, "id", getPackageName());
            String idP5 = "prt5" + i;
            int tvTP5 = getResources().getIdentifier(idP5, "id", getPackageName());
            String idP6 = "prt6" + i;
            int tvTP6 = getResources().getIdentifier(idP6, "id", getPackageName());

            myTippsTextView[0][j] = (TextView) findViewById(tvTP1);
            myTippsTextView[2][j] = (TextView) findViewById(tvTP2);
            myTippsTextView[4][j] = (TextView) findViewById(tvTP3);
            myTippsTextView[6][j] = (TextView) findViewById(tvTP4);
            myTippsTextView[8][j] = (TextView) findViewById(tvTP5);
            myTippsTextView[10][j] = (TextView) findViewById(tvTP6);


            for (int q = 0; q < currentPlayers; q++)
            {
                int b = q + (2 * currentPlayers * (i - 1));
                myTippsTextView[q * 2][j].setText(String.valueOf(completeTippsResultsRealmList.get(b).getVal()));
            }

            if (currentPlayers > 3)
            {
                tvRound.setTextSize(10);
                myTippsTextView[0][j].setTextSize(10);
                myTippsTextView[2][j].setTextSize(10);
                myTippsTextView[4][j].setTextSize(10);
                myTippsTextView[6][j].setTextSize(10);
                myTippsTextView[8][j].setTextSize(10);
                myTippsTextView[10][j].setTextSize(10);
            }
        }
    }

    private void writeTippsResultsOnBlock()
    {
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();
        completeTippsResultsRealmList = gameRealm.getTippsResults();

        for (int i = 1; i <= currentGameRound - 1; i++)
        {

            int j = i - 1;
            String idSeperator = "roundSeperator" + i;
            int tvSeperatorId = getResources().getIdentifier(idSeperator, "id", getPackageName());
            vSeperator = findViewById(tvSeperatorId);
            vSeperator.setVisibility(View.VISIBLE);

            String idP1 = "prr1" + i;
            int tvTP1 = getResources().getIdentifier(idP1, "id", getPackageName());
            String idP2 = "prr2" + i;
            int tvTP2 = getResources().getIdentifier(idP2, "id", getPackageName());
            String idP3 = "prr3" + i;
            int tvTP3 = getResources().getIdentifier(idP3, "id", getPackageName());
            String idP4 = "prr4" + i;
            int tvTP4 = getResources().getIdentifier(idP4, "id", getPackageName());
            String idP5 = "prr5" + i;
            int tvTP5 = getResources().getIdentifier(idP5, "id", getPackageName());
            String idP6 = "prr6" + i;
            int tvTP6 = getResources().getIdentifier(idP6, "id", getPackageName());

            myTippsTextView[1][j] = (TextView) findViewById(tvTP1);
            myTippsTextView[3][j] = (TextView) findViewById(tvTP2);
            myTippsTextView[5][j] = (TextView) findViewById(tvTP3);
            myTippsTextView[7][j] = (TextView) findViewById(tvTP4);
            myTippsTextView[9][j] = (TextView) findViewById(tvTP5);
            myTippsTextView[11][j] = (TextView) findViewById(tvTP6);


            for (int q = 0; q < currentPlayers; q++)
            {
                int b = q + (2 * currentPlayers * (i - 1)) + currentPlayers;
                myTippsTextView[(q * 2) + 1][j].setText(String.valueOf(completeTippsResultsRealmList.get(b).getVal()));
            }

            if (currentPlayers > 3)
            {
                tvRound.setTextSize(10);
                myTippsTextView[1][j].setTextSize(10);
                myTippsTextView[3][j].setTextSize(10);
                myTippsTextView[5][j].setTextSize(10);
                myTippsTextView[7][j].setTextSize(10);
                myTippsTextView[9][j].setTextSize(10);
                myTippsTextView[11][j].setTextSize(10);
            }
        }
    }

    private void writeScoresOnBlock()
    {
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();
        completeScoresRealmList = gameRealm.getScoreAddTotal();

    //    Log.d("GameBlock", "completeScoresSize: " + completeScoresRealmList.size());

        for (int i = 1; i <= currentGameRound - 1; i++)
        {
            int tmp = i - 1;

            // Add Score
            String idP1 = "ra1" + i;
            int tvTP1 = getResources().getIdentifier(idP1, "id", getPackageName());
            String idP2 = "ra2" + i;
            int tvTP2 = getResources().getIdentifier(idP2, "id", getPackageName());
            String idP3 = "ra3" + i;
            int tvTP3 = getResources().getIdentifier(idP3, "id", getPackageName());
            String idP4 = "ra4" + i;
            int tvTP4 = getResources().getIdentifier(idP4, "id", getPackageName());
            String idP5 = "ra5" + i;
            int tvTP5 = getResources().getIdentifier(idP5, "id", getPackageName());
            String idP6 = "ra6" + i;
            int tvTP6 = getResources().getIdentifier(idP6, "id", getPackageName());

            myScoresTextView[0][tmp] = (TextView) findViewById(tvTP1);
            myScoresTextView[2][tmp] = (TextView) findViewById(tvTP2);
            myScoresTextView[4][tmp] = (TextView) findViewById(tvTP3);
            myScoresTextView[6][tmp] = (TextView) findViewById(tvTP4);
            myScoresTextView[8][tmp] = (TextView) findViewById(tvTP5);
            myScoresTextView[10][tmp] = (TextView) findViewById(tvTP6);


            for (int q = 0; q < currentPlayers; q++)
            {
                int b = q + (2 * currentPlayers * (i - 1));
                myScoresTextView[(q * 2)][tmp].setText(String.valueOf(completeScoresRealmList.get(b).getVal()));
            }

            // Total Score
            String idrP1 = "rt1" + i;
            int tvrTP1 = getResources().getIdentifier(idrP1, "id", getPackageName());
            String idrP2 = "rt2" + i;
            int tvrTP2 = getResources().getIdentifier(idrP2, "id", getPackageName());
            String idrP3 = "rt3" + i;
            int tvrTP3 = getResources().getIdentifier(idrP3, "id", getPackageName());

            String idrP4 = "rt4" + i;
            int tvrTP4 = getResources().getIdentifier(idrP4, "id", getPackageName());
            String idrP5 = "rt5" + i;
            int tvrTP5 = getResources().getIdentifier(idrP5, "id", getPackageName());
            String idrP6 = "rt6" + i;
            int tvrTP6 = getResources().getIdentifier(idrP6, "id", getPackageName());

            myScoresTextView[1][tmp] = (TextView) findViewById(tvrTP1);
            myScoresTextView[3][tmp] = (TextView) findViewById(tvrTP2);
            myScoresTextView[5][tmp] = (TextView) findViewById(tvrTP3);
            myScoresTextView[7][tmp] = (TextView) findViewById(tvrTP4);
            myScoresTextView[9][tmp] = (TextView) findViewById(tvrTP5);
            myScoresTextView[11][tmp] = (TextView) findViewById(tvrTP6);

            for (int q = 0; q < currentPlayers; q++)
            {
                int b = q + (2 * currentPlayers * (i - 1)) + currentPlayers;
                myScoresTextView[(q * 2) + 1][tmp].setText(String.valueOf(completeScoresRealmList.get(b).getVal()));
            }

            if (currentPlayers > 3)
            {
                myScoresTextView[0][tmp].setTextSize(10);
                myScoresTextView[2][tmp].setTextSize(10);
                myScoresTextView[4][tmp].setTextSize(10);
                myScoresTextView[6][tmp].setTextSize(10);
                myScoresTextView[8][tmp].setTextSize(10);
                myScoresTextView[10][tmp].setTextSize(10);
                myScoresTextView[1][tmp].setTextSize(10);
                myScoresTextView[3][tmp].setTextSize(10);
                myScoresTextView[5][tmp].setTextSize(10);
                myScoresTextView[7][tmp].setTextSize(10);
                myScoresTextView[9][tmp].setTextSize(10);
                myScoresTextView[11][tmp].setTextSize(10);
            }
        }
    }

    private void changePlayerNames()
    {
        Intent intent = new Intent(this, PlayerSettings.class);
        intent.putExtra("changenames", true);
        startActivity(intent);
    }

    private void checkWinner()
    {
        ToSort toSortArray[] = new ToSort[currentPlayers];
        List<ToSort> sortList = new ArrayList<ToSort>();
        int size = completeScoresRealmList.size();

        for (int i = 0; i < currentPlayers; i++)
        {
            int id = size - ((currentPlayers - i));
            int score = completeScoresRealmList.get(id).getVal();
            toSortArray[i] = new ToSort(new Float(score), playerNamesRealmList.get(i).getVal());
            sortList.add(toSortArray[i]);
        }

        Collections.sort(sortList);
        for (ToSort toSort : sortList)
        {
            Log.d("GameBlock", "Result: " + toSort.toString());
        }


        boolean twoWinner = false;
        winner = sortList.get(sortList.size() - 1).toString();
        if (sortList.get(sortList.size() - 1).getWinnerScore() == sortList.get(sortList.size() - 2).getWinnerScore())
        {
            winner = sortList.get(sortList.size() - 1).toString() + " & " + sortList.get(sortList.size() - 2).toString();
            twoWinner = true;
        }
        winnerScore = (int) sortList.get(sortList.size() - 1).getWinnerScore();
        Log.d("GameBlock", "checkWinner winnerscore: " + winnerScore);
        safeWinnerScoreIntoNewRealm();

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(R.string.game_over);
        builder1.setMessage(getString(R.string.congratulations) + " " + winner + ",\n" + getString(R.string.onewinner) + " " + winnerScore + " " + getString(R.string.win));
        if (twoWinner)
        {
            builder1.setMessage(getString(R.string.congratulations) + " " + winner + ",\n" + getString(R.string.twowinner) + " " + winnerScore + " " + getString(R.string.win));
        }
        builder1.setCancelable(true);


        builder1.setPositiveButton(
                R.string.scoreboard,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        dialog.cancel();
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();





      //  Toast.makeText(GameBlock.this, "Winner: " + sortList.get(sortList.size()-1).toString(), Toast.LENGTH_SHORT).show();

    }

    private void safeWinnerScoreIntoNewRealm()
    {
        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext())
                .name("highscoreRealm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

        myRealm = Realm.getDefaultInstance();

        myRealm.beginTransaction();

        HighScoreRealm highScoreRealm = myRealm.createObject(HighScoreRealm.class);
        highScoreRealm.setWinnerScore(winnerScore);
        highScoreRealm.setWinnerName(winner);
        highScoreRealm.setGameDate(currentGameDate);
        highScoreRealm.setGameName(currentGameName);
        highScoreRealm.setPlayerCount(currentPlayers);

        myRealm.commitTransaction();

        checkIfWinnerScoreAlreadyInDataBase();

        RealmConfiguration configuration2 = new RealmConfiguration.Builder(getApplicationContext())
                .name("myRealm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration2);

    }

    private void checkIfWinnerScoreAlreadyInDataBase()
    {
        myRealm.beginTransaction();
        RealmResults<HighScoreRealm> highScoreResults = myRealm.where(HighScoreRealm.class).findAll();
        HighScoreRealm myHighscore = highScoreResults.last();

        if (highScoreResults.size() > 1)
        {
            for (int i = highScoreResults.size()-2; i >= 0; i--)
            {
                if (myHighscore.getGameDate().equalsIgnoreCase(highScoreResults.get(i).getGameDate()))
                {
                    highScoreResults.remove(i);
                }
            }
        }
        myRealm.commitTransaction();
    }

    private void changeTippsOrResult()
    {
   //     Log.d("GameBlock", "changeTippsOrResult gameround: " + currentGameRound);

        changeTippsOrResult = true;
    //    Log.d("GameBlock", "changeTippsOrResult: " + changeTippsOrResult);

        if (resultEntered)
        {
            currentGameRound = gameRealm.getCurrentGameRound() - 1;
        }
        else
        {
            currentGameRound = gameRealm.getCurrentGameRound();
        }
     //   Log.d("GameBlock", "changeTippsOrResult gameround: " + currentGameRound);


        if (resultEntered)
        {
            openTippsOrResultActivity(OPEN_RESULTS);
        }
        else if (tippsSaved)
        {
            openTippsOrResultActivity(OPEN_TIPPS);
        }
    }

    private void openSettings()
    {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    private void setUpFocus()
    {
        int add;
        if (currentGameRound < 10)
        {
            add = 1;
        }
        else
        {
            add = currentGameRound - 9;
        }
        String idFocus = "tableRow" + add;
        int tvFocusId = getResources().getIdentifier(idFocus, "id", getPackageName());
        tableRow = (TableRow) findViewById(tvFocusId);
        svGameBlock.post(new Runnable()
        {
            @Override
            public void run()
            {
                svGameBlock.scrollTo(0, tableRow.getTop());
            }
        });
    }

    // set display always active if checked in GameSettings (called by onCreate)
    private void setDisplayAlwaysActive(boolean value)
    {
        if (value)
        {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        else
        {
            getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_game_block, menu);
        invalidateOptionsMenu();


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (tippsSaved)
        {
            menu.findItem(R.id.nextTippOrErgebnis).setTitle(R.string.next_result);
            menu.findItem(R.id.changeTippOrResult).setTitle(R.string.changeTipps);
        }
        else
        {
            if (currentGameRound != 1)
            {
                menu.findItem(R.id.changeTippOrResult).setTitle(R.string.changeResults);
            }
            else
            {
                menu.findItem(R.id.changeTippOrResult).setTitle(R.string.change);
                menu.findItem(R.id.changeTippOrResult).setEnabled(false);
            }

            if (currentGameRound <= maxGameRounds)
            {
                menu.findItem(R.id.nextTippOrErgebnis).setTitle(R.string.next_tipp);

            }
            else
            {
                menu.findItem(R.id.nextTippOrErgebnis).setEnabled(false);
               // Toast.makeText(GameBlock.this, R.string.game_over, Toast.LENGTH_LONG).show();
                if (showWinner)
                {
                    checkWinner();
                    showWinner = false;
                }
            }
        }


        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nextTippOrErgebnis:
                changeTippsOrResult = false;
                if (tippsSaved)
                {
                    openTippsOrResultActivity(OPEN_RESULTS);
                }
                else if (resultEntered)
                {
                    openTippsOrResultActivity(OPEN_TIPPS);
                }
                break;
            case R.id.changeNames:
                changePlayerNames();
                break;
            case R.id.startNewGame:
                reallyWantToStartNewGame();
                break;
            case R.id.changeTippOrResult:
                changeTippsOrResult();
                break;
            case R.id.settings:
                openSettings();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private boolean backPressed = false;

    @Override
    public void onBackPressed()
    {
        if (backPressed)
        {
            finish();
            super.onBackPressed();
        }
        else
        {
            Toast.makeText(GameBlock.this, R.string.backpress, Toast.LENGTH_LONG).show();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    backPressed = false;
                }
            }, 3000);
            backPressed = true;
        }
    }

    public static int getCurrentGameRound()
    {
        return currentGameRound;
    }

    public static void setTippsSaved(boolean tippsSaved)
    {
        GameBlock.tippsSaved = tippsSaved;
    }

    public static void setResultEntered(boolean resultEntered)
    {
        GameBlock.resultEntered = resultEntered;
    }

    public static void setTippOrResultCanceled(boolean tippOrResultCanceled)
    {
        GameBlock.tippOrResultCanceled = tippOrResultCanceled;
    }

    public static int getCurrentPlayers()
    {
        return currentPlayers;
    }

    public static RealmList<RealmString> getPlayerNamesRealmList()
    {
        return playerNamesRealmList;
    }

    public static boolean isStitchesEqualTipps()
    {
        return stitchesEqualTipps;
    }
}
