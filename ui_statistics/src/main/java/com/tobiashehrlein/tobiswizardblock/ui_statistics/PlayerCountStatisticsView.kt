package com.tobiashehrlein.tobiswizardblock.ui_statistics

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
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.getColorReference
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.layoutInflater
import com.tobiashehrlein.tobiswizardblock.ui_statistics.databinding.WidgetStatisticsGamePlayerCountBinding


class PlayerCountStatisticsView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attributeSet, defStyleAttr) {

    private val binding: WidgetStatisticsGamePlayerCountBinding = DataBindingUtil.inflate(
        context.layoutInflater,
        R.layout.widget_statistics_game_player_count,
        this,
        true
    )

    fun setPlayerCountStatistics(playerCount: Map<Int, Int>?) {
        if (playerCount == null) return
        binding.statisticsPlayerCountChart.apply {
            val entries: ArrayList<BarEntry> = ArrayList()
            (2..6).forEach {
                entries.add(BarEntry(it.toFloat(), playerCount.getOrDefault(it, 0).toFloat()))
            }
            val barDataSet = BarDataSet(entries, "").apply {
                //Changing the color of the bar
                color = ContextCompat.getColor(context, R.color.color_primary)
                //Setting the size of the form in the legend
                formSize = 15f
                //showing the value of the bar, default true if not set
                setDrawValues(false)
                //setting the text size of the value of the bar
                valueTextSize = 12f
            }
            val data = BarData(barDataSet)
            setData(data)

            //hiding the grey background of the chart, default false if not set
            setDrawGridBackground(false)
            //remove the bar shadow, default false if not set
            setDrawBarShadow(false)
            //remove border of the chart, default false if not set
            setDrawBorders(false)
            //remove the description label text located at the lower right corner
            description = Description().apply {
                isEnabled = false
            }
            //setting animation for y-axis, the bar will pop up from 0 to its value within the time we set
            animateY(1000)
            //setting animation for x-axis, the bar will pop up separately within the time we set
            animateX(1000)
            // disable scaling
            setScaleEnabled(false)

            xAxis.apply {
                //change the position of x-axis to the bottom
                position = XAxis.XAxisPosition.BOTTOM
                //set the horizontal distance of the grid line
                granularity = 1f
                //hiding the x-axis line, default true if not set
                setDrawAxisLine(false)
                //hiding the vertical grid lines, default true if not set
                setDrawGridLines(false)
                valueFormatter = object : ValueFormatter() {
                    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                        return value.toInt().toString()
                    }
                }
                textColor = context.getColorReference(R.attr.colorOnBackground)
            }

            val yAxisFormatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return value.toInt().toString()
                }
            }

            axisLeft.apply {
                removeAllLimitLines()
                resetAxisMaximum()
                resetAxisMinimum()
                //hiding the left y-axis line, default true if not set
                setDrawAxisLine(false)
                granularity = 1f
//                valueFormatter = yAxisFormatter
                axisMinimum = 0.0f
                axisMaximum = playerCount.values.maxOf { it }.toFloat()
                textColor = context.getColorReference(R.attr.colorOnBackground)

            }

            axisRight.apply {
                setDrawLabels(false)
                setDrawAxisLine(false)
                isEnabled = false
            }

            legend.apply {
                //setting the shape of the legend form to line, default square shape
                form = Legend.LegendForm.NONE

                //setting the text size of the legend
                textSize = 11f
                //setting the alignment of legend toward the chart
                verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
                horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
                //setting the stacking direction of legend
                orientation = Legend.LegendOrientation.HORIZONTAL
                //setting the location of legend outside the chart, default false if not set
                setDrawInside(false)
            }

            invalidate()
        }
    }
}