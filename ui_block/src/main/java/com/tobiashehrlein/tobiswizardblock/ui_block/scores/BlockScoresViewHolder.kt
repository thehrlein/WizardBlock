package com.tobiashehrlein.tobiswizardblock.ui_block.scores

import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScore
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.ItemBlockScoreBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.executeAfter

class BlockScoresViewHolder(private val binding: ItemBlockScoreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GameScore) {
        binding.executeAfter {
            this.gameScore = item
        }
    }
}
