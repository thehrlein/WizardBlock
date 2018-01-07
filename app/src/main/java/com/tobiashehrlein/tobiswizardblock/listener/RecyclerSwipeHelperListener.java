package com.tobiashehrlein.tobiswizardblock.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Tobias Hehrlein on 07.01.2018.
 */

public interface RecyclerSwipeHelperListener {

    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
