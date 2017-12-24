package com.tobiashehrlein.tobiswizardblock.old;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tobiashehrlein.tobiswizardblock.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class PlayerSettings extends AppCompatActivity implements View.OnClickListener
{

    private EditText[] etPlayers = new EditText[6];
    private Button btNext;
    private boolean allPlayerNamesEntered;
    private int currentPlayers;
    private static String currentGameDate;
    private String currentGameName;
    private RealmList<RealmString> playerNames;
    private RealmList<RealmInt> scores;
    private RealmList<RealmInt> tipps;
    private int currentGameRound;
    private int maxGameRounds;
    private Realm myRealm;
    private RealmResults<GameRealm> results;
    private GameRealm gameRealm;
    private boolean comefromblock;
    private boolean stitchesEqualTipps;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_settings);
        setTitle(R.string.player_settings);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        myRealm = Realm.getDefaultInstance();

        etPlayers[0] = (EditText) findViewById(R.id.etPlayerOne);
        etPlayers[1] = (EditText) findViewById(R.id.etPlayerTwo);
        etPlayers[2] = (EditText) findViewById(R.id.etPlayerThree);
        etPlayers[3] = (EditText) findViewById(R.id.etPlayerFour);
        etPlayers[4] = (EditText) findViewById(R.id.etPlayerFive);
        etPlayers[5] = (EditText) findViewById(R.id.etPlayerSix);
        btNext = (Button) findViewById(R.id.btNext2);
        btNext.setOnClickListener(this);
        playerNames = new RealmList<>();

        comefromblock = getIntent().getBooleanExtra("changenames", false);
     //   Log.d("PlayerSettings", "Comefromblock --> Change Names: " + comefromblock);
        if (comefromblock)
        {
            getInfosFromRealm();
        }
        else
        {
            currentPlayers = GameSettings.getCurrentPlayers();
            currentGameName = GameSettings.getCurrentGameName();
            currentGameRound = 1;
            maxGameRounds = 60 / currentPlayers;
            stitchesEqualTipps = GameSettings.isStitchesEqualPlays();
         //   Log.d("PlayerSettings", "StitchesEqualTipps is " + stitchesEqualTipps);
        }
        showPlayerNameEditTexts();
    }

    private void getInfosFromRealm()
    {
       // Log.d("PlayerSettings", "comeBackFromBlock and getInfosFromRealm");
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();
        currentPlayers = gameRealm.getCountPlayers();
        currentGameName = gameRealm.getGameName();
        currentGameDate = gameRealm.getGameDate();
        scores = gameRealm.getScoreAddTotal();
        tipps = gameRealm.getTippsResults();
        currentGameRound = gameRealm.getCurrentGameRound();
        maxGameRounds = gameRealm.getMaxGameRounds();
        playerNames = gameRealm.getPlayerNames();
        stitchesEqualTipps = gameRealm.isStitchesEqualsTipps();
    }

    private void showPlayerNameEditTexts()
    {

        if (!playerNames.isEmpty())
        {
            for (int i = 0; i < currentPlayers; i++)
            {
                etPlayers[i].setText(playerNames.get(i).getVal());
            }
        }
//        switch (currentPlayers)
//        {
//            case 6:
//                etPlayers[5].setVisibility(View.VISIBLE);
//            case 5:
//                etPlayers[4].setVisibility(View.VISIBLE);
//            case 4:
//                etPlayers[3].setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onClick(View v)
    {
        allPlayerNamesEntered = true;
        checkPlayerNames(currentPlayers);
        for (int i = 0; i < currentPlayers; i++)
        {
            if (etPlayers[i].getText().toString().isEmpty())
            {
             //   Log.d("PlayerSettings", "EditText " + i + " is empty");
                allPlayerNamesEntered = false;
            }

        }
        if (allPlayerNamesEntered)
        {

            if (!comefromblock)
            {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyy, HH:mm:ss", Locale.GERMANY);
                currentGameDate = simpleDateFormat.format(calendar.getTime());
                GameBlock.setTippsSaved(false);
                GameBlock.setResultEntered(true);
            }
          //  Log.d("Playersettings", "Gamedate: " + currentGameDate);

            saveMyRealmGame();
            if (comefromblock)
            {
                deleteLastBecauseDuplicated();
            }

            showMyRealms();

            Intent intent = new Intent(this, GameBlock.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            intent.putExtra("newGame", true);
            if (comefromblock)
            {
                intent.putExtra("comefromlastgame", true);
            }
            startActivity(intent);
        }
    }

    private void deleteLastBecauseDuplicated()
    {
       // Log.d("PlayerSettings", "delete second last GameSettings because it's duplicated");
        myRealm.beginTransaction();
        results = myRealm.where(GameRealm.class).findAll();
      //  Log.d("PlayerSettings", "Results size: " + results.size());
        results.remove(results.size()-2);

        gameRealm = results.last();
        RealmList<RealmString> player = gameRealm.getPlayerNames();
        for (int i = 0; i < gameRealm.getCountPlayers(); i++)
        {
            Log.d("Name last", player.get(i).getVal());
        }

        myRealm.commitTransaction();
    }

    private void showMyRealms()
    {
        RealmResults<GameRealm> results = myRealm.where(GameRealm.class).findAll();
       // Log.d("showMyRealms", "Results: " + results.size() + " " + results.toString());

    }

    private void checkPlayerNames(int currentPlayers)
    {
        myRealm.beginTransaction();
        playerNames.clear();
        switch (currentPlayers)
        {
            case 3:
                getPlayerNamesThree();
                break;
            case 4:
                getPlayerNamesFour();
                break;
            case 5:
                getPlayerNamesFive();
                break;
            case 6:
                getPlayerNamesSix();
                break;
        }

        myRealm.commitTransaction();

        for (int i = 0; i < currentPlayers; i++)
        {
            if (playerNames.get(i).getVal().isEmpty())
            {
               etPlayers[i].setError(getString(R.string.player_name_not_entered));
            }
        }
    }

    private void getPlayerNamesThree()
    {
        RealmString string0 = new RealmString(etPlayers[0].getText().toString());
        playerNames.add(string0);
        RealmString string1 = new RealmString(etPlayers[1].getText().toString());
        playerNames.add(string1);
        RealmString string2 = new RealmString(etPlayers[2].getText().toString());
        playerNames.add(string2);
      /*  Log.d("PlayerSettings", "getPlayerNames " + playerNames.get(0).getVal());
        Log.d("PlayerSettings", "getPlayerNames " + playerNames.get(1).getVal());
        Log.d("PlayerSettings", "getPlayerNames " + playerNames.get(2).getVal()); */

    }

    private void getPlayerNamesFour()
    {
        getPlayerNamesThree();
        RealmString string3 = new RealmString(etPlayers[3].getText().toString());
        playerNames.add(3, string3);
    }

    private void getPlayerNamesFive()
    {
        getPlayerNamesFour();
        RealmString string4 = new RealmString(etPlayers[4].getText().toString());
        playerNames.add(4, string4);
    }

    private void getPlayerNamesSix()
    {
        getPlayerNamesFive();
        RealmString string5 = new RealmString(etPlayers[5].getText().toString());
        playerNames.add(5, string5);
    }

    private void saveMyRealmGame()
    {
        myRealm = Realm.getDefaultInstance();
        myRealm.beginTransaction();
        gameRealm = myRealm.createObject(GameRealm.class);

        RealmList<RealmString> playerNamesNew = new RealmList<>();
        for (int i = 0; i < playerNames.size(); i++)
        {
            RealmString realmString = myRealm.copyToRealm(new RealmString(playerNames.get(i).getVal()));
            playerNamesNew.add(realmString);
        }

        if(!comefromblock)
        {
            scores = new RealmList<>();
            tipps = new RealmList<>();

          //  Log.d("PlayerSettings", "maxgamerounds: " + maxGameRounds);
        }

      //  Log.d("PlayerSettings", "saveMyRealm gameround: " + currentGameRound);

        for (int i = 0; i < playerNames.size(); i++)
        {
            Log.d("PlayerSettings", "playerNames " + i + " " + playerNamesNew.get(i).getVal());
        }


        gameRealm.setGameName(currentGameName);
        gameRealm.setGameDate(currentGameDate);
        gameRealm.setCountPlayers(currentPlayers);
        gameRealm.setPlayerNames(playerNamesNew);
        gameRealm.setTippsResults(tipps);
        gameRealm.setScoreAddTotal(scores);
        gameRealm.setCurrentGameRound(currentGameRound);
        gameRealm.setMaxGameRounds(maxGameRounds);
        gameRealm.setStitchesEqualsTipps(stitchesEqualTipps);
        myRealm.commitTransaction();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

//        getMenuInflater().inflate(R.menu.menu_players, menu);
        invalidateOptionsMenu();


        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
//            case R.id.goFurther:
//                onClick(item.getActionView());
        }
        return super.onOptionsItemSelected(item);
    }
}
