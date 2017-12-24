package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Dialog;
import android.view.LayoutInflater;

import com.tobiashehrlein.tobiswizardblock.databinding.DialogChangePlayerNamesBinding;
import com.tobiashehrlein.tobiswizardblock.listener.ChangePlayerListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

import static com.tobiapplications.thutils.NullPointerUtils.isNotNull;

/**
 * Created by Tobias Hehrlein on 24.12.2017.
 */

public class ChangePlayerNamesDialog extends Dialog {

    private DialogChangePlayerNamesBinding bind;
    private Context context;
    private ChangePlayerListener listener;
    private List<PlayerNameInput> playerNameInputList;

    public ChangePlayerNamesDialog(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ChangePlayerNamesDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(context);
    }

    public ChangePlayerNamesDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        playerNameInputList = new ArrayList<>();
        bind = DialogChangePlayerNamesBinding.inflate(LayoutInflater.from(context));
        setContentView(bind.getRoot());
        setCanceledOnTouchOutside(false);

        bind.cancelChangeNames.setOnClickListener(v -> dismiss());
        bind.confirmChangeNames.setOnClickListener(v -> confirmNewPlayerNames());
    }

    private void confirmNewPlayerNames() {
        RealmList<String> newNames = new RealmList<>();
        for (PlayerNameInput input : playerNameInputList) {
            newNames.add(input.getInput());
        }

        if (isNotNull(listener)) {
            listener.onPlayerNameChanged(newNames);
        }

        dismiss();
    }

    public void setPlayerNames(RealmList<String> playerNames) {
        for (String name : playerNames) {
            PlayerNameInput input = new PlayerNameInput(context);
            input.setHint(name);
            input.setText(name);
            playerNameInputList.add(input);
            bind.changePlayerLayout.addView(input);
        }
    }

    public void setPlayerNameChangeListener(ChangePlayerListener listener) {
        this.listener = listener;
    }
}
