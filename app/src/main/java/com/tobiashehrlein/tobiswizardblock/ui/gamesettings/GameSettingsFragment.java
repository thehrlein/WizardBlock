package com.tobiashehrlein.tobiswizardblock.ui.gamesettings;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentGameSettingsBinding;
import com.tobiashehrlein.tobiswizardblock.model.Settings;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.ui.views.PlayerSelectionView;
import com.tobiashehrlein.tobiswizardblock.ui.views.SwitchTextInfoView;
import com.tobiashehrlein.tobiswizardblock.utils.dialog.DialogBuilderUtil;

import java.util.List;

/**
 * Created by Tobias Hehrlein on 27.11.2017.
 */

public class GameSettingsFragment extends Fragment implements GameSettingsContract.View {

    private FragmentGameSettingsBinding bind;
    private GameSettingsContract.Presenter presenter;
    private FragmentNavigationListener listener;
    private Context context;
    private Dialog infoDialog;
    private SwitchTextInfoView disableRuleInFirstRound;
    private SwitchTextInfoView anniveraryOption;
    private SwitchTextInfoView tippsEqualStitches;

    public static GameSettingsFragment newInstance() {
        return new GameSettingsFragment();
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentGameSettingsBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = bind.getRoot().getContext();
        presenter = new GameSettingsPresenter();
        presenter.attach(this);
        presenter.init(listener);

        // ONLY TEST
        bind.gameName.setText("My Round");
    }

    @Override
    public String getTitle() {
        return context.getString(R.string.title_game_settings);
    }

    @Override
    public void setListener() {
        infoDialog = DialogBuilderUtil.createDialog(context, context.getString(R.string.title_info_dialog), context.getString(R.string.gamename_info), true);

        bind.gameNameInfo.setOnClickListener(view -> infoDialog.show());
        bind.btNext.setOnClickListener(view -> presenter.startNewGame());
        bind.playerChooser.setPlayerChooseListener(view -> bind.playerNameGroup.setPlayerFieldsVisibleUntil(((PlayerSelectionView) view).getNumber()));
        bind.playerChooser.initStandardPlayers();
    }

    @Override
    public void createTippsEqualStitchesOption() {
        tippsEqualStitches = new SwitchTextInfoView(context);
        tippsEqualStitches.setText(context.getString(R.string.stitches_can_be_equal_tipps));
        tippsEqualStitches.setChecked(false);
        tippsEqualStitches.setInfoText(context.getString(R.string.stitches_can_be_equal_tipps_info));
        tippsEqualStitches.addOnSwitchCheckListener((compoundButton, checked) -> setTippsEqualStitches(checked));

        bind.switchInfoLayout.addView(tippsEqualStitches);
    }

    @Override
    public void createAdditionalFirstRoundTippsEqualStitchesOption() {
        disableRuleInFirstRound = new SwitchTextInfoView(context);
        disableRuleInFirstRound.setText(context.getString(R.string.stitches_can_be_equal_in_first_round_text));
        disableRuleInFirstRound.setChecked(false);
        disableRuleInFirstRound.setInfoText(context.getString(R.string.stitches_can_be_equal_in_first_round_info));
        disableRuleInFirstRound.addOnSwitchCheckListener((compoundButton, checked) -> setTippsEqualStitchesInFirstRound(checked));

        bind.switchInfoLayout.addView(disableRuleInFirstRound);
    }

    @Override
    public void createAnniversaryStitchesCanBeLessOption() {
        anniveraryOption = new SwitchTextInfoView(context);
        anniveraryOption.setText(context.getString(R.string.anniversary_option_stitches_can_be_less));
        anniveraryOption.setChecked(false);
        anniveraryOption.setInfoText(context.getString(R.string.anniversary_option_stitches_can_be_less_info));

        bind.switchInfoLayout.addView(anniveraryOption);
    }

    private void setTippsEqualStitches(boolean tippsEqualStitches) {
        presenter.setTippsEqualStitches(tippsEqualStitches);
    }

    private void setTippsEqualStitchesInFirstRound(boolean tippsEqualStitchesInFirstRound) {
        presenter.setTippsEqualStitchesInFirstRound(tippsEqualStitchesInFirstRound);
    }

    @Override
    public void hideFirstRoundExceptionOption() {
        disableRuleInFirstRound.setVisibility(View.GONE);
    }

    @Override
    public void showFirstRoundExceptionOption() {
        disableRuleInFirstRound.setVisibility(View.VISIBLE);
    }

    @Override
    public void setFirstRoundExceptionOptionDisabled() {
        disableRuleInFirstRound.setChecked(false);
    }

    @Override
    public String getGameName() {
        return bind.gameName.getText().toString();
    }

    @Override
    public List<String> getPlayerNames() {
        return bind.playerNameGroup.getPlayerNames(bind.playerChooser.getCurrentPlayerCount());
    }

    @Override
    public Settings getSettings() {
        boolean easyMode = tippsEqualStitches.isChecked();
        boolean easyModeFirstRound = disableRuleInFirstRound.isChecked();
        boolean anniversaryMode = anniveraryOption.isChecked();
        return Settings.create(easyMode, easyModeFirstRound, anniversaryMode);
    }
}
