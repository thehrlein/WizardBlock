package com.tobiashehrlein.tobiswizardblock.feature.navigation

import android.os.Bundle
import com.tobiashehrlein.tobiswizardblock.core.presentation.navigation.NavigationViewModel
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.BaseFragment
import com.tobiashehrlein.tobiswizardblock.feature.navigation.databinding.FragmentNavigationBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class NavigationFragment : BaseFragment<NavigationViewModel, FragmentNavigationBinding>() {

    override val viewModel: NavigationViewModel by activityViewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_navigation

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
    }
}
