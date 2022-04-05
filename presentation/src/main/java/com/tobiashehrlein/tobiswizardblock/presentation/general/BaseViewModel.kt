package com.tobiashehrlein.tobiswizardblock.presentation.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page

abstract class BaseViewModel : ViewModel() {

    abstract val navigationEvent: LiveData<Page>

    abstract fun navigateTo(page: Page)
}
