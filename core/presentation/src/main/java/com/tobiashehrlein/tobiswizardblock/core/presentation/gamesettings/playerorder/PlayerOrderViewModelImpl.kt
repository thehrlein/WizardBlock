package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.playerorder

import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.BasePage

class PlayerOrderViewModelImpl : PlayerOrderViewModel() {

    override fun onProceedClicked() {
        navigateTo(BasePage.PlayerOrder.GameRules)
    }

    override fun onInfoIconClicked() {
        navigateTo(BasePage.PlayerOrder.Info)
    }
}
