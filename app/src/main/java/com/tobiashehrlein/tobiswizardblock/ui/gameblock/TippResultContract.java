package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;
import android.support.annotation.StringRes;

import com.tobiapplications.thutils.dialog.DialogOneButtonListener;
import com.tobiapplications.thutils.dialog.DialogTwoButtonListener;
import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.DialogDismissListener;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 08.12.2017.
 */

public interface TippResultContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener, Bundle arguments);
        void onEnterButtonClicked();
    }

    interface View extends BaseView, DialogOneButtonListener {

        TippStitchSeekBarLayout createTippStitchesLayout(String gameSettings, int round);
        void setTippsButton();
        void setResultsButton();
        void setListener();
        void initializeToolbar();
        void setTippsToolbar();
        void setResultsToolbar();
        void dismissOverlay(boolean b);
        RealmList<Integer> getSeekBarValues();
        void setTippsHeadline();
        void setResultsHeadline();
        void displayInvalidInput(@StringRes int message);
    }
}
