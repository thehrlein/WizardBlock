package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;

import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;
import com.tobiashehrlein.tobiswizardblock.utils.Constants;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BasePresenter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Tobias Hehrlein on 08.12.2017.
 */

public class TippResultPresenter extends BasePresenter<TippResultContract.View> implements TippResultContract.Presenter {

    private FragmentNavigationListener listener;
    private List<TippStitchSeekBarLayout> tippStitchesLayouts;
    private GameSettings gameSettings;
    private int round;
    private @Constants.EnterType int enterType;

    public TippResultPresenter() {
        tippStitchesLayouts = new ArrayList<>();
    }

    @Override
    public void init(FragmentNavigationListener listener, Bundle arguments) {
        this.listener = listener;
        parseArguments(arguments);
        createEnteringControls();
        setUpFragmentBasedOnEnterType();
        listener.setBackPressEnabled(true);

        if (isAttached()) {
            getView().initializeToolbar();
            getView().setListener();
        }
    }

    private void parseArguments(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        if (arguments.containsKey(Constants.GAME_SETTINGS)) {
            this.gameSettings = (GameSettings) arguments.getSerializable(Constants.GAME_SETTINGS);
        }

        if (arguments.containsKey(Constants.ROUND)) {
            this.round = arguments.getInt(Constants.ROUND);
        }

        if (arguments.containsKey(Constants.ENTER_TYPE)) {
            enterType = arguments.getInt(Constants.ENTER_TYPE);
        }
    }

    private void createEnteringControls() {
        if (gameSettings == null) {
            return;
        }

        List<String> playerNames = gameSettings.getPlayerNames();
        if (playerNames == null || playerNames.isEmpty()) {
            return;
        }

        for (int i = 0; i < gameSettings.getPlayerNames().size(); i++) {
            if (isAttached()) {
                TippStitchSeekBarLayout layout = getView().createTippStitchesLayout(playerNames.get(i), round);
                tippStitchesLayouts.add(layout);
            }
        }
    }

    private void setUpFragmentBasedOnEnterType() {
        if (enterType == Constants.EnterType.TIPPS) {
            if (isAttached()) {
                getView().setTippsToolbar();
                getView().setTippsButton();
            }
        } else {
            if (isAttached()) {
                getView().setResultsToolbar();
                getView().setResultsButton();

            }
        }
    }

    @Override
    public void onEnterButtonClicked() {

    }
}
