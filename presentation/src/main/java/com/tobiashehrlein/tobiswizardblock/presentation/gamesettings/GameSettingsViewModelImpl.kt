package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.gameinfo.GetLastGameSettingsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import kotlinx.coroutines.launch

class GameSettingsViewModelImpl(
    private val getLastGameSettingsUseCase: GetLastGameSettingsUseCase
) : GameSettingsViewModel() {

    override val playerNames = MutableLiveData<List<String>>()
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
                    }
                }
                is AppResult.Error -> setDefaults()
            }
        }
    }

    private fun setDefaults() {
        setPlayerNames(emptyList())
        setGameName("")
        setGameSettings(GameSettings.Default)
    }

    override fun setPlayerNames(playerNames: List<String>) {
        if (this.playerNames.value != playerNames) {
            this.playerNames.value = playerNames
        }
    }

    override fun setGameName(gameName: String) {
        if (this.gameName.value != gameName) {
            this.gameName.value = gameName
        }
    }

    override fun setGameSettings(gameSettings: GameSettings) {
        if (this.gameSettings.value != gameSettings) {
            this.gameSettings.value = gameSettings
        }
    }
}
