package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tobiapplications.thutils.GeneralUtils;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.listener.SeekBarValueListener;


/**
 * Created by Tobias Hehrlein on 10.12.2017.
 */

public class TippStitchSeekBarLayout extends LinearLayout {

    private final String INITIAL_VALUE = "0";
    private TextView playerName;
    private SeekBarControl tippsStitchesControl;
    private TextView tippsStitchesText;
    private TextView announcedTipps;

    public TippStitchSeekBarLayout(Context context) {
        super(context);
        init(context);
    }

    public TippStitchSeekBarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TippStitchSeekBarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        createViews(context);
        setLayoutParams(context);
        setGravity(Gravity.CENTER);
    }

    public void setOnSeekBarValueChangeListener(SeekBarValueListener seekBarValueChangeListener) {
        tippsStitchesControl.setOnSeekBarValueChangeListener(value -> {
            tippsStitchesText.setText(String.valueOf(value));
            seekBarValueChangeListener.onValueChange(value);
        });
    }

    private void createViews(Context context) {
        playerName = new TextView(context);
        tippsStitchesControl = new SeekBarControl(context);
        tippsStitchesText = new TextView(context);
        tippsStitchesText.setText(INITIAL_VALUE);
        tippsStitchesText.setPadding(GeneralUtils.pxFromDp(context, 8), 0, 0, 0);
        announcedTipps = new TextView(context);
        announcedTipps.setPadding(GeneralUtils.pxFromDp(context, 8), 0, 0, 0);
        Button plusButton = createButton(context, "+");
        Button minusButton = createButton(context, "-");

        plusButton.setOnClickListener(v -> increaseValue());
        minusButton.setOnClickListener(v -> decreaseValue());

        addView(playerName);
        addView(minusButton);
        addView(tippsStitchesControl);
        addView(plusButton);
        addView(tippsStitchesText);
        addView(announcedTipps);
    }

    private Button createButton(Context context, String text) {
        Button button = new Button(context);
        button.setText(text);
        int buttonHeightWidth = GeneralUtils.pxFromDp(context, 30);
        LayoutParams buttonParams = new LayoutParams(buttonHeightWidth, buttonHeightWidth);
        button.setLayoutParams(buttonParams);
        button.setBackground(ContextCompat.getDrawable(context, R.drawable.border_player_choose_view));
        button.setGravity(Gravity.CENTER);
        button.setPadding(0, 0, 0, 0);
        return button;
    }

    private void increaseValue() {
        tippsStitchesControl.increaseValue();
    }

    private void decreaseValue() {
        tippsStitchesControl.decreaseValue();
    }

    private void setLayoutParams(Context context) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginTop = GeneralUtils.pxFromDp(context, 32);
        layoutParams.setMargins(0, marginTop, 0, 0);
        setLayoutParams(layoutParams);

        LayoutParams nameParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
        playerName.setLayoutParams(nameParams);

        LayoutParams controlParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 4);
        tippsStitchesControl.setLayoutParams(controlParams);

        LayoutParams numberParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        tippsStitchesText.setLayoutParams(numberParams);
        announcedTipps.setLayoutParams(numberParams);
    }

    public void setPlayerName(String name) {
        playerName.setText(name);
    }

    public void setMax(int round) {
        tippsStitchesControl.setMax(round);
    }

    public Integer getValue() {
        return tippsStitchesControl.getProgress();
    }
}
