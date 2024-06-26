package com.tobiashehrlein.tobiswizardblock.ui_statistics

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.entities.statistics.TopPointsStatisticsData
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.ui_statistics.databinding.WidgetStatisticsTopPointsRowBinding

class TopPointsStatisticsRowView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetStatisticsTopPointsRowBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_statistics_top_points_row,
        this,
        true
    )

    fun setData(topPointsStatisticsData: TopPointsStatisticsData) {
        binding.position.text = context.getString(com.tobiashehrlein.tobiswizardblock.ui_common.R.string.statistics_most_wins_position_placeholder, topPointsStatisticsData.position)
        binding.playerName.text = topPointsStatisticsData.playerName
        binding.points.text = topPointsStatisticsData.points.toString()
    }
}