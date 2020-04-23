package com.tobiashehrlein.tobiswizardblock.ui.fragments.navigation;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.ui.fragments.gamesettings.GameSettingsFragment;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.ui.fragments.highscore.HighscoreFragment;
import com.tobiashehrlein.tobiswizardblock.ui.fragments.lastgames.LastGamesFragment;
import com.tobiashehrlein.tobiswizardblock.utils.FabricUtils;

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
        listener.setGameFinished(false);
        listener.disableToolbarMenu();
    }

    @Override
    public void startNewGame() {
        FabricUtils.newGameStarted();
        GameSettingsFragment gameSettingsFragment = GameSettingsFragment.newInstance();
        listener.replaceFragment(gameSettingsFragment, true);
    }

    @Override
    public void openSavedGames() {
        FabricUtils.savedGamesOpened();
        LastGamesFragment lastGamesFragment = LastGamesFragment.newInstance();
        listener.replaceFragment(lastGamesFragment, true);
    }

    @Override
    public void openHighScore() {
        FabricUtils.highscoresOpened();
        HighscoreFragment highscoreFragment = HighscoreFragment.newInstance();
        listener.replaceFragment(highscoreFragment, true);
    }
}
