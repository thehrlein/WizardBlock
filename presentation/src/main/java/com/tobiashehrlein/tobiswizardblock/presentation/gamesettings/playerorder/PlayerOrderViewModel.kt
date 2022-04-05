package com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerorder

import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModelImpl

abstract class PlayerOrderViewModel : BaseViewModelImpl() {

    abstract fun onProceedClicked()
    abstract fun onInfoIconClicked()
}
