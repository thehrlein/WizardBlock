package com.tobiashehrlein.tobiswizardblock.ui_statistics

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.tobiashehrlein.tobiswizardblock.presentation.statistics.StatisticsViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogInteractor
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogRequestCode
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogResultCode
import com.tobiashehrlein.tobiswizardblock.ui_statistics.databinding.FragmentStatisticsBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class StatisticsFragment : BaseFragment<StatisticsViewModel, FragmentStatisticsBinding>(), DialogInteractor {

    override val viewModel: StatisticsViewModel by sharedViewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_statistics
    override val hasOptionsMenu: Boolean = true

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        viewModel.anyStatisticsAvailable.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        return inflater.inflate(R.menu.menu_statistics, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.findItem(R.id.action_clear).isVisible = viewModel.anyStatisticsAvailable.value == true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear -> {
                viewModel.onClearActionClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DialogRequestCode.CLEAR_STATISTICS -> when (resultCode) {
                DialogResultCode.POSITIVE -> viewModel.onClearStatisticsConfirmed()
            }
        }
    }
}