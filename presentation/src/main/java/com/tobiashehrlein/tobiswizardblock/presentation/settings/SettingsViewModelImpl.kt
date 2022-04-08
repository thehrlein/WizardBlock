package com.tobiashehrlein.tobiswizardblock.presentation.settings

import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.settings.GetDisplayAlwaysOnUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.settings.SetDisplayAlwaysOnUseCase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SettingsViewModelImpl(
    private val getDisplayAlwaysOnUseCase: GetDisplayAlwaysOnUseCase,
    private val setDisplayAlwaysOnUseCase: SetDisplayAlwaysOnUseCase
): SettingsViewModel() {

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
                is AppResult.Success -> Unit
                is AppResult.Error -> Unit
            }
        }
    }
}