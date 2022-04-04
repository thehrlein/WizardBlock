package com.tobiashehrlein.tobiswizardblock.ui_block.results

import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockRound
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.ItemBlockRoundBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.executeAfter

class BlockRoundViewHolder(private val binding: ItemBlockRoundBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BlockRound) {
        binding.executeAfter {
            this.item = item
        }
    }
}