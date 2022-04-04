package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class GameSettingsViewModel : BaseToolbarViewModelImpl() {

    abstract val playerNames: LiveData<List<String>>
    abstract val gameName: LiveData<String>
    abstract val gameSettings: LiveData<GameSettings>

    abstract fun setPlayerNames(playerNames: List<String>)
    abstract fun setGameName(gameName: String)
    abstract fun setGameSettings(gameSettings: GameSettings)
}
