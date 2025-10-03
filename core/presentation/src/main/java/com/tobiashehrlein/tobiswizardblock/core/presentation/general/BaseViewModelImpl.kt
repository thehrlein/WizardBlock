package com.tobiashehrlein.tobiswizardblock.core.presentation.general

import com.tobiashehrlein.tobiswizardblock.core.entities.navigation.BasePage

abstract class BaseViewModelImpl : BaseViewModel() {

    override val navigationEvent = SingleLiveEvent<BasePage>()

    override fun navigateTo(page: BasePage) {
        navigationEvent.value = page
    }
}
