package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.view.MenuItem;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

import static com.tobiapplications.thutils.NullPointerUtils.isNotNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNotNullOrEmpty;
import static com.tobiapplications.thutils.NullPointerUtils.isNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public class GameBlockPresenter extends BasePresenter<GameBlockContract.View> implements GameBlockContract.Presenter {

    public static final int CARD_COUNT = 60;
    private FragmentNavigationListener listener;
    private WizardGame wizardGame;
    private boolean isTippMode;
    private int roundsToPlay;
    private boolean gameOver;

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
            roundsToPlay = getHowMuchRoundsToPlay();
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

        if (finished(results) && isAttached()) {
            gameOver = true;
            getView().disableEnterButton();
            getView().showWinnerDialog(getWinner(results));
        }
    }

    private Map<String, Integer> getWinner(RealmList<Round> results) {
        Round lastRound = results.get(results.size() - 1);
        RealmList<Integer> totalPoints = lastRound.getPointsTotal();
        Map<String, Integer> winners = new HashMap<>();
        RealmList<String> playerNames = wizardGame.getGameSettings().getPlayerNames();
        winners.put(playerNames.first(), totalPoints.first());

        for (int i = 1; i < totalPoints.size(); i++) {
            int previousScore = totalPoints.get(i - 1);
            int currentScore = totalPoints.get(i);
            String currentPlayerName = playerNames.get(i);
            if (currentScore > previousScore) {
                winners.clear();
                winners.put(currentPlayerName, currentScore);
            } else if (currentScore == previousScore) {
                winners.put(currentPlayerName, currentScore);
            }
        }

        return winners;
    }

    private boolean finished(RealmList<Round> results) {
        if (isNullOrEmpty(results)) {
            return false;
        }

        Round lastRound = results.get(results.size() - 1);
        if (isNull(lastRound)) {
            return false;
        }

        RealmList<Integer> totalPoints = lastRound.getPointsTotal();
        if (isNullOrEmpty(totalPoints)) {
            return false;
        }

        return results.size() == roundsToPlay;
    }

    @Override
    public void openTippsResult() {
        if (isNotNull(listener)) {
            TippResultFragment tippResultFragment = TippResultFragment.newInstance(isTippMode);
            tippResultFragment.setOnDismissListener(this::backFromTippsResults);
            listener.showDialog(tippResultFragment);
        }
    }

    private void backFromTippsResults() {
        isTippMode = !isTippMode;
        setAllPreviousTippsAndResults();

        if (!gameOver) {
            setEnterButton();
        }

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

    @Override
    public void startNewGame() {
        if (isNotNull(listener)) {
            listener.startNewGame();
        }
    }

    @Override
    public StringBuilder getCompleteWinnerString(List<String> winnerNames) {
        final String appending = " & ";
        StringBuilder completeWinnerNames = new StringBuilder();
        Iterator<String> iterator = winnerNames.iterator();

        completeWinnerNames.append(iterator.next());
        while (iterator.hasNext()) {
            completeWinnerNames.append(appending);
            completeWinnerNames.append(iterator.next());
        }

        return completeWinnerNames;
    }

    @Override
    public void openAbout() {
        if (isNotNull(listener)) {
            AboutFragment aboutFragment = AboutFragment.newInstance();
            listener.replaceFragment(aboutFragment, true);
        }
    }
}
