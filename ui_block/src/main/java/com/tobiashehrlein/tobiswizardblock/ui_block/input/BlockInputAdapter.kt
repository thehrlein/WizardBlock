package com.tobiashehrlein.tobiswizardblock.ui_block.input

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputDataItem
import com.tobiashehrlein.tobiswizardblock.presentation.block.input.BlockInputInteractions
import com.tobiashehrlein.tobiswizardblock.ui_block.R
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater

class BlockInputAdapter(
    private val interactions: BlockInputInteractions
) : ListAdapter<InputDataItem, BlockInputViewHolder>(
    BockInputDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockInputViewHolder {
        return BlockInputViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_block_input,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BlockInputViewHolder, position: Int) {
        holder.bind(getItem(position), interactions)
    }
}

object BockInputDiff : DiffUtil.ItemCallback<InputDataItem>() {

    override fun areItemsTheSame(oldItem: InputDataItem, newItem: InputDataItem): Boolean {
        return oldItem.player == newItem.player
    }

    override fun areContentsTheSame(oldItem: InputDataItem, newItem: InputDataItem): Boolean {
        return oldItem.player == newItem.player &&
            oldItem.cards == newItem.cards &&
            oldItem.currentRound == newItem.currentRound &&
            oldItem.type == newItem.type
    }
}
