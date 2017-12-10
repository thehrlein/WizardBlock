package com.tobiashehrlein.tobiswizardblock.ui.gamesettings;

import com.tobiashehrlein.tobiswizardblock.model.Settings;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BaseView;

import java.util.List;

/**
 * Created by Tobias Hehrlein on 27.11.2017.
 */

public interface GameSettingsContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener);
        void setTippsEqualStitches(boolean tippsEqualStitches);
        void setTippsEqualStitchesInFirstRound(boolean tippsEqualStitchesInFirstRound);
        void startNewGame();
    }

    interface View extends BaseView {

        String getTitle();
        void createTippsEqualStitchesOption();
        void createAdditionalFirstRoundTippsEqualStitchesOption();
        void hideFirstRoundExceptionOption();
        void showFirstRoundExceptionOption();
        void setFirstRoundExceptionOptionDisabled();
        void createAnniversaryStitchesCanBeLessOption();
        void setListener();
        String getGameName();
        List<String> getPlayerNames();
        Settings getSettings();
    }
}
