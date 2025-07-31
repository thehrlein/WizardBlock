package com.tobiashehrlein.tobiswizardblock.core.presentation.block.trump

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseViewModelImpl

abstract class BlockTrumpViewModel : BaseViewModelImpl() {

    abstract val showTrumpDialogEnabled: LiveData<Boolean>
    abstract val selectedTrump: LiveData<TrumpType>

    abstract fun onAutoShowTrumpDialogChanged(checked: Boolean)
    abstract fun setSelectedTrump(trumpType: TrumpType)
}
