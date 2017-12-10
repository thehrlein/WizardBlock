package com.tobiashehrlein.tobiswizardblock.ui.navigation;

import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseView;

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
