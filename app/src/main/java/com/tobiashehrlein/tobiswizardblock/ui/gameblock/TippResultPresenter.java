package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;
import android.support.annotation.StringRes;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.model.settings.Settings;
import com.tobiashehrlein.tobiswizardblock.model.settings.SettingsFactory;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;
import com.tobiashehrlein.tobiswizardblock.utils.Constants;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;


/**
 * Created by Tobias Hehrlein on 08.12.2017.
 */

public class TippResultPresenter extends BasePresenter<TippResultContract.View> implements TippResultContract.Presenter {

    private List<TippStitchSeekBarLayout> tippStitchesLayouts;
    private WizardGame wizardGame;
    private boolean isTippMode;
    private int currentRound;

    public TippResultPresenter() {
        tippStitchesLayouts = new ArrayList<>();
        wizardGame = Storage.getInstance().getWizardGame();
    }

    @Override
    public void init(FragmentNavigationListener listener, Bundle arguments) {
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

        if (arguments.containsKey(Constants.ISTIPPMODE)) {
            isTippMode = arguments.getBoolean(Constants.ISTIPPMODE);
        }
    }

    private void createEnteringControls() {
        List<String> playerNames = wizardGame.getGameSettings().getPlayerNames();
        if (playerNames == null || playerNames.isEmpty()) {
            return;
        }

        RealmList<Round> results = wizardGame.getResults();
        if (results == null) {
            currentRound = 1;
        } else if (isTippMode){
            currentRound = results.size() + 1;
        } else {
            currentRound = results.size();
        }

        for (int i = 0; i < playerNames.size(); i++) {
            if (isAttached()) {
                TippStitchSeekBarLayout layout = getView().createTippStitchesLayout(playerNames.get(i), currentRound);
                tippStitchesLayouts.add(layout);
            }
        }
    }

    private void setUpFragmentBasedOnEnterType() {
        if (isTippMode) {
            if (isAttached()) {
                getView().setTippsToolbar();
                getView().setTippsButton();
                getView().setTippsHeadline();
            }
        } else {
            if (isAttached()) {
                getView().setResultsToolbar();
                getView().setResultsButton();
                getView().setResultsHeadline();
            }
        }
    }

    @Override
    public void onEnterButtonClicked() {
        if (isAttached()) {
            RealmList<Integer> input = getView().getSeekBarValues();
            @StringRes int message = validInput(input);

            if (message == Constants.Validation.VALID.stringResId) {
                saveInput(input);
                getView().dismissOverlay(true);
            } else {
                getView().displayInvalidInput(message);
            }

        }
    }

    private @StringRes int validInput(RealmList<Integer> input) {
        SettingsFactory settingsFactory = new SettingsFactory();
        Settings settings = settingsFactory.getSettings(wizardGame.getGameSettings().getSettings());
        return settings.validInput(input, currentRound, isTippMode);
    }

    private void saveInput(RealmList<Integer> input) {
        Storage.getInstance().saveInput(input, isTippMode);
    }

}