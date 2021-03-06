package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import androidx.core.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tobiapplications.thutils.GeneralUtils;
import com.tobiashehrlein.tobiswizardblock.R;

/**
 * Created by Tobias Hehrlein on 02.12.2017.
 */

public class PlayerChooseSingleView extends RelativeLayout {

    private RelativeLayout wrapperLayout;
    private TextView numberTextView;
    private int textSelected;
    private int textNormal;
    private int backgroundSelected;
    private int backgroundNormal;
    private int number;

    public PlayerChooseSingleView(Context context) {
        super(context);
        init(context);
    }

    public PlayerChooseSingleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerChooseSingleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        wrapperLayout = new RelativeLayout(context);
        int padding = 3;
        setPadding(padding, padding, padding, padding);
        numberTextView = new TextView(context);
        textSelected = ContextCompat.getColor(context, R.color.colorWhite);
        textNormal = ContextCompat.getColor(context, R.color.colorBlack);
        backgroundSelected = ContextCompat.getColor(context, R.color.colorPrimary);
        backgroundNormal = ContextCompat.getColor(context, R.color.colorLightGrey);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        setLayoutParams(params);

        LinearLayout.LayoutParams pa = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GeneralUtils.pxFromDp(context, 50));
        wrapperLayout.setLayoutParams(pa);
    }

    public void setNumber(int number) {
        this.number = number;
        numberTextView.setText(String.valueOf(number));
        wrapperLayout.addView(numberTextView);
        wrapperLayout.setGravity(Gravity.CENTER);
        addView(wrapperLayout);
    }

    public void setSelected() {
        numberTextView.setTextColor(textSelected);
        wrapperLayout.setBackgroundColor(backgroundSelected);
    }

    public void setNormal() {
        numberTextView.setTextColor(textNormal);
        wrapperLayout.setBackgroundColor(backgroundNormal);
    }

    public int getNumber() {
        return number;
    }
}
