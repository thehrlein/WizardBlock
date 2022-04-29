package com.tobiashehrlein.tobiswizardblock.ui_statistics

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.entities.statistics.MostWinStatisticsData
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.ui_statistics.databinding.WidgetStatisticsMostWinRowBinding

class MostWinsRowView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetStatisticsMostWinRowBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_statistics_most_win_row,
        this,
        true
    )

    fun setData(mostWinStatisticsData: MostWinStatisticsData) {
        binding.position.text = context.getString(R.string.statistics_most_wins_position_placeholder, mostWinStatisticsData.position)
        binding.playerName.text = mostWinStatisticsData.playerName
        binding.wins.text = mostWinStatisticsData.wins.toString()
    }
}