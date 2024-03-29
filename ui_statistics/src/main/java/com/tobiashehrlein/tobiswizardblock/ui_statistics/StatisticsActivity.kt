package com.tobiashehrlein.tobiswizardblock.ui_statistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiashehrlein.tobiswizardblock.entities.general.ToolbarButtonType
import com.tobiashehrlein.tobiswizardblock.presentation.statistics.StatisticsViewModel
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.BaseToolbarActivity
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogInteractor
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.dispatchOnDialogResult
import com.tobiashehrlein.tobiswizardblock.ui_statistics.databinding.ActivityStatisticsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatisticsActivity : BaseToolbarActivity<StatisticsViewModel, ActivityStatisticsBinding>(), DialogInteractor {

    override val viewModel: StatisticsViewModel by viewModel()
    override val navHostFragment: Int = R.id.activity_statistics_nav_host_fragment
    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.Back
    override val toolbarTitle: String
        get() = getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.statistics_toolbar_title)
    override val contentViewModelResId: Int = BR.viewModel
    override val contentLayoutRes: Int = R.layout.activity_statistics

    companion object {
        fun start(activity: AppCompatActivity) {
            activity.startActivity(Intent(activity, StatisticsActivity::class.java))
        }
    }

    override fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
        supportFragmentManager.dispatchOnDialogResult(requestCode, resultCode, data)
    }
}