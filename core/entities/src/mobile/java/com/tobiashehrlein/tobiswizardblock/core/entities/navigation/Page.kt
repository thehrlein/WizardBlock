package com.tobiashehrlein.tobiswizardblock.core.entities.navigation

sealed class Page : BasePage() {

    sealed class Navigation : BaseNavigation() {
        object Settings: Navigation()
        object Info : Navigation()
    }

    sealed class PlayerSelection : Page() {
        object PlayerOrder : PlayerSelection()
    }

    sealed class PlayerOrder : Page() {
        object GameRules : PlayerOrder()
        object Info : PlayerOrder()
    }

    sealed class Block : BaseBlock() {
        object Settings : Block()
    }
}