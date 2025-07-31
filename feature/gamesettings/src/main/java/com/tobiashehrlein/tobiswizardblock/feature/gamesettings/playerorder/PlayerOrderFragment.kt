package com.tobiashehrlein.tobiswizardblock.feature.gamesettings.playerorder

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.GameSettingsViewModel
import com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.playerorder.PlayerOrderViewModel
import com.tobiashehrlein.tobiswizardblock.feature.common.BR
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.BaseToolbarFragment
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.R
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.databinding.FragmentPlayerOrderBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerOrderFragment : BaseToolbarFragment<PlayerOrderViewModel, GameSettingsViewModel, FragmentPlayerOrderBinding>() {

    override val viewModel: PlayerOrderViewModel by viewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_player_order
    override val hasOptionsMenu: Boolean = true
    override val activityToolbarViewModel: GameSettingsViewModel by activityViewModel()

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(
            getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.player_order_toolbar_title)
        )
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.Back)

        initAdapter()

        binding.playerOrderButtonProceed.setOnClickListener {
            viewModel.onProceedClicked()
        }
    }

    private fun initAdapter() {
        PlayerOrderAdapter(activityToolbarViewModel).also { playerOrderAdapter ->
            binding.playerOrderList.apply {
                adapter = playerOrderAdapter

                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))

                val callback = SimpleItemTouchHelper(playerOrderAdapter)
                val itemTouchHelper = ItemTouchHelper(callback)
                itemTouchHelper.attachToRecyclerView(this)
                playerOrderAdapter.setOnItemTouchListener {
                    itemTouchHelper.startDrag(it)
                }
            }

            activityToolbarViewModel.playerNames.observe(viewLifecycleOwner) {
                playerOrderAdapter.setItems(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_player_order, menu)
        menu.findItem(R.id.action_info).icon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                com.tobiashehrlein.tobiswizardblock.feature.common.R.color.color_on_primary
            )
        )
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                viewModel.onInfoIconClicked()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
