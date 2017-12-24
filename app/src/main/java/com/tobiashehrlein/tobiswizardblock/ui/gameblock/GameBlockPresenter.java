package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.view.MenuItem;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public class GameBlockPresenter extends BasePresenter<GameBlockContract.View> implements GameBlockContract.Presenter {

    public static final int CARD_COUNT = 60;
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
        initRoundNumbers();
        setAllPreviousTippsAndResults();

        listener.inflateToolbarMenu();
        listener.setBackPressEnabled(false);
        listener.setToolbarMenuItemListener(this::onMenuItemClicked);
    }

    private boolean onMenuItemClicked(MenuItem item) {
        return isAttached() && getView().onMenuItemClicked(item.getItemId());
    }

    private void initRoundNumbers() {
        if (isAttached()) {
            int roundsToPlay = getHowMuchRoundsToPlay();
            getView().addRoundNumbersFor(roundsToPlay);
        }
    }

    private int getHowMuchRoundsToPlay() {
        int playerCount = wizardGame.getGameSettings().getPlayerNames().size();
        return CARD_COUNT / playerCount;
    }

    private void initTitle() {
        if (listener != null) {
            listener.setToolbarTitle(wizardGame.getGameSettings().getGameName());
        }
    }

    private void initHeader() {
        if (isAttached()) {
            getView().initRoundHeadline();
            getView().initHeader(wizardGame.getGameSettings().getPlayerNames());
            getView().setListener();
        }

        setEnterButton();
    }

    private void setAllPreviousTippsAndResults() {
        if (wizardGame == null) {
            return;
        }

        RealmList<Round> results = wizardGame.getResults();
        if (results == null || results.isEmpty()) {
            return;
        }

        if (isAttached()) {
            getView().clearBlock();
        }

        for (Round round : results) {
            RealmList<Integer> tippsAnnounced = round.getAnnouncedTipps();
            RealmList<Integer> stitchesMade = round.getMadeStitches();
            RealmList<Integer> pointsAdded = round.getPointsAdded();
            RealmList<Integer> pointsTotal = round.getPointsTotal();

            if (isAttached()) {
                getView().addRound(tippsAnnounced, stitchesMade, pointsAdded, pointsTotal);
            }
        }
    }

    @Override
    public void openTippsResult() {
        if (listener != null) {
            TippResultFragment tippResultFragment = TippResultFragment.newInstance(isTippMode);
            tippResultFragment.setOnDismissListener(this::backFromTippsResults);
            listener.showDialog(tippResultFragment);
        }
    }

    private void backFromTippsResults() {
        isTippMode = !isTippMode;
        setAllPreviousTippsAndResults();
        setEnterButton();

        if (listener != null) {
            listener.setBackPressEnabled(false);
        }
    }

    private void setEnterButton() {
        if (isTippMode && isAttached()) {
            getView().setButtonTipps();
        } else if (isAttached()) {
            getView().setButtonResults();
        }
    }

    @Override
    public void changePlayerNames() {
        if (isAttached()) {
            getView().openChangePlayerNamesDialog(wizardGame.getGameSettings().getPlayerNames());
        }
    }
}
