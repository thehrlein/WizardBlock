package com.tobiashehrlein.tobiswizardblock.presentation.about

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class AboutViewModel : BaseToolbarViewModelImpl() {

    abstract val sendEmail: LiveData<Unit>
    abstract val openWizard: LiveData<Unit>
    abstract val openFahrstuhl: LiveData<Unit>
    abstract val openMovieBase: LiveData<Unit>

    abstract fun onFabClicked()

    abstract fun onWizardBlockClicked()

    abstract fun onFahrstuhlBlockClicked()

    abstract fun onMovieBaseClicked()
}
