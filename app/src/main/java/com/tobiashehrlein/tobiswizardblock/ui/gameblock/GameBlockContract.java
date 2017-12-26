package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;

import com.tobiapplications.thutils.dialog.DialogTwoButtonListener;
import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public interface GameBlockContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener);
        void openTippsResult();
        void changePlayerNames();
        void startNewGame();
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
        boolean onMenuItemClicked(int itemId);
        void openChangePlayerNamesDialog(RealmList<String> playerNames);
    }
}
