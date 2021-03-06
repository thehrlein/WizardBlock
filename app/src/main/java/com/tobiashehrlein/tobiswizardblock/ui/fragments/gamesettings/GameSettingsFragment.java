package com.tobiashehrlein.tobiswizardblock.ui.fragments.gamesettings;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tobiapplications.thutils.mvp.BaseMvpPresenter;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.databinding.FragmentGameSettingsBinding;
import com.tobiashehrlein.tobiswizardblock.listener.FragmentNavigationListener;
import com.tobiashehrlein.tobiswizardblock.model.settings.SettingsFactory;
import com.tobiashehrlein.tobiswizardblock.ui.views.PlayerChooseSingleView;
import com.tobiashehrlein.tobiswizardblock.ui.views.SwitchTextInfoView;

import io.realm.RealmList;

import static android.text.TextUtils.isEmpty;
import static com.tobiapplications.thutils.NullPointerUtils.isNotEmpty;
import static com.tobiapplications.thutils.NullPointerUtils.isNotNullOrEmpty;
import static com.tobiapplications.thutils.NullPointerUtils.isNull;
import static com.tobiapplications.thutils.NullPointerUtils.isNullOrEmpty;
import static com.tobiashehrlein.tobiswizardblock.utils.lambda.NullCoalescence.letVoid;

/**
 * Created by Tobias Hehrlein on 27.11.2017.
 */

public class GameSettingsFragment extends Fragment implements GameSettingsContract.View {

    private FragmentGameSettingsBinding bind;
    private GameSettingsContract.Presenter presenter;
    private FragmentNavigationListener listener;
    private Context context;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        bind = FragmentGameSettingsBinding.inflate(inflater);

        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = bind.getRoot().getContext();
        presenter = new GameSettingsPresenter();
        presenter.attach(this);
        presenter.init(listener);
    }

    @Override
    public String getTitle() {
        return context.getString(R.string.title_game_settings);
    }

    @Override
    public void setListener() {
        bind.btNext.setOnClickListener(view -> checkIfAllInputsAreValid());
        bind.playerChooser.setPlayerChooseListener(view -> bind.playerNameGroup.setPlayerFieldsVisibleUntil(((PlayerChooseSingleView) view).getNumber(), true));
        bind.playerChooser.initStandardPlayers();
        bind.gameName.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && isEmpty(bind.gameName.getText().toString())) {
                setGameNameError();
            } else if (hasFocus) {
                resetGameNameError();
            }
        });
        bind.gameName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isNotEmpty(s.toString())) {
                    resetGameNameError();
                }
            }
        });
    }

    private void setGameNameError() {
        bind.gameNameLayout.setErrorEnabled(true);
        bind.gameNameLayout.setError(context.getString(R.string.game_settings_game_name_must_not_be_empty));
    }

    private void resetGameNameError() {
        bind.gameNameLayout.setError(null);
        bind.gameNameLayout.setErrorEnabled(false);
    }

    private void checkIfAllInputsAreValid() {
        if (isEmpty(bind.gameName.getText().toString())) {
            setGameNameError();
            return;
        } else if (bind.playerNameGroup.invalidInput()) {
            bind.playerNameGroup.setPlayerErrorIfInvalid();
            return;
        } else {
            letVoid(presenter, GameSettingsContract.Presenter::startNewGame);
        }
    }

    @Override
    public void setFocusToGameNameIfNecessary() {
        if (isEmpty(bind.gameName.getText().toString())) {
            bind.gameName.requestFocus();
            bind.playerNameGroup.clearErrors();
        }
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
    public RealmList<String> getPlayerNames() {
        return bind.playerNameGroup.getPlayerNames(bind.playerChooser.getCurrentPlayerCount() - 1);
    }

    @Override
    public @SettingsFactory.SettingsType int getSettings() {
        boolean easyMode = tippsEqualStitches.isChecked();
        boolean easyModeFirstRound = disableRuleInFirstRound.isChecked();
        boolean anniversaryMode = anniveraryOption.isChecked();

        SettingsFactory settingsFactory = new SettingsFactory();
        return settingsFactory.getSettings(easyMode, easyModeFirstRound, anniversaryMode);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        letVoid(presenter, BaseMvpPresenter::detach);
    }
}
