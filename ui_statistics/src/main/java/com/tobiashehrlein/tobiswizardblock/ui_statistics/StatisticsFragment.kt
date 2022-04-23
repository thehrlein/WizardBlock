package com.tobiashehrlein.tobiswizardblock.ui_statistics

import android.os.Bundle
import com.tobiashehrlein.tobiswizardblock.presentation.statistics.StatisticsViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseFragment
import com.tobiashehrlein.tobiswizardblock.ui_statistics.databinding.FragmentStatisticsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StatisticsFragment : BaseFragment<StatisticsViewModel, FragmentStatisticsBinding>() {

    override val viewModel: StatisticsViewModel by sharedViewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_statistics

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

    }
}