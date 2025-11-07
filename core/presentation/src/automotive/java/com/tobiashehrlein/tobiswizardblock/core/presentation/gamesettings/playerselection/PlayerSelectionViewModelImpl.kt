package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.playerselection

import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.player.GetPlayerNamesUseCase

class PlayerSelectionViewModelImpl(getPlayerNamesUseCase: GetPlayerNamesUseCase
) : PlayerSelectionViewModel(
    getPlayerNamesUseCase = getPlayerNamesUseCase
) {

    override fun onProceedClicked() {
        navigateTo(Page.PlayerSelection.GameRules)
    }
}
