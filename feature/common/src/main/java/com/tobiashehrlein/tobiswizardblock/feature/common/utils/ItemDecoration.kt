package com.tobiashehrlein.tobiswizardblock.feature.common.utils

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView

/**
 * DividerItemDecoration is a [RecyclerView.ItemDecoration] that can be used as a divider
 * between items of a [androidx.recyclerview.widget.LinearLayoutManager]. It supports both [.HORIZONTAL] and
 * [.VERTICAL] orientations.
 *
 * <pre>
 * drawableItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
 * mLayoutManager.getOrientation());
 * recyclerView.addItemDecoration(drawableItemDecoration);
</pre> *
 */
class ItemDecoration(
    private val orientation: Int,
    private val drawable: Drawable?
) : RecyclerView.ItemDecoration() {

    override fun onDrawOver(
        c: Canvas,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        drawable?.let {
            if (orientation == LinearLayout.VERTICAL) {
                drawVertical(c, parent, it)
            } else {
                drawHorizontal(c, parent, it)
            }
        }
    }

    private fun drawVertical(
        canvas: Canvas,
        parent: RecyclerView,
        drawable: Drawable
    ) {
        canvas.save()
        val childCount = parent.childCount
        val parentWidth = parent.width
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val right = child.right
            if (right == parentWidth) continue
            val left = right - drawable.intrinsicHeight
            val top = child.top
            val bottom = child.bottom
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }
        canvas.restore()
    }

    private fun drawHorizontal(
        canvas: Canvas,
        parent: RecyclerView,
        drawable: Drawable
    ) {
        canvas.save()
        val childCount = parent.childCount
        val right = parent.width
        val left = 0
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val bottom = child.bottom
            val top = bottom - drawable.intrinsicHeight
            drawable.setBounds(left, top, right, bottom)
            drawable.draw(canvas)
        }
        canvas.restore()
    }
}
