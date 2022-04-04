package com.tobiashehrlein.tobiswizardblock.presentation.savedgames

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.entities.savedgames.SavedGameEntity
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.savedgames.DeleteAllSavedGamesUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.savedgames.DeleteSavedGameUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.savedgames.GetAllSavedGamesUseCase
import kotlinx.coroutines.launch

class SavedGamesViewModelImpl(
    private val getAllSavedGamesUseCase: GetAllSavedGamesUseCase,
    private val deleteSavedGameUseCase: DeleteSavedGameUseCase,
    private val deleteAllSavedGamesUseCase: DeleteAllSavedGamesUseCase
) : SavedGamesViewModel() {

    override val savedGames = MutableLiveData<List<SavedGameEntity>>()
    override val noSavedGames = MutableLiveData<Boolean>()

    init {
        getAllSavedGames()
    }

    private fun getAllSavedGames() {
        viewModelScope.launch {
            when (val result = getAllSavedGamesUseCase.invoke()) {
                is AppResult.Success -> convertGames(result.value)
                is AppResult.Error -> noSavedGames.value = true
            }
        }
    }

    private fun convertGames(games: List<Game>) {
        val continuableGames = games
            .map {
                SavedGameEntity(
                    gameId = it.gameInfo.gameId,
                    gameStartDate = it.gameInfo.gameStartDate,
                    gameName = it.gameInfo.gameName,
                    players = it.gameInfo.players,
                    gameSettings = it.gameInfo.gameSettings,
                    currentRound = it.currentRoundNumber,
                    maxRound = it.maxRound,
                    gameFinished = it.gameInfo.gameFinished
                )
            }

        savedGames.value = continuableGames
        noSavedGames.value = continuableGames.isEmpty()
    }

    override fun onGameRemoved(item: SavedGameEntity?) {
        item?.gameId?.let {
            viewModelScope.launch {
                deleteSavedGameUseCase.invoke(it)
                getAllSavedGames()
            }
        }
    }

    override fun onSavedGameClicked(gameId: Long) {
        navigateTo(Page.SavedGames.ContinueGame(gameId))
    }

    override fun onInfoClicked(gameSettings: GameSettings) {
        navigateTo(Page.SavedGames.Info(gameSettings))
    }

    override fun onDeleteActionClicked() {
        navigateTo(Page.SavedGames.Delete)
    }

    override fun onDeleteGamesConfirmed() {
        viewModelScope.launch {
            when (val result = deleteAllSavedGamesUseCase.invoke()) {
                is AppResult.Success -> getAllSavedGames()
                is AppResult.Error -> Unit
            }
        }
    }
}