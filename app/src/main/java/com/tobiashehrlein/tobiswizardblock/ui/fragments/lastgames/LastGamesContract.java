package com.tobiashehrlein.tobiswizardblock.ui.fragments.lastgames;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;

import java.util.List;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public interface LastGamesContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener);
    }

    interface View extends BaseView {

        String getTitle();
        void setUpRecyclerViewAndAdapter();
        void addSavedGames(List<DisplayableItem> savedGames);
    }
}