package com.tobiashehrlein.tobiswizardblock.presentation.settings

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModelImpl

abstract class SettingsViewModel : BaseToolbarViewModelImpl() {

    abstract val displayAlwaysOn : LiveData<Boolean>

    abstract fun onDisplayAlwaysOnInfoClicked()
    abstract fun onDisplayAlwaysOnChecked(alwaysOn: Boolean)
}