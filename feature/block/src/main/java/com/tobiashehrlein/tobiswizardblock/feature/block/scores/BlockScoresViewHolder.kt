package com.tobiashehrlein.tobiswizardblock.feature.block.scores

import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.GameScore
import com.tobiashehrlein.tobiswizardblock.feature.block.databinding.ItemBlockScoreBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.executeAfter

class BlockScoresViewHolder(private val binding: ItemBlockScoreBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: GameScore) {
        binding.executeAfter {
            this.gameScore = item
        }
    }
}
