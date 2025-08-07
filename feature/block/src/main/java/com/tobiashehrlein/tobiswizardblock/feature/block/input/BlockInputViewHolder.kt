package com.tobiashehrlein.tobiswizardblock.feature.block.input

import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.tobiashehrlein.tobiswizardblock.core.entities.game.input.InputDataItem
import com.tobiashehrlein.tobiswizardblock.core.presentation.block.input.BlockInputInteractions
import com.tobiashehrlein.tobiswizardblock.feature.block.databinding.ItemBlockInputBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.executeAfter

class BlockInputViewHolder(private val binding: ItemBlockInputBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(
        inputDataItem: InputDataItem,
        interactions: BlockInputInteractions
    ) {
        bindInputData(inputDataItem)
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                if (fromUser) {
                    inputDataItem.userInput = progress
                    bindInputData(inputDataItem)
                    interactions.onInputChanged(inputDataItem)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // not needed
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // not needed
            }
        })

        binding.buttonDecrease.setOnClickListener {
            if (inputDataItem.userInput > 0) {
                inputDataItem.userInput = inputDataItem.userInput - 1
                bindInputData(inputDataItem)
                interactions.onInputChanged(inputDataItem)
            }
        }

        binding.buttonIncrease.setOnClickListener {
            if (inputDataItem.userInput < inputDataItem.cards) {
                inputDataItem.userInput = inputDataItem.userInput + 1
                bindInputData(inputDataItem)
                interactions.onInputChanged(inputDataItem)
            }
        }
    }

    fun bindInputData(inputDataItem: InputDataItem) {
        binding.executeAfter {
            this.item = inputDataItem
        }

        binding.buttonDecrease.isEnabled = inputDataItem.userInput > inputDataItem.minInput
        binding.buttonIncrease.isEnabled = inputDataItem.userInput != inputDataItem.currentRound
        binding.cloudCardPlayedHint.text = binding.root.context.getString(
            com.tobiashehrlein.tobiswizardblock.feature.common.R.string.block_input_anniversary_version_cloud_card_played_hint,
            inputDataItem.player
        )
    }
}
