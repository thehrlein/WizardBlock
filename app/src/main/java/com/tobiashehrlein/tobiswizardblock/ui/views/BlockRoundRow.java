package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tobiapplications.thutils.GeneralUtils;
import com.tobiashehrlein.tobiswizardblock.R;

import io.realm.RealmList;

/**
 * Created by Tobias Hehrlein on 16.12.2017.
 */

public class BlockRoundRow extends LinearLayout {

    public static final SpannableStringBuilder EMPTY_STRING = new SpannableStringBuilder("");
    public static final String PLUS_SIGN = "+";
    private LinearLayout tippLayout;
    private LinearLayout resultLayout;
    private LayoutParams blockViewParams;
    private Context context;
    private int textBlack;
    private int textGreen;
    private int textRed;

    public BlockRoundRow(Context context) {
        super(context);
        init(context);
    }

    public BlockRoundRow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BlockRoundRow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        tippLayout = new LinearLayout(context);
        resultLayout = new LinearLayout(context);
        tippLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        resultLayout.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(VERTICAL);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        blockViewParams = new LinearLayout.LayoutParams(0, GeneralUtils.pxFromDp(context, 30), 1);

        textBlack = ContextCompat.getColor(context, R.color.colorBlack);
        textGreen = ContextCompat.getColor(context, R.color.colorGreen);
        textRed = ContextCompat.getColor(context, R.color.colorRed);
    }

    public void setData(RealmList<Integer> tippsAnnounced, RealmList<Integer> stitchesMade, RealmList<Integer> pointsAdded, RealmList<Integer> pointsTotal) {
        removeAllViews();

        createTippStitchLayout(tippLayout, tippsAnnounced, stitchesMade, false, false);

        if (pointsAdded == null || pointsAdded.isEmpty() || pointsTotal == null || pointsTotal.isEmpty()) {
            return;
        }

        createTippStitchLayout(resultLayout, pointsAdded, pointsTotal, true, true);
    }

    private void createTippStitchLayout(LinearLayout layout, RealmList<Integer> firstColumnList, RealmList<Integer> secondColumnList, boolean showPlusSign, boolean showBold) {
        layout.removeAllViews();
        for (int i = 0; i < firstColumnList.size(); i++) {
            layout.addView(createTextView(firstColumnList.get(i), showPlusSign, false, false));
            if (!secondColumnList.isEmpty() || i < secondColumnList.size()) {
                layout.addView(createTextView(secondColumnList.get(i), false, showBold, true));
            } else {
                layout.addView(createTextView(EMPTY_STRING, textBlack, false));
            }
        }

        addView(layout);
    }

    private View createTextView(Integer value, boolean showPlusSign, boolean showBold, boolean borderRight) {
        SpannableStringBuilder text;
        int textColor;
        if (value > 0 && showPlusSign) {
            text = new SpannableStringBuilder(PLUS_SIGN + value);
            textColor = textGreen;
        } else if (showPlusSign){
            textColor = textRed;
            text = new SpannableStringBuilder(String.valueOf(value));
        } else {
            textColor = textBlack;
            text = new SpannableStringBuilder(String.valueOf(value));
        }

        if (showBold) {
            StyleSpan bold = new StyleSpan(Typeface.BOLD);
            text.setSpan(bold, 0, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }

        return createTextView(text, textColor, borderRight);
    }

    private TextView createTextView(SpannableStringBuilder value, int textColor, boolean borderRight) {
        TextView textView = new TextView(context);
        textView.setText(value);
        textView.setTextColor(textColor);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(blockViewParams);

        if (borderRight) {
            textView.setBackground(ContextCompat.getDrawable(context, R.drawable.border_right_grey));
        }
        return textView;
    }
}
