package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;

import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseView;

import java.util.List;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public interface GameBlockContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener, Bundle arguments);
        void openTippsResult();
    }

    interface View extends BaseView {

        void initRoundHeadline();
        void initHeader(List<String> playerNames);
        void setButtonTipps();
        void setButtonResults();
        void setListener();
    }
}
