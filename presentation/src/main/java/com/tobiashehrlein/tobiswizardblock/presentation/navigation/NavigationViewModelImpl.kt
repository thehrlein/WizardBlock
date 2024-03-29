package com.tobiashehrlein.tobiswizardblock.presentation.navigation

import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.entities.tracking.UserProperty
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockUserProperty
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.general.TrackAnalyticsUserPropertyUseCase
import kotlinx.coroutines.launch

class NavigationViewModelImpl(
    private val trackAnalyticsUserPropertyUseCase: TrackAnalyticsUserPropertyUseCase
) : NavigationViewModel() {

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

    override fun trackSystemAppearanceUserProperty(systemAppearance: String) {
        viewModelScope.launch {
            trackAnalyticsUserPropertyUseCase.invoke(
                WizardBlockUserProperty(
                    userProperty = UserProperty.SYSTEM_APPEARANCE,
                    value = systemAppearance
                )
            )
        }
    }
}
