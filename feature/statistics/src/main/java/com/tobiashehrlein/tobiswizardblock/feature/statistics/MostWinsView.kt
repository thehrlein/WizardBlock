package com.tobiashehrlein.tobiswizardblock.feature.statistics

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.feature.statistics.databinding.WidgetStatisticsMostWinsBinding

class MostWinsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetStatisticsMostWinsBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_statistics_most_wins,
        this,
        true
    )

    fun setPlayerCountStatistics(mostWins: List<MostWinStatisticsData>?) {
        binding.statisticsMostWinsList.removeAllViews()
        if (mostWins == null) return

        mostWins.map { data ->
            MostWinsRowView(context).apply {
                setData(data)
            }
        }.forEach { view ->
            binding.statisticsMostWinsList.addView(view)
        }
    }
}
