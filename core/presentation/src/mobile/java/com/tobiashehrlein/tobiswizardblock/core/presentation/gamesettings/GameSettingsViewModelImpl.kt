package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings

import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.gameinfo.GetLastGameSettingsUseCase
import com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.playerorder.PlayerOrderInteractions

class GameSettingsViewModelImpl(
    getLastGameSettingsUseCase: GetLastGameSettingsUseCase,
) : GameSettingsViewModel(
    getLastGameSettingsUseCase = getLastGameSettingsUseCase
), PlayerOrderInteractions {

    override fun onPlayerOrderChanged(names: List<String>) {
        playerNames.value = names
    }
}
