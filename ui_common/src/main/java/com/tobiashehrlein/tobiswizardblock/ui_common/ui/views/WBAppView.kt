package com.tobiashehrlein.tobiswizardblock.ui_common.ui.views

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.ui_common.R
import com.tobiashehrlein.tobiswizardblock.ui_common.databinding.WidgetWbAppViewBinding
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater

class WBAppView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetWbAppViewBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_wb_app_view,
        this,
        true
    )

    fun setAppIcon(appIcon: Drawable) {
        binding.appViewImage.setImageDrawable(appIcon)
    }

    fun setAppName(appName: String) {
        binding.appViewText.text = appName
    }
}
