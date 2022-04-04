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

    override val gameName = MutableLiveData<String>()
    override val gameNameOptions = MutableLiveData<Set<String>>(emptySet())
    override val gameSettings = MutableLiveData<GameSettings>()

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

    override fun setTipsEqualStitches(enabled: Boolean) {
        gameSettings.value = gameSettings.value?.copy(
            tipsEqualStitches = enabled
        )
    }

    override fun setTipsEqualStitchesFirstRound(enabled: Boolean) {
        gameSettings.value = gameSettings.value?.copy(
            tipsEqualStitchesFirstRound = enabled
        )
    }

    override fun setAnniversaryVersion(enabled: Boolean) {
        gameSettings.value = gameSettings.value?.copy(
            anniversaryVersion = enabled
        )
    }

    override fun setGameSettings(gameSettings: GameSettings) {
        this.gameSettings.value = gameSettings
    }

    override fun setGameName(gameName: String) {
        this.gameName.value = gameName
    }

    override fun onProceedClicked(gameName: String, playerNames: List<String>) {
        viewModelScope.launch {
            val gameSettings = gameSettings.value ?: return@launch
            storeGameInfo(gameName, playerNames, gameSettings)
        }
    }

    private suspend fun storeGameInfo(
        gameName: String,
        playerNames: List<String>,
        gameSettings: GameSettings
    ) {
        val gameInfo = GameInfo(
            players = playerNames,
            gameName = gameName,
            gameSettings = gameSettings
        )
        when (val result = storeGameInfoUseCase.invoke(gameInfo)) {
            is AppResult.Success -> navigateTo(Page.GameRules.Block(result.value))
            is AppResult.Error -> Unit
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
