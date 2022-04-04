package com.tobiashehrlein.tobiswizardblock.ui_game_settings.playerorder

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.R
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.databinding.ItemPlayerRowBinding

class PlayerOrderViewHolder(private val binding: ItemPlayerRowBinding) :
    RecyclerView.ViewHolder(binding.root), ItemTouchHelperViewHolder {

    private var notifyDataSetChanged : (() -> Unit)? = null

    @SuppressLint("ClickableViewAccessibility")
    fun bind(
        item: String,
        onItemTouchListener: ((RecyclerView.ViewHolder) -> Unit)?,
        notifyDataSetChanged : () -> Unit
    ) {
        this.notifyDataSetChanged = notifyDataSetChanged
        binding.playerName.text = item
        binding.playerPosition.text =
            binding.root.context.getString(R.string.player_order_position, absoluteAdapterPosition + 1)
        binding.iconDrag.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                onItemTouchListener?.invoke(this)
            }
            true
        }
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(0)
        notifyDataSetChanged?.invoke()
    }
}