package com.tobiashehrlein.tobiswizardblock.ui.gameblock;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import com.tobiapplications.thutils.dialog.DialogBuilderUtil;
import com.tobiapplications.thutils.dialog.DialogUtils;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentTippsResultsBinding;
import com.tobiashehrlein.tobiswizardblock.listener.DialogDismissListener;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.ui.views.TippStitchSeekBarLayout;
import com.tobiashehrlein.tobiswizardblock.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

import static com.tobiapplications.thutils.dialog.DialogUtils.isDialogShowing;

/**
 * Created by Tobias Hehrlein on 08.12.2017.
 */

public class TippResultFragment extends DialogFragment implements TippResultContract.View {

    private FragmentTippsResultsBinding bind;
    private TippResultContract.Presenter presenter;
    private FragmentNavigationListener listener;
    private List<TippStitchSeekBarLayout> seekBarLayouts;
    private Context context;
    private DialogDismissListener dismissListener;

    public static TippResultFragment newInstance(boolean isTippMode) {
        TippResultFragment tippResultFragment = new TippResultFragment();
        Bundle args = new Bundle();
        args.putBoolean(Constants.ISTIPPMODE, isTippMode);
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
                dismissOverlay(false);
                return true;
            }
            return false;
        });

        builder.setView(bind.getRoot());

        context = getContext();
        seekBarLayouts = new ArrayList<>();
        initializePresenter();

        return builder.create();
    }

    public void setOnDismissListener(DialogDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }

    private void initializePresenter() {
        presenter = new TippResultPresenter();
        presenter.attach(this);
        presenter.init(listener, getArguments());
    }

    @Override
    public void initializeToolbar() {
        bind.toolbar.setNavigationIcon(R.drawable.ic_close);
        bind.toolbar.setNavigationOnClickListener(view -> dismissOverlay(false));
    }

    @Override
    public TippStitchSeekBarLayout createTippStitchesLayout(String playerName, int round) {
        TippStitchSeekBarLayout seekBarLayout = new TippStitchSeekBarLayout(context);
        seekBarLayout.setPlayerName(playerName);
        seekBarLayout.setMax(round);
        bind.tippStitchesLayout.addView(seekBarLayout);
        seekBarLayouts.add(seekBarLayout);
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
        bind.enterButton.setText(context.getString(R.string.confirm_tipps));
    }

    @Override
    public void setResultsButton() {
        bind.enterButton.setText(context.getString(R.string.confirm_results));
    }

    @Override
    public void dismissOverlay(boolean enteredTippsOrResults) {
        if (enteredTippsOrResults && dismissListener != null) {
            dismissListener.onDismiss();
        }
        new Handler().postDelayed(this::dismiss, 200);
    }

    @Override
    public RealmList<Integer> getSeekBarValues() {
        RealmList<Integer> values = new RealmList<>();
        for (TippStitchSeekBarLayout seekBarLayout : seekBarLayouts) {
            values.add(seekBarLayout.getValue());
        }

        return values;
    }

    @Override
    public void setTippsHeadline() {
        bind.headline.setText(context.getString(R.string.how_many_stitches));
    }

    @Override
    public void setResultsHeadline() {
        bind.headline.setText(context.getString(R.string.how_many_stitches_result));
    }

    @Override
    public void displayInvalidInput(@StringRes int message) {
        String title = context.getString(R.string.error_title);
        String text = context.getString(message);

        Dialog failureDialog = DialogBuilderUtil.createOneButtonDialog(context, title, text);
        failureDialog.show();
    }
}
