package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tobiapplications.thutils.GeneralUtils;
import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class HighscoreRow extends LinearLayout {

    private Context context;
    private @ColorInt int colorGold;
    private @ColorInt int colorSilver;
    private @ColorInt int colorBronze;
    private @ColorInt int colorNoRanking;

    public HighscoreRow(Context context) {
        super(context);
        init(context);
    }

    public HighscoreRow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HighscoreRow(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.colorGold = ContextCompat.getColor(context, R.color.colorGold);
        this.colorSilver = ContextCompat.getColor(context, R.color.colorSilver);
        this.colorBronze = ContextCompat.getColor(context, R.color.colorBronze);
        this.colorNoRanking = ContextCompat.getColor(context, R.color.colorNoRanking);
        setGravity(Gravity.CENTER);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, GeneralUtils.pxFromDp(context, 50)));
    }


    public void setHighscore(Highscore score, int ranking) {
        TextView rankingView = new TextView(context);
        TextView playerName = new TextView(context);
        TextView scoreView = new TextView(context);

        rankingView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2));
        playerName.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3));
        scoreView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 2));

        rankingView.setText(context.getString(R.string.highscore_ranking, ranking));
        playerName.setText(score.getPlayerName());
        scoreView.setText(context.getString(R.string.highscore_total, score.getScore()));

        rankingView.setTypeface(null, Typeface.BOLD);
        playerName.setTypeface(null, Typeface.BOLD);
        scoreView.setTypeface(null, Typeface.BOLD);

        rankingView.setGravity(Gravity.CENTER);
        showRankingColor(ranking);

        addView(rankingView);
        addView(playerName);
        addView(scoreView);
    }

    private void showRankingColor(int ranking) {
        if (ranking == 1) {
            setBackgroundColor(colorGold);
        } else if (ranking == 2) {
            setBackgroundColor(colorSilver);
        } else if (ranking == 3) {
            setBackgroundColor(colorBronze);
        } else {
            setBackgroundColor(colorNoRanking);
        }
    }
}
