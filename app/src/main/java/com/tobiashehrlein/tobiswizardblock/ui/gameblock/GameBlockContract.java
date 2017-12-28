package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.DialogDismissListener;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;

import java.util.List;
import java.util.Map;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public interface GameBlockContract {

    interface Presenter extends BaseMvpPresenter<View>, DialogDismissListener {

        void init(FragmentNavigationListener listener);
        void openNextInputEntering();
        void changePlayerNames();
        void startNewGame();
        StringBuilder getCompleteWinnerString(List<String> winnerNames);
        void openAbout();
        void changeLastRoundInput();
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
        void showWinnerDialog(Map<String, Integer> winner);
        void disableEnterButton();
    }
}
