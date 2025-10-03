package com.tobiashehrlein.tobiswizardblock.core.presentation.navigation

import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.general.TrackAnalyticsUserPropertyUseCase

abstract class NavigationViewModel(
    trackAnalyticsUserPropertyUseCase: TrackAnalyticsUserPropertyUseCase
) : BaseNavigationViewModelImpl(trackAnalyticsUserPropertyUseCase) {
    abstract fun onInfoClicked()
    abstract fun onSettingsClicked()
}
