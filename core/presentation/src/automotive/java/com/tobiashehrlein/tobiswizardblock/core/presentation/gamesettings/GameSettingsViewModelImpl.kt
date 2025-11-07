package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings

import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.gameinfo.GetLastGameSettingsUseCase

class GameSettingsViewModelImpl(
    getLastGameSettingsUseCase: GetLastGameSettingsUseCase,
) : GameSettingsViewModel(
    getLastGameSettingsUseCase = getLastGameSettingsUseCase
)