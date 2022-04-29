package com.tobiashehrlein.tobiswizardblock.presentation.block

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class GameBlockViewModel : BaseToolbarViewModelImpl() {
    abstract val gameId: LiveData<Long>

    abstract fun openMenu()
    abstract fun trackGameFinished(points: Int)
}
