package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.gamerules

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class GameRulesViewModel : BaseToolbarViewModelImpl() {

    abstract val gameNameOptions: LiveData<Set<String>>

    abstract fun onProceedClicked(
        gameName: String, playerNames: List<String>,
        gameSettings: GameSettings
    )

    abstract fun onInfoIconClicked()
    abstract fun onSettingsEqualStitchesInfoIconClicked()
    abstract fun onSettingsEqualStitchesFirstRoundInfoIconClicked()
    abstract fun onSettingsAnniversaryVersionInfoIconClicked()
}
