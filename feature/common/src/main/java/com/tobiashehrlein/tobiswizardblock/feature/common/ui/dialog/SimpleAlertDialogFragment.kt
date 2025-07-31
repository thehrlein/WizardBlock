package com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.dialog.utils.DialogResultCode
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.getSerializableSafe

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
        val dialogEntity =
            requireArguments().getSerializableSafe(DialogEntity.KEY_DIALOG_ENTITY) as? DialogEntity.Text
        dialogEntity ?: throw IllegalArgumentException("DialogEntity cannot be null")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(dialogEntity.title))

        dialogEntity.message?.let {
            builder.setMessage(getString(it))
        }
        dialogEntity.messageString?.let {
            builder.setMessage(it)
        }
        dialogEntity.neutralButtonText?.let {
            builder.setNeutralButton(it) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.NEUTRAL)
            }
        }
        dialogEntity.negativeButtonText?.let {
            builder.setNegativeButton(it) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.NEGATIVE)
            }
        }
        dialogEntity.positiveButtonText?.let {
            builder.setPositiveButton(it) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.POSITIVE)
            }
        }

        return builder.create().also {
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
