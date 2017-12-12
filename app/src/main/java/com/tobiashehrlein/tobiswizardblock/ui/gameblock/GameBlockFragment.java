package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentGameBlockBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.ui.views.GameHeader;
import com.tobiashehrlein.tobiswizardblock.utils.Constants;

import java.util.List;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public class GameBlockFragment extends Fragment implements GameBlockContract.View {

    private FragmentGameBlockBinding bind;
    private GameBlockContract.Presenter presenter;
    private FragmentNavigationListener listener;
    private Context context;

    public static GameBlockFragment newInstance(GameSettings gameSettings) {
        GameBlockFragment gameBlockFragment = new GameBlockFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.GAME_SETTINGS, gameSettings);
        gameBlockFragment.setArguments(args);
        return gameBlockFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentNavigationListener) {
            listener = (FragmentNavigationListener) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new GameBlockPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentGameBlockBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = bind.getRoot().getContext();
        presenter.attach(this);
        presenter.init(listener, getArguments());
    }

    @Override
    public void initRoundHeadline() {
        bind.roundHeadline.setText(context.getString(R.string.round));
    }

    @Override
    public void initHeader(List<String> playerNames) {
        GameHeader gameHeader = new GameHeader(context);
        gameHeader.setPlayerNames(playerNames);
        bind.header.addView(gameHeader);
    }

    @Override
    public void setButtonTipps() {
        bind.enterButton.setText(context.getString(R.string.enter_tipps));
    }

    @Override
    public void setButtonResults() {
        bind.enterButton.setText(context.getString(R.string.enter_results));
    }

    @Override
    public void setListener() {
        bind.enterButton.setOnClickListener(view -> presenter.openTippsResult());
    }


}
