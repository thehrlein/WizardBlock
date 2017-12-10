package com.tobiashehrlein.tobiswizardblock.ui.coverpage;

import com.tobiashehrlein.tobiswizardblock.listener.CoverPageListener;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BasePresenter;

/**
 * Created by Tobias Hehrlein on 27.11.2017.
 */

public class CoverPagePresenter extends BasePresenter<CoverPageContract.View> implements CoverPageContract.Presenter {

    private CoverPageListener listener;
    private static final int TIME_OUT = 20;

    @Override
    public void init(CoverPageListener listener) {
        this.listener = listener;

        if (isAttached()) {
            getView().initCounter(TIME_OUT);
        }
    }

    @Override
    public void onWaitCompleted() {
        listener.onWaitCompleted();
    }
}
