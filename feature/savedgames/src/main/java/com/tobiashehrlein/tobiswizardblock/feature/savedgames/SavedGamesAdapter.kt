package com.tobiashehrlein.tobiswizardblock.feature.savedgames

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.tobiashehrlein.tobiswizardblock.entities.savedgames.SavedGameEntity
import com.tobiashehrlein.tobiswizardblock.core.presentation.savedgames.SavedGamesInteractions
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater

class SavedGamesAdapter(
    private val savedGamesInteractions: SavedGamesInteractions
) : ListAdapter<SavedGameEntity, SavedGameViewHolder>(
    SavedGameDiff
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedGameViewHolder {
        return SavedGameViewHolder(
            DataBindingUtil.inflate(
                parent.context.layoutInflater,
                R.layout.item_saved_game,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SavedGameViewHolder, position: Int) {
        holder.bind(getItem(position), savedGamesInteractions)
    }
}

object SavedGameDiff : DiffUtil.ItemCallback<SavedGameEntity>() {

    override fun areItemsTheSame(oldItem: SavedGameEntity, newItem: SavedGameEntity): Boolean =
        oldItem.gameId == newItem.gameId

    override fun areContentsTheSame(oldItem: SavedGameEntity, newItem: SavedGameEntity): Boolean =
        oldItem.players == newItem.players &&
            oldItem.currentRound == newItem.currentRound &&
            oldItem.maxRound == newItem.maxRound
}
