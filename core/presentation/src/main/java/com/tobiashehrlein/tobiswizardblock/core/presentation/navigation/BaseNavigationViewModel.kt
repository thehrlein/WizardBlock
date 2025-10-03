package com.tobiashehrlein.tobiswizardblock.core.presentation.navigation

import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseViewModelImpl

abstract class BaseNavigationViewModel : BaseViewModelImpl() {

    abstract fun onNewGameClicked()
    abstract fun onLoadGamesClicked()
    abstract fun onStatisticsClicked()
    abstract fun trackSystemAppearanceUserProperty(systemAppearance: String)
}
