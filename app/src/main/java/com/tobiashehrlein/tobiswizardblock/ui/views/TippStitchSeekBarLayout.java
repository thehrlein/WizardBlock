package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tobiashehrlein.tobiswizardblock.utils.GeneralUtils;


/**
 * Created by Tobias Hehrlein on 10.12.2017.
 */

public class TippStitchSeekBarLayout extends LinearLayout {

    private TextView playerName;
    private SeekBarControl tippsStitchesControl;
    private TextView tippsStitchesText;
    private TextView announcedTipps;
    private final String INITIAL_VALUE = "0";

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

        tippsStitchesControl.setOnSeekBarValueChangeListener(value -> tippsStitchesText.setText(String.valueOf(value)));
    }

    private void createViews(Context context) {
        playerName = new TextView(context);
        tippsStitchesControl = new SeekBarControl(context);
        tippsStitchesText = new TextView(context);
        tippsStitchesText.setText(INITIAL_VALUE);
        announcedTipps = new TextView(context);

        addView(playerName);
        addView(tippsStitchesControl);
        addView(tippsStitchesText);
        addView(announcedTipps);
    }

    private void setLayoutParams(Context context) {
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginTop = GeneralUtils.pxFromDp(context, 32);
        layoutParams.setMargins(0, marginTop, 0, 0);
        setLayoutParams(layoutParams);

        LayoutParams nameParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3);
        playerName.setLayoutParams(nameParams);

        LayoutParams controlParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 5);
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
