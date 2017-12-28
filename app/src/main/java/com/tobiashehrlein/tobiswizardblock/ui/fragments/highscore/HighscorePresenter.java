package com.tobiashehrlein.tobiswizardblock.ui.fragments.highscore;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;
import com.tobiashehrlein.tobiswizardblock.ui.views.HighscoreRow;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.List;

import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class HighscorePresenter extends BasePresenter<HighscoreContract.View> implements HighscoreContract.Presenter {

    private FragmentNavigationListener listener;

    @Override
    public void init(FragmentNavigationListener listener) {
        this.listener = listener;


        listener.setToolbarBackButtonEnabled();
        listener.disableToolbarMenu();
        listener.setBackPressEnabled(true);

        setTitle();
        setUpHighscores();
    }

    private void setTitle() {
        if (isAttached()) {
            String title = getView().getTitle();
            letVoid(listener, l -> l.setToolbarTitle(title));
        }
    }

    private void setUpHighscores() {
        List<Highscore> highscores = Storage.getInstance().getHighscores();
        for (Highscore score : highscores) {
            if (isAttached()) {
                getView().createNewHighscore(score);
            }
        }
    }
}
