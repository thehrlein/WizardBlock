package com.tobiashehrlein.tobiswizardblock.ui_block.results

import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockResult
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.ItemBlockResultBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.executeAfter

class BlockResultViewHolder(private val binding: ItemBlockResultBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: BlockResult) {
        binding.executeAfter {
            this.item = item
        }
    }
}
