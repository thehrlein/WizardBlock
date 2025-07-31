package com.tobiashehrlein.tobiswizardblock.core.presentation.about

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseToolbarViewModelImpl

abstract class AboutViewModel : BaseToolbarViewModelImpl() {

    abstract val sendEmail: LiveData<Unit>
    abstract val openWizard: LiveData<Unit>
    abstract val openFahrstuhl: LiveData<Unit>
    abstract val openMovieBase: LiveData<Unit>

    abstract fun onFabClicked()
    abstract fun onWizardBlockClicked()
    abstract fun onFahrstuhlBlockClicked()
    abstract fun onMovieBaseClicked()
    abstract fun trackOpenStoreEvent(packageName: String)
}
