package com.tobiashehrlein.tobiswizardblock.ui.gamesettings;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.settings.Settings;
import com.tobiashehrlein.tobiswizardblock.model.settings.SettingsFactory;
import com.tobiashehrlein.tobiswizardblock.ui.gameblock.GameBlockFragment;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.List;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 27.11.2017.
 */

public class GameSettingsPresenter extends BasePresenter<GameSettingsContract.View> implements GameSettingsContract.Presenter {

    private FragmentNavigationListener listener;

    @Override
    public void init(FragmentNavigationListener listener) {
        this.listener = listener;
        if (isAttached()) {
            String gameSettingsTitle = getView().getTitle();
            listener.setToolbarTitle(gameSettingsTitle);
            listener.setToolbarBackButtonEnabled();
            getView().setListener();
            getView().createTippsEqualStitchesOption();
            getView().createAdditionalFirstRoundTippsEqualStitchesOption();
            getView().createAnniversaryStitchesCanBeLessOption();
        }

        listener.setBackPressEnabled(true);
        listener.disableToolbarMenu();
    }

    @Override
    public void setTippsEqualStitches(boolean tippsEqualStitches) {
        if (isAttached()) {
            if (tippsEqualStitches) {
                getView().hideFirstRoundExceptionOption();
            } else {
                getView().showFirstRoundExceptionOption();
                getView().setFirstRoundExceptionOptionDisabled();
            }
        }
    }

    @Override
    public void startNewGame() {
        if (isAttached()) {
            String gameName = getView().getGameName();
            RealmList<String> playerNames = getView().getPlayerNames();
            @SettingsFactory.SettingsType int settingsType = getView().getSettings();

            GameSettings gameSettings = new GameSettings(gameName, playerNames, settingsType);

            Storage.getInstance().setGameSettings(gameSettings);
        }

        if (listener != null) {
            GameBlockFragment gameBlockFragment = GameBlockFragment.newInstance();
            listener.replaceFragment(gameBlockFragment, true);
        }
    }
}
