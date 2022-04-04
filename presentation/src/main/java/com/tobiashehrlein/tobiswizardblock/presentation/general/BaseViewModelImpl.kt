package com.tobiashehrlein.tobiswizardblock.presentation.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tobiashehrlein.tobiswizardblock.entities.navigation.Page

abstract class BaseViewModelImpl : BaseViewModel() {

    override val navigationEvent = SingleLiveEvent<Page>()

    override fun navigateTo(page: Page) {
        navigationEvent.value = page
    }
}
