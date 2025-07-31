package com.tobiashehrlein.tobiswizardblock.feature.common.ui

import androidx.databinding.ViewDataBinding
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseToolbarViewModel
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseViewModel

abstract class BaseToolbarFragment<Model : BaseViewModel, ToolbarViewModel : BaseToolbarViewModel, Binding : ViewDataBinding> : BaseFragment<Model, Binding>() {

    abstract val activityToolbarViewModel: ToolbarViewModel
}
