package com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.entity

import androidx.annotation.StringRes
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.GameSettings
import com.tobiashehrlein.tobiswizardblock.core.entities.game.general.PlayerTipData
import com.tobiashehrlein.tobiswizardblock.core.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.feature.common.R
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.utils.DialogRequestCode
import java.io.Serializable

sealed class DialogEntity : Serializable {

    companion object {
        const val KEY_DIALOG_ENTITY = "key.dialog_entity"
    }

    abstract val requestCode: Int
    open val isCancelable: Boolean = false

    sealed class Text(
        @StringRes val title: Int,
        @StringRes val message: Int? = null,
        val messageString: String? = null,
        @StringRes val positiveButtonText: Int? = null,
        @StringRes val negativeButtonText: Int? = null,
        @StringRes val neutralButtonText: Int? = null
    ) : DialogEntity(), Serializable {

        data object Exit : Text(
            title = R.string.dialog_title_attention,
            message = R.string.dialog_exit_message,
            positiveButtonText = R.string.dialog_exit_button_menu,
            negativeButtonText = R.string.dialog_exit_button_quit,
            neutralButtonText = R.string.general_cancel
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.BLOCK_EXIT
        }

        class GameFinished(message: String) : Text(
            title = R.string.game_block_results_winner_title,
            messageString = message,
            positiveButtonText = R.string.general_ok
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.GAME_FINISHED
        }

        class PlayerOrderInfo() : Text(
            title = R.string.dialog_title_info,
            message = R.string.player_order_description,
            neutralButtonText = R.string.general_ok
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class GameRulesInfo() : Text(
            title = R.string.dialog_title_info,
            message = R.string.game_rules_info_description,
            neutralButtonText = R.string.general_ok
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class GameRulesInfoTipsEqualStitches() : Text(
            title = R.string.dialog_title_info,
            message = R.string.game_rules_stitches_can_be_equal_bets_info,
            neutralButtonText = R.string.general_ok
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.TIPS_EQUAL_STITCHES
        }

        class GameRulesInfoTipsEqualStitchesFirstRound() : Text(
            title = R.string.dialog_title_info,
            message = R.string.game_rules_stitches_can_be_equal_in_first_round_info,
            neutralButtonText = R.string.general_ok
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.TIPS_EQUAL_STITCHES_FIRST_ROUND
        }

        class GameRulesInfoAnniversaryMode() : Text(
            title = R.string.dialog_title_info,
            message = R.string.game_rules_anniversary_option_stitches_can_be_less_info,
            neutralButtonText = R.string.general_ok
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.ANNIVERSARY_VERSION
        }

        class InputInfo(
            message: String
        ) : Text(
            title = R.string.dialog_title_info,
            messageString = message,
            neutralButtonText = R.string.general_ok
        ), Serializable{
            override val requestCode: Int = DialogRequestCode.INPUT_INFO
        }

        class DeleteSavedGames() : Text(
            title = R.string.saved_game_dialog_delete_title,
            message = R.string.saved_game_dialog_delete_message,
            positiveButtonText = R.string.general_yes,
            negativeButtonText = R.string.general_cancel
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.SAVED_GAMES_DELETE
        }

        class BlockInputBombPlayed() : Text(
            title = R.string.block_input_anniversary_version_bomb_played_dialog_title,
            message = R.string.block_input_anniversary_version_bomb_played_dialog_message,
            positiveButtonText =R.string.general_ok
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.BLOCK_INPUT_BOMB_PLAYED
        }

        class SettingsDisplayAlwaysOn() : Text(
            title = R.string.settings_display_always_active_dialog_title,
            message = R.string.settings_display_always_active_dialog_message,
            neutralButtonText = R.string.general_ok
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.SETTINGS_DISPLAY_ALWAYS_ON
        }

        class FinishGameManually() : Text(
            title = R.string.game_block_finish_manually_title,
            message = R.string.game_block_finish_manually_message,
            positiveButtonText = R.string.general_finish,
            negativeButtonText = R.string.general_cancel
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.GAME_BLOCK_FINISH_MANUALLY
        }

        class ClearStatistics() : Text(
            title = R.string.statistics_clear_all_dialog_title,
            message = R.string.statistics_clear_all_dialog_message,
            positiveButtonText = R.string.statistics_clear_all,
            negativeButtonText = R.string.general_cancel
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.CLEAR_STATISTICS
        }
    }

    sealed class Custom(
        @StringRes val title: Int?,
    ) : DialogEntity(), Serializable {

        class Trump(var selectedTrumpType: TrumpType, ) : Custom(
            title = R.string.block_trump_title
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.CHOOSE_TRUMP
        }

        data class CorrectTipsChoosePlayer(
            val playerTipData: List<PlayerTipData>,
            val round: Int,
        ) : Custom(
            title = R.string.block_input_correct_bets_choose_player_title
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.CORRECT_TIPS_CHOOSE_PLAYER
        }

        class SavedGamesInfo(
            val gameSettings: GameSettings
        ) : Custom(
            title = R.string.saved_game_dialog_info_title
        ), Serializable {
            override val requestCode: Int = DialogRequestCode.SAVED_GAMES_INFO
        }
    }

    class Loading(
        val dimWindow: Boolean
    ) : DialogEntity(), Serializable {
        override val requestCode: Int = DialogRequestCode.DIALOG_LOADING
        override val isCancelable: Boolean = false
    }
}
