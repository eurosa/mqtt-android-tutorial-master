package helpers

import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

/**
 * Created by ranojan on 11/22/2019.
 */
class ChartHelper( var mChart: LineChart) : OnChartValueSelectedListener {
    fun setChart(chart: LineChart) {
        mChart = chart
    }

    fun addEntry(value: Float) {
        val data = mChart.data
        if (data != null) {
            var set = data.getDataSetByIndex(0)
            // set.addEntry(...); // can be called as well
            if (set == null) {
                set = createSet()
                data.addDataSet(set)
            }
            data.addEntry(Entry(set.entryCount.toFloat(), value), 0)
            Log.w("anjing", set.getEntryForIndex(set.entryCount - 1).toString())
            data.notifyDataChanged()
            // let the chart know it's data has changed
            mChart.notifyDataSetChanged()
            // limit the number of visible entries
            mChart.setVisibleXRangeMaximum(10f)
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);
// move to the latest entry
            mChart.moveViewTo(set.entryCount - 1.toFloat(), data.yMax, YAxis.AxisDependency.LEFT)
            // this automatically refreshes the chart (calls invalidate())
// mChart.moveViewTo(data.getXValCount()-7, 55f,
// AxisDependency.LEFT);
        }
    }

    private fun createSet(): LineDataSet {
        val set = LineDataSet(null, "Data")
        set.axisDependency = YAxis.AxisDependency.LEFT
        set.color = Color.rgb(67, 164, 34)
        //set.setCircleColor(Color.WHITE);
        set.lineWidth = 2f
        //set.setCircleRadius(4f);
        set.fillAlpha = 65
        set.fillColor = Color.rgb(67, 164, 34)
        set.highLightColor = Color.rgb(67, 164, 34)
        set.valueTextColor = Color.rgb(67, 164, 34)
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        Log.i("Entry selected", e.toString())
    }

    override fun onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.")
    }

    init {
        mChart.setOnChartValueSelectedListener(this)
        // no description text
        mChart.setNoDataText("You need to provide data for the chart.")
        // enable touch gestures
        mChart.setTouchEnabled(true)
        // enable scaling and dragging
        mChart.isDragEnabled = true
        mChart.setScaleEnabled(true)
        mChart.setDrawGridBackground(false)
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true)
        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE)
        mChart.setBorderColor(Color.rgb(67, 164, 34))
        val data = LineData()
        data.setValueTextColor(Color.WHITE)
        // add empty data
        mChart.data = data
        // get the legend (only possible after setting data)
        val l = mChart.legend
        // modify the legend ...
// l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.form = Legend.LegendForm.LINE
        l.typeface = Typeface.MONOSPACE
        l.textColor = Color.rgb(67, 164, 34)
        val xl = mChart.xAxis
        xl.typeface = Typeface.MONOSPACE
        xl.textColor = Color.rgb(67, 164, 34)
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true
        val leftAxis = mChart.axisLeft
        leftAxis.typeface = Typeface.MONOSPACE
        leftAxis.textColor = Color.rgb(67, 164, 34)
        leftAxis.setDrawGridLines(true)
        val rightAxis = mChart.axisRight
        rightAxis.isEnabled = false
    }
}