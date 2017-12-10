package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Tobias Hehrlein on 07.12.2017.
 */

public class GameHeader extends LinearLayout {

    private Context context;

    public GameHeader(Context context) {
        super(context);
        init(context);
    }

    public GameHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        setOrientation(HORIZONTAL);
        LayoutParams headerParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 6);
        setLayoutParams(headerParams);
    }

    public void setPlayerNames(List<String> playerNames) {
        if (playerNames == null || playerNames.isEmpty()) {
            return;
        }

        LayoutParams textViewParams = new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1);

        for (String name : playerNames) {
            TextView textView = new TextView(context);
            textView.setText(name);
            textView.setLayoutParams(textViewParams);
            textView.setGravity(Gravity.CENTER);
            addView(textView);
        }
    }
}
