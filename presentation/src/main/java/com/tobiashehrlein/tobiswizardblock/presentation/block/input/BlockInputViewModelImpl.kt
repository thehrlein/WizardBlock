package com.tobiashehrlein.tobiswizardblock.presentation.block.input

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tobiashehrlein.tobiswizardblock.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameRound
import com.tobiashehrlein.tobiswizardblock.entities.game.general.InsertRoundData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.PlayerResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.general.PlayerTipData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.CalculateResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.CheckInputValidityData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputDataItem
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.game.input.ResultData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.entities.general.AppResult
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.GetGameUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input.CalculateResultsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input.GetBlockInputModelsUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input.InputsValidUseCase
import com.tobiashehrlein.tobiswizardblock.interactor.usecase.block.input.StoreRoundUseCase
import kotlinx.coroutines.launch

private const val DEFAULT_BOMB_PLAYED = false

class BlockInputViewModelImpl(
    private val gameId: Long,
    private val getGameUseCase: GetGameUseCase,
    private val getBlockInputModelsUseCase: GetBlockInputModelsUseCase,
    private val inputsValidUseCase: InputsValidUseCase,
    private val storeRoundUseCase: StoreRoundUseCase,
    private val calculateResultsUseCase: CalculateResultsUseCase
) : BlockInputViewModel() {

    override val inputType = MutableLiveData<InputType>()
    override val inputModels = MutableLiveData<List<InputDataItem>>()
    override val game = MutableLiveData<Game>()
    override val inputsValid = MutableLiveData<Boolean>()
    override val showAnniversaryOption = MutableLiveData<Boolean>()
    override val summedInputs = MutableLiveData<Int>()
    override val trumpType = MutableLiveData<TrumpType>()
    override val bombPlayed = MutableLiveData(DEFAULT_BOMB_PLAYED)
    override val playerTipDataCorrectedEvent = MutableLiveData<PlayerTipData>()
    private val round = MutableLiveData<GameRound>()

    init {
        getCurrentGame()
    }

    private fun getCurrentGame() {
        viewModelScope.launch {
            when (val result = getGameUseCase.invoke(gameId)) {
                is AppResult.Success -> setInputModels(result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun setInputModels(game: Game) {
        this.game.value = game
        this.trumpType.value = game.currentGameRound?.trumpType
        viewModelScope.launch {
            when (val result = getBlockInputModelsUseCase.invoke(game)) {
                is AppResult.Success -> {
                    round.value = result.value.currentGameRound
                    inputType.value = result.value.inputType
                    inputModels.value = result.value.inputModels
                    showAnniversaryOption.value = game.gameInfo.gameSettings.anniversaryVersion &&
                        result.value.inputType == InputType.RESULT
                }
                is AppResult.Error -> Unit
            }
        }
    }

    override fun onInputChanged() {
        val bombPlayed = this.bombPlayed.value ?: DEFAULT_BOMB_PLAYED
        val data = CheckInputValidityData(getGameData(), bombPlayed, getInputs().sumOf { it.userInput })

        summedInputs.value = data.inputSum

        viewModelScope.launch {
            inputsValid.postValue(
                when (val result = inputsValidUseCase.invoke(data)) {
                    is AppResult.Success -> result.value
                    is AppResult.Error -> false
                }
            )
        }
    }

    override fun onInfoIconClicked() {
        val game = getGameData()
        navigateTo(
            Page.Input.Info(
                inputType = game.inputType,
                round = game.currentRoundNumber,
                gameSettings = game.gameInfo.gameSettings
            )
        )
    }

    override fun onCorrectTipsClicked() {
        val game = getGameData()
        val playerTipData = game.currentGameRound?.playerTipData ?: error("no tips available")
        val round = game.currentRoundNumber
        navigateTo(Page.Input.CorrectTipsBecauseOfCloudCard(playerTipData, round))
    }

    override fun correctPlayerTips(correctedPlayerTipData: List<PlayerTipData>) {
        val currentRound = round.value ?: error("could not determine round")
        val round = InsertRoundData(
            gameId,
            currentRound.copy(
                playerTipData = correctedPlayerTipData
            )
        )

        val oldPlayerTipData = currentRound.playerTipData
        val correctPlayer = correctedPlayerTipData.firstOrNull { corrected ->
            oldPlayerTipData?.firstOrNull { it.playerName == corrected.playerName && it.tip != corrected.tip } != null
        } ?: error("no change detected")

        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> {
                    getCurrentGame()
                    playerTipDataCorrectedEvent.value = correctPlayer
                }
                is AppResult.Error -> Unit
            }
        }
    }

    override fun onSaveClicked() {
        val currentRound = round.value ?: error("could not determine round")
        val inputs = getInputs()
        if (currentRound.inputTypeForThisRound == InputType.TIPP) {
            storeTips(currentRound, inputs)
        } else {
            calculateResults(currentRound, inputs)
        }
    }

    private fun storeTips(
        currentGameRound: GameRound,
        inputItems: List<InputDataItem>
    ) {
        val round = InsertRoundData(
            gameId,
            currentGameRound.copy(
                playerTipData = inputItems.map {
                    PlayerTipData(
                        playerName = it.player,
                        tip = it.userInput
                    )
                }
            )
        )

        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> navigateTo(Page.Input.Block)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun calculateResults(currentGameRound: GameRound, inputItems: List<InputDataItem>) {
        val game = getGameData()
        val previousTotals = game.previousTotals
        val calculateResultData = CalculateResultData(
            resultData = inputItems.map { inputDataItem ->
                ResultData(
                    playerName = inputDataItem.player,
                    tip = currentGameRound.playerTipData?.first { it.playerName == inputDataItem.player }?.tip
                        ?: error("no tip for this round and player"),
                    result = inputDataItem.userInput,
                    previousTotal = previousTotals.first { it.playerName == inputDataItem.player }.input
                )
            }
        )
        viewModelScope.launch {
            when (val result = calculateResultsUseCase.invoke(calculateResultData)) {
                is AppResult.Success -> storeResults(currentGameRound, result.value)
                is AppResult.Error -> Unit
            }
        }
    }

    private fun storeResults(currentGameRound: GameRound, results: List<PlayerResultData>) {
        val round = InsertRoundData(
            gameId,
            currentGameRound.copy(
                playerResultData = results
            )
        )

        viewModelScope.launch {
            when (val result = storeRoundUseCase.invoke(round)) {
                is AppResult.Success -> navigateTo(Page.Input.Block)
                is AppResult.Error -> Unit
            }
        }
    }

    override fun onBlockInputBombPlayedInfoClicked() {
        navigateTo(Page.Input.BombPlayed)
    }

    override fun onBlockPlayedSwitchChanged(bombPlayed: Boolean) {
        this.bombPlayed.value = bombPlayed
        onInputChanged()
    }

    private fun getGameData() = game.value ?: error("could not determine game")

    private fun getInputs() = inputModels.value ?: error("could not determine input models")
}
