package com.tobiashehrlein.tobiswizardblock.ui.fragments.navigation;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentNavigationBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;

import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 26.11.2017.
 */

public class NavigationFragment extends Fragment implements NavigationContract.View {

    private FragmentNavigationBinding bind;
    private NavigationContract.Presenter presenter;
    private FragmentNavigationListener listener;

    public static NavigationFragment newInstance() {
        return new NavigationFragment();
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);

        if (activity instanceof FragmentNavigationListener) {
            listener = (FragmentNavigationListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentNavigationBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new NavigationPresenter();
        presenter.attach(this);
        presenter.init(listener);
    }

    @Override
    public void initListener() {
        bind.btNewGame.setOnClickListener(v -> letVoid(presenter, NavigationContract.Presenter::startNewGame));
        bind.btLoadGames.setOnClickListener(v -> letVoid(presenter, NavigationContract.Presenter::openSavedGames));
        bind.btHighscore.setOnClickListener(v -> letVoid(presenter, NavigationContract.Presenter::openHighScore));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        letVoid(presenter, BaseMvpPresenter::detach);
    }
}
