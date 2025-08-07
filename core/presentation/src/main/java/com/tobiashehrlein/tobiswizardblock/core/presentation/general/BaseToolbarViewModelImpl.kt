package com.tobiashehrlein.tobiswizardblock.core.presentation.general

import androidx.lifecycle.MutableLiveData
import com.tobiashehrlein.tobiswizardblock.core.entities.general.ToolbarButtonType

abstract class BaseToolbarViewModelImpl : BaseToolbarViewModel() {

    override val toolbarTitle = MutableLiveData<String>()
    override val toolbarButton = MutableLiveData<ToolbarButtonType>(ToolbarButtonType.None)
    override val toolbarEvent = SingleLiveEvent<Unit>()

    override fun toolBarButtonClicked() {
        toolbarEvent.call()
    }

    override fun setTitle(title: String) {
        toolbarTitle.value = title
    }

    override fun setToolbarButton(toolbarButtonType: ToolbarButtonType) {
        toolbarButton.value = toolbarButtonType
    }
}
