package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.gamerules

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo.GetGameNameOptionsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo.StoreGameInfoUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import kotlinx.coroutines.launch

class GameRulesViewModelImpl(
    private val storeGameInfoUseCase: StoreGameInfoUseCase,
    private val getGameNameOptionsUseCase: GetGameNameOptionsUseCase
) : GameRulesViewModel() {

    override val gameNameOptions = MutableLiveData<Set<String>>(emptySet())

    init {
        getGameNameOptions()
    }

    private fun getGameNameOptions() {
        viewModelScope.launch {
            gameNameOptions.value = when (val result = getGameNameOptionsUseCase.invoke()) {
                is AppResult.Success -> result.value
                is AppResult.Error -> emptySet()
            }
        }
    }

    override fun onProceedClicked(
        gameName: String,
        playerNames: List<String>,
        gameSettings: GameSettings
    ) {
        viewModelScope.launch {
            val gameId = storeGameInfo(gameName, playerNames, gameSettings)
            navigateTo(Page.GameRules.Block(gameId))
        }
    }

    private suspend fun storeGameInfo(
        gameName: String,
        playerNames: List<String>,
        gameSettings: GameSettings
    ): Long {
        val gameInfo = GameInfo(
            players = playerNames,
            gameName = gameName,
            gameSettings = gameSettings
        )
        return when (val result = storeGameInfoUseCase.invoke(gameInfo)) {
            is AppResult.Success -> result.value
            is AppResult.Error -> error("gameId can't be null")
        }
    }

    override fun onInfoIconClicked() {
        navigateTo(Page.GameRules.Info)
    }

    override fun onSettingsEqualStitchesInfoIconClicked() {
        navigateTo(Page.GameRules.TipsEqualStitchesInfo)
    }

    override fun onSettingsEqualStitchesFirstRoundInfoIconClicked() {
        navigateTo(Page.GameRules.TipsEqualStitchesInfoFirstRound)
    }

    override fun onSettingsAnniversaryVersionInfoIconClicked() {
        navigateTo(Page.GameRules.AnniversaryVersion)
    }
}
