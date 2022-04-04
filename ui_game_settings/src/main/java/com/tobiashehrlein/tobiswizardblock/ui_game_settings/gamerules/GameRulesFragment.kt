package com.tobiashehrlein.tobiswizardblock.ui_game_settings.gamerules

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.GameSettingsViewModel
import com.tobiashehrlein.tobiswizardblock.presentation.gamesettings.gamerules.GameRulesViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseToolbarFragment
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.BR
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.R
import com.tobiashehrlein.tobiswizardblock.ui_game_settings.databinding.FragmentGameRulesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameRulesFragment : BaseToolbarFragment<GameRulesViewModel, GameSettingsViewModel, FragmentGameRulesBinding>() {

    override val viewModel: GameRulesViewModel by viewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_game_rules
    override val activityToolbarViewModel: GameSettingsViewModel by sharedViewModel()
    override val hasOptionsMenu: Boolean = true

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(getString(R.string.game_rules_toolbar_title))
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.Back)

        binding.gameRulesSettingsTipsEqualStitches.onCheckedChange {
            viewModel.setTipsEqualStitches(it)
        }
        binding.gameRulesSettingsTipsEqualStitchesFirstRound.onCheckedChange {
            viewModel.setTipsEqualStitchesFirstRound(it)
        }
        binding.gameRulesSettingsAnniversaryVersion.onCheckedChange {
            viewModel.setAnniversaryVersion(it)
        }
        binding.gameRulesGameName.addTextChangeListener {
            viewModel.setGameName(it)
        }

        viewModel.gameSettings.observe(viewLifecycleOwner) {
            activityToolbarViewModel.setGameSettings(it)
        }

        viewModel.gameName.observe(viewLifecycleOwner) {
            activityToolbarViewModel.setGameName(it)
        }

        activityToolbarViewModel.gameSettings.value?.let {
            viewModel.setGameSettings(it)
        }

        activityToolbarViewModel.gameName.value?.let {
            viewModel.setGameName(it)
        }

        binding.gameRulesButtonProceed.setOnClickListener {
            val gameName = binding.gameRulesGameName.getName()
            val playerNames = activityToolbarViewModel.playerNames.value ?: error("playerNames must not be null")
            if (gameName.isNullOrEmpty()) {
                binding.gameRulesGameName.setError(getString(R.string.game_rules_game_name_must_not_be_empty))
            } else {
                binding.gameRulesGameName.setError(null)
                viewModel.onProceedClicked(gameName, playerNames)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_game_rules, menu)
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