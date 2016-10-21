package com.tobiashehrlein.tobiswizardblock;

import android.content.Context;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmBaseAdapter;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LastGames extends AppCompatActivity implements
        AbsListView.MultiChoiceModeListener,
        AbsListView.OnItemClickListener
{

    private TextView tvNoGames;
    private ListView listView;
    MyAdapter myAdapter;

    private Realm myRealm;
    RealmResults<GameRealm> results;
    private int nr = 0;
    private RealmList<RealmString> playerNamesRealmList;
    private RealmList<RealmInt> tippsResults;
    private RealmList<RealmInt> scoreAddTotal;
    private int currentGameRound;
    private int maxGameRounds;
    private boolean stitchesEqualTipps;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_last_games);

        tvNoGames = (TextView) findViewById(R.id.tvNoGames);
        listView = (ListView) findViewById(R.id.listviewLastGames);

        listView.setMultiChoiceModeListener(this);
        listView.setOnItemClickListener(this);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        setTitle(R.string.last_games);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        checkRealmResults();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        results = myRealm.where(GameRealm.class).findAll();
        gameRealm = results.get(position);
        currentGameDate = gameRealm.getGameDate();
        currentGameName = gameRealm.getGameName();
        currentPlayers = gameRealm.getCountPlayers();
        //playerNamesArray = new String[currentPlayers];
        playerNamesRealmList = gameRealm.getPlayerNames();
        maxGameRounds = gameRealm.getMaxGameRounds();
        currentGameRound = gameRealm.getCurrentGameRound();
        tippsResults = gameRealm.getTippsResults();
        scoreAddTotal = gameRealm.getScoreAddTotal();
        stitchesEqualTipps = gameRealm.isStitchesEqualsTipps();

       // Log.d("LastGames", "onItemClick position: " + position + " currentgameround:" + gameRealm.getCurrentGameRound() + " date: " + currentGameDate
      //      + " name: " + currentGameName + " players: " + currentPlayers);

        Intent intent = new Intent(this, GameBlock.class);
        intent.putExtra("newGame", true);
        intent.putExtra("comefromlastgame", true);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        saveMyRealmGame();



        startActivity(intent);

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
           // Log.d("Nr", "Ausgewählte Elemente: " + nr);
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

    private void setUpRealmDatabase()
    {
        RealmConfiguration configuration = new RealmConfiguration.Builder(getApplicationContext())
                .name("myRealm")
                .build();
        Realm.setDefaultConfiguration(configuration);
    }


    @Override
    public void onDestroyActionMode(ActionMode mode)
    {
        myAdapter.clearSelection();
    }

    private void checkRealmResults()
    {
        setUpRealmDatabase();

        myRealm = Realm.getDefaultInstance();

        results = myRealm.where(GameRealm.class).findAll();
        if (results.isEmpty())
        {
           // Log.d("Realm", "keine alten spiele");
            tvNoGames.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }
        else
        {
          //  Log.d("Realm", "es sind alte spiele verfügbar");
            tvNoGames.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            final ArrayList<GameRealm> myGameList = new ArrayList<>();
            for (GameRealm c : results)
            {
                myGameList.add(c);
            }

            myAdapter = new MyAdapter(getApplicationContext(), results);
            listView.setAdapter(myAdapter);

        }
    }

    private GameRealm gameRealm;
    private int currentPlayers;
    private String currentGameName;
    private String currentGameDate;

    private void saveMyRealmGame()
    {
        myRealm = Realm.getDefaultInstance();
        myRealm.beginTransaction();
        gameRealm = myRealm.createObject(GameRealm.class);

   //     Log.d("PlayerSettings", "maxgamerounds: " + maxGameRounds);

        gameRealm.setGameName(currentGameName);
        gameRealm.setGameDate(currentGameDate);
        gameRealm.setCountPlayers(currentPlayers);
        gameRealm.setPlayerNames(playerNamesRealmList);
        gameRealm.setTippsResults(tippsResults);
        gameRealm.setScoreAddTotal(scoreAddTotal);
        gameRealm.setCurrentGameRound(currentGameRound);
        gameRealm.setMaxGameRounds(maxGameRounds);
        gameRealm.setStitchesEqualsTipps(stitchesEqualTipps);
        myRealm.commitTransaction();

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


    private void deleteRealmDatabase()
    {
        myRealm.beginTransaction();
        myRealm.clear(GameRealm.class);
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

    public class MyAdapter extends RealmBaseAdapter //ArrayAdapter<GameRealm>
    {
        private RealmResults<GameRealm> results;
        private Context context;
        private HashMap<Integer, Boolean> mSelection = new HashMap<Integer, Boolean>();

        public MyAdapter(Context context, RealmResults<GameRealm> results)
        {
            super(context, results, true);
            this.results = results;
            this.context = context;
        }

        public void setNewSelection(int position, boolean value)
        {
            mSelection.put(position, value);
            notifyDataSetChanged();
          //  Log.d("Ausgewählt", "Ausgewählt: " + mSelection.toString());
        }

        public boolean isPositionChecked(int position)
        {
            Boolean result = mSelection.get(position);
         //   Log.d("Checked", "Checked: " + position + " = " + result);
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
            Log.d("Test", "Resultssize: " + results.size());
            int length = results.size();

            for (int i = length - 1; i >= 0; i--)
            {
              //  Log.d("Test", "For Schleife: " + i);
                if (isPositionChecked(i))
                {
                //    Log.d("Test", "mSelection an Stelle " + i + " ist ausgewählt");
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
                convertView = inflater.inflate(R.layout.row_layout, parent, false);
            }

            convertView.setBackgroundColor(getResources().getColor(android.R.color.background_light));
            if (mSelection.get(position) != null)
            {
                convertView.setBackgroundColor(getResources().getColor(R.color.colorGrey));
            }

            TextView gameName = (TextView) convertView.findViewById(R.id.gameName);
            TextView gameDate = (TextView) convertView.findViewById(R.id.gamedate);
            TextView player = (TextView) convertView.findViewById(R.id.players);

            GameRealm gameRealm = results.get(position);

            gameName.setText(gameRealm.getGameName());
            gameDate.setText(gameRealm.getGameDate() + " " + getString(R.string.clock));
            player.setText(gameRealm.getCountPlayers() + " " + getString(R.string.player_settings));

            return convertView;
        }
    }
}
