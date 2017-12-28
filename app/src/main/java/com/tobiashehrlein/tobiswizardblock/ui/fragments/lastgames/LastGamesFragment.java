package com.tobiashehrlein.tobiswizardblock.ui.fragments.lastgames;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentLastGamesBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.model.lastgames.SavedGame;
import com.tobiashehrlein.tobiswizardblock.ui.viewhandler.LastGamesAdapter;

import java.util.List;

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
    }

    @Override
    public void addSavedGames(List<DisplayableItem> savedGames) {
        letVoid(adapter, a -> a.setSavedGames(savedGames));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detach();
    }
}
