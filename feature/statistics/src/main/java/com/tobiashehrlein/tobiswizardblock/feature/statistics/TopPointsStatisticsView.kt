package com.tobiashehrlein.tobiswizardblock.feature.statistics

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.feature.statistics.databinding.WidgetStatisticsTopPointsBinding

class TopPointsStatisticsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetStatisticsTopPointsBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_statistics_top_points,
        this,
        true
    )

    fun setTopPointsStatistics(topPoints: List<TopPointsStatisticsData>?) {
        binding.statisticsTopPointsList.removeAllViews()
        if (topPoints == null) return

        topPoints.map { data ->
            TopPointsStatisticsRowView(context).apply {
                setData(data)
            }
        }.forEach { view ->
            binding.statisticsTopPointsList.addView(view)
        }
    }
}
