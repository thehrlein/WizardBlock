package com.tobiashehrlein.tobiswizardblock.ui.fragments.lastgames;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentLastGamesBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.ui.viewhandler.LastGamesAdapter;
import com.tobiashehrlein.tobiswizardblock.ui.viewholder.LastGameHolder;
import com.tobiashehrlein.tobiswizardblock.utils.RecyclerSwipeHelper;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.List;

import io.realm.RealmList;

import static com.tobiapplications.thutils.NullPointerUtils.isNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.let;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class LastGamesFragment extends Fragment implements LastGamesContract.View {

    private FragmentLastGamesBinding bind;
    private LastGamesContract.Presenter presenter;
    private LastGamesAdapter adapter;
    private FragmentNavigationListener listener;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentNavigationListener) {
            listener = (FragmentNavigationListener) context;
        }
    }

    public static LastGamesFragment newInstance() {
        return new LastGamesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentLastGamesBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = bind.getRoot().getContext();

        presenter = new LastGamesPresenter();
        presenter.attach(this);
        presenter.init(listener);
    }

    @Override
    public String getTitle() {
        return let(context, c -> c.getString(R.string.title_last_games));
    }

    @Override
    public void setUpRecyclerViewAndAdapter() {
        bind.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new LastGamesAdapter(listener);
        bind.recyclerView.setAdapter(adapter);
        bind.recyclerView.setItemAnimator(new DefaultItemAnimator());
        bind.recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        ItemTouchHelper.SimpleCallback itemTouchHelper = new RecyclerSwipeHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(bind.recyclerView);
    }

    @Override
    public void addSavedGames(List<DisplayableItem> savedGames) {
        showNoLastGamesAvailable(isNullOrEmpty(savedGames));
        letVoid(adapter, a -> a.setSavedGames(savedGames));
    }

    @Override
    public void showNoLastGamesAvailable(boolean noLastGames) {
        if (noLastGames) {
            bind.noOldGames.setVisibility(View.VISIBLE);
            bind.lastGamesHeader.setVisibility(View.INVISIBLE);
        } else {
            bind.noOldGames.setVisibility(View.GONE);
            bind.lastGamesHeader.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof LastGameHolder) {
            String gameName = ((LastGameHolder) viewHolder).getGameName();

            int deletedIndex = viewHolder.getAdapterPosition();
            DisplayableItem deletedItem = let(adapter, a -> a.removeItem(deletedIndex));

            WizardGame tempDeletedGame = createTempDeleteGame(deletedItem);
            deleteFromRealm(deletedItem);

            if (isNull(deletedItem)) {
                return;
            }

            letVoid(presenter, LastGamesContract.Presenter::onGameDeleted);

            Snackbar snackbar = Snackbar
                    .make(bind.getRoot(), context.getString(R.string.last_game_deleted, gameName), Snackbar.LENGTH_LONG);
            snackbar.setAction(context.getString(R.string.last_games_undo), view -> restoreThisGame(deletedIndex, tempDeletedGame));
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }

    private WizardGame createTempDeleteGame(DisplayableItem deletedItem) {
        WizardGame wizardGame = new WizardGame();
        if (deletedItem instanceof WizardGame) {
            WizardGame deletedGame = (WizardGame) deletedItem;

            wizardGame.setGameDate(deletedGame.getGameDate());
            wizardGame.setGameSettings(deletedGame.getGameSettings());
            RealmList<Round> tmpResults = new RealmList<>();
            tmpResults.addAll(deletedGame.getResults());
            wizardGame.setResults(tmpResults);
        }

        return wizardGame;
    }

    private void deleteFromRealm(DisplayableItem deletedItem) {
        if (deletedItem instanceof WizardGame) {
            WizardGame savedGame = (WizardGame) deletedItem;
            Storage.getInstance().deleteThisSavedGame(savedGame);
        }
    }

    private void restoreThisGame(int deletedIndex, DisplayableItem deletedItem) {
        letVoid(adapter, a -> a.restoreItem(deletedItem, deletedIndex));
        if (deletedItem instanceof WizardGame) {
            WizardGame savedGame = (WizardGame) deletedItem;
            Storage.getInstance().restoreGame(savedGame);
            letVoid(presenter, LastGamesContract.Presenter::onGameRestored);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        letVoid(presenter, BaseMvpPresenter::detach);
    }
}
