package com.tobiashehrlein.tobiswizardblock.listener;

import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Tobias Hehrlein on 07.01.2018.
 */

public interface RecyclerSwipeHelperListener {

    void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
}
