package com.tobiashehrlein.tobiswizardblock.presentation.navigation

import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page

class NavigationViewModelImpl : NavigationViewModel() {

    override fun onNewGameClicked() {
        navigateTo(Page.Navigation.GameSettings)
    }

    override fun onLoadGamesClicked() {
        navigateTo(Page.Navigation.LastGames)
    }

    override fun onInfoClicked() {
        navigateTo(Page.Navigation.Info)
    }

    override fun onSettingsClicked() {
        navigateTo(Page.Navigation.Settings)
    }

    override fun onStatisticsClicked() {
        navigateTo(Page.Navigation.Statistics)
    }
}
