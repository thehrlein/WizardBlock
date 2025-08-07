package com.tobiashehrlein.tobiswizardblock.feature.statistics

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.tobiashehrlein.tobiswizardblock.core.entities.statistics.GameRulesStatisticsData
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.getColorReference
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.feature.statistics.databinding.WidgetStatisticsGameRulesBinding

private const val TIPS_EQUAL_STITCHES_X_ENTRY = 1
private const val TIPS_EQUAL_STITCHES_FIRST_ROUND_X_ENTRY = 2
private const val ANNIVERSARY_VERSION_X_ENTRY = 3

class GameRulesStatisticsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetStatisticsGameRulesBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_statistics_game_rules,
        this,
        true
    )

    fun setGameRulesStatistics(gameRulesStatisticsData: GameRulesStatisticsData?) {
        if (gameRulesStatisticsData == null || gameRulesStatisticsData.noGamesPlayed) {
            binding.statisticsGameRulesChart.apply {
                setNoDataText(
                    context.getString(
                        com.tobiashehrlein.tobiswizardblock.feature.common.R.string.statistics_player_no_data_available
                    )
                )
                data = null
                invalidate()
            }
        } else {
            binding.statisticsGameRulesChart.apply {
                val entries: ArrayList<BarEntry> = ArrayList()

                entries.add(
                    BarEntry(
                        TIPS_EQUAL_STITCHES_X_ENTRY.toFloat(),
                        gameRulesStatisticsData.tipsEqualStitches.toFloat()
                    )
                )
                entries.add(
                    BarEntry(
                        TIPS_EQUAL_STITCHES_FIRST_ROUND_X_ENTRY.toFloat(),
                        gameRulesStatisticsData.tipsEqualStitchesFirstRound.toFloat()
                    )
                )
                entries.add(
                    BarEntry(
                        ANNIVERSARY_VERSION_X_ENTRY.toFloat(),
                        gameRulesStatisticsData.anniversaryVersion.toFloat()
                    )
                )

                val barDataSet = BarDataSet(entries, "").apply {
                    // Changing the color of the bar
                    color = ContextCompat.getColor(context, com.tobiashehrlein.tobiswizardblock.feature.common.R.color.color_primary)
                    // Setting the size of the form in the legend
                    formSize = 15f
                    // showing the value of the bar, default true if not set
                    setDrawValues(false)
                    // setting the text size of the value of the bar
                    valueTextSize = 12f
                }
                val data = BarData(barDataSet)
                setData(data)

                marker = WizardMarkerView(context) {
                    when (it) {
                        TIPS_EQUAL_STITCHES_X_ENTRY -> context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.statistics_game_rules_bets_equal_stitches_tooltip)
                        TIPS_EQUAL_STITCHES_FIRST_ROUND_X_ENTRY -> context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.statistics_game_rules_bets_equal_stitches_first_round_tooltip)
                        else -> context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.statistics_game_rules_bets_equal_stitches_anniversary_version_tooltip)
                    }
                }

                // hiding the grey background of the chart, default false if not set
                setDrawGridBackground(false)
                // remove the bar shadow, default false if not set
                setDrawBarShadow(false)
                // remove border of the chart, default false if not set
                setDrawBorders(false)
                // remove the description label text located at the lower right corner
                description = Description().apply {
                    isEnabled = false
                }
                // setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
                animateY(1000)
                // setting animation for x-axis, the bar will pop up separately within the time we set
                animateX(1000)
                // disable scaling
                setScaleEnabled(false)

                xAxis.apply {
                    // change the position of x-axis to the bottom
                    position = XAxis.XAxisPosition.BOTTOM
                    // set the horizontal distance of the grid line
                    granularity = 1f
                    // hiding the x-axis line, default true if not set
                    setDrawAxisLine(false)
                    // hiding the vertical grid lines, default true if not set
                    setDrawGridLines(false)
                    valueFormatter = object : ValueFormatter() {
                        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                            return when (value.toInt()) {
                                TIPS_EQUAL_STITCHES_X_ENTRY -> context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.statistics_game_rules_bets_equal_stitches_x_label)
                                TIPS_EQUAL_STITCHES_FIRST_ROUND_X_ENTRY -> context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.statistics_game_rules_bets_equal_stitches_first_round_x_label)
                                else -> context.getString(com.tobiashehrlein.tobiswizardblock.feature.common.R.string.statistics_game_rules_bets_equal_stitches_anniversary_version_x_label)
                            }
                        }
                    }
                    textColor = context.getColorReference(com.google.android.material.R.attr.colorOnBackground)
                }

                axisLeft.apply {
                    removeAllLimitLines()
                    resetAxisMaximum()
                    resetAxisMinimum()
                    // hiding the left y-axis line, default true if not set
                    setDrawAxisLine(false)
                    granularity = 1f
                    axisMinimum = 0.0f
                    axisMaximum = listOf(
                        gameRulesStatisticsData.tipsEqualStitches,
                        gameRulesStatisticsData.tipsEqualStitchesFirstRound,
                        gameRulesStatisticsData.anniversaryVersion
                    ).maxOf { it }.takeIf { it > 0 }?.toFloat() ?: 1f
                    textColor = context.getColorReference(com.google.android.material.R.attr.colorOnBackground)
                }

                axisRight.apply {
                    setDrawLabels(false)
                    setDrawAxisLine(false)
                    isEnabled = false
                }

                legend.apply {
                    // setting the shape of the legend form to line, default square shape
                    form = Legend.LegendForm.NONE

                    // setting the text size of the legend
                    textSize = 11f
                    // setting the alignment of legend toward the chart
                    verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                    horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                    // setting the stacking direction of legend
                    orientation = Legend.LegendOrientation.HORIZONTAL
                    // setting the location of legend outside the chart, default false if not set
                    setDrawInside(false)
                }

                invalidate()
            }
        }
    }
}
