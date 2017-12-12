package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.os.Bundle;

import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;
import com.tobiashehrlein.tobiswizardblock.utils.Constants;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;
import com.tobiashehrlein.tobiswizardblock.utils.mvp.BasePresenter;

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

    public TippResultPresenter(Bundle arguments) {
        parseArguments(arguments);
        tippStitchesLayouts = new ArrayList<>();
        wizardGame = Storage.getInstance().getWizardGame();
    }

    private void parseArguments(Bundle arguments) {
        if (arguments == null) {
            return;
        }

        if (arguments.containsKey(Constants.ISTIPPMODE)) {
            isTippMode = arguments.getBoolean(Constants.ISTIPPMODE);
        }
    }

    @Override
    public void init(FragmentNavigationListener listener) {
        createEnteringControls();
        setUpFragmentBasedOnEnterType();
        listener.setBackPressEnabled(true);

        if (isAttached()) {
            getView().initializeToolbar();
            getView().setListener();
        }
    }

    private void createEnteringControls() {
        List<String> playerNames = wizardGame.getGameSettings().getPlayerNames();
        if (playerNames == null || playerNames.isEmpty()) {
            return;
        }

        int currentRound;
        RealmList<Round> results = wizardGame.getResults();
        if (results == null) {
            currentRound = 1;
        } else {
            currentRound = results.size() + 1;
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
        if (isTippMode) {
            saveAnnouncedTipps();
        } else {
            saveMadeTipps();
        }
        if (isAttached()) {
            getView().dismissOverlay();
        }
    }

    private void saveAnnouncedTipps() {
        if (isAttached()) {
            RealmList<Integer> announcedTipps = getView().getSeekBarValues();
            Round round = new Round();
            round.setAnnouncedTipps(announcedTipps);

            Storage.getInstance().setAnnouncedTipps(announcedTipps);
        }
    }

    private void saveMadeTipps() {

    }
}
