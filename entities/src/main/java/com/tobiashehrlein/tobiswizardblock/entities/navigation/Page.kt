package com.tobiashehrlein.tobiswizardblock.entities.navigation

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.entities.game.general.PlayerTipData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScore
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScoreData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType

sealed class Page {

    sealed class General : Page() {
        sealed class Loading : General() {
            class Show(val dim: Boolean) : Loading()
            object Hide : Loading()
        }
    }

    sealed class Navigation : Page() {
        object GameSettings : Navigation()
        object LastGames : Navigation()
        object Info : Navigation()
        object Settings : Navigation()
    }

    sealed class PlayerSelection : Page() {
        object PlayerOrder : PlayerSelection()
    }

    sealed class PlayerOrder : Page() {
        object GameRules : PlayerOrder()
        object Info : PlayerOrder()
    }

    sealed class GameRules : Page() {
        class Block(val gameId: Long) : GameRules()
        object Info : GameRules()
        object TipsEqualStitchesInfo : GameRules()
        object TipsEqualStitchesInfoFirstRound : GameRules()
        object AnniversaryVersion : GameRules()
    }

    sealed class Block : Page() {
        class Input(val gameId: Long, val inputType: InputType) : Block()
        object Exit : Block()
        object Menu : Block()
        object About : Block()
        object Settings : Block()
        class Trump(val trumpType: TrumpType) : Block()
        class Scores(val gameScoreData: GameScoreData) : Block()
        class GameFinished(val winners: List<GameScore>) : Block()
    }

    sealed class Input : Page() {
        object Block : Input()
        class Info(
            val inputType: InputType,
            val bombPlayed: Boolean,
            val round: Int,
            val gameSettings: GameSettings
        ) : Input()

        class CorrectTipsBecauseOfCloudCard(
            val playerTipData: List<PlayerTipData>,
            val round: Int
        ) : Input()

        object BombPlayed : Input()
    }

    sealed class SavedGames : Page() {
        class ContinueGame(val gameId: Long) : SavedGames()
        class Info(val gameSettings: GameSettings) : SavedGames()
        object Delete : SavedGames()
    }

    sealed class Settings : Page() {
        object DialogDisplayAlwaysOn : Settings()
    }
}
