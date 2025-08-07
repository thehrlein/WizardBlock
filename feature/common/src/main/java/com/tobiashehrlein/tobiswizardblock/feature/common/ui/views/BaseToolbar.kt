package com.tobiashehrlein.tobiswizardblock.feature.common.ui.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.ImageViewCompat
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.core.entities.extension.checkAllMatched
import com.tobiashehrlein.tobiswizardblock.core.entities.general.OnToolbarButtonClickListener
import com.tobiashehrlein.tobiswizardblock.core.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.feature.common.R
import com.tobiashehrlein.tobiswizardblock.feature.common.databinding.WidgetToolbarBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.EllipsizeAttribute

class BaseToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.toolbarStyle,
    defStyleRes: Int = 0
) : Toolbar(context, attrs, defStyleAttr) {

    private var listener: OnToolbarButtonClickListener? = null

    private val binding: WidgetToolbarBinding = DataBindingUtil.inflate(
        LayoutInflater.from(context),
        R.layout.widget_toolbar,
        this,
        true
    )

    init {
        val typedArray =
            context.obtainStyledAttributes(
                attrs,
                R.styleable.BaseToolbar,
                defStyleAttr,
                defStyleRes
            )

        typedArray.apply {
            val titleGravity = getInt(R.styleable.BaseToolbar_android_gravity, 0)
            val iconTint = getColor(R.styleable.BaseToolbar_android_buttonTint, 0)
            val maxLines = getInt(R.styleable.BaseToolbar_android_maxLines, 1)
            val ellipsize = getInt(R.styleable.BaseToolbar_android_ellipsize, 1).let {
                EllipsizeAttribute.getEllipsize(it).truncateAt
            }

            binding.apply {
                toolbarTitle.gravity = titleGravity
                toolbarTitle.maxLines = maxLines
                toolbarTitle.ellipsize = ellipsize
                ImageViewCompat.setImageTintList(toolbarIcon, ColorStateList.valueOf(iconTint))
            }
            recycle()
        }
        binding.toolbarIcon.setOnClickListener {
            listener?.onButtonClicked()
        }
    }

    fun setToolbarTitle(title: String?) {
        binding.toolbarTitle.text = title
    }

    fun toolbarButton(toolbarButtonType: ToolbarButtonType) {
        binding.toolbarIcon.setImageResource(getButtonDrawable(toolbarButtonType))
    }

    fun onToolbarButtonClick(toolbarButtonClickListener: OnToolbarButtonClickListener) {
        this.listener = toolbarButtonClickListener
    }

    @DrawableRes
    private fun getButtonDrawable(toolbarButtonType: ToolbarButtonType): Int =
        when (toolbarButtonType) {
            ToolbarButtonType.Back -> R.drawable.wb_ic_arrow_back
            ToolbarButtonType.Close -> R.drawable.wb_ic_close
            else -> android.R.color.transparent
        }.checkAllMatched
}
