package com.tobiashehrlein.tobiswizardblock.old;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.Toast;

import com.tobiashehrlein.tobiswizardblock.R;

public class GameSettings extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener,
        View.OnClickListener,
        NumberPicker.OnValueChangeListener,
        View.OnLongClickListener
{

    private Switch switchStitches;
    private Button btNext;
    private NumberPicker howMuchPlayers;
    private static String currentGameName;
    private static int currentPlayers;
    private static boolean stitchesEqualPlays;
    private static boolean isDisplayActiveChecked;
    private boolean longClicked;
    private EditText etGameName;
    private static boolean firstRoundCanBeEqual;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game_settings);

        findMyViews();

        setTitle(R.string.title_game_settings);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void findMyViews()
    {
//        switchStitches = (Switch) findViewById(R.id.equal_switch);
        switchStitches.setOnCheckedChangeListener(this);
        switchStitches.setOnLongClickListener(this);
        stitchesEqualPlays = switchStitches.isChecked();
        btNext = (Button) findViewById(R.id.btNext);
        btNext.setOnClickListener(this);
//        howMuchPlayers = (NumberPicker) findViewById(R.id.numberPicker);
        howMuchPlayers.setMinValue(3);
        howMuchPlayers.setMaxValue(6);
        howMuchPlayers.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        howMuchPlayers.setWrapSelectorWheel(false);
        howMuchPlayers.setOnValueChangedListener(this);
        String player = getString(R.string.player_settings);
        howMuchPlayers.setDisplayedValues(new String[] {"3 " + player, "4 " + player, "5 " + player, "6 " + player});
        currentPlayers = 3;
//        etGameName = findViewById(R.id.gameName);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal)
    {
        currentPlayers = newVal;
    }

    @Override
    public void onClick(View v)
    {

        if (etGameName.getText().toString().isEmpty())
        {
            etGameName.setError(getString(R.string.no_game_name));
        }
        else if (!stitchesEqualPlays)
        {
            askForFirstRoundEqual();
        }
        else
        {
            startPlayerSettingsActivity();
        }
    }

    private void startPlayerSettingsActivity()
    {
        currentGameName = etGameName.getText().toString();
        Intent intent = new Intent(this, PlayerSettings.class);
        startActivity(intent);
    }

    private void askForFirstRoundEqual()
    {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(R.string.can_first_round_be_equal);
        builder1.setCancelable(true);


        builder1.setPositiveButton(
                R.string.yes,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        firstRoundCanBeEqual = true;
                        startPlayerSettingsActivity();
                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                R.string.no,
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        firstRoundCanBeEqual = false;
                        startPlayerSettingsActivity();
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
    {
        if (longClicked)
        {
            buttonView.setChecked(!isChecked);
            longClicked = false;
            return;
        }

//        switch (buttonView.getId())
//        {
//            case R.id.equal_switch:
//                stitchesEqualPlays = isChecked;
//                if (stitchesEqualPlays)
//                {
//                    Toast.makeText(GameSettings.this, R.string.stitches_can_equal, Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    Toast.makeText(GameSettings.this, R.string.stitches_cant_be_equal, Toast.LENGTH_LONG).show();
//                }
//                break;
//        }

    }

    @Override
    public boolean onLongClick(View v)
    {

        longClicked = true;

//        switch (v.getId())
//        {
//            case R.id.equal_switch:
//                if (stitchesEqualPlays)
//                {
//                    Toast.makeText(GameSettings.this, R.string.stitches_can_equal, Toast.LENGTH_LONG).show();
//                }
//                else
//                {
//                    Toast.makeText(GameSettings.this, R.string.stitches_cant_be_equal, Toast.LENGTH_LONG).show();
//                }
//                break;
//        }
        return false;
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
                Intent intent = new Intent(this, StartActivity.class);
                startActivity(intent);
                return true;
//            case R.id.goFurther:
//                onClick(item.getActionView());
//                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static int getCurrentPlayers()
    {
        return currentPlayers;
    }


    public static boolean isStitchesEqualPlays()
    {
        return stitchesEqualPlays;
    }

    public static String getCurrentGameName()
    {
        return currentGameName;
    }

    public static boolean isFirstRoundCanBeEqual()
    {
        return firstRoundCanBeEqual;
    }
}
