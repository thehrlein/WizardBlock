package com.tobiashehrlein.tobiswizardblock.feature.block.results

import android.os.Bundle
import com.tobiashehrlein.tobiswizardblock.core.entities.general.ToolbarButtonType

class BlockResultsFragment : BaseBlockResultsFragment() {

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.Back)
    }
}
