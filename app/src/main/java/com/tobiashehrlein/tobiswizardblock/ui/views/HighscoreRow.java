package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tobiashehrlein.tobiswizardblock.R;
import com.tobiashehrlein.tobiswizardblock.model.highscore.Highscore;

/**
 * Created by Tobias Hehrlein on 28.12.2017.
 */

public class HighscoreRow extends LinearLayout {

    private Context context;

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
    }


    public void setHighscore(Highscore score) {
        TextView playerName = new TextView(context);
        TextView scoreView = new TextView(context);

        playerName.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3));
        scoreView.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

        playerName.setText(score.getPlayerName());
        scoreView.setText(context.getString(R.string.highscore_total, score.getScore()));

        addView(playerName);
        addView(scoreView);
    }
}
