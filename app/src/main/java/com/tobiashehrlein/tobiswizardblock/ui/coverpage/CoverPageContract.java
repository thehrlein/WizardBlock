package com.tobiashehrlein.tobiswizardblock.ui.coverpage;

import com.tobiashehrlein.tobiswizardblock.listener.CoverPageListener;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseView;

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
