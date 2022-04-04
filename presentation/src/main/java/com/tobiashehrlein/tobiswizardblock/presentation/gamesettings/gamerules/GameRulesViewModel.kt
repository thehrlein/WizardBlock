package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.gamerules

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class GameRulesViewModel : BaseToolbarViewModelImpl() {

    abstract val gameName: LiveData<String>
    abstract val gameNameOptions: LiveData<Set<String>>
    abstract val gameSettings: LiveData<GameSettings>

    abstract fun setTipsEqualStitches(enabled: Boolean)
    abstract fun setTipsEqualStitchesFirstRound(enabled: Boolean)
    abstract fun setAnniversaryVersion(enabled: Boolean)
    abstract fun setGameSettings(gameSettings: GameSettings)
    abstract fun setGameName(gameName: String)
    abstract fun onProceedClicked(gameName: String, playerNames: List<String>)
    abstract fun onInfoIconClicked()
    abstract fun onSettingsEqualStitchesInfoIconClicked()
    abstract fun onSettingsEqualStitchesFirstRoundInfoIconClicked()
    abstract fun onSettingsAnniversaryVersionInfoIconClicked()
}