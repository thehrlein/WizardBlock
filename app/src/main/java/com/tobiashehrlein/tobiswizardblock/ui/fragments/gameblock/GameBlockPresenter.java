package com.tobiashehrlein.tobiswizardblock.ui.fragments.gameblock;

import android.support.annotation.MenuRes;
import android.view.MenuItem;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.model.Round;
import com.tobiashehrlein.tobiswizardblock.model.WizardGame;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import io.realm.RealmList;

import static com.tobiapplications.thutils.NullPointerUtils.isNotNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.let;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

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
    }

    @Override
    public void init(FragmentNavigationListener listener, @MenuRes int menuGameBlock) {
        this.listener = listener;

        listener.inflateToolbarMenu(menuGameBlock);
        listener.setBackPressEnabled(false);
        listener.setToolbarMenuItemListener(this::onMenuItemClicked);

        getCurrentMode();
        setModifyLastInputTitle();
        initTitle();
        initHeader();
        initRoundNumbers();
        setAllPreviousTippsAndResults();

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
        String gamenName = let(wizardGame, game -> let(game.getGameSettings(), GameSettings::getGameName));
        letVoid(listener, l -> l.setToolbarTitle(gamenName));
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
        if (isNull(wizardGame)) {
            return;
        }

        RealmList<Round> results = wizardGame.getResults();
        if (results == null || results.isEmpty()) {
            letVoid(listener, FragmentNavigationListener::disableModifyLastInputAction);
            return;
        }

        letVoid(listener, FragmentNavigationListener::enableModifyLastInputAction);

        if (isAttached()) {
            getView().clearBlock();
        }

        for (Round round : results) {
            addRoundToUi(round);
        }

        if (finished(results) && isAttached()) {
            gameFinished();
        }
    }

    private void addRoundToUi(Round round) {
        RealmList<Integer> tippsAnnounced = round.getAnnouncedTipps();
        RealmList<Integer> stitchesMade = round.getMadeStitches();
        RealmList<Integer> pointsAdded = round.getPointsAdded();
        RealmList<Integer> pointsTotal = round.getPointsTotal();

        if (isAttached()) {
            getView().addRound(tippsAnnounced, stitchesMade, pointsAdded, pointsTotal);
        }
    }

    private void gameFinished() {
        gameOver = true;
        letVoid(listener, FragmentNavigationListener::disableModifyLastInputAction);
        Storage.getInstance().saveHighscores();
        getView().disableEnterButton();
        Map<String, Integer> winners = Storage.getInstance().getWinner();
        getView().showWinnerDialog(winners);
        Storage.getInstance().deleteCurrentGameFromLastGameList();
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
    public void openNextInputEntering() {
        openTippsResults(isTippMode, false);
    }

    @Override
    public void changeLastRoundInput() {
        boolean lastType = !isTippMode;
        openTippsResults(lastType, true);
    }

    private void openTippsResults(boolean isTippMode, boolean changeLastRoundInput) {
        if (isNotNull(listener)) {
            TippResultFragment tippResultFragment = TippResultFragment.newInstance(isTippMode, changeLastRoundInput);
            tippResultFragment.setOnDismissListener(this);
            listener.showDialog(tippResultFragment);
        }
    }

    private void backFromTippsResults() {
        getCurrentMode();
        setModifyLastInputTitle();
        setAllPreviousTippsAndResults();
        scrollToVisiblePosition();
        if (!gameOver) {
            setEnterButton();
        }

        letVoid(listener, l -> l.setBackPressEnabled(false));
    }

    private void scrollToVisiblePosition() {
        if (isAttached()) {
            int currentRound = let(wizardGame, game -> let(game.getResults(), results -> results.size()));
            if (currentRound > 5) {
                getView().scrollTo(currentRound - 4);
            }
        }
    }

    private void setModifyLastInputTitle() {
        if (isTippMode) {
            letVoid(listener, FragmentNavigationListener::setLastInputActionResults);
        } else {
            letVoid(listener, FragmentNavigationListener::setLastInputActionTipps);
        }
    }

    private void getCurrentMode() {
        if (isNull(wizardGame) || isNullOrEmpty(wizardGame.getResults())) {
            isTippMode = true;
        } else {
            RealmList<Integer> lastMadeStitches = let(wizardGame, game -> let(game.getLastRound(), Round::getMadeStitches));
            isTippMode = !isNullOrEmpty(lastMadeStitches);
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
        letVoid(listener, FragmentNavigationListener::startNewGame);
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

    @Override
    public void onDismissInputValid() {
        backFromTippsResults();
    }

    @Override
    public void onCloseInputInvalid() {
        letVoid(listener, l -> l.setBackPressEnabled(false));
    }
}
