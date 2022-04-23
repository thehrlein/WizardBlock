package com.tobiashehrlein.tobiswizardblock.ui_statistics

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.ui_statistics.databinding.WidgetStatisticsGamesPlayedBinding


class GamesPlayedStatisticsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetStatisticsGamesPlayedBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_statistics_games_played,
        this,
        true
    )

    fun setGamesPlayedStatistics(gamesPlayed: Int?) {
        if (gamesPlayed == null) return
        binding.statisticsGamesPlayedCount.text = gamesPlayed.toString()
    }
}