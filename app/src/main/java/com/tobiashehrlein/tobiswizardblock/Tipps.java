package com.tobiashehrlein.tobiswizardblock;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class Tipps extends AppCompatActivity   implements
        SeekBar.OnSeekBarChangeListener,
        View.OnClickListener
{

    private int currentPlayers;
    private RealmList<RealmString> playerNamesRealmList;
    private TextView ueberschrift;

    private TextView[] tvStitchesPlayers = new TextView[6];
    private TextView[] tvStitchesTippsPlayers = new TextView[6];
    private SeekBar[] seekbarTippsPlayer = new SeekBar[6];
    private LinearLayout[] seekBarTippsLayoutPlayers = new LinearLayout[3];
    private static RealmList<RealmInt> playerTippsAndResultRealmList;
    private TextView[] tvStitchesTippsSaid = new TextView[6];
    private Button btSaveTipps;
    private String tippsOrResult;
    private boolean resultEqualPossibleStitches;
    private boolean tippsEqualRound;
    private Realm myRealm;
    private GameRealm gameRealm;
    private int currentGameRound;
    private boolean changeTippOrResult;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipps);

        setDisplayAlwaysActive(Settings.isDisplayActive());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        tippsOrResult = intent.getStringExtra("tippOrResult");
        changeTippOrResult = intent.getBooleanExtra("changeTippsOrResult", false);

        setTitle(tippsOrResult + " " + getString(R.string.round) + " " + GameBlock.getCurrentGameRound());
        playerTippsAndResultRealmList = new RealmList<>();
        getGameInfos();
        setUpTitleAndViews();
        setUpPlayerNames();
        setUpSeekbarsMaximum();

        if (tippsOrResult.equals("Ergebnis"))
        {
            showTippsToo();
        }
    }

    // show tipps when entering results
    private void showTippsToo()
    {
        myRealm = Realm.getDefaultInstance();

        tvStitchesTippsSaid[0] = (TextView) findViewById(R.id.tvStitchesSaidOne);
        tvStitchesTippsSaid[1] = (TextView) findViewById(R.id.tvStitchesSaidTwo);
        tvStitchesTippsSaid[2] = (TextView) findViewById(R.id.tvStitchesSaidThree);
        tvStitchesTippsSaid[3] = (TextView) findViewById(R.id.tvStitchesSaidFour);
        tvStitchesTippsSaid[4] = (TextView) findViewById(R.id.tvStitchesSaidFive);
        tvStitchesTippsSaid[5] = (TextView) findViewById(R.id.tvStitchesSaidSix);

        RealmResults<GameRealm> results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.last();
        RealmList<RealmInt> completeTipps = gameRealm.getTippsResults();

        for (int i = 0; i < currentPlayers; i++)
        {
            if (changeTippOrResult)
            {
                tvStitchesTippsSaid[i].setText("(" + String.valueOf(completeTipps.get(completeTipps.size() - (currentPlayers * 2 - i)).getVal()) + ")");
            }
            else
            {
                tvStitchesTippsSaid[i].setText("(" + String.valueOf(completeTipps.get(completeTipps.size() - (currentPlayers - i)).getVal()) + ")");

            }
        }

    }

    // get relevant informations from GameBlock
    private void getGameInfos()
    {
        currentPlayers = GameBlock.getCurrentPlayers();
        currentGameRound = GameBlock.getCurrentGameRound();
        playerNamesRealmList = GameBlock.getPlayerNamesRealmList();
      //  Log.d("Tipps", "getGameInfo gameround: " + currentGameRound);
    }

    // show title depnding on tippsOrResult and find all views
    private void setUpTitleAndViews()
    {
        ueberschrift = (TextView) findViewById(R.id.tvUeberschrift);
        if (tippsOrResult.equalsIgnoreCase("Ergebnis"))
        {
            ueberschrift.setText(R.string.how_many_stitches_result);
        }
        else
        {
            ueberschrift.setText(R.string.how_many_stitches);
        }

        tvStitchesPlayers[0] = (TextView) findViewById(R.id.tvStitchesPlayerOne);
        tvStitchesPlayers[1] = (TextView) findViewById(R.id.tvStitchesPlayerTwo);
        tvStitchesPlayers[2] = (TextView) findViewById(R.id.tvStitchesPlayerThree);
        tvStitchesPlayers[3] = (TextView) findViewById(R.id.tvStitchesPlayerFour);
        tvStitchesPlayers[4] = (TextView) findViewById(R.id.tvStitchesPlayerFive);
        tvStitchesPlayers[5] = (TextView) findViewById(R.id.tvStitchesPlayerSix);


        seekbarTippsPlayer[0] = (SeekBar) findViewById(R.id.seekBarTippsPlayerOne);
        seekbarTippsPlayer[1] = (SeekBar) findViewById(R.id.seekBarTippsPlayerTwo);
        seekbarTippsPlayer[2] = (SeekBar) findViewById(R.id.seekBarTippsPlayerThree);
        seekbarTippsPlayer[3] = (SeekBar) findViewById(R.id.seekBarTippsPlayerFour);
        seekbarTippsPlayer[4] = (SeekBar) findViewById(R.id.seekBarTippsPlayerFive);
        seekbarTippsPlayer[5] = (SeekBar) findViewById(R.id.seekBarTippsPlayerSix);



        tvStitchesTippsPlayers[0] = (TextView) findViewById(R.id.tvStitchesPlayerOneTipps);
        tvStitchesTippsPlayers[1] = (TextView) findViewById(R.id.tvStitchesPlayerTwoTipps);
        tvStitchesTippsPlayers[2] = (TextView) findViewById(R.id.tvStitchesPlayerThreeTipps);
        tvStitchesTippsPlayers[3] = (TextView) findViewById(R.id.tvStitchesPlayerFourTipps);
        tvStitchesTippsPlayers[4] = (TextView) findViewById(R.id.tvStitchesPlayerFiveTipps);
        tvStitchesTippsPlayers[5] = (TextView) findViewById(R.id.tvStitchesPlayerSixTipps);

        // tvTippsError = (TextView) findViewById(R.id.tvTippsError);
        btSaveTipps = (Button) findViewById(R.id.btSaveTipps);
        if (tippsOrResult.equals("Tipp"))
        {
            btSaveTipps.setText(R.string.next_tipp);
        }
        else
        {
            btSaveTipps.setText(R.string.next_result);
        }

        seekbarTippsPlayer[0].setOnSeekBarChangeListener(this);
        seekbarTippsPlayer[1].setOnSeekBarChangeListener(this);
        seekbarTippsPlayer[2].setOnSeekBarChangeListener(this);
        seekbarTippsPlayer[3].setOnSeekBarChangeListener(this);
        seekbarTippsPlayer[4].setOnSeekBarChangeListener(this);
        seekbarTippsPlayer[5].setOnSeekBarChangeListener(this);
        btSaveTipps.setOnClickListener(this);
    }

    // show textview and seekbar of all players depending on currentplayers
    private void setUpPlayerNames()
    {
        tvStitchesPlayers[0].setText(playerNamesRealmList.get(0).getVal());
        tvStitchesPlayers[1].setText(playerNamesRealmList.get(1).getVal());
        tvStitchesPlayers[2].setText(playerNamesRealmList.get(2).getVal());

        switch (currentPlayers)
        {
            case 6:
                seekBarTippsLayoutPlayers[2] = (LinearLayout) findViewById(R.id.seekBarTippsPlayerSixLayout);
                seekBarTippsLayoutPlayers[2].setVisibility(View.VISIBLE);
                tvStitchesPlayers[5].setVisibility(View.VISIBLE);
                tvStitchesPlayers[5].setText(playerNamesRealmList.get(5).getVal());
            case 5:
                seekBarTippsLayoutPlayers[1] = (LinearLayout) findViewById(R.id.seekBarTippsPlayerFiveLayout);
                seekBarTippsLayoutPlayers[1].setVisibility(View.VISIBLE);
                tvStitchesPlayers[4].setVisibility(View.VISIBLE);
                tvStitchesPlayers[4].setText(playerNamesRealmList.get(4).getVal());
            case 4:
                seekBarTippsLayoutPlayers[0] = (LinearLayout) findViewById(R.id.seekBarTippsPlayerFourLayout);
                seekBarTippsLayoutPlayers[0].setVisibility(View.VISIBLE);
                tvStitchesPlayers[3].setVisibility(View.VISIBLE);
                tvStitchesPlayers[3].setText(playerNamesRealmList.get(3).getVal());
        }

    }

    private void setUpSeekbarsMaximum()
    {
        seekbarTippsPlayer[0].setMax(GameBlock.getCurrentGameRound());
        seekbarTippsPlayer[1].setMax(GameBlock.getCurrentGameRound());
        seekbarTippsPlayer[2].setMax(GameBlock.getCurrentGameRound());
        seekbarTippsPlayer[3].setMax(GameBlock.getCurrentGameRound());
        seekbarTippsPlayer[4].setMax(GameBlock.getCurrentGameRound());
        seekbarTippsPlayer[5].setMax(GameBlock.getCurrentGameRound());
    }


    // on button click
    @Override
    public void onClick(View view)
    {
        if (tippsOrResult.equals("Tipp"))
        {
            for (int i = 0; i < currentPlayers; i++)
            {
                RealmInt myRealmInt = new RealmInt(Integer.valueOf(tvStitchesTippsPlayers[i].getText().toString()));
                playerTippsAndResultRealmList.add(myRealmInt);
            }

            int tippsSum = 0;
            for (int i = 0; i < playerTippsAndResultRealmList.size(); i++)
            {
                tippsSum = tippsSum + playerTippsAndResultRealmList.get(i).getVal();
             //   i = i + 2;
            }
          //  Log.d("Tipps", "TippsSum: " + tippsSum);


            if (!GameBlock.isStitchesEqualTipps()) // Tipps darf nicht gleich möglicher Stiche sein
            {
                if (tippsSum == currentGameRound)
                {
                    if (GameSettings.isFirstRoundCanBeEqual())
                    {
                        if (currentGameRound == 1)
                        {
                            tippsEqualRound = false;
                        }
                        else
                        {
                            Snackbar snackbar = Snackbar.make(view, R.string.tipps_can_not_be_equal_round, Snackbar.LENGTH_SHORT);
                            snackbar.show();

                            final Snackbar snackbar2 = Snackbar.make(view, R.string.last_one_more_or_less, Snackbar.LENGTH_INDEFINITE);
                            snackbar2.setAction("Ok", new View.OnClickListener()
                            {
                                @Override
                                public void onClick(View view)
                                {
                                    snackbar2.dismiss();
                                }
                            });

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    snackbar2.show();
                                }
                            }, 2000);

                            //tvTippsError.setText(R.string.tipps_can_not_be_equal_round);
                           // tvTippsError.setGravity(Gravity.CENTER);
                            tippsEqualRound = true;
                            playerTippsAndResultRealmList.clear();
                        }
                    }
                    else
                    {
                        Snackbar snackbar = Snackbar.make(view, R.string.tipps_can_not_be_equal_round, Snackbar.LENGTH_SHORT);
                        snackbar.show();

                        final Snackbar snackbar2 = Snackbar.make(view, R.string.last_one_more_or_less, Snackbar.LENGTH_INDEFINITE);
                        snackbar2.setAction("Ok", new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View view)
                            {
                                snackbar2.dismiss();
                            }
                        });

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable()
                        {
                            @Override
                            public void run()
                            {
                                snackbar2.show();
                            }
                        }, 2000);
                      //  tvTippsError.setText(R.string.tipps_can_not_be_equal_round);
                      //  tvTippsError.setGravity(Gravity.CENTER);
                        tippsEqualRound = true;
                        playerTippsAndResultRealmList.clear();
                    }

                }
                else
                {
                    tippsEqualRound = false;
                }
            }
            else
            {
                tippsEqualRound = false;
            }

            if (!tippsEqualRound)
            {
                GameBlock.setTippsSaved(true);
                GameBlock.setResultEntered(false);
                GameBlock.setTippOrResultCanceled(false);
                resultEqualPossibleStitches = true;
            }
        }
        else if (tippsOrResult.equals("Ergebnis"))
        {
            for (int i = 0; i < currentPlayers; i++)
            {
                RealmInt myRealmInt = new RealmInt(Integer.valueOf(tvStitchesTippsPlayers[i].getText().toString()));
                playerTippsAndResultRealmList.add(myRealmInt);
            }

            int sum = 0;
            for (int i = 0; i < playerTippsAndResultRealmList.size(); i++)
            {
                sum = sum + playerTippsAndResultRealmList.get(i).getVal();
                // i = i + 2;
            }
           // Log.d("Tipps", "Sum: " + sum);

            if (sum == currentGameRound)
            {
                resultEqualPossibleStitches = true;

                GameBlock.setResultEntered(true);
                GameBlock.setTippsSaved(false);
                GameBlock.setTippOrResultCanceled(false);
            }
            else
            {
                resultEqualPossibleStitches = false;
                final Snackbar snackbar = Snackbar.make(view, R.string.tipps_error, Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Ok", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
               // tvTippsError.setText(R.string.tipps_error);
                playerTippsAndResultRealmList.clear();
            }
        }

        if (resultEqualPossibleStitches)
        {
          /*  for (int i = 0; i < playerTippsAndResultRealmList.size(); i++)
            {
                Log.d("Tipps", "Tipps / Results: " + playerTippsAndResultRealmList.get(i).getVal());
            } */

            Intent intent = new Intent(this, GameBlock.class);


            if (tippsOrResult.equals("Ergebnis"))
            {
                intent.putExtra("backfromresults", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
            intent.putExtra("backFromTipps", true);
            intent.putExtra("changeTippsOrResult", changeTippOrResult);
          //  Log.d("Tipps", "onClick changeTippsOrResult was " + changeTippOrResult);
            startActivity(intent);
        }
    }

    // sliding progress bar change it's textview with the value
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b)
    {
        switch (seekBar.getId())
        {
            case R.id.seekBarTippsPlayerOne:
                tvStitchesTippsPlayers[0].setText(String.valueOf(i));
                break;
            case R.id.seekBarTippsPlayerTwo:
                tvStitchesTippsPlayers[1].setText(String.valueOf(i));
                break;
            case R.id.seekBarTippsPlayerThree:
                tvStitchesTippsPlayers[2].setText(String.valueOf(i));
                break;
            case R.id.seekBarTippsPlayerFour:
                tvStitchesTippsPlayers[3].setText(String.valueOf(i));
                break;
            case R.id.seekBarTippsPlayerFive:
                tvStitchesTippsPlayers[4].setText(String.valueOf(i));
                break;
            case R.id.seekBarTippsPlayerSix:
                tvStitchesTippsPlayers[5].setText(String.valueOf(i));
                break;
        }
    }

    // do nothing
    @Override
    public void onStartTrackingTouch(SeekBar seekBar)
    {

    }

    // do nothing
    @Override
    public void onStopTrackingTouch(SeekBar seekBar)
    {

    }

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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        GameBlock.setTippOrResultCanceled(true);
        super.onBackPressed();
    }

    public static RealmList<RealmInt> getPlayerTippsAndResultRealmList()
    {
        return playerTippsAndResultRealmList;
    }
}
