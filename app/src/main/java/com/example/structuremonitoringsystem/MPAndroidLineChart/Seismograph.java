package com.example.structuremonitoringsystem.MPAndroidLineChart;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class Seismograph{

    private LineChart chart;

    public Seismograph(LineChart chart){
        this.chart = chart;
    };


    //initializing the data logging line graph
    public void dataLoggingSeismograph(ArrayList<Entry> yValues){

        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        LineDataSet lineDataSet1 = new LineDataSet(yValues, "Data Set 1");

        lineDataSet1.setFillAlpha(110);

        lineDataSet1.setColor(Color.BLACK);
        lineDataSet1.setLineWidth(1f);
        lineDataSet1.setHighlightEnabled(false);
        lineDataSet1.setDrawValues(false);
        lineDataSet1.setDrawCircles(false);
//        lineDataSet1.setValueTextSize(10f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet1);

        LineData lineData = new LineData(dataSets);



        chart.setData(lineData);
    }

    //initializing the realtime line graph
    public void realTimeSeismograph(){

        chart.getDescription().setEnabled(true);
        chart.getDescription().setText("Real Time Chart");

        chart.setTouchEnabled(false);
        chart.setDragEnabled(true);
        chart.setScaleEnabled(false);
        chart.setDrawGridBackground(false);
        chart.setPinchZoom(false);
        chart.setBackgroundColor(Color.WHITE);

        LineData realTimeLineData = new LineData();
        realTimeLineData.setValueTextColor(Color.BLACK);

        chart.setData(realTimeLineData);

        // get the legend (only possible after setting data)
        Legend l = chart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);

        XAxis xl = chart.getXAxis();
//        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = chart.getAxisLeft();
//        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(true);
//        leftAxis.setDrawGridLines(true);
        leftAxis.setEnabled(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        chart.getAxisLeft().setDrawGridLines(true);
        chart.getXAxis().setDrawGridLines(true);
        chart.setDrawBorders(false);

    }


    //updating the data of the line graph
    public void addRealtimeEntry(double input) {

        LineData data = chart.getData();

        if (data != null) {

            ILineDataSet set = data.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well

            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }

//            data.addEntry(new Entry(set.getEntryCount(), (float) (Math.random() * 80) + 10f), 0);
            data.addEntry(new Entry(set.getEntryCount(), (float) input), 0);
            data.notifyDataChanged();

            // let the chart know it's data has changed
            chart.notifyDataSetChanged();

            // limit the number of visible entries
            chart.setVisibleXRangeMaximum(150);
            // mChart.setVisibleYRange(30, AxisDependency.LEFT);

            // move to the latest entry
            chart.moveViewToX(data.getEntryCount());

        }
    }
    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "Dynamic Data");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(1f);
        set.setColor(Color.BLACK);
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setCubicIntensity(0.2f);
        return set;
    }

}

