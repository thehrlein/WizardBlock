package com.tobiashehrlein.tobiswizardblock.ui_block.trump

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.presentation.block.trump.BlockTrumpViewModel
import com.tobiashehrlein.tobiswizardblock.ui_block.BR
import com.tobiashehrlein.tobiswizardblock.ui_block.R
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.DialogBlockTrumpBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.BaseDialogFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogResultCode
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BlockTrumpDialog : BaseDialogFragment<BlockTrumpViewModel, DialogBlockTrumpBinding>() {

    override val viewModel: BlockTrumpViewModel by viewModel {
        parametersOf(dialogEntity.selectedTrumpType)
    }
    override val layoutId: Int = R.layout.dialog_block_trump
    override val viewModelVariableId: Int = BR.viewModel

    companion object {
        private val TAG: String = BlockTrumpDialog::class.java.simpleName

        fun show(fragmentManager: FragmentManager, dialogEntity: DialogEntity.Custom) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return
            val bundle = Bundle().apply {
                putSerializable(DialogEntity.KEY_DIALOG_ENTITY, dialogEntity)
            }

            BlockTrumpDialog().apply {
                arguments = bundle
                isCancelable = dialogEntity.isCancelable
            }.show(fragmentManager, TAG)
        }

        fun dismiss(fragmentManager: FragmentManager) {
            val dialog = fragmentManager.findFragmentByTag(TAG) as? DialogFragment
            dialog?.dismiss()
        }
    }

    private val dialogEntity: DialogEntity.Custom.Trump by lazy {
        requireArguments().getSerializable(DialogEntity.KEY_DIALOG_ENTITY) as DialogEntity.Custom.Trump
    }

    override fun createDialog(savedInstanceState: Bundle?, view: View): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .setTitle(dialogEntity.title)
            .setPositiveButton(R.string.general_ok) { _, _ ->
                dialogEntity.selectedTrumpType = viewModel.selectedTrump.value ?: TrumpType.Selected.None
                if (dialogEntity.selectedTrumpType == TrumpType.Unselected) {
                    dialogEntity.selectedTrumpType = TrumpType.Selected.None
                }
                sendDialogResult(dialogEntity, DialogResultCode.POSITIVE)
            }
            .setNegativeButton(R.string.general_cancel) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.NEGATIVE)
            }
            .setNeutralButton(R.string.block_trump_reset_selection, null)
            .create()

        dialog.setOnShowListener {
            (dialog as? AlertDialog)?.getButton(AlertDialog.BUTTON_NEUTRAL)?.setOnClickListener {
                binding.trumpSelectionGroup.reset()
                viewModel.setSelectedTrump(TrumpType.Selected.None)
            }
        }

        return dialog
    }

    override fun onBindingCreated() {
        super.onBindingCreated()

        binding.trumpSelectionGroup.setOnCheckedChangeListener {
            viewModel.setSelectedTrump(it)
        }
        binding.autoShowTrumpDialog.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAutoShowTrumpDialogChanged(isChecked)
        }
    }
}
