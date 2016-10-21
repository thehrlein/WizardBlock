package com.tobiashehrlein.tobiswizardblock;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class HighScore extends AppCompatActivity implements
        AbsListView.MultiChoiceModeListener,
        AbsListView.OnItemClickListener
{

    private TextView tvNoGamesHighscore;
    private ListView listView;
    MyAdapter myAdapter;

    private Realm myRealm;
    RealmResults<HighScoreRealm> results;
    private int nr = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        tvNoGamesHighscore = (TextView) findViewById(R.id.tvNoGamesHighScore);
        listView = (ListView) findViewById(R.id.listviewHighscore);

        listView.setMultiChoiceModeListener(this);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        setTitle("Highscore");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkRealmResults();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        results = myRealm.where(HighScoreRealm.class).findAll();
        HighScoreRealm highScoreRealm = results.get(position);
        Snackbar snackbar = Snackbar.make(view, highScoreRealm.getGameName() + " " + getString(R.string.from) + " " + highScoreRealm.getGameDate()
                + " (" + highScoreRealm.getPlayerCount() + " " + getString(R.string.player_settings) + ")", Snackbar.LENGTH_LONG);
        snackbar.show();

    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked)
    {
        if (checked)
        {
            nr++;
            myAdapter.setNewSelection(position, checked);
          //  Log.d("Nr", "Ausgewählte Elemente: " + nr);
        }
        else
        {
            nr--;
            myAdapter.removeSelection(position);
          //  Log.d("Nr", "Ausgewählte Elemente: " + nr);
        }
        mode.setTitle(nr + " " + getString(R.string.activated));
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu)
    {
        nr = 0;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_last_games, menu);

        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu)
    {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item)
    {
        myAdapter.deleteFromRealm();
        myAdapter.clearSelection();
        mode.finish();
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode)
    {
        myAdapter.clearSelection();
    }

    private void checkRealmResults()
    {
        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext())
                .name("highscoreRealm")
                .build();
        Realm.setDefaultConfiguration(configuration);

        myRealm = Realm.getDefaultInstance();

        results = myRealm.where(HighScoreRealm.class).findAll();
        if (results.isEmpty())
        {
            Log.d("Realm", "keine Highscores");
            tvNoGamesHighscore.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else
        {
            Log.d("Realm", "es sind Highscores verfügbar");
            tvNoGamesHighscore.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            final List<HighScoreRealm> myHighscoreList = new ArrayList<HighScoreRealm>();
            results.sort("winnerScore", false);
            for (int i = 0; i < results.size(); i++)
            {
                Log.d("HighScore", "afterResultsSort: " + i + " = " + results.get(i).getWinnerScore());
            }

            for (HighScoreRealm c : results)
            {
                myHighscoreList.add(c);
            }

            myAdapter = new MyAdapter(getApplicationContext(), results);
            listView.setAdapter(myAdapter);

        }
    }


    private void deleteRealmDatabase()
    {
        myRealm.beginTransaction();
        myRealm.clear(HighScoreRealm.class);
        myRealm.commitTransaction();

        myAdapter.notifyDataSetChanged();
        invalidateOptionsMenu();
        Log.d("Delete", "alle Spiele gelöscht");
        checkRealmResults();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_last_games, menu);
        invalidateOptionsMenu();

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        if (results.isEmpty())
        {
            menu.findItem(R.id.menuItemDeleteALl).setVisible(false);
        }
        else
        {
            menu.findItem(R.id.menuItemDeleteALl).setVisible(true);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menuItemDeleteALl:
                deleteRealmDatabase();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends RealmBaseAdapter
    {
        private RealmResults<HighScoreRealm> results;
        private Context context;
        private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

        public MyAdapter(Context context, RealmResults<HighScoreRealm> results)
        {
            super(context, results, true);
            this.results = results;
            this.context = context;
        }

        public void setNewSelection(int position, boolean value)
        {
            mSelection.put(position, value);
            notifyDataSetChanged();
           // Log.d("Ausgewählt", "Ausgewählt: " + mSelection.toString());
        }

        public boolean isPositionChecked(int position)
        {
            Boolean result = mSelection.get(position);
        //    Log.d("Checked", "Checked: " + position + " = " + result);
            return result == null ? false : result;
        }

        public Set<Integer> getCurrentCheckedPosition()
        {
            return mSelection.keySet();
        }

        public void removeSelection(int position)
        {
            mSelection.remove(position);
            notifyDataSetChanged();
        }


        public void clearSelection()
        {
            mSelection = new HashMap<>();
            notifyDataSetChanged();
        }

        public void deleteFromRealm()
        {
            myRealm.beginTransaction();
          //  Log.d("Test", "Resultssize: " + results.size());
            int length = results.size();

            for (int i = length - 1; i >= 0; i--)
            {
              //  Log.d("Test", "For Schleife: " + i);
                if (isPositionChecked(i))
                {
                 //   Log.d("Test", "mSelection an Stelle " + i + " ist ausgewählt");
                    results.remove(i);

                }
                else
                {
                    Log.d("Test", "mSelection an Stelle " + i + " ist nicht ausgewählt");

                }
            }
            myRealm.commitTransaction();
            notifyDataSetChanged();
            invalidateOptionsMenu();
            checkRealmResults();

        }

        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.row_layout_highscore, parent, false);
            }

            convertView.setBackgroundColor(getResources().getColor(android.R.color.background_light));
            if (mSelection.get(position) != null)
            {
                convertView.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            }

            TextView playerName = (TextView) convertView.findViewById(R.id.playerName);
            TextView ranking = (TextView) convertView.findViewById(R.id.ranking);
            TextView score = (TextView) convertView.findViewById(R.id.score);

            HighScoreRealm highScoreRealm = results.get(position);

            playerName.setText(String.valueOf(highScoreRealm.getWinnerName()));
            ranking.setText(String.valueOf(position + 1) + ".");
            score.setText(String.valueOf(highScoreRealm.getWinnerScore()) + " " + getString(R.string.points));

            return convertView;
        }
    }
}
