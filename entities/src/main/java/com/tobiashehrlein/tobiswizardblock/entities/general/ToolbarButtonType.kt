package com.tobiashehrlein.tobiswizardblock.entities.general

sealed class ToolbarButtonType {

    object None : ToolbarButtonType()
    object Back : ToolbarButtonType()
    object Close : ToolbarButtonType()
}
