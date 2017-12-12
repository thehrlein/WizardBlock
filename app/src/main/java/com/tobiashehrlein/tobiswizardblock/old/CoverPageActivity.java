package com.tobiashehrlein.tobiswizardblock.old;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.tobiashehrlein.tobiswizardblock.R;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;


public class CoverPageActivity extends AppCompatActivity
{
    private static final int TIME_OUT = 2000;
    private int showTutorial;
    private Realm myRealm;
    private RealmResults<RealmInt> results;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

      /*  if (Build.VERSION.SDK_INT >= 21)
        {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorStatusBarGrey));
        } */

        setContentView(R.layout.fragment_cover_page);

        setUpRealmDatabase();

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = null;
                if (showTutorial == 1)
                {
                    intent = new Intent(CoverPageActivity.this, GameInfoSlideActivity.class);
                }
                else
                {
                    intent = new Intent(CoverPageActivity.this, StartActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }, TIME_OUT);
    }

    private void setUpRealmDatabase()
    {
//        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext())
//                .name("tutorial")
//                .build();
//        Realm.setDefaultConfiguration(configuration);

        myRealm = Realm.getDefaultInstance();
        results = myRealm.where(RealmInt.class).findAll();

        if (results.size() < 1)
        {
            showTutorial = 1;
            Log.d("CoverPage", "Tutorial wasn't shown yet, showTutorial = " + showTutorial);
        }
        else
        {
            showTutorial = results.last().getVal();
            Log.d("CoverPage", "Tutorial was already shown, showTutorial = " + showTutorial);
        }


    }
}
