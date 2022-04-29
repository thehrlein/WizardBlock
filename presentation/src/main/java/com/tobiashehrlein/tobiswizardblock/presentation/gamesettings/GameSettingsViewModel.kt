package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerorder.PlayerOrderInteractions
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class GameSettingsViewModel : BaseToolbarViewModelImpl(), PlayerOrderInteractions {

    abstract val playerNames: LiveData<List<String>>
    abstract val playerCount: LiveData<Int>
    abstract val gameName: LiveData<String>
    abstract val gameSettings: LiveData<GameSettings>

    abstract fun setPlayerNames(playerNames: List<String>)
    abstract fun setPlayerCount(playerCount: Int)
    abstract fun setGameName(gameName: String)
    abstract fun setGameSettings(gameSettings: GameSettings)
    abstract fun setTipsEqualStitches(enabled: Boolean)
    abstract fun setTipsEqualStitchesFirstRound(enabled: Boolean)
    abstract fun setAnniversaryVersion(enabled: Boolean)

}
