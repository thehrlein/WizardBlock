package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.tobiashehrlein.tobiswizardblock.R;

/**
 * Created by Tobias Hehrlein on 02.12.2017.
 */

public class PrimaryButton extends AppCompatButton {

    public PrimaryButton(Context context) {
        super(context);
        init(context);
    }

    public PrimaryButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PrimaryButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setAllCaps(false);
        setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        setTextSize(18);
    }
}
