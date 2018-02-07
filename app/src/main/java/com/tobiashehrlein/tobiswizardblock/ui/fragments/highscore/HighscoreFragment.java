package com.tobiashehrlein.tobiswizardblock.ui.fragments.highscore;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiapplications.thutils.dialog.DialogBuilderUtil;
import com.tobiapplications.thutils.dialog.DialogTwoButtonListener;
import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentHighscoreBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;
import com.tobiashehrlein.tobiswizardblock.ui.views.HighscoreRow;

import java.util.Map;

import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.let;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class HighscoreFragment extends Fragment implements HighscoreContract.View {

    private FragmentHighscoreBinding bind;
    private FragmentNavigationListener listener;
    private HighscoreContract.Presenter presenter;
    private Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentNavigationListener) {
            this.listener = (FragmentNavigationListener) context;
        }
    }

    public static HighscoreFragment newInstance() {
        return new HighscoreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentHighscoreBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = bind.getRoot().getContext();

        presenter = new HighscorePresenter();
        presenter.attach(this);
        presenter.init(listener, R.menu.menu_higscore);
    }

    @Override
    public String getTitle() {
        return let(context, c -> c.getString(R.string.title_highscore));
    }

    @Override
    public void createNewHighscore(Highscore highscore, Integer ranking) {
        HighscoreRow row = new HighscoreRow(context);
        row.setHighscore(highscore, ranking);

        bind.highscoreList.addView(row);
    }

    @Override
    public boolean onMenuItemClicked(int itemId) {
        switch (itemId) {
            case R.id.action_delete:
                openDeleteAllHighscoreDialog();
                return true;
            default:
                return false;
        }
    }

    private void openDeleteAllHighscoreDialog() {
        String title = context.getString(R.string.highscore_dialog_title);
        String message = context.getString(R.string.highscore_dialog_message);
        Dialog deleteHighscore = DialogBuilderUtil.createDialog(context, title, message, new DialogTwoButtonListener() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onConfirm() {
                letVoid(presenter, HighscoreContract.Presenter::deleteAllHighscore);
            }
        });
        deleteHighscore.show();
    }

    @Override
    public void clearHighscoreList() {
        bind.highscoreList.removeAllViews();
    }

    @Override
    public void showNoScoresAvailable() {
        bind.noHighscores.setVisibility(View.VISIBLE);
        bind.highscoreTitle.setVisibility(View.INVISIBLE);
        bind.highscoreHeader.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideNoScoresAvailable() {
        bind.noHighscores.setVisibility(View.GONE);
        bind.highscoreTitle.setVisibility(View.VISIBLE);
        bind.highscoreHeader.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        letVoid(presenter, BaseMvpPresenter::detach);
    }
}
