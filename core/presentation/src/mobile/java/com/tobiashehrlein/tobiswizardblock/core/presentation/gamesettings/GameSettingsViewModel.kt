package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings

import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.gameinfo.GetLastGameSettingsUseCase
import com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.playerorder.PlayerOrderInteractions

abstract class GameSettingsViewModel(
    getLastGameSettingsUseCase: GetLastGameSettingsUseCase
) : BaseGameSettingsViewModelImpl(
    getLastGameSettingsUseCase = getLastGameSettingsUseCase
), PlayerOrderInteractions
