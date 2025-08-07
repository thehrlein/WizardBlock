package com.tobiashehrlein.tobiswizardblock.feature.block.results

import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.BlockRound
import com.tobiashehrlein.tobiswizardblock.feature.block.databinding.ItemBlockRoundBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.executeAfter

class BlockRoundViewHolder(private val binding: ItemBlockRoundBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: BlockRound) {
        binding.executeAfter {
            this.item = item
        }
    }
}
