package com.tobiashehrlein.tobiswizardblock.presentation.block.trump

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModelImpl

abstract class BlockTrumpViewModel : BaseViewModelImpl() {

    abstract val showTrumpDialogEnabled: LiveData<Boolean>

    abstract fun onAutoShowTrumpDialogChanged(checked: Boolean)
}
