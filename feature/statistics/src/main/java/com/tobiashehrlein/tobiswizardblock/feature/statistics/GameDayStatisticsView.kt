package com.tobiashehrlein.tobiswizardblock.feature.statistics

import android.content.Context
import android.os.Build
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
import com.tobiashehrlein.tobiswizardblock.core.entities.statistics.GameDayStatisticsData
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.getColorReference
import com.tobiashehrlein.tobiswizardblock.feature.common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.feature.statistics.databinding.WidgetStatisticsGameDayBinding
import java.text.DateFormatSymbols
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.Locale

class GameDayStatisticsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetStatisticsGameDayBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_statistics_game_day,
        this,
        true
    )
    private val textSizeLabels: Float
        get() = if (BuildConfig.FLAVOR == "automotive") {
            24f
        } else {
            12f
        }

    fun setGameDayStatistics(gameDayStatisticsData: GameDayStatisticsData?) {
        if (gameDayStatisticsData == null || gameDayStatisticsData.gameDays.isEmpty()) {
            binding.statisticsGameDayChart.apply {
                setNoDataText(
                    context.getString(
                        com.tobiashehrlein.tobiswizardblock.feature.common.R.string.statistics_player_no_data_available
                    )
                )
                data = null
                invalidate()
            }
        } else {
            binding.statisticsGameDayChart.apply {
                val entries: ArrayList<BarEntry> = ArrayList()

                gameDayStatisticsData.gameDays.forEach { entry ->
                    // Use ordinal+1 (MONDAY.ordinal==0) to get 1..7 without calling DayOfWeek.getValue (API 26)
                    val dayIndex = entry.key.ordinal + 1
                    entries.add(BarEntry(dayIndex.toFloat(), entry.value.toFloat()))
                }
                val barDataSet = BarDataSet(entries, "").apply {
                    // Changing the color of the bar
                    color = ContextCompat.getColor(context, com.tobiashehrlein.tobiswizardblock.feature.common.R.color.color_primary)
                    // Setting the size of the form in the legend
                    formSize = 15f
                    // showing the value of the bar, default true if not set
                    setDrawValues(false)
                    // setting the text size of the value of the bar
                    valueTextSize = textSizeLabels
                }
                val data = BarData(barDataSet)
                setData(data)

                // Use a marker that shows the full weekday name. Use helper for API compatibility.
                marker = WizardMarkerView(context) { dayValue ->
                    getDayNameFull(dayValue)
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
                            // value is expected to be 1..7 (DayOfWeek.value where MONDAY=1)
                            val dayInt = value.toInt().coerceIn(1, 7)
                            return getDayNameShort(dayInt)
                        }
                    }
                    textColor = context.getColorReference(com.google.android.material.R.attr.colorOnBackground)
                    textSize = textSizeLabels
                }

                axisLeft.apply {
                    removeAllLimitLines()
                    resetAxisMaximum()
                    resetAxisMinimum()
                    // hiding the left y-axis line, default true if not set
                    setDrawAxisLine(false)
                    granularity = 1f
                    axisMinimum = 0.0f
                    axisMaximum = gameDayStatisticsData.gameDays.values.maxOf { it }.toFloat()
                    textColor = context.getColorReference(com.google.android.material.R.attr.colorOnBackground)
                    textSize = textSizeLabels
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

    // Helper: returns the localized short weekday name for a DayOfWeek-like integer (1=MON ... 7=SUN)
    private fun getDayNameShort(dayValue: Int): String {
        val day = dayValue.coerceIn(1, 7)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DayOfWeek.of(day).getDisplayName(TextStyle.SHORT, Locale.getDefault())
        } else {
            // DateFormatSymbols.shortWeekdays is indexed by Calendar (1=Sunday ... 7=Saturday)
            val shortWeekdays = DateFormatSymbols.getInstance(Locale.getDefault()).shortWeekdays
            // Map DayOfWeek value (1=Mon .. 7=Sun) to Calendar index: index = (day % 7) + 1
            val idx = (day % 7) + 1
            shortWeekdays.getOrNull(idx) ?: day.toString()
        }
    }

    // Helper: returns the localized full weekday name for a DayOfWeek-like integer (1=MON ... 7=SUN)
    private fun getDayNameFull(dayValue: Int): String {
        val day = dayValue.coerceIn(1, 7)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DayOfWeek.of(day).getDisplayName(TextStyle.FULL, Locale.getDefault())
        } else {
            val weekdays = DateFormatSymbols.getInstance(Locale.getDefault()).weekdays
            val idx = (day % 7) + 1
            weekdays.getOrNull(idx) ?: day.toString()
        }
    }
}
