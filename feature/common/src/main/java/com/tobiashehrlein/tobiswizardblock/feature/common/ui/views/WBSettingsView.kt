package com.tobiashehrlein.tobiswizardblock.feature.common.ui.views

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.feature.common.R
import com.tobiashehrlein.tobiswizardblock.feature.common.databinding.WidgetWbSettingsViewBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater

class WBSettingsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetWbSettingsViewBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_wb_settings_view,
        this,
        true
    )

    fun setSwitchText(text: String) {
        binding.settingsViewSwitch.text = text
    }

    fun setChecked(checked: Boolean) {
        binding.settingsViewSwitch.isChecked = checked
    }

    fun isChecked(): Boolean {
        return binding.settingsViewSwitch.isChecked
    }

    fun showInfoIcon(show: Boolean) {
        binding.settingsViewInfoIcon.isVisible = show
    }

    fun disableSwitch(disable: Boolean) {
        binding.settingsViewSwitch.apply {
            isClickable = !disable
        }
    }

    fun onCheckedChange(onCheckedChange: (Boolean) -> Unit) {
        binding.settingsViewSwitch.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange.invoke(isChecked)
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        binding.settingsViewSwitch.isEnabled = enabled
    }

    fun setOnInfoIconClickListener(onClickListener: OnClickListener) {
        binding.settingsViewInfoIcon.setOnClickListener(onClickListener)
    }
}
