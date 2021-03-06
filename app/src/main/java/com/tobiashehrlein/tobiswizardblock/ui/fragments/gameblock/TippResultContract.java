package com.tobiashehrlein.tobiswizardblock.ui.fragments.gameblock;

import android.os.Bundle;
import androidx.annotation.StringRes;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 08.12.2017.
 */

public interface TippResultContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener, Bundle arguments);
        void onEnterButtonClicked();
        void saveLastInputBecauseOfModifying();
        void checkAllSeekbarValues();
    }

    interface View extends BaseView {

        TippStitchSeekBarLayout createTippStitchesLayout(String gameSettings, int round);
        void setTippsButton();
        void setResultsButton();
        void setListener();
        void initializeToolbar();
        void setTippsToolbar();
        void setResultsToolbar();
        void dismissOverlay(boolean enteredTippsOrResults);
        RealmList<Integer> getSeekBarValues();
        void setTippsHeadline();
        void setResultsHeadline();
        void displayInvalidInput(@StringRes int message);
        void enableEnterButton();
        void disableEnterButton();
        void setCurrentTotalTipps(int tippsValue, int totalTippsValue);
    }
}
