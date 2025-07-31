package com.tobiashehrlein.tobiswizardblock.core.presentation.general

import androidx.lifecycle.LiveData
import com.tobiashehrlein.tobiswizardblock.core.entities.general.ToolbarButtonType

abstract class BaseToolbarViewModel : BaseViewModelImpl() {

    abstract val toolbarTitle: LiveData<String>
    abstract val toolbarButton: LiveData<ToolbarButtonType>
    abstract val toolbarEvent: LiveData<Unit>

    abstract fun toolBarButtonClicked()
    abstract fun setTitle(title: String)
    abstract fun setToolbarButton(toolbarButtonType: ToolbarButtonType)
}
