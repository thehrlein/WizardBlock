package com.tobiashehrlein.tobiswizardblock.ui.activity;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.ui.coverpage.CoverPage;
import com.tobiashehrlein.tobiswizardblock.ui.navigation.NavigationFragment;

/**
 * Created by Tobias Hehrlein on 26.11.2017.
 */

public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> implements MainActivityContract.Presenter {

    public MainActivityPresenter() {

    }

    @Override
    public void init() {
        openCoverPage();
    }

    private void openCoverPage() {
        if (isAttached()) {
            CoverPage coverPage = CoverPage.newInstance();
            getView().hideToolbar();
            getView().replaceFragment(coverPage, false);
        }
    }

    @Override
    public void onCoverPageWaitCompleted() {
        if (isAttached()) {
            NavigationFragment navigationFragment = NavigationFragment.newInstance();
            getView().showToolbar();
            getView().replaceFragment(navigationFragment, false);
        }
    }
}
