package com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogResultCode

class SimpleAlertDialogFragment : DialogInteractionFragment() {

    companion object {
        private val TAG: String = SimpleAlertDialogFragment::class.java.simpleName

        fun show(fragmentManager: FragmentManager, dialogEntity: DialogEntity.Text) {
            val fragmentTag = TAG.plus(dialogEntity::class.java)
            if (fragmentManager.findFragmentByTag(fragmentTag) != null) return

            val bundle = Bundle().apply {
                putSerializable(DialogEntity.KEY_DIALOG_ENTITY, dialogEntity)
            }

            SimpleAlertDialogFragment().apply {
                arguments = bundle
            }.showNow(fragmentManager, fragmentTag)
        }
    }

    private val dialogEntity: DialogEntity.Text by lazy {
        requireArguments().getSerializable(DialogEntity.KEY_DIALOG_ENTITY) as DialogEntity.Text
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(dialogEntity.title)
            .setMessage(dialogEntity.message)
            .setNeutralButton(dialogEntity.neutralButtonText) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.NEUTRAL)
            }
            .setNegativeButton(dialogEntity.negativeButtonText) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.NEGATIVE)
            }
            .setPositiveButton(dialogEntity.positiveButtonText) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.POSITIVE)
            }
            .create().also {
                isCancelable = dialogEntity.isCancelable
            }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        if (dialogEntity.negativeButtonText != null) {
            sendDialogResult(dialogEntity, DialogResultCode.NEGATIVE)
        } else {
            sendDialogResult(dialogEntity, DialogResultCode.POSITIVE)
        }
    }
}
