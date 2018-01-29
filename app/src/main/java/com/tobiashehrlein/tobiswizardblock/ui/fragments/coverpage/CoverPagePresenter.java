package com.tobiashehrlein.tobiswizardblock.ui.fragments.coverpage;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.listener.CoverPageListener;

import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 27.11.2017.
 */

public class CoverPagePresenter extends BasePresenter<CoverPageContract.View> implements CoverPageContract.Presenter {

    private CoverPageListener listener;
    private static final int TIME_OUT = 1500;

    @Override
    public void init(CoverPageListener listener) {
        this.listener = listener;

        if (isAttached()) {
            getView().initCounter(TIME_OUT);
        }
    }

    @Override
    public void onWaitCompleted() {
        letVoid(listener, CoverPageListener::onWaitCompleted);
    }
}
