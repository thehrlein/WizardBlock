package com.tobiashehrlein.tobiswizardblock.ui_block.input.correcttips

import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tobiashehrlein.tobiswizardblock.presentation.block.input.correcttips.BlockInputCorrectTipsChoosePlayerViewModel
import com.tobiashehrlein.tobiswizardblock.ui_block.BR
import com.tobiashehrlein.tobiswizardblock.ui_block.R
import com.tobiashehrlein.tobiswizardblock.ui_block.databinding.DialogBlockInputCorrectTipsChoosePlayerBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.BaseDialogFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.entity.DialogEntity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogResultCode
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.getDimen
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val CHILD_RADIO_GROUP = 0
private const val CHILD_COUNT = 1
private const val ZERO_TIP = 0

class BlockInputCorrectTipsChoosePlayerDialog :
    BaseDialogFragment<BlockInputCorrectTipsChoosePlayerViewModel, DialogBlockInputCorrectTipsChoosePlayerBinding>() {

    override val viewModel: BlockInputCorrectTipsChoosePlayerViewModel by viewModel()
    override val layoutId: Int = R.layout.dialog_block_input_correct_tips_choose_player
    override val viewModelVariableId: Int = BR.viewModel

    companion object {
        private val TAG: String = BlockInputCorrectTipsChoosePlayerDialog::class.java.simpleName

        fun show(fragmentManager: FragmentManager, dialogEntity: DialogEntity.Custom) {
            if (fragmentManager.findFragmentByTag(TAG) != null) return
            val bundle = Bundle().apply {
                putSerializable(DialogEntity.KEY_DIALOG_ENTITY, dialogEntity)
            }

            BlockInputCorrectTipsChoosePlayerDialog().apply {
                arguments = bundle
                isCancelable = false
            }.show(fragmentManager, TAG)
        }

        fun dismiss(fragmentManager: FragmentManager) {
            val dialog = fragmentManager.findFragmentByTag(TAG) as? DialogFragment
            dialog?.dismiss()
        }
    }

    private val dialogEntity: DialogEntity.Custom.CorrectTipsChoosePlayer by lazy {
        requireArguments().getSerializable(DialogEntity.KEY_DIALOG_ENTITY) as
                DialogEntity.Custom.CorrectTipsChoosePlayer
    }

    override fun createDialog(savedInstanceState: Bundle?, view: View): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setView(view)
            .setTitle(dialogEntity.title)
            .setPositiveButton(R.string.general_button_proceed, null)
            .setNegativeButton(R.string.general_cancel) { _, _ ->
                sendDialogResult(dialogEntity, DialogResultCode.NEGATIVE)
            }
            .setNeutralButton(R.string.general_back, null)
            .create()

        dialog.setOnShowListener {
            showNeutralButton(dialog, false)
            setPositiveButtonEnabled(false)
        }
        return dialog
    }

    override fun onBindingCreated() {
        super.onBindingCreated()

        dialogEntity.playerTipData.forEach { data ->
            val radio = RadioButton(requireContext()).apply {
                id = data.playerName.hashCode()
                text = getString(
                    R.string.block_input_correct_tips_choose_player_name_and_tip,
                    data.playerName,
                    data.tip
                )
                setTextAppearance(R.style.TextAppearance_WizardBlock_Body1)
                val padding = context.getDimen(R.dimen.space_2)
                setPadding(padding, padding, padding, padding)
                val params = RadioGroup.LayoutParams(
                    RadioGroup.LayoutParams.MATCH_PARENT,
                    RadioGroup.LayoutParams.WRAP_CONTENT
                )
                val margin = context.getDimen(R.dimen.space_1)
                params.setMargins(margin, margin, margin, margin)
                layoutParams = params
            }
            binding.blockInputCorrectTipsRadioGroup.addView(radio)
        }

        binding.blockInputCorrectTipsRadioGroup.setOnCheckedChangeListener { _, _ ->
            setPositiveButtonEnabled(true)
        }
    }

    private fun showNeutralButton(dialog: AlertDialog, show: Boolean) {
        dialog.getButton(
            AlertDialog.BUTTON_NEUTRAL
        )?.apply {
            isVisible = show
            if (show) {
                setOnClickListener {
                    binding.correctTipsViewSwitcher.displayedChild = CHILD_RADIO_GROUP
                    showNeutralButton(dialog, false)
                }
            } else {
                setOnClickListener(null)
            }
        }
    }

    private fun setPositiveButtonEnabled(enabled: Boolean) {
        val dialog = dialog as? AlertDialog ?: return
        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.apply {
            isEnabled = enabled
            if (enabled) {
                setOnClickListener {
                    if (binding.correctTipsViewSwitcher.displayedChild == CHILD_RADIO_GROUP) {
                        setUpCountLayout(dialog)
                    } else {
                        val selectedPlayerTipData = dialogEntity.playerTipData.first {
                            it.playerName.hashCode() == binding.blockInputCorrectTipsRadioGroup.checkedRadioButtonId
                        }.copy(
                            tip = binding.correctTipsLayout.seekBar.progress,
                            correctedCauseOfCloudCard = true
                        )

                        val updatedPlayerTipData = dialogEntity.playerTipData.map {
                            if (it.playerName == selectedPlayerTipData.playerName) {
                                selectedPlayerTipData
                            } else {
                                it
                            }
                        }
                        sendDialogResult(
                            dialogEntity.copy(
                                playerTipData = updatedPlayerTipData
                            ),
                            DialogResultCode.POSITIVE
                        )
                        dismiss()
                    }
                }
            } else {
                setOnClickListener(null)
            }
        }
    }

    private fun setUpCountLayout(dialog: AlertDialog) {
        binding.correctTipsViewSwitcher.displayedChild = CHILD_COUNT
        val selectedPlayerTipData = dialogEntity.playerTipData.first {
            it.playerName.hashCode() == binding.blockInputCorrectTipsRadioGroup.checkedRadioButtonId
        }
        dialog.setTitle(
            getString(
                R.string.block_input_correct_tips_count_title,
                selectedPlayerTipData.playerName
            )
        )
        binding.correctTipsLayout.playerInputValue.text = selectedPlayerTipData.tip.toString()
        binding.correctTipsLayout.seekBar.apply {
            max = dialogEntity.round
            progress = selectedPlayerTipData.tip
            setOnTouchListener { _, _ ->
                performClick()
                true
            }
        }

        binding.correctTipsLayout.buttonDecrease.setOnClickListener {
            val updatedValue = binding.correctTipsLayout.seekBar.progress - 1
            binding.correctTipsLayout.seekBar.progress = updatedValue
            binding.correctTipsLayout.playerInputValue.text = updatedValue.toString()
            checkButtons(selectedPlayerTipData.tip, binding.correctTipsLayout.seekBar.progress)
        }
        binding.correctTipsLayout.buttonIncrease.setOnClickListener {
            val updatedValue = binding.correctTipsLayout.seekBar.progress + 1
            binding.correctTipsLayout.seekBar.progress = updatedValue
            binding.correctTipsLayout.playerInputValue.text = updatedValue.toString()
            checkButtons(selectedPlayerTipData.tip, binding.correctTipsLayout.seekBar.progress)
        }
        showNeutralButton(dialog, true)
        setPositiveButtonEnabled(false)
        checkButtons(selectedPlayerTipData.tip, selectedPlayerTipData.tip)
    }

    private fun checkButtons(initialTip: Int, updatedTip: Int) {
        when {
            updatedTip < initialTip -> {
                binding.correctTipsLayout.buttonDecrease.isEnabled = false
                binding.correctTipsLayout.buttonIncrease.isEnabled = true
                setPositiveButtonEnabled(true)
            }
            updatedTip > initialTip -> {
                binding.correctTipsLayout.buttonDecrease.isEnabled = true
                binding.correctTipsLayout.buttonIncrease.isEnabled = false
                setPositiveButtonEnabled(true)
            }
            else -> {
                val zeroTip = initialTip == ZERO_TIP
                val maxTip = initialTip == dialogEntity.round
                binding.correctTipsLayout.buttonDecrease.isEnabled = !zeroTip
                binding.correctTipsLayout.buttonIncrease.isEnabled = !maxTip
                setPositiveButtonEnabled(false)
            }
        }
    }
}
