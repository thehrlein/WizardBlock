package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;

import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseView;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 08.12.2017.
 */

public interface TippResultContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener, Bundle arguments);
        void onEnterButtonClicked();
    }

    interface View extends BaseView {

        TippStitchSeekBarLayout createTippStitchesLayout(String gameSettings, int round);
        void setTippsButton();
        void setResultsButton();
        void setListener();
        void initializeToolbar();
        void setTippsToolbar();
        void setResultsToolbar();
        void dismissOverlay(boolean b);
        RealmList<Integer> getSeekBarValues();
    }
}
