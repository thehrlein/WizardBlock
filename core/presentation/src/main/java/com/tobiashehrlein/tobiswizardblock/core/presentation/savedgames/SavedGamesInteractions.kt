package com.tobiashehrlein.tobiswizardblock.core.presentation.savedgames

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameSettings

interface SavedGamesInteractions {

    fun onSavedGameClicked(gameId: Long)

    fun onInfoClicked(gameSettings: GameSettings)
}
