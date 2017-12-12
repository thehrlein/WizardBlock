package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;

import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.utils.Constants;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BasePresenter;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public class GameBlockPresenter extends BasePresenter<GameBlockContract.View> implements GameBlockContract.Presenter {

    private FragmentNavigationListener listener;
    private WizardGame wizardGame;
    private boolean isTippMode;

    public GameBlockPresenter() {
        wizardGame = Storage.getInstance().getWizardGame();
        isTippMode = true;
    }

    @Override
    public void init(FragmentNavigationListener listener) {
        this.listener = listener;

        initTitle();
        initHeader();

        listener.setBackPressEnabled(false);

    }

    private void initTitle() {
        if (listener != null) {
            listener.setToolbarTitle(wizardGame.getGameSettings().getGameName());
        }
    }

    private void initHeader() {
        if (isAttached()) {
            getView().setButtonTipps();
            getView().initRoundHeadline();
            getView().initHeader(wizardGame.getGameSettings().getPlayerNames());
            getView().setListener();
        }
    }

    @Override
    public void openTippsResult() {
        if (listener != null) {
            listener.showDialog(TippResultFragment.newInstance(isTippMode));
        }
    }
}
