package com.tobiashehrlein.tobiswizardblock.core.presentation.block.results

import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.GetGameUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.input.StoreRoundUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.results.GetBlockResultsUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.results.GetGameScoresUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.results.RemoveRoundUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.block.results.StoreGameFinishedUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.general.TrackAnalyticsEventUseCase
import com.tobiashehrlein.tobiswizardblock.core.interactor.usecase.user.IsShowTrumpDialogEnabledUseCase

class BlockResultsViewModelImpl(
    getGameUseCase: GetGameUseCase,
    getBlockResultsUseCase: GetBlockResultsUseCase,
    getGameScoresUseCase: GetGameScoresUseCase,
    storeGameFinishedUseCase: StoreGameFinishedUseCase,
    storeRoundUseCase: StoreRoundUseCase,
    removeRoundUseCase: RemoveRoundUseCase,
    isShowTrumpDialogEnabledUseCase: IsShowTrumpDialogEnabledUseCase,
    trackAnalyticsEventUseCase: TrackAnalyticsEventUseCase
) : BlockResultsViewModel(
    getGameUseCase = getGameUseCase,
    getBlockResultsUseCase = getBlockResultsUseCase,
    getGameScoresUseCase = getGameScoresUseCase,
    storeGameFinishedUseCase = storeGameFinishedUseCase,
    storeRoundUseCase = storeRoundUseCase,
    removeRoundUseCase = removeRoundUseCase,
    isShowTrumpDialogEnabledUseCase = isShowTrumpDialogEnabledUseCase,
    trackAnalyticsEventUseCase = trackAnalyticsEventUseCase
)
