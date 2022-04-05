package com.tobiashehrlein.tobiswizardblock.ui_game_settings.playerselection

import android.os.Bundle
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.GameSettingsViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.playerselection.PlayerSelectionViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseToolbarFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.bindings.setPlayerCount
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.BR
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.R
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.databinding.FragmentPlayerSelectionBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PlayerSelectionFragment :
    BaseToolbarFragment<PlayerSelectionViewModel, GameSettingsViewModel, FragmentPlayerSelectionBinding>() {

    override val viewModel: PlayerSelectionViewModel by viewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_player_selection
    override val activityToolbarViewModel: GameSettingsViewModel by sharedViewModel()
    private val playerSettingsHandler = PlayerSelectionHandler()

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)
        activityToolbarViewModel.setTitle(getString(R.string.player_selection_toolbar_title))
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.Back)

        binding.playerCountChooseButtonGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked) {
                activityToolbarViewModel.setPlayerCount(
                    when (checkedId) {
                        R.id.toggle_four -> 4
                        R.id.toggle_five -> 5
                        R.id.toggle_six -> 6
                        else -> 3
                    }
                )
            }
        }

        playerSettingsHandler.setInputViews(
            listOf(
                binding.playerOneLayout,
                binding.playerTwoLayout,
                binding.playerThreeLayout,
                binding.playerFourLayout,
                binding.playerFiveLayout,
                binding.playerSixLayout
            )
        )

        activityToolbarViewModel.playerCount.observe(viewLifecycleOwner) {
            playerSettingsHandler.setPlayerCount(it)
            binding.playerCountChooseButtonGroup.setPlayerCount(it)
        }
        activityToolbarViewModel.playerNames.observe(viewLifecycleOwner) {
            playerSettingsHandler.setPlayerNames(it)
        }
        viewModel.playerNameOptions.observe(viewLifecycleOwner) {
            playerSettingsHandler.setPlayerNameOptions(it)
        }

        binding.playerSelectionButtonProceed.setOnClickListener {
            playerSettingsHandler.getValues()?.let { inputs ->
                val names = inputs.mapNotNull { it.second }
                activityToolbarViewModel.setPlayerNames(names)
                viewModel.onProceedClicked()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        playerSettingsHandler.onDestroy()
    }
}
