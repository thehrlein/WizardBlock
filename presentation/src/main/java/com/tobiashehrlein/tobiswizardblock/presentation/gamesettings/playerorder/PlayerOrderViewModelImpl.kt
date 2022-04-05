package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerorder

import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page

class PlayerOrderViewModelImpl : PlayerOrderViewModel() {

    override fun onProceedClicked() {
        navigateTo(Page.PlayerOrder.GameRules)
    }

    override fun onInfoIconClicked() {
        navigateTo(Page.PlayerOrder.Info)
    }
}
