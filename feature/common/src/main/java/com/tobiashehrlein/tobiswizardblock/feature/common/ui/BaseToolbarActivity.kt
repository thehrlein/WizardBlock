package com.tobiashehrlein.tobiswizardblock.feature.common.ui

import android.graphics.Rect
import android.os.Build
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import com.tobiashehrlein.tobiswizardblock.core.presentation.general.BaseToolbarViewModel
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.feature.common.BR
import com.tobiashehrlein.tobiswizardblock.feature.common.R
import com.tobiashehrlein.tobiswizardblock.feature.common.databinding.ActivityBaseToolbarBinding
import com.tobiashehrlein.tobiswizardblock.feature.common.ui.views.BaseToolbar


abstract class BaseToolbarActivity<Model : BaseToolbarViewModel, Binding : ViewDataBinding> :
    BaseActivity<Model, ActivityBaseToolbarBinding>() {

    final override val layoutRes: Int = R.layout.activity_base_toolbar
    final override val viewModelVariableId: Int = BR.toolbarViewModel
    protected abstract var toolbarButtonType: ToolbarButtonType
    protected abstract val toolbarTitle: String?
    protected abstract val contentViewModelResId: Int

    @get:LayoutRes
    protected abstract val contentLayoutRes: Int
    protected lateinit var contentBinding: ViewDataBinding
        private set

    open fun onToolbarButtonClicked() {
        when (toolbarButtonType) {
            ToolbarButtonType.Close -> finish()
            ToolbarButtonType.Back -> onBackPressed()
            else -> return
        }
    }

    final override fun onBindingCreated() {
        super.onBindingCreated()
        val activityBaseToolbarBinding = binding
        setUpToolbar(activityBaseToolbarBinding.baseToolbar)
        setUpToolbarViewModel()
        createContentBinding(activityBaseToolbarBinding.baseContentContainer)
        adjustStatusBarHeight()
    }

    private fun adjustStatusBarHeight() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 and above, use WindowInsets to get the status bar height
            ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
                val bars = insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                        or WindowInsetsCompat.Type.displayCutout()
                )
                binding.statusBarBackground.layoutParams.height = bars.top
                WindowInsetsCompat.CONSUMED
            }
        } else {
            // For older versions, use a hardcoded value or a custom method to get the status bar height
            val rectangle = Rect()
            val window = getWindow()
            window.decorView.getWindowVisibleDisplayFrame(rectangle)
            binding.statusBarBackground.layoutParams.height = rectangle.top
        }
    }

    private fun createContentBinding(
        container: ViewGroup
    ) {
        DataBindingUtil.inflate<Binding>(layoutInflater, contentLayoutRes, container, true).also {
            contentBinding = it
            contentBinding.lifecycleOwner = provideLifecycleOwner()
            contentBinding.setVariable(contentViewModelResId, viewModel)
            onContentBindingCreated()
        }
    }

    private fun setUpToolbar(toolbar: BaseToolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }
    }

    private fun setUpToolbarViewModel() {
        viewModel.apply {
            setToolbarButton(toolbarButtonType)
            this@BaseToolbarActivity.toolbarTitle?.let {
                setTitle(it)
            }

            toolbarEvent.observe(this@BaseToolbarActivity) {
                onToolbarButtonClicked()
            }

            toolbarButton.observe(this@BaseToolbarActivity) {
                toolbarButtonType = it
            }
        }
    }

    open fun onContentBindingCreated() = Unit

    private fun provideLifecycleOwner(): LifecycleOwner = this
}
