package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.playerorder

import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.Page

class PlayerOrderViewModelImpl : PlayerOrderViewModel() {

    override fun onProceedClicked() {
        navigateTo(Page.PlayerOrder.GameRules)
    }

    override fun onInfoIconClicked() {
        navigateTo(Page.PlayerOrder.Info)
    }
}
