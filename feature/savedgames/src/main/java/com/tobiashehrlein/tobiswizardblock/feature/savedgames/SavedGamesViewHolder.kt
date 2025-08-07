package com.tobiashehrlein.tobiswizardblock.feature.savedgames

import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.core.entities.savedgames.SavedGameEntity
import com.tobiashehrlein.tobiswizardblock.core.presentation.savedgames.SavedGamesInteractions
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.executeAfter
import com.tobiashehrlein.tobiswizardblock.feature.savedgames.databinding.ItemSavedGameBinding

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
