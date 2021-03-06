package com.tobiashehrlein.tobiswizardblock.ui.fragments.highscore;

import androidx.annotation.MenuRes;
import android.view.MenuItem;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.Map;

import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class HighscorePresenter extends BasePresenter<HighscoreContract.View> implements HighscoreContract.Presenter {

    private FragmentNavigationListener listener;
    private @MenuRes int highscoreMenu;

    @Override
    public void init(FragmentNavigationListener listener, @MenuRes int menuHigscore) {
        this.listener = listener;
        this.highscoreMenu = menuHigscore;

        listener.setToolbarBackButtonEnabled();
        listener.inflateToolbarMenu(menuHigscore);
        listener.setToolbarMenuItemListener(this::onMenuItemClicked);
        listener.setBackPressEnabled(true);
        listener.setGameFinished(false);

        setTitle();
        setUpHighscores();
    }

    private boolean onMenuItemClicked(MenuItem item) {
        return isAttached() && getView().onMenuItemClicked(item.getItemId());
    }

    private void setTitle() {
        if (isAttached()) {
            String title = getView().getTitle();
            letVoid(listener, l -> l.setToolbarTitle(title));
        }
    }

    private void setUpHighscores() {
        clearHighscoreList();
        Map<Highscore, Integer> highscores = Storage.getInstance().getHighscores();

        if (isNullOrEmpty(highscores)) {
            showNoScoresAvailable();
        } else {
            hideNoScoresAvailable();
        }

        for (Map.Entry<Highscore, Integer> entry : highscores.entrySet())
        if (isAttached()) {
            getView().createNewHighscore(entry.getKey(), entry.getValue());
        }

    }

    private void clearHighscoreList() {
        if (isAttached()) {
            getView().clearHighscoreList();
            showNoScoresAvailable();
        }
    }

    private void showNoScoresAvailable() {
        if (isAttached()) {
            getView().showNoScoresAvailable();
        }

        letVoid(listener, FragmentNavigationListener::disableToolbarMenu);
    }

    private void hideNoScoresAvailable() {
        if (isAttached()) {
            getView().hideNoScoresAvailable();
        }

        letVoid(listener, l -> l.inflateToolbarMenu(highscoreMenu));
    }

    @Override
    public void deleteAllHighscore() {
        Storage.getInstance().deleteAllHighscore();
        clearHighscoreList();
    }
}
