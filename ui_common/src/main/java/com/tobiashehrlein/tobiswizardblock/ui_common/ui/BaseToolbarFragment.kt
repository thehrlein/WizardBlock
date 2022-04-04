package com.tobiashehrlein.tobiswizardblock.ui_common.ui

import androidx.databinding.ViewDataBinding
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseToolbarViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.general.BaseViewModel

abstract class BaseToolbarFragment<Model : BaseViewModel, ToolbarViewModel : BaseToolbarViewModel, Binding : ViewDataBinding> : BaseFragment<Model, Binding>() {

    abstract val activityToolbarViewModel: ToolbarViewModel
}
