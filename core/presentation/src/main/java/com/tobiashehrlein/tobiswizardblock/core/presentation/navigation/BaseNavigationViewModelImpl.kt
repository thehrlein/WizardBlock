package com.tobiashehrlein.tobiswizardblock.core.presentation.navigation

import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.BasePage
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.UserProperty
import com.tobiashehrlein.tobiswizardblock.core.entities.tracking.WizardBlockUserProperty
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.general.TrackAnalyticsUserPropertyUseCase
import kotlinx.coroutines.launch

abstract class BaseNavigationViewModelImpl(
    private val trackAnalyticsUserPropertyUseCase: TrackAnalyticsUserPropertyUseCase
) : BaseNavigationViewModel() {

    override fun onNewGameClicked() {
        navigateTo(BasePage.BaseNavigation.GameSettings)
    }

    override fun onLoadGamesClicked() {
        navigateTo(BasePage.BaseNavigation.LastGames)
    }

    override fun onStatisticsClicked() {
        navigateTo(BasePage.BaseNavigation.Statistics)
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
