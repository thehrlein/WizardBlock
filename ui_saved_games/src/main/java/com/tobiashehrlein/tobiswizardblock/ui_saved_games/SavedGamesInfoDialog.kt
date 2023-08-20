package com.tobiashehrlein.tobiswizardblock.ui_saved_games

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tobiashehrlein.tobiswizardblock.presentation.savedgames.info.SavedGamesInfoViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.BaseDialogFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogResultCode
import com.tobiashehrlein.tobiswizardblock.ui_saved_games.databinding.DialogSavedGamesInfoBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SavedGamesInfoDialog : BaseDialogFragment<SavedGamesInfoViewModel, DialogSavedGamesInfoBinding>() {

    override val viewModel: SavedGamesInfoViewModel by viewModel {
        parametersOf(dialogEntity.gameSettings)
    }
    override val layoutId: Int = R.layout.dialog_saved_games_info
    override val viewModelVariableId: Int = BR.viewModel

    companion object {
        private val TAG: String = SavedGamesInfoDialog::class.java.simpleName

        fun show(fragmentManager: FragmentManager, dialogEntity: DialogEntity.Custom) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return
            val bundle = Bundle().apply {
                putSerializable(DialogEntity.KEY_DIALOG_ENTITY, dialogEntity)
            }

            SavedGamesInfoDialog().apply {
                arguments = bundle
                isCancelable = false
            }.show(fragmentManager, TAG)
        }

        fun dismiss(fragmentManager: FragmentManager) {
            val dialog = fragmentManager.findFragmentByTag(TAG) as? DialogFragment
            dialog?.dismiss()
        }
    }

    private val dialogEntity: DialogEntity.Custom.SavedGamesInfo by lazy {
        requireArguments().getSerializable(DialogEntity.KEY_DIALOG_ENTITY) as DialogEntity.Custom.SavedGamesInfo
    }

    override fun createDialog(savedInstanceState: Bundle?, view: View): AlertDialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .setTitle(dialogEntity.title)
            .setPositiveButton(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.general_ok) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.POSITIVE)
            }
            .create()
    }
}
