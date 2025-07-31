package com.tobiashehrlein.tobiswizardblock.core.presentation.block.results

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.game.result.BlockItem
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseToolbarViewModelImpl

abstract class BlockResultsViewModel : BaseToolbarViewModelImpl(), BlockResultsInteractions {

    abstract val gameName: LiveData<String>
    abstract val inputType: LiveData<InputType>
    abstract val gameFinished: LiveData<Boolean>
    abstract val columnCount: LiveData<Int>
    abstract val blockItems: LiveData<List<BlockItem>>
    abstract val showGameFinishedEvent: LiveData<Int>
    abstract val editInputEnabled: LiveData<Boolean>
    abstract val finishManuallyEnabled: LiveData<Boolean>

    abstract fun setGameId(gameId: Long)
    abstract fun onFabClicked()
    abstract fun onTrophyClicked()
    abstract fun updateTrumpType(trumpType: TrumpType)
    abstract fun onMenuDeleteInputClicked()
    abstract fun onMenuInfoClicked()
    abstract fun onMenuSettingsClicked()
    abstract fun showExitDialog()
    abstract fun finishGameManuallyClicked()
    abstract fun onFinishGameManuallyConfirmed()
}
