package com.tobiashehrlein.tobiswizardblock.feature.block.results

import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockResult
import com.tobiashehrlein.tobiswizardblock.feature.block.databinding.ItemBlockResultBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.executeAfter

class BlockResultViewHolder(private val binding: ItemBlockResultBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BlockResult) {
        binding.executeAfter {
            this.item = item
        }
    }
}
