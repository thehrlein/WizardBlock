package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;

import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseView;

/**
 * Created by Tobias Hehrlein on 08.12.2017.
 */

public interface TippResultContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener, Bundle arguments);
    }

    interface View extends BaseView {

        TippStitchSeekBarLayout createTippStitchesLayout(String gameSettings, int round);
    }
}
