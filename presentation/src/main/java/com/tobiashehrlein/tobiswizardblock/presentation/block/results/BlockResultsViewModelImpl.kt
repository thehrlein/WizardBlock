package com.tobiashehrlein.tobiswizardblock.presentation.block.results

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.game.general.DeleteRoundData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.InsertRoundData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockItem
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockItemData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockPlaceholder
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockResult
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScore
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScoreData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.GetGameUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input.StoreRoundUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results.GetBlockResultsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results.GetGameScoresUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results.RemoveRoundUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.results.StoreGameFinishedUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.invoke
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.user.IsShowTrumpDialogEnabledUseCase
import com.tobiashehrlein.tobiswizardblock.presentation.general.SingleLiveEvent
import kotlinx.coroutines.launch

private const val WINNER_POSITION = 1

class BlockResultsViewModelImpl(
    private val getGameUseCase: GetGameUseCase,
    private val getBlockResultsUseCase: GetBlockResultsUseCase,
    private val getGameScoresUseCase: GetGameScoresUseCase,
    private val storeGameFinishedUseCase: StoreGameFinishedUseCase,
    private val storeRoundUseCase: StoreRoundUseCase,
    private val removeRoundUseCase: RemoveRoundUseCase,
    private val isShowTrumpDialogEnabledUseCase: IsShowTrumpDialogEnabledUseCase
) : BlockResultsViewModel() {

    private val game = MutableLiveData<Game>()
    override val gameName = MutableLiveData<String>()
    override val inputType = MutableLiveData<InputType>()
    private val gameScores = MediatorLiveData<GameScoreData>().also { mediator ->
        mediator.addSource(game) {
            viewModelScope.launch {
                when (val result = getGameScoresUseCase.invoke(it)) {
                    is AppResult.Success -> {
                        mediator.postValue(result.value)
                        if (result.value.gameFinished && result.value.winnerAlreadyShown.not()) {
                            onGameFinished(result.value.results)
                        }
                    }
                    is AppResult.Error -> Unit
                }
            }
        }
    }
    override val gameFinished = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(gameScores) {
            mediator.value = it.gameFinished
        }
    }
    override val columnCount = MutableLiveData<Int>()
    override val blockItems = MutableLiveData<List<BlockItem>>()
    override val showGameFinishedEvent = SingleLiveEvent<Unit>()
    override val editInputEnabled = MediatorLiveData<Boolean>().also { mediator ->
        mediator.addSource(blockItems) {
            mediator.postValue(
                it.filterIsInstance<BlockResult>().isNotEmpty() && gameFinished.value != false
            )
        }
        mediator.addSource(gameFinished) {
            mediator.postValue(
                it != true && blockItems.value?.filterIsInstance<BlockResult>()
                    ?.isNotEmpty() == true
            )
        }
    }

    override fun setGameId(gameId: Long) {
        viewModelScope.launch {
            val currentGame = getCurrentGame(gameId) ?: error("no game found for this gameId")
            game.value = currentGame
            gameName.value = currentGame.gameInfo.gameName
            getBlockItems(currentGame)
        }
    }

    private suspend fun getCurrentGame(gameId: Long): Game? {
        return when (val result = getGameUseCase.invoke(gameId)) {
            is AppResult.Success -> result.value
            is AppResult.Error -> null
        }
    }

    private suspend fun getBlockItems(currentGame: Game) {
        when (val result = getBlockResultsUseCase.invoke(currentGame)) {
            is AppResult.Success -> {
                columnCount.value = result.value.columnCount
                blockItems.value = result.value.items
                inputType.value = result.value.inputType
                showTrumpSelectionDialog(result.value)
            }
            is AppResult.Error -> Unit
        }
    }

    private fun showTrumpSelectionDialog(data: BlockItemData) {
        val placeHolderItem =
            data.items.firstOrNull { it is BlockPlaceholder } as? BlockPlaceholder ?: return
        if (data.inputType == InputType.TIPP && placeHolderItem.trumpType == TrumpType.Unselected) {
            viewModelScope.launch {
                when (val result = isShowTrumpDialogEnabledUseCase.invoke()) {
                    is AppResult.Success -> {
                        if (result.value) {
                            navigateTo(Page.Block.Trump(TrumpType.Unselected))
                        }
                    }
                    is AppResult.Error -> Unit
                }
            }
        }
    }

    override fun onTrumpClicked(trumpType: TrumpType) {
        if (gameFinished.value == true) return
        navigateTo(Page.Block.Trump(trumpType))
    }

    override fun onFabClicked() {
        val gameFinished = gameFinished.value ?: false
        if (gameFinished) {
            navigateTo(Page.Block.Exit)
        } else {
            val gameId = game.value?.gameInfo?.gameId ?: error("could not determine gameId")
            val inputType = this.inputType.value ?: error("could not determine inputType")
            navigateTo(Page.Block.Input(gameId, inputType))
        }
    }

    override fun onTrophyClicked() {
        val scores = gameScores.value ?: return
        navigateTo(Page.Block.Scores(scores))
    }

    private fun onGameFinished(results: List<GameScore>, onFinishedSuccess: (() -> Unit)? = null) {
        showGameFinishedEvent.value = Unit
        viewModelScope.launch {
            val gameId = game.value?.gameInfo?.gameId ?: return@launch
            when (val result = storeGameFinishedUseCase.invoke(gameId)) {
                is AppResult.Success -> {
                    navigateTo(Page.Block.GameFinished(results.filter { it.position == WINNER_POSITION }))
                    onFinishedSuccess?.invoke()
                }
                is AppResult.Error -> Unit
            }
        }
    }

    override fun updateTrumpType(trumpType: TrumpType) {
        val game = game.value ?: error("round not initialized - could not set trump type")
        val currentRound = game.currentGameRound ?: error("could not get current round")
        val round = InsertRoundData(
            gameId = game.gameInfo.gameId,
            gameRound = currentRound.copy(
                trumpType = trumpType
            )
        )
        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> setGameId(game.gameInfo.gameId)
                is AppResult.Error -> Unit
            }
        }
    }

    override fun onInfoClicked() {
        navigateTo(Page.Block.About)
    }

    override fun onDeleteInputClicked() {
        val game = game.value ?: error("round not initialized - could not set trump type")
        val currentRound = game.currentGameRound ?: return

        viewModelScope.launch {
            navigateTo(Page.General.Loading.Show(dim = true))

            val result = if (currentRound.playerTipData == null) {
                val round = game.lastPlayedGameRound?.copy(
                    playerResultData = emptyList()
                ) ?: return@launch

                storeRoundUseCase.invoke(InsertRoundData(game.gameInfo.gameId, round))
            } else {
                removeRoundUseCase.invoke(DeleteRoundData(game.gameInfo.gameId, currentRound))
            }

            when (result) {
                is AppResult.Success -> setGameId(game.gameInfo.gameId)
                is AppResult.Error -> Unit
            }

            navigateTo(Page.General.Loading.Hide)
        }
    }

    override fun showExitDialog() {
        navigateTo(Page.Block.Exit)
    }
}
