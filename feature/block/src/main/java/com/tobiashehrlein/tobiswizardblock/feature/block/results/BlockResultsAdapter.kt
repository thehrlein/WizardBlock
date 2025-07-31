package com.tobiashehrlein.tobiswizardblock.feature.block.results

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.BlockItem
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.BlockResult
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.BlockRound
import com.tobiashehrlein.tobiswizardblock.feature.block.R
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater

class BlockResultsAdapter : ListAdapter<BlockItem, RecyclerView.ViewHolder>(
    BlockResultsDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.context.layoutInflater

        return when (viewType) {
            R.layout.item_block_round -> {
                BlockRoundViewHolder(
                    DataBindingUtil.inflate(inflater, viewType, parent, false)
                )
            }
            R.layout.item_block_result -> {
                BlockResultViewHolder(
                    DataBindingUtil.inflate(inflater, viewType, parent, false)
                )
            }
            else -> throw IllegalStateException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (val item = getItem(position)) {
            is BlockRound -> R.layout.item_block_round
            is BlockResult -> R.layout.item_block_result
            else -> throw IllegalStateException("Unknown item type: $item")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is BlockRoundViewHolder -> {
                if (item is BlockRound) {
                    holder.bind(item)
                }
            }
            is BlockResultViewHolder -> {
                if (item is BlockResult) {
                    holder.bind(item)
                }
            }
        }
    }
}

object BlockResultsDiff : DiffUtil.ItemCallback<BlockItem>() {

    override fun areItemsTheSame(oldItem: BlockItem, newItem: BlockItem) = when {
        oldItem is BlockRound && newItem is BlockRound && oldItem.round == newItem.round -> true
        oldItem is BlockResult && newItem is BlockResult && oldItem.round == newItem.round &&
            oldItem.player == newItem.player -> true
        else -> false
    }

    override fun areContentsTheSame(oldItem: BlockItem, newItem: BlockItem) = when {
        oldItem is BlockRound && newItem is BlockRound && oldItem.round == newItem.round -> true
        oldItem is BlockResult && newItem is BlockResult &&
            oldItem.round == newItem.round &&
            oldItem.player == newItem.player &&
            oldItem.tip == newItem.tip &&
            oldItem.result == newItem.result &&
            oldItem.difference == newItem.difference &&
            oldItem.total == newItem.total -> true
        else -> false
    }
}
