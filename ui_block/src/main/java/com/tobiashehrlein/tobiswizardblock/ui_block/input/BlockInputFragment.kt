package com.tobiashehrlein.tobiswizardblock.ui_block.input

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.presentation.block.GameBlockViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.block.input.BlockInputViewModel
import com.tobiashehrlein.tobiswizardblock.ui_block.BR
import com.tobiashehrlein.tobiswizardblock.ui_block.R
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.FragmentBlockInputBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseToolbarFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogInteractor
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogRequestCode
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogResultCode
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.getColorRes
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.getNameRes
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.isLandscape
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

private const val BLOCK_INPUT_INDEX = 1
private const val BLOCK_INPUT_DELAY: Long = 200

class BlockInputFragment :
    BaseToolbarFragment<BlockInputViewModel, GameBlockViewModel, FragmentBlockInputBinding>(),
    DialogInteractor {

    override val viewModel: BlockInputViewModel by viewModel {
        parametersOf(navArgs.gameId)
    }
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_block_input
    override val activityToolbarViewModel: GameBlockViewModel by sharedViewModel()
    private val navArgs: BlockInputFragmentArgs by navArgs()
    override val hasOptionsMenu: Boolean = true

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.Back)
        activityToolbarViewModel.setTitle(
            getString(
                when (navArgs.inputType) {
                    InputType.TIPP -> com.tobiashehrlein.tobiswizardblock.ui_common.R.string.block_input_toolbar_title_bets
                    InputType.RESULT -> com.tobiashehrlein.tobiswizardblock.ui_common.R.string.block_input_toolbar_title_results
                    else -> error("could not determine input type")
                }
            )
        )

        viewModel.trumpType.observe(viewLifecycleOwner) {
            requireActivity().invalidateOptionsMenu()
        }

        viewModel.playerTipDataCorrectedEvent.observe(viewLifecycleOwner) {
            Snackbar.make(
                binding.blockInputCoordinator,
                getString(
                    com.tobiashehrlein.tobiswizardblock.ui_common.R.string.block_input_correct_bets_snackbar_message,
                    it.playerName,
                    it.tip
                ),
                Snackbar.LENGTH_LONG
            ).show()
        }

        binding.blockInputBombPlayed.onCheckedChange {
            viewModel.onBlockPlayedSwitchChanged(it)
        }

        initAdapter()
    }

    private fun initAdapter() {
        BlockInputAdapter(viewModel).also { blockInputAdapter ->
            binding.blockInputList.apply {
                adapter = blockInputAdapter
                addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
                val isLandscape = context?.isLandscape() ?: false
                layoutManager = if (isLandscape) {
                    GridLayoutManager(context, 2)
                } else {
                    LinearLayoutManager(context)
                }
            }

            viewModel.inputModels.observe(viewLifecycleOwner) {
                blockInputAdapter.submitList(it)

                lifecycleScope.launchWhenStarted {
                    delay(BLOCK_INPUT_DELAY)
                    binding.blockInputSwitcher.displayedChild = BLOCK_INPUT_INDEX
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_input, menu)
        menu.findItem(R.id.action_info).icon?.setTint(
            ContextCompat.getColor(
                requireContext(),
                com.tobiashehrlein.tobiswizardblock.ui_common.R.color.color_on_primary
            )
        )
        viewModel.trumpType.value?.let { toolbarIcon ->
            menu.findItem(R.id.action_trump).apply {
                toolbarIcon.getColorRes()?.let {
                    icon?.setTint(ContextCompat.getColor(requireContext(), it))
                }
                isVisible =
                    toolbarIcon != TrumpType.Unselected && toolbarIcon != TrumpType.Selected.None
                title = toolbarIcon.getNameRes()?.let { getString(it) }
            }
        }

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

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            DialogRequestCode.CORRECT_TIPS_CHOOSE_PLAYER -> when (resultCode) {
                DialogResultCode.POSITIVE -> {
                    (data?.getSerializableExtra(DialogEntity.KEY_DIALOG_ENTITY) as?
                            DialogEntity.Custom.CorrectTipsChoosePlayer)?.let {
                        viewModel.correctPlayerTips(it.playerTipData)
                    }
                }
            }
        }
    }
}
