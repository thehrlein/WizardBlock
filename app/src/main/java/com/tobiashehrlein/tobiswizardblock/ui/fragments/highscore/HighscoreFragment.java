package com.tobiashehrlein.tobiswizardblock.ui.fragments.highscore;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentHighscoreBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;
import com.tobiashehrlein.tobiswizardblock.ui.views.HighscoreRow;

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
        presenter.init(listener);
    }

    @Override
    public String getTitle() {
        return let(context, c -> c.getString(R.string.title_highscore));
    }

    @Override
    public void createNewHighscore(Highscore score) {
        HighscoreRow row = new HighscoreRow(context);
        row.setHighscore(score);

        bind.highscoreList.addView(row);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        letVoid(presenter, BaseMvpPresenter::detach);
    }
}
