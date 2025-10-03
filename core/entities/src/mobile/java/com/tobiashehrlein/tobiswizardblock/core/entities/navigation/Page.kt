package com.tobiashehrlein.tobiswizardblock.core.entities.navigation

sealed class Page : BasePage() {

    sealed class Navigation : BaseNavigation() {
        object Settings: Navigation()
        object Info : Navigation()
    }
}