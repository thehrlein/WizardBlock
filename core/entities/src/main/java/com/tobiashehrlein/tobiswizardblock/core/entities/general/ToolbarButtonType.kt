package com.tobiashehrlein.tobiswizardblock.core.entities.general

sealed class ToolbarButtonType {

    object None : ToolbarButtonType()
    object Back : ToolbarButtonType()
    object Close : ToolbarButtonType()
}
