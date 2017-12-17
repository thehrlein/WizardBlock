package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tobiashehrlein.tobiswizardblock.utils.GeneralUtils;

import io.realm.RealmList;
import timber.log.Timber;

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
            layout.addView(createTextView(firstColumnList.get(i), showPlusSign, false));
            if (!secondColumnList.isEmpty() || i < secondColumnList.size()) {
                layout.addView(createTextView(secondColumnList.get(i), false, showBold));
            } else {
                layout.addView(createTextView(EMPTY_STRING));
            }
        }

        addView(layout);
    }

    private View createTextView(Integer value, boolean showPlusSign, boolean showBold) {
        SpannableStringBuilder text;
        if (value > 0 && showPlusSign) {
            text = new SpannableStringBuilder(PLUS_SIGN + value);
        } else {
            text = new SpannableStringBuilder(String.valueOf(value));
        }

        if (showBold) {
            StyleSpan bold = new StyleSpan(Typeface.BOLD);
            text.setSpan(bold, 0, text.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }

        return createTextView(text);
    }

    private TextView createTextView(SpannableStringBuilder value) {
        TextView textView = new TextView(context);
        textView.setText(value);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(blockViewParams);
        return textView;
    }
}
