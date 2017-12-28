package com.tobiashehrlein.tobiswizardblock.ui.fragments.navigation;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;

/**
 * Created by Tobias Hehrlein on 26.11.2017.
 */

public interface NavigationContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener);
        void startNewGame();
        void openLoadGames();
        void openHighScore();
    }

    interface View extends BaseView {

        void initListener();
    }
}
