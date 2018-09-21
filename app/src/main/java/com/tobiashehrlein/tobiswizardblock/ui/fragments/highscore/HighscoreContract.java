package com.tobiashehrlein.tobiswizardblock.ui.fragments.highscore;

import androidx.annotation.MenuRes;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public interface HighscoreContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener, @MenuRes int menuHigscore);
        void deleteAllHighscore();
    }

    interface View extends BaseView {

        String getTitle();
        boolean onMenuItemClicked(int itemId);
        void clearHighscoreList();
        void showNoScoresAvailable();
        void hideNoScoresAvailable();
        void createNewHighscore(Highscore highscore, Integer ranking);
    }
}

