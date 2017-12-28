package com.tobiashehrlein.tobiswizardblock.ui.fragments.highscore;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;
import com.tobiashehrlein.tobiswizardblock.ui.views.HighscoreRow;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public interface HighscoreContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener);
    }

    interface View extends BaseView {

        String getTitle();
        void createNewHighscore(Highscore score);
    }
}

