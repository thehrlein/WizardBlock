package com.tobiashehrlein.tobiswizardblock.ui.fragments.lastgames;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.model.lastgames.SavedGame;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.List;

import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class LastGamesPresenter extends BasePresenter<LastGamesContract.View> implements LastGamesContract.Presenter {

    private FragmentNavigationListener listener;

    @Override
    public void init(FragmentNavigationListener listener) {
        this.listener = listener;

        listener.setBackPressEnabled(true);
        listener.disableToolbarMenu();
        listener.setToolbarBackButtonEnabled();

        setTitle();
        setUpRecyclerViewAndAdapter();
        createSavedGames();

    }

    private void setTitle() {
        if (isAttached()) {
            String title = getView().getTitle();
            letVoid(listener, l -> l.setToolbarTitle(title));
        }
    }

    private void setUpRecyclerViewAndAdapter() {
        if (isAttached()) {
            getView().setUpRecyclerViewAndAdapter();
        }
    }

    private void createSavedGames() {
        List<DisplayableItem> savedGames = Storage.getInstance().getSavedGames();
        if (isAttached()) {
            getView().addSavedGames(savedGames);
        }
    }
}
