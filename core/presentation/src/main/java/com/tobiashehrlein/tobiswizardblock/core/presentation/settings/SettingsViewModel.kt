package com.tobiashehrlein.tobiswizardblock.core.presentation.settings

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseToolbarViewModelImpl

abstract class SettingsViewModel : BaseToolbarViewModelImpl() {

    abstract val displayAlwaysOn: LiveData<Boolean>

    abstract fun onDisplayAlwaysOnInfoClicked()
    abstract fun onDisplayAlwaysOnChecked(alwaysOn: Boolean)
}
