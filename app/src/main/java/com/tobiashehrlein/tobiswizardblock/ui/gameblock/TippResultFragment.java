package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiashehrlein.tobiswizardblock.databinding.FragmentTippsResultsBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.GameSettings;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;
import com.tobiashehrlein.tobiswizardblock.utils.Constants;

/**
 * Created by Tobias Hehrlein on 08.12.2017.
 */

public class TippResultFragment extends Fragment implements TippResultContract.View {

    private FragmentTippsResultsBinding bind;
    private TippResultContract.Presenter presenter;
    private FragmentNavigationListener listener;
    private Context context;

    public static TippResultFragment newInstance(GameSettings gameSettings, int round) {
        TippResultFragment tippResultFragment = new TippResultFragment();
        Bundle args = new Bundle();
        args.putSerializable(Constants.GAME_SETTINGS, gameSettings);
        args.putInt(Constants.ROUND, round);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        presenter = new TippResultPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentTippsResultsBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getContext();
        presenter.attach(this);
        presenter.init(listener, getArguments());
    }

    @Override
    public TippStitchSeekBarLayout createTippStitchesLayout(String playerName, int round) {
        TippStitchSeekBarLayout seekBarLayout = new TippStitchSeekBarLayout(context);
        seekBarLayout.setPlayerName(playerName);
        seekBarLayout.setMax(round);
        bind.tippStitchesLayout.addView(seekBarLayout);

        return seekBarLayout;
    }

    //    @NonNull
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        context = getContext();
//        bind = FragmentTippsResultsBinding.inflate(getLayoutInflater());
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.SlidingDialogTheme);
//        builder.setOnKeyListener(((dialog, keyCode, keyEvent) -> {
//            if (keyCode == KeyEvent.KEYCODE_BACK) {
//                dismissOverlay();
//                return true;
//            }
//            return false;
//        }));
//        builder.setView(bind.getRoot());
//
//        return builder.create();
//    }

//    private void dismissOverlay() {
//        new Handler().postDelayed(this::dismiss, 200);
//    }
}
