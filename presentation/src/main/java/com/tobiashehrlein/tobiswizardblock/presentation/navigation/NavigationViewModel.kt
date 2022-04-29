package com.tobiashehrlein.tobiswizardblock.presentation.navigation

import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModelImpl

abstract class NavigationViewModel : BaseViewModelImpl() {

    abstract fun onNewGameClicked()
    abstract fun onLoadGamesClicked()
    abstract fun onInfoClicked()
    abstract fun onSettingsClicked()
    abstract fun onStatisticsClicked()
    abstract fun trackSystemAppearanceUserProperty(systemAppearance: String)
}
