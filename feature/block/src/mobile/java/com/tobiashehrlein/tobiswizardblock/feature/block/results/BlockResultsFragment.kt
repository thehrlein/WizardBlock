package com.tobiashehrlein.tobiswizardblock.feature.block.results

import android.os.Bundle
import android.view.MenuItem
import com.tobiashehrlein.tobiswizardblock.core.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.feature.block.R

class BlockResultsFragment : BaseBlockResultsFragment() {

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.None)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                viewModel.onMenuInfoClicked()
                true
            }
            R.id.action_settings -> {
                viewModel.onMenuSettingsClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
