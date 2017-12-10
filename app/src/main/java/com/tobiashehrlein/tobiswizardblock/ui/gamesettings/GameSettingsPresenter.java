package com.tobiashehrlein.tobiswizardblock.ui.gamesettings;

import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.model.Settings;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.ui.gameblock.GameBlockFragment;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BasePresenter;

import java.util.List;

/**
 * Created by Tobias Hehrlein on 27.11.2017.
 */

public class GameSettingsPresenter extends BasePresenter<GameSettingsContract.View> implements GameSettingsContract.Presenter {

    private FragmentNavigationListener listener;
    private boolean tippsEqualStitches;
    private boolean tippsEqualStitchesInFirstRound;

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
    }

    @Override
    public void setTippsEqualStitches(boolean tippsEqualStitches) {
        this.tippsEqualStitches = tippsEqualStitches;

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
    public void setTippsEqualStitchesInFirstRound(boolean tippsEqualStitchesInFirstRound) {
        this.tippsEqualStitchesInFirstRound = tippsEqualStitchesInFirstRound;
    }

    @Override
    public void startNewGame() {
        GameSettings gameSettings = null;
        if (isAttached()) {
            String gameName = getView().getGameName();
            List<String> playerNames = getView().getPlayerNames();
            Settings settings = getView().getSettings();

            gameSettings = GameSettings.create(gameName, playerNames, settings);
        }

        if (gameSettings != null && listener != null) {
            GameBlockFragment gameBlockFragment = GameBlockFragment.newInstance(gameSettings);
            listener.replaceFragment(gameBlockFragment, true);
        }
    }
}
