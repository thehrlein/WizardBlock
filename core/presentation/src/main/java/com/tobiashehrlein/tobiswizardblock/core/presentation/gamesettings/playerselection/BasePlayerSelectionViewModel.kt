package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.playerselection

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseViewModelImpl

abstract class BasePlayerSelectionViewModel : BaseViewModelImpl() {

    abstract val playerNameOptions: LiveData<Set<String>>

    abstract fun onProceedClicked()
}
