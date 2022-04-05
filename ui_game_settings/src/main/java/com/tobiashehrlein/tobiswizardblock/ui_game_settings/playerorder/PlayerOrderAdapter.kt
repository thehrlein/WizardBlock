package com.tobiashehrlein.tobiswizardblock.ui_game_settings.playerorder

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerorder.PlayerOrderInteractions
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.R
import kotlin.collections.ArrayList

class PlayerOrderAdapter(
    private val interactions: PlayerOrderInteractions
) : RecyclerView.Adapter<PlayerOrderViewHolder>(),
    ItemTouchHelperAdapter {

    private val items = mutableListOf<String>()
    private var onItemTouchListener: ((RecyclerView.ViewHolder) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerOrderViewHolder {
        return PlayerOrderViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_player_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PlayerOrderViewHolder, position: Int) {
        holder.bind(items[position], onItemTouchListener) {
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(list: List<String>) {
        items.clear()
        items.addAll(list)
    }

    fun setOnItemTouchListener(onItemTouchListener: ((RecyclerView.ViewHolder) -> Unit)) {
        this.onItemTouchListener = onItemTouchListener
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        val prev: String = items.removeAt(fromPosition)
        items.add(toPosition, prev)
        notifyItemMoved(fromPosition, toPosition)
        interactions.onPlayerOrderChanged(ArrayList(items))
    }
}
