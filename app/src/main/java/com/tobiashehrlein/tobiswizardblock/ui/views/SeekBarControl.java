package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.AttributeSet;
import android.widget.SeekBar;

import com.tobiashehrlein.tobiswizardblock.listener.SeekBarValueListener;

/**
 * Created by Tobias Hehrlein on 10.12.2017.
 */

public class SeekBarControl extends AppCompatSeekBar implements SeekBar.OnSeekBarChangeListener {

    private SeekBarValueListener valueListener;

    public SeekBarControl(Context context) {
        super(context);
        init(context);
    }

    public SeekBarControl(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SeekBarControl(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOnSeekBarChangeListener(this);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        if (valueListener != null) {
            valueListener.onValueChange(i);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setOnSeekBarValueChangeListener(SeekBarValueListener valueListener) {
        this.valueListener = valueListener;
    }
}
