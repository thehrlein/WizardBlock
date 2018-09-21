package com.tobiashehrlein.tobiswizardblock.ui.views;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.tobiapplications.thutils.GeneralUtils;

import java.util.ArrayList;

/**
 * Created by Tobias Hehrlein on 02.12.2017.
 */

public class PlayerChooseGroupView extends LinearLayout {

    private final int MIN_PLAYERS = 3;
    private final int MAX_PLAYERS = 6;
    private ArrayList<PlayerChooseSingleView> playerViews;
    private OnClickListener listener;
    private int currentPlayerCount;

    public PlayerChooseGroupView(Context context) {
        super(context);
        init(context);
    }

    public PlayerChooseGroupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerChooseGroupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        playerViews = new ArrayList<>();
        for (int i = MIN_PLAYERS; i <= MAX_PLAYERS; i++) {
            createNewPlaySelection(context, i);
        }
    }

    private void createNewPlaySelection(Context context, int i) {
        int id = View.generateViewId();
        PlayerChooseSingleView playerChooseSingleView = new PlayerChooseSingleView(context);
        playerChooseSingleView.setNumber(i);
        playerChooseSingleView.setId(id);
        playerChooseSingleView.setMinimumHeight(GeneralUtils.pxFromDp(context, 50));
        playerChooseSingleView.setOnClickListener(view -> setSelection(id));
        playerViews.add(playerChooseSingleView);
        addView(playerChooseSingleView);
    }

    public void setPlayerChooseListener(OnClickListener onClickListener) {
        this.listener = onClickListener;
    }

    public void initStandardPlayers() {
        setSelection(playerViews.get(0).getId());
    }

    private void setSelection(int id) {
        for (PlayerChooseSingleView player : playerViews) {
            if (player.getId() == id) {
                player.setSelected();
                currentPlayerCount = player.getNumber();
                listener.onClick(player);
            } else {
                player.setNormal();
            }
        }
    }

    public int getCurrentPlayerCount() {
        return currentPlayerCount;
    }
}
