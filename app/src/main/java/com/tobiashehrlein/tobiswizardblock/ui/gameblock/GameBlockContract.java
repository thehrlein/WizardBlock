package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;

import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseView;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public interface GameBlockContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener);
        void openTippsResult();
    }

    interface View extends BaseView {

        void initRoundHeadline();
        void initHeader(List<String> playerNames);
        void setButtonTipps();
        void setButtonResults();
        void setListener();
        void addRound(RealmList<Integer> tippsAnnounced, RealmList<Integer> stitchesMade, RealmList<Integer> pointsAdded, RealmList<Integer> pointsTotal);
        void addRoundNumbersFor(int roundsToPlay);
        void clearBlock();
    }
}
