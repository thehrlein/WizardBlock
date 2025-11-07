package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings

import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.gameinfo.GetLastGameSettingsUseCase

abstract class GameSettingsViewModel(
    getLastGameSettingsUseCase: GetLastGameSettingsUseCase
): BaseGameSettingsViewModelImpl(
    getLastGameSettingsUseCase = getLastGameSettingsUseCase
)