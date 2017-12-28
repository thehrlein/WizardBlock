package com.tobiashehrlein.tobiswizardblock.ui.fragments.gamesettings;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiapplications.thutils.mvp.BaseView;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.settings.SettingsFactory;


import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 27.11.2017.
 */

public interface GameSettingsContract {

    interface Presenter extends BaseMvpPresenter<View> {

        void init(FragmentNavigationListener listener);
        void setTippsEqualStitches(boolean tippsEqualStitches);
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
        RealmList<String> getPlayerNames();
        @SettingsFactory.SettingsType int getSettings();
    }
}
