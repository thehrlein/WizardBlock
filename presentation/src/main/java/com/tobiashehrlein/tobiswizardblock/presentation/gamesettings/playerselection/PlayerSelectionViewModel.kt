package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerselection

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModelImpl

abstract class PlayerSelectionViewModel : BaseViewModelImpl() {

    abstract val playerNameOptions: LiveData<Set<String>>

    abstract fun onProceedClicked()
}
