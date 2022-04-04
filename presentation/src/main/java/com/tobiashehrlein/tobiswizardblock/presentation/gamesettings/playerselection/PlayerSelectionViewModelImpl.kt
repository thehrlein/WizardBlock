package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerselection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.player.GetPlayerNamesUseCase
import kotlinx.coroutines.launch

private const val DEFAULT_PLAYER_COUNT = 3
private const val MIN_PLAYER_COUNT = 3

class PlayerSelectionViewModelImpl(
    private val getPlayerNamesUseCase: GetPlayerNamesUseCase
) : PlayerSelectionViewModel() {

    override val playerNameOptions = MutableLiveData<Set<String>>()
    override val playerCount = MutableLiveData(DEFAULT_PLAYER_COUNT)
    override val playerNames = MutableLiveData<List<String>>()

    init {
        getPlayerNameOptionsSet()
    }

    override fun setPlayerCount(playerCount: Int) {
        if (playerCount >= MIN_PLAYER_COUNT) {
            this.playerCount.value = playerCount
        }
    }

    override fun setPlayerNames(playerNames: List<String>) {
        this.playerNames.value = playerNames
    }

    override fun onProceedClicked(inputs: List<Pair<Int, String?>>?) {
        if (inputs != null) {
            val names = inputs.mapNotNull { it.second }
            playerNames.value = names
            navigateTo(Page.PlayerSelection.PlayerOrder)
        }
    }

    private fun getPlayerNameOptionsSet() {
        viewModelScope.launch {
            when (val result = getPlayerNamesUseCase.invoke()) {
                is AppResult.Success -> playerNameOptions.value = result.value
                is AppResult.Error -> Unit
            }
        }
    }
}
