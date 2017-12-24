package com.tobiashehrlein.tobiswizardblock.ui.coverpage;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.CoverPageListener;

/**
 * Created by Tobias Hehrlein on 27.11.2017.
 */

public interface CoverPageContract {

    interface Presenter extends BaseMvpPresenter<View> {
        void init(CoverPageListener listener);
        void onWaitCompleted();
    }

    interface View extends BaseView {

        void initCounter(int timeOut);
    }
}
