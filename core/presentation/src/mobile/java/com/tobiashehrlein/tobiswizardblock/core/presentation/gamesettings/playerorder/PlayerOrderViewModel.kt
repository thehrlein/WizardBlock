package com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.playerorder

import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseViewModelImpl

abstract class PlayerOrderViewModel : BaseViewModelImpl() {

    abstract fun onProceedClicked()
    abstract fun onInfoIconClicked()
}
