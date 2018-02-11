package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.tobiashehrlein.tobiswizardblock.R;

/**
 * Created by Tobias Hehrlein on 05.12.2017.
 */

public class PlayerNameInput extends TextInputLayout {

    private static final int MAX_LINES = 1;
    private EditText input;
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
        input = new EditText(context);
        input.setMaxLines(MAX_LINES);
        input.setSingleLine();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        input.setLayoutParams(params);
        addView(input);
    }

    public void addPlayerNumber(int playerNumber) {
        // ONLY TEST
        input.setText("A " + playerNumber);
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
}
