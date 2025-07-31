package com.tobiashehrlein.tobiswizardblock.feature.gamesettings.gamerules

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.GameSettingsViewModel
import com.tobiashehrlein.tobiswizardblock.core.presentation.gamesettings.gamerules.GameRulesViewModel
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.BaseToolbarFragment
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.BR
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.R
import com.tobiashehrlein.tobiswizardblock.feature.gamesettings.databinding.FragmentGameRulesBinding
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GameRulesFragment : BaseToolbarFragment<GameRulesViewModel, GameSettingsViewModel, FragmentGameRulesBinding>() {

    override val viewModel: GameRulesViewModel by viewModel()
    override val viewModelVariableId: Int = BR.viewModel
    override val layoutId: Int = R.layout.fragment_game_rules
    override val activityToolbarViewModel: GameSettingsViewModel by activityViewModel()
    override val hasOptionsMenu: Boolean = true

    override fun onBindingCreated(savedInstanceState: Bundle?) {
        super.onBindingCreated(savedInstanceState)

        activityToolbarViewModel.setTitle(
            getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.game_rules_toolbar_title)
        )
        activityToolbarViewModel.setToolbarButton(ToolbarButtonType.Back)

        binding.gameRulesSettingsTipsEqualStitches.onCheckedChange {
            activityToolbarViewModel.setTipsEqualStitches(it)
        }
        binding.gameRulesSettingsTipsEqualStitchesFirstRound.onCheckedChange {
            activityToolbarViewModel.setTipsEqualStitchesFirstRound(it)
        }
        binding.gameRulesSettingsAnniversaryVersion.onCheckedChange {
            activityToolbarViewModel.setAnniversaryVersion(it)
        }
        binding.gameRulesGameName.addTextChangeListener {
            activityToolbarViewModel.setGameName(it)
        }
        activityToolbarViewModel.gameSettings.observe(viewLifecycleOwner) {
            binding.gameRulesSettingsTipsEqualStitches.setChecked(it.tipsEqualStitches)
            binding.gameRulesSettingsTipsEqualStitchesFirstRound.setChecked(it.tipsEqualStitchesFirstRound)
            binding.gameRulesSettingsTipsEqualStitchesFirstRound.isEnabled = !it.tipsEqualStitches
            binding.gameRulesSettingsAnniversaryVersion.setChecked(it.anniversaryVersion)
        }
        activityToolbarViewModel.gameName.observe(viewLifecycleOwner) {
            binding.gameRulesGameName.setName(it)
        }

        binding.gameRulesButtonProceed.setOnClickListener {
            val gameName = binding.gameRulesGameName.getName()
            val playerNames = activityToolbarViewModel.playerNames.value ?: error("playerNames must not be null")
            val gameSettings = activityToolbarViewModel.gameSettings.value ?: error("gameSettings must not be null")
            if (gameName.isNullOrEmpty()) {
                binding.gameRulesGameName.setError(
                    getString(
                        com.tobiashehrlein.tobiswizardblock.feature.common.R.string.game_rules_game_name_must_not_be_empty
                    )
                )
            } else {
                binding.gameRulesGameName.setError(null)
                viewModel.onProceedClicked(gameName, playerNames, gameSettings)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_game_rules, menu)
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
