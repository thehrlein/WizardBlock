package com.tobiashehrlein.tobiswizardblock.ui.activity;

import android.support.v4.app.DialogFragment;

import com.tobiapplications.thutils.dialog.DialogTwoButtonListener;
import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.CoverPageListener;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;

/**
 * Created by Tobias Hehrlein on 26.11.2017.
 */

public interface MainActivityContract {

    interface Presenter extends BaseMvpPresenter<View> {
        void init();
        void onCoverPageWaitCompleted();
    }

    interface View extends BaseView, CoverPageListener, FragmentNavigationListener, DialogTwoButtonListener {

        void hideToolbar();
        void showToolbar();
    }
}
