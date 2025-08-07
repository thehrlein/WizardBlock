package com.tobiashehrlein.tobiswizardblock.core.presentation.block

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseToolbarViewModelImpl

abstract class GameBlockViewModel : BaseToolbarViewModelImpl() {
    abstract val gameId: LiveData<Long>

    abstract fun openMenu()
    abstract fun trackGameFinished(points: Int)
}
