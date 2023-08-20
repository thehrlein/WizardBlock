package com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.entity

import com.tobiashehrlein.tobiswizardblock.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.entities.game.general.PlayerTipData
import com.tobiashehrlein.tobiswizardblock.entities.game.input.InputType
import com.tobiashehrlein.tobiswizardblock.entities.game.result.GameScore
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.ui_common.R
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogRequestCode
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.ResourceHelper
import java.io.Serializable

sealed class DialogEntity : Serializable {

    companion object {
        const val KEY_DIALOG_ENTITY = "key.dialog_entity"
    }

    abstract val requestCode: Int
    open val isCancelable: Boolean = false

    sealed class Text(
        val title: String? = null,
        val message: String? = null,
        val positiveButtonText: String? = null,
        val negativeButtonText: String? = null,
        val neutralButtonText: String? = null
    ) : DialogEntity() {

        class Exit(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_title_attention),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_exit_message),
            positiveButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_exit_button_menu),
            negativeButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_exit_button_quit),
            neutralButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_cancel)
        ) {
            override val requestCode: Int = DialogRequestCode.BLOCK_EXIT
        }

        class GameFinished(winners: List<GameScore>, resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.game_block_results_winner_title),
            message = resourceHelper.getPlural(
                R.plurals.game_winner_message,
                winners.size,
                winners.joinToString { it.player },
                winners.first().points
            ),
            positiveButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.GAME_FINISHED
        }

        class PlayerOrderInfo(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_title_info),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.player_order_description),
            neutralButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class GameRulesInfo(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_title_info),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.game_rules_info_description),
            neutralButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class GameRulesInfoTipsEqualStitches(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_title_info),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.game_rules_stitches_can_be_equal_tips_info),
            neutralButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.TIPS_EQUAL_STITCHES
        }

        class GameRulesInfoTipsEqualStitchesFirstRound(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_title_info),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.game_rules_stitches_can_be_equal_in_first_round_info),
            neutralButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.TIPS_EQUAL_STITCHES_FIRST_ROUND
        }

        class GameRulesInfoAnniversaryMode(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_title_info),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.game_rules_anniversary_option_stitches_can_be_less_info),
            neutralButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.ANNIVERSARY_VERSION
        }

        class InputInfo(
            inputType: InputType,
            bombPlayed: Boolean,
            round: Int,
            gameSettings: GameSettings,
            resourceHelper: ResourceHelper
        ) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.dialog_title_info),
            message = when (inputType) {
                InputType.TIPP ->
                    when {
                        gameSettings.tipsEqualStitchesFirstRound && round == 1 -> resourceHelper.getString(
                            R.string.block_input_info_tips_can_be_equal_stitches_message_first_round
                        )
                        gameSettings.tipsEqualStitches -> resourceHelper.getString(
                            R.string.block_input_info_tips_can_be_equal_stitches_message,
                            round
                        )
                        else -> resourceHelper.getString(
                            R.string.block_input_info_tips_tips_must_be_unequal_stitches_message,
                            round
                        )
                    }
                InputType.RESULT ->
                    resourceHelper.getString(
                        R.string.block_input_info_result_message,
                        round - if (bombPlayed) 1 else 0
                    )

            },
            neutralButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class DeleteSavedGames(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.saved_game_dialog_delete_title),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.saved_game_dialog_delete_message),
            positiveButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_yes),
            negativeButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_cancel)
        ) {
            override val requestCode: Int = DialogRequestCode.SAVED_GAMES_DELETE
        }

        class BlockInputBombPlayed(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.block_input_anniversary_version_bomb_played_dialog_title),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.block_input_anniversary_version_bomb_played_dialog_message),
            positiveButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.BLOCK_INPUT_BOMB_PLAYED
        }

        class SettingsDisplayAlwaysOn(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.settings_display_always_active_dialog_title),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.settings_display_always_active_dialog_message),
            neutralButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok)
        ) {
            override val requestCode: Int = DialogRequestCode.SETTINGS_DISPLAY_ALWAYS_ON
        }

        class FinishGameManually(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.game_block_finish_manually_title),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.game_block_finish_manually_message),
            positiveButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_finish),
            negativeButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_cancel)
        ) {
            override val requestCode: Int = DialogRequestCode.GAME_BLOCK_FINISH_MANUALLY
        }

        class ClearStatistics(resourceHelper: ResourceHelper) : Text(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.statistics_clear_all_dialog_title),
            message = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.statistics_clear_all_dialog_message),
            positiveButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.statistics_clear_all),
            negativeButtonText = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_cancel)
        ) {
            override val requestCode: Int = DialogRequestCode.CLEAR_STATISTICS
        }
    }

    sealed class Custom(
        val title: String?,
        val positiveButtonText: String? = null,
        val negativeButtonText: String? = null,
        val neutralButtonText: String? = null
    ) : DialogEntity() {

        class Trump(var selectedTrumpType: TrumpType, resourceHelper: ResourceHelper) : Custom(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.block_trump_title)
        ) {
            override val requestCode: Int = DialogRequestCode.CHOOSE_TRUMP
        }

        data class CorrectTipsChoosePlayer(
            val playerTipData: List<PlayerTipData>,
            val round: Int,
            val resourceHelper: ResourceHelper
        ) : Custom(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.block_input_correct_tips_choose_player_title)
        ) {
            override val requestCode: Int = DialogRequestCode.CORRECT_TIPS_CHOOSE_PLAYER
        }

        class SavedGamesInfo(
            val gameSettings: GameSettings,
            resourceHelper: ResourceHelper
        ) : Custom(
            title = resourceHelper.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.saved_game_dialog_info_title)
        ) {
            override val requestCode: Int = DialogRequestCode.SAVED_GAMES_INFO
        }
    }

    class Loading(
        val dimWindow: Boolean
    ) : DialogEntity() {
        override val requestCode: Int = DialogRequestCode.DIALOG_LOADING
        override val isCancelable: Boolean = false
    }
}
