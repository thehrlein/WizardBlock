package com.tobiashehrlein.tobiswizardblock.core.presentation.navigation

import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.general.TrackAnalyticsUserPropertyUseCase

class NavigationViewModelImpl(
    private val trackAnalyticsUserPropertyUseCase: TrackAnalyticsUserPropertyUseCase
) : NavigationViewModel(trackAnalyticsUserPropertyUseCase) {

    override fun onInfoClicked() {
        navigateTo(Page.Navigation.Info)
    }

    override fun onSettingsClicked() {
        navigateTo(Page.Navigation.Settings)
    }
}
