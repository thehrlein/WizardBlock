package com.tobiashehrlein.tobiswizardblock.core.presentation.settings

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingEvent
import com.tobiashehrlein.tobiswizardblock.entities.tracking.TrackingParam
import com.tobiashehrlein.tobiswizardblock.entities.tracking.WizardBlockTrackingEvent
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.general.TrackAnalyticsEventUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.settings.GetDisplayAlwaysOnUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.settings.SetDisplayAlwaysOnUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModelImpl(
    private val getDisplayAlwaysOnUseCase: GetDisplayAlwaysOnUseCase,
    private val setDisplayAlwaysOnUseCase: SetDisplayAlwaysOnUseCase,
    private val trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase
) : SettingsViewModel() {

    override val displayAlwaysOn = getDisplayAlwaysOnUseCase.invoke().map {
        when (it) {
            is AppResult.Success -> it.value
            is AppResult.Error -> false
        }
    }.asLiveData()

    override fun onDisplayAlwaysOnInfoClicked() {
        navigateTo(Page.Settings.DialogDisplayAlwaysOn)
    }

    override fun onDisplayAlwaysOnChecked(alwaysOn: Boolean) {
        viewModelScope.launch {
            when (val result = setDisplayAlwaysOnUseCase.invoke(alwaysOn)) {
                is AppResult.Success -> trackDisplayAlwaysOnEvent(alwaysOn)
                is AppResult.Error -> Unit
            }
        }
    }

    private suspend fun trackDisplayAlwaysOnEvent(alwaysOn: Boolean) {
        trackAnalyticsEventUseCase.invoke(
            WizardBlockTrackingEvent(
                eventName = TrackingEvent.DISPLAY_ALWAYS_ON,
                params = mapOf(
                    TrackingParam.ALWAYS_ON to alwaysOn
                )
            )
        )
    }
}
