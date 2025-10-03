package com.tobiashehrlein.tobiswizardblock.core.presentation.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.BasePage

abstract class BaseViewModel : ViewModel() {

    abstract val navigationEvent: LiveData<BasePage>

    abstract fun navigateTo(page: BasePage)
}
