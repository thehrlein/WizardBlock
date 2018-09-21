package com.tobiashehrlein.tobiswizardblock.utils;

import android.graphics.Canvas;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.view.View;

import com.tobiashehrlein.tobiswizardblock.listener.RecyclerSwipeHelperListener;
import com.tobiashehrlein.tobiswizardblock.ui.viewholder.LastGameHolder;

import static com.tobiapplications.thutils.NullPointerUtils.isNotNull;

/**
 * Created by Tobias Hehrlein on 07.01.2018.
 */

public class RecyclerSwipeHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerSwipeHelperListener listener;

    public RecyclerSwipeHelper(int dragDirs, int swipeDirs, RecyclerSwipeHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder instanceof LastGameHolder) {
            final View foreground = ((LastGameHolder) viewHolder).getForegroundLayout();
            if (isNotNull(foreground)) {
                getDefaultUIUtil().onSelected(foreground);
            }
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof LastGameHolder) {
            final View foregroundView = ((LastGameHolder) viewHolder).getForegroundLayout();
            if (isNotNull(foregroundView)) {
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof LastGameHolder) {
            final View foregroundView = ((LastGameHolder) viewHolder).getForegroundLayout();
            if (isNotNull(foregroundView)) {
                getDefaultUIUtil().clearView(foregroundView);
            }
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof LastGameHolder) {
            final View foregroundView = ((LastGameHolder) viewHolder).getForegroundLayout();
            if (isNotNull(foregroundView)) {
                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (isNotNull(listener, viewHolder)) {
            listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
        }
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }
}
