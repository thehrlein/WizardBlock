package com.tobiashehrlein.tobiswizardblock.feature.block.scores

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScore
import com.tobiashehrlein.tobiswizardblock.feature.block.R
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater

class BlockScoresAdapter : ListAdapter<GameScore, BlockScoresViewHolder>(
    BlockScoreDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlockScoresViewHolder {
        return BlockScoresViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_block_score,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BlockScoresViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object BlockScoreDiff : DiffUtil.ItemCallback<GameScore>() {

    override fun areItemsTheSame(oldItem: GameScore, newItem: GameScore): Boolean =
        oldItem.player == newItem.player

    override fun areContentsTheSame(oldItem: GameScore, newItem: GameScore): Boolean =
        oldItem.points == newItem.points &&
            oldItem.position == newItem.position
}
