package com.tobiashehrlein.tobiswizardblock.ui_saved_games

import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.entities.savedgames.SavedGameEntity
import com.tobiashehrlein.tobiswizardblock.presentation.savedgames.SavedGamesInteractions
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.executeAfter
import com.tobiashehrlein.tobiswizardblock.ui_saved_games.databinding.ItemSavedGameBinding

class SavedGameViewHolder(val binding: ItemSavedGameBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: SavedGameEntity,
        interactions: SavedGamesInteractions
    ) {
        binding.executeAfter {
            this.item = item
        }

        binding.root.setOnClickListener {
            interactions.onSavedGameClicked(item.gameId)
        }

        binding.gameSettingsIcon.setOnClickListener {
            interactions.onInfoClicked(item.gameSettings)
        }
    }
}