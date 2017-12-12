package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.utils.Constants;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BasePresenter;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public class GameBlockPresenter extends BasePresenter<GameBlockContract.View> implements GameBlockContract.Presenter {

    private FragmentNavigationListener listener;
    private GameSettings gameSettings;
    private @Constants.EnterType int enterType;
    private int round;

    public GameBlockPresenter() {
        this.enterType = Constants.EnterType.TIPPS;
        round = 1;
    }

    @Override
    public void init(FragmentNavigationListener listener, Bundle arguments) {
        this.listener = listener;
        parseArguments(arguments);
        initTitle();
        initHeader();

        listener.setBackPressEnabled(false);

    }

    private void initTitle() {
        if (gameSettings != null && listener != null) {
            listener.setToolbarTitle(gameSettings.getGamename());
        }
    }

    private void initHeader() {
        if (isAttached()) {
            getView().setButtonTipps();
            getView().initRoundHeadline();
            getView().initHeader(gameSettings.getPlayerNames());
            getView().setListener();
        }
    }

    private void parseArguments(Bundle arguments) {
        if (arguments.containsKey(Constants.GAME_SETTINGS)) {
            gameSettings = (GameSettings) arguments.getSerializable(Constants.GAME_SETTINGS);
        }
    }

    @Override
    public void openTippsResult() {
        if (listener != null) {
            listener.showDialog(TippResultFragment.newInstance(gameSettings, round, enterType));
        }
    }
}
