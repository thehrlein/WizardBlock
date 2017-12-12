package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentTippsResultsBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;
import com.tobiashehrlein.tobiswizardblock.utils.Constants;

/**
 * Created by Tobias Hehrlein on 08.12.2017.
 */

public class TippResultFragment extends DialogFragment implements TippResultContract.View {

    private FragmentTippsResultsBinding bind;
    private TippResultContract.Presenter presenter;
    private FragmentNavigationListener listener;
    private Context context;

    public static TippResultFragment newInstance(GameSettings gameSettings, int round, @Constants.EnterType int enterType) {
        TippResultFragment tippResultFragment = new TippResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.GAME_SETTINGS, gameSettings);
        args.putInt(Constants.ROUND, round);
        args.putInt(Constants.ENTER_TYPE, enterType);
        tippResultFragment.setArguments(args);
        return tippResultFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof FragmentNavigationListener) {
            listener = (FragmentNavigationListener) context;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        bind = FragmentTippsResultsBinding.inflate(inflater);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.SlidingDialogTheme);
        builder.setOnKeyListener((dialogInterface, keyCode, keyEvent) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                dismissOverlay();
                return true;
            }
            return false;
        });

        builder.setView(bind.getRoot());

        context = getContext();
        initializePresenter();

        return builder.create();
    }

    private void initializePresenter() {
        presenter = new TippResultPresenter();
        presenter.attach(this);
        presenter.init(listener, getArguments());
    }

    @Override
    public void initializeToolbar() {
        bind.toolbar.setNavigationIcon(R.drawable.ic_close);
        bind.toolbar.setNavigationOnClickListener(view -> dismissOverlay());
    }

    @Override
    public TippStitchSeekBarLayout createTippStitchesLayout(String playerName, int round) {
        TippStitchSeekBarLayout seekBarLayout = new TippStitchSeekBarLayout(context);
        seekBarLayout.setPlayerName(playerName);
        seekBarLayout.setMax(round);
        bind.tippStitchesLayout.addView(seekBarLayout);

        return seekBarLayout;
    }

    @Override
    public void setListener() {
        bind.enterButton.setOnClickListener(view -> presenter.onEnterButtonClicked());
    }

    @Override
    public void setTippsToolbar() {
        bind.toolbarText.setText(context.getString(R.string.title_tipps));
    }

    @Override
    public void setResultsToolbar() {
        bind.toolbarText.setText(context.getString(R.string.title_results));
    }

    @Override
    public void setTippsButton() {
        bind.enterButton.setText(context.getString(R.string.enter_tipps));
    }

    @Override
    public void setResultsButton() {
        bind.enterButton.setText(context.getString(R.string.enter_results));
    }

    private void dismissOverlay() {
        new Handler().postDelayed(this::dismiss, 200);
    }
}
