package com.tobiashehrlein.tobiswizardblock.ui.fragments.gameblock;

import android.content.Intent;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;

/**
 * Created by Tobias Hehrlein on 13.10.2017.
 */

public interface AboutContract {

    interface Presenter extends BaseMvpPresenter<View> {
        void init(FragmentNavigationListener listener);
        void fabButtonClicked();
        void openMovieBase();
    }

    interface View extends BaseView {
        void setListener();
        void sendEmail(Intent mailer);
        String getTitle();
        void openMovieBase(String url);
    }
}
