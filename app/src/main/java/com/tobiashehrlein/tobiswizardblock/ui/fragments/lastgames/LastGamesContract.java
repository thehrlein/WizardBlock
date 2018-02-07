package com.tobiashehrlein.tobiswizardblock.ui.fragments.lastgames;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.listener.RecyclerSwipeHelperListener;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;

import java.util.List;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public interface LastGamesContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener);
        void onGameDeleted();
        void onGameRestored();
    }

    interface View extends BaseView, RecyclerSwipeHelperListener {

        String getTitle();
        void setUpRecyclerViewAndAdapter();
        void addSavedGames(List<DisplayableItem> savedGames);
        void showNoLastGamesAvailable(boolean noLastGames);
    }
}
