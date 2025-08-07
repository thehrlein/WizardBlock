package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.core.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.gameinfo.GetLastGameSettingsUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.invoke
import kotlinx.coroutines.launch

private const val DEFAULT_PLAYER_COUNT = 3
private const val MIN_PLAYER_COUNT = 2
private const val DEFAULT_GAME_NAME = ""

class GameSettingsViewModelImpl(
    private val getLastGameSettingsUseCase: GetLastGameSettingsUseCase,
) : GameSettingsViewModel() {

    override val playerNames = MutableLiveData<List<String>>()
    override val playerCount = MutableLiveData<Int>()
    override val gameName = MutableLiveData<String>()
    override val gameSettings = MutableLiveData<GameSettings>()

    init {
        getLastGameSettings()
    }

    private fun getLastGameSettings() {
        viewModelScope.launch {
            when (val result = getLastGameSettingsUseCase.invoke()) {
                is AppResult.Success -> {
                    val gameInfo = result.value
                    if (gameInfo == null) {
                        setDefaults()
                    } else {
                        setPlayerNames(gameInfo.players)
                        setGameName(gameInfo.gameName)
                        setGameSettings(gameInfo.gameSettings)
                        setPlayerCount(gameInfo.players.size)
                    }
                }
                is AppResult.Error -> setDefaults()
            }
        }
    }

    private fun setDefaults() {
        setPlayerNames(emptyList())
        setGameName(DEFAULT_GAME_NAME)
        setGameSettings(GameSettings.Default)
        setPlayerCount(DEFAULT_PLAYER_COUNT)
    }

    // PlayerSelectionFragment
    override fun setPlayerCount(playerCount: Int) {
        if (playerCount >= MIN_PLAYER_COUNT) {
            this.playerCount.value = playerCount
        }
    }

    override fun setPlayerNames(playerNames: List<String>) {
        this.playerNames.value = playerNames
    }

    // / PlayerOrderFragment
    override fun onPlayerOrderChanged(names: List<String>) {
        playerNames.value = names
    }

    // GameRulesFragment
    override fun setGameName(gameName: String) {
        this.gameName.value = gameName
    }

    override fun setGameSettings(gameSettings: GameSettings) {
        this.gameSettings.value = gameSettings
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
}
