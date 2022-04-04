package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerorder

import androidx.lifecycle.MutableLiveData
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page

class PlayerOrderViewModelImpl : PlayerOrderViewModel() {

    override val playerNames = MutableLiveData<List<String>>()

    override fun onPlayerOrderChanged(names: List<String>) {
        playerNames.value = names
    }

    override fun onProceedClicked() {
        navigateTo(Page.PlayerOrder.GameRules)
    }

    override fun onInfoIconClicked() {
        navigateTo(Page.PlayerOrder.Info)
    }
}