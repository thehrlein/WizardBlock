package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tobiashehrlein.tobiswizardblock.R;

import static android.text.TextUtils.isEmpty;
import static com.tobiapplications.thutils.NullPointerUtils.isNotEmpty;

/**
 * Created by Tobias Hehrlein on 05.12.2017.
 */

public class PlayerNameInput extends TextInputLayout {

    private static final int MAX_LINES = 1;
    private TextInputEditText input;
    private Context context;

    public PlayerNameInput(Context context) {
        super(context);
        init(context);
    }

    public PlayerNameInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerNameInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        input = new TextInputEditText(context);
        input.setMaxLines(MAX_LINES);
        input.setSingleLine();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(params);
        addView(input);
        input.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && isEmpty(getInput())) {
                setPlayerInputError(context);
            } else if (hasFocus) {
                resetPlayerInputError();
            }
        });

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isNotEmpty(s.toString())) {
                    resetPlayerInputError();
                }
            }
        });
    }

    public void setPlayerInputError(Context context) {
        setErrorEnabled(true);
        setError(context.getString(R.string.game_settings_name_must_not_be_empty));
    }

    public void resetPlayerInputError() {
        setError(null);
        setErrorEnabled(false);
    }

    public void addPlayerNumber(int playerNumber) {
        setHint(context.getString(R.string.player_hint_placeholder, playerNumber));
    }

    public void setText(String text) {
        input.append(text);
    }

    public String getInput() {
        return input.getText().toString();
    }

    public void clearText() {
        input.setText("");
    }

    public boolean invalidInput() {
        return isEmpty(getInput());
    }

    public boolean isVisible() {
        return getVisibility() == View.VISIBLE;
    }
}
