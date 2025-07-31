package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.mapper

import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.Game
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameInfo
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameRound
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.PlayerResultData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.PlayerTipData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbGame
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbGameRound
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbGameSettings
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbPlayerResultData
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbPlayerTipData
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes.DbTrumpType
import com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.entities.DbGameInfo

fun GameInfo.mapToDbData() = DbGameInfo(
    gameId = gameId,
    gameStartDate = gameStartDate,
    players = players,
    gameName = gameName,
    gameSettings = gameSettings.mapToDbData(),
    gameFinished = gameFinished,
    removedFromSavedGames = false
)

fun DbGameInfo.mapToEntity() = GameInfo(
    gameId = gameId,
    gameStartDate = gameStartDate,
    players = players,
    gameName = gameName,
    gameSettings = gameSettings.mapToEntity(),
    gameFinished = gameFinished
)

fun GameSettings.mapToDbData() = DbGameSettings(
    tipsEqualStitches = tipsEqualStitches,
    tipsEqualStitchesFirstRound = tipsEqualStitchesFirstRound,
    anniversaryVersion = anniversaryVersion
)

fun DbGameSettings.mapToEntity() = GameSettings(
    tipsEqualStitches = tipsEqualStitches,
    tipsEqualStitchesFirstRound = tipsEqualStitchesFirstRound,
    anniversaryVersion = anniversaryVersion
)

fun GameRound.mapToDbData(gameId: Long) = DbGameRound(
    gameId = gameId,
    round = round,
    playerTipData = playerTipData?.map { it.mapToDbData() },
    playerResultData = playerResultData?.map { it.mapToDbData() },
    trumpType = trumpType.mapToDbTyp()
)

fun DbGameRound.mapToEntity() = GameRound(
    round = round,
    playerTipData = playerTipData?.map { it.mapToEntity() },
    playerResultData = playerResultData?.map { it.mapToEntity() },
    trumpType = trumpType.mapToEntity()
)

fun PlayerTipData.mapToDbData() = DbPlayerTipData(
    playerName = playerName,
    tip = tip,
    correctedCauseOfCloudCard = correctedCauseOfCloudCard
)

fun DbPlayerTipData.mapToEntity() = PlayerTipData(
    playerName = playerName,
    tip = tip,
    correctedCauseOfCloudCard = correctedCauseOfCloudCard
)

fun PlayerResultData.mapToDbData() = DbPlayerResultData(
    playerName = playerName,
    difference = difference,
    total = total,
    result = result
)

fun DbPlayerResultData.mapToEntity() = PlayerResultData(
    playerName = playerName,
    difference = difference,
    total = total,
    result = result
)

fun DbGame.mapToEntity() = Game(
    gameInfo = gameInfo.mapToEntity(),
    gameRounds = rounds.map { it.mapToEntity() }
)

fun TrumpType.mapToDbTyp() = when (this) {
    is TrumpType.Unselected -> DbTrumpType.Unselected
    is TrumpType.Selected -> when (this) {
        is TrumpType.Selected.None -> DbTrumpType.Selected.None
        is TrumpType.Selected.Blue -> DbTrumpType.Selected.Blue
        is TrumpType.Selected.Red -> DbTrumpType.Selected.Red
        is TrumpType.Selected.Green -> DbTrumpType.Selected.Green
        is TrumpType.Selected.Yellow -> DbTrumpType.Selected.Yellow
    }
}

fun DbTrumpType.mapToEntity() = when (this) {
    is DbTrumpType.Unselected -> TrumpType.Unselected
    is DbTrumpType.Selected -> when (this) {
        is DbTrumpType.Selected.None -> TrumpType.Selected.None
        is DbTrumpType.Selected.Blue -> TrumpType.Selected.Blue
        is DbTrumpType.Selected.Red -> TrumpType.Selected.Red
        is DbTrumpType.Selected.Green -> TrumpType.Selected.Green
        is DbTrumpType.Selected.Yellow -> TrumpType.Selected.Yellow
    }
}
