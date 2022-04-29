package com.tobiashehrlein.tobiswizardblock.ui_statistics

import android.content.Context
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class WizardMarkerView(
    context: Context,
    private val convertValue: (Int) -> String
) : MarkerView(context, R.layout.widget_wizard_graph_marker) {

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.x?.toInt()?.let { xValue ->
            val textView = findViewById<TextView>(R.id.wizard_graph_marker_text).apply {
                text = convertValue.invoke(xValue)
                measure(0, 0)

            }

            findViewById<ConstraintLayout>(R.id.wizard_graph_marker_layout).apply {
                layoutParams = LayoutParams(
                    textView.measuredWidth + paddingStart + paddingEnd,
                    textView.measuredHeight + paddingTop + paddingBottom
                )
            }
        }


        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2).toFloat(), -height.toFloat())
    }
}