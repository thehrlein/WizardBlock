package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

import com.tobiashehrlein.tobiswizardblock.R;

/**
 * Created by Tobias Hehrlein on 02.12.2017.
 */

public class PrimaryButton extends AppCompatButton {

    private int backgroundNormal;
    private int backgroundDisabled;
    private int textNormal;
    private int textDisabled;

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
        backgroundNormal = ContextCompat.getColor(context, R.color.colorPrimary);
        backgroundDisabled = ContextCompat.getColor(context, R.color.colorGrey);
        textNormal = ContextCompat.getColor(context, R.color.colorWhite);
        textDisabled = ContextCompat.getColor(context, R.color.colorBlack);

        setBackgroundColor(backgroundNormal);
        setTextColor(textNormal);
        setTextSize(18);
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (enabled) {
            setBackgroundColor(backgroundNormal);
            setTextColor(textNormal);
        } else {
            setBackgroundColor(backgroundDisabled);
            setTextColor(textDisabled);
        }
        super.setEnabled(enabled);
    }
}
