package com.tobiashehrlein.tobiswizardblock.ui_common.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.ui_common.R
import com.tobiashehrlein.tobiswizardblock.ui_common.databinding.WidgetWbInputViewBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater

class WBInputView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetWbInputViewBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_wb_input_view,
        this,
        true
    )

    fun setName(text: String?) {
        if (text != getName()) {
            binding.autoCompleteText.setText(text)
        }
    }

    fun getName(): String? {
        return binding.autoCompleteText.text.toString()
    }

    fun setHint(hint: String) {
        binding.textInputLayoutOutlined.hint = hint
    }

    fun addTextChangeListener(textChangeListener: (String) -> Unit) {
        binding.autoCompleteText.addTextChangedListener { editable ->
            editable?.let {
                textChangeListener.invoke(it.toString())
            }
        }
    }

    fun setAdapter(items: Set<String>) {
        binding.autoCompleteText.setAdapter(
            ArrayAdapter(
                context,
                R.layout.widget_auto_complete_text_row,
                items.toList()
            )
        )
    }

    fun setError(errorText: String?) {
        binding.textInputLayoutOutlined.apply {
            if (errorText.isNullOrEmpty()) {
                error = null
                isErrorEnabled = false
            } else {
                error = errorText
                isErrorEnabled = true
            }
        }
    }
}
