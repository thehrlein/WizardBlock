package com.tobiashehrlein.tobiswizardblock.core.entities.navigation

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.PlayerTipData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.GameScore
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.GameScoreData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.TrumpType

sealed class BasePage {

    sealed class General : BasePage() {
        sealed class Loading : General() {
            class Show(val dim: Boolean) : Loading()
            object Hide : Loading()
        }
    }

    sealed class BaseNavigation : BasePage() {
        object GameSettings : BaseNavigation()
        object LastGames : BaseNavigation()
        object Statistics : BaseNavigation()
    }

    sealed class GameRules : BasePage() {
        class Block(val gameId: Long) : GameRules()
        object Info : GameRules()
        object TipsEqualStitchesInfo : GameRules()
        object TipsEqualStitchesInfoFirstRound : GameRules()
        object AnniversaryVersion : GameRules()
    }

    sealed class BaseBlock : BasePage() {
        class Input(val gameId: Long, val inputType: InputType) : BaseBlock()
        object Exit : BaseBlock()
        object FinishManually : BaseBlock()
        object Menu : BaseBlock()
        object About : BaseBlock()
        class Trump(val trumpType: TrumpType) : BaseBlock()
        class Scores(val gameScoreData: GameScoreData) : BaseBlock()
        class GameFinished(val winners: List<GameScore>) : BaseBlock()
    }

    sealed class Input : BasePage() {
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

    sealed class SavedGames : BasePage() {
        class ContinueGame(val gameId: Long) : SavedGames()
        class Info(val gameSettings: GameSettings) : SavedGames()
        object Delete : SavedGames()
    }

    sealed class Settings : BasePage() {
        object DialogDisplayAlwaysOn : Settings()
    }

    sealed class Statistics : BasePage() {
        object Clear : Statistics()
    }
}
