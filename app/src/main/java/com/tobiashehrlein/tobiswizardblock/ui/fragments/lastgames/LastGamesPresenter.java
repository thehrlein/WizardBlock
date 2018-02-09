package com.tobiashehrlein.tobiswizardblock.ui.fragments.lastgames;

import com.tobiapplications.thutils.mvp.BasePresenter;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.DisplayableItem;
import com.tobiashehrlein.tobiswizardblock.utils.Storage;

import java.util.List;

import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;
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
        if (isAttached()) {
            getView().addSavedGames(getSavedGames());
        }
    }

    @Override
    public void onGameDeleted() {
        if (isAttached()) {
            getView().showNoLastGamesAvailable(isNullOrEmpty(getSavedGames()));
        }
    }

    @Override
    public void onGameRestored() {
        if (isAttached()) {
            getView().showNoLastGamesAvailable(isNullOrEmpty(getSavedGames()));
        }
    }

    private List<DisplayableItem> getSavedGames() {
        return Storage.getInstance().getSavedGames();
    }
}
