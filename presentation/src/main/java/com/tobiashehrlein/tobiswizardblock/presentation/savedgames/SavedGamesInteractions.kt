package com.tobiashehrlein.tobiswizardblock.presentation.savedgames

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings

interface SavedGamesInteractions {

    fun onSavedGameClicked(gameId: Long)

    fun onInfoClicked(gameSettings: GameSettings)
}
