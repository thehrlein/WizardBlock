package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerorder

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class PlayerOrderViewModel : BaseToolbarViewModelImpl(), PlayerOrderInteractions {

    abstract val playerNames: LiveData<List<String>>

    abstract fun onProceedClicked()
    abstract fun onInfoIconClicked()
}
