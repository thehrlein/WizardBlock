package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.playerselection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.player.GetPlayerNamesUseCase
import kotlinx.coroutines.launch

class PlayerSelectionViewModelImpl(
    private val getPlayerNamesUseCase: GetPlayerNamesUseCase
) : PlayerSelectionViewModel() {

    override val playerNameOptions = MutableLiveData<Set<String>>()

    init {
        getPlayerNameOptionsSet()
    }

    private fun getPlayerNameOptionsSet() {
        viewModelScope.launch {
            when (val result = getPlayerNamesUseCase.invoke()) {
                is AppResult.Success -> playerNameOptions.value = result.value
                is AppResult.Error -> Unit
            }
        }
    }

    override fun onProceedClicked() {
        navigateTo(Page.PlayerSelection.PlayerOrder)
    }
}
