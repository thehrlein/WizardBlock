package com.tobiashehrlein.tobiswizardblock.old;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tobiashehrlein.tobiswizardblock.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class StartActivity extends AppCompatActivity implements View.OnClickListener
{
    private Button btNewGame;
    private Button btLastGames;
    private Button btHighscore;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_navigation);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        btNewGame  = (Button) findViewById(R.id.btNewGame);
        btNewGame.setOnClickListener(this);

        btLastGames = (Button) findViewById(R.id.btLoadGames);
        btLastGames.setOnClickListener(this);

        btHighscore = (Button) findViewById(R.id.btHighscore);
        btHighscore.setOnClickListener(this);
     //   Log.d("StartActivity", "onCreate");
        setUpRealmDatabase();

    }

    private void setUpRealmDatabase()
    {
//        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext())
//                .deleteRealmIfMigrationNeeded()
//                .name("myRealm")
//                .build();
//        Realm.setDefaultConfiguration(configuration);
    }


    @Override
    public void onClick(View v)
    {
        Intent intent;
        switch (v.getId())
        {
            case R.id.btNewGame:
                intent = new Intent(this, GameSettings.class);
                break;
            case R.id.btLoadGames:
                intent = new Intent (this, LastGames.class);
                break;
            case R.id.btHighscore:
                intent = new Intent(this, HighScore.class);
                break;
            default:
                intent = null;
        }
        startActivity(intent);
    }

    @Override
    protected void onResume()
    {
      //  Log.d("StartActivity", "onResume");

        setUpRealmDatabase();
        super.onResume();
    }
}
