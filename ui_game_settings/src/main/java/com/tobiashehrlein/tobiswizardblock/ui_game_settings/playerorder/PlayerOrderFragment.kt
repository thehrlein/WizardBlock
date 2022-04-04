package com.tobiashehrlein.tobiswizardblock.ui_game_settings.playerorder

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.GameSettingsViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerorder.PlayerOrderViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.BR
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseToolbarFragment
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.R
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.databinding.FragmentPlayerOrderBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerOrderFragment : BaseToolbarFragment<PlayerOrderViewModel, GameSettingsViewModel, FragmentPlayerOrderBinding>() {

    override val viewModel: PlayerOrderViewModel by viewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_player_order
    override val activityToolbarViewModel: GameSettingsViewModel by sharedViewModel()
    override val hasOptionsMenu: Boolean = true

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(R.string.player_order_toolbar_title))
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.Back)

        initAdapter()

        viewModel.playerNames.observe(viewLifecycleOwner) {
            activityToolbarViewModel.setPlayerNames(it)
        }

        activityToolbarViewModel.playerNames.value?.let {
            viewModel.onPlayerOrderChanged(it)
        }
    }

    private fun initAdapter() {
        PlayerOrderAdapter(viewModel).also { playerOrderAdapter ->
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

                viewModel.playerNames.observe(viewLifecycleOwner) {
                    playerOrderAdapter.setItems(it)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_player_order, menu)
        menu.findItem(R.id.action_info).icon.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.color_on_primary
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