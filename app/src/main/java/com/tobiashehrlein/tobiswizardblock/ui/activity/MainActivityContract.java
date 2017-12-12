package com.tobiashehrlein.tobiswizardblock.ui.activity;

import android.support.v4.app.DialogFragment;

import com.tobiashehrlein.tobiswizardblock.listener.CoverPageListener;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.utils.dialog.DialogBuilderListener;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseView;

/**
 * Created by Tobias Hehrlein on 26.11.2017.
 */

public interface MainActivityContract {

    interface Presenter extends BaseMvpPresenter<View> {
        void init();
        void onCoverPageWaitCompleted();
    }

    interface View extends BaseView, CoverPageListener, FragmentNavigationListener, DialogBuilderListener {

        void hideToolbar();
        void showToolbar();
    }
}
