package com.tobiashehrlein.tobiswizardblock.core.presentation.general

import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.Page

abstract class BaseViewModelImpl : BaseViewModel() {

    override val navigationEvent = SingleLiveEvent<Page>()

    override fun navigateTo(page: Page) {
        navigationEvent.value = page
    }
}
