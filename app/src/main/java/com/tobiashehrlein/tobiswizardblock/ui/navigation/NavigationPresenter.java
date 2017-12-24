package com.tobiashehrlein.tobiswizardblock.ui.navigation;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.ui.gamesettings.GameSettingsFragment;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;

/**
 * Created by Tobias Hehrlein on 26.11.2017.
 */

public class NavigationPresenter extends BasePresenter<NavigationContract.View> implements NavigationContract.Presenter {

    private FragmentNavigationListener listener;

    @Override
    public void init(FragmentNavigationListener listener) {
        this.listener = listener;

        if (isAttached()){
            getView().initListener();
        }

        listener.setToolbarBackButtonDisabled();
        listener.setToolbarTitle(null);
        listener.setBackPressEnabled(true);
        listener.disableToolbarMenu();
    }

    @Override
    public void startNewGame() {
        GameSettingsFragment gameSettingsFragment = GameSettingsFragment.newInstance();
        listener.replaceFragment(gameSettingsFragment, true);
    }

    @Override
    public void openLoadGames() {

//        listener.replaceFragment();
    }

    @Override
    public void openHighScore() {

//        listener.replaceFragment();
    }
}
