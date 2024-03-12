package com.example.structuremonitoringsystem.Testing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.example.structuremonitoringsystem.Excel.ExportDataToExcel;
import com.example.structuremonitoringsystem.MPAndroidLineChart.Seismograph;
import com.example.structuremonitoringsystem.OtherFunction.GlobalVariable;
import com.example.structuremonitoringsystem.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public class LineChartView extends AppCompatActivity {

    private Seismograph dataLogSeismograph, realtimeSeismograph;
    private ExportDataToExcel exportDataToExcel;
    private GlobalVariable globalVariable;
    private LineChart logSeismograph, rlSeismograph;
    private AppCompatButton zeroBtn, randBtn, exportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_line_chart_view);

        //initializing the graph on the layout
        rlSeismograph = (LineChart) findViewById(R.id.lChart);
        logSeismograph = (LineChart) findViewById(R.id.lChart2);

        zeroBtn = (AppCompatButton) findViewById(R.id.addZeroData);
        randBtn = (AppCompatButton) findViewById(R.id.addRandomData);
        exportBtn = (AppCompatButton) findViewById(R.id.exportData);

        //setting what the graph will handle
        dataLogSeismograph = new Seismograph(logSeismograph);
        realtimeSeismograph = new Seismograph(rlSeismograph);
        exportDataToExcel = new ExportDataToExcel(LineChartView.this);

        //random values of data logging
        ArrayList<Entry> yValues = new ArrayList<>();

        for(int x = 0 ; x < 300; x ++){
            if(x >50 && x <250){
                double randomYVal = globalVariable.getRandomDouble(-5.0, 5.0);
                yValues.add(new Entry(x, (float) randomYVal));
            }
            else {
                yValues.add(new Entry(x, 0f));
            }

        }



        //setting the ui of the graphs
        dataLogSeismograph.dataLoggingSeismograph(yValues);
        realtimeSeismograph.realTimeSeismograph();


        zeroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realtimeSeismograph.addRealtimeEntry(0);
            }

        });

        randBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realtimeSeismograph.addRealtimeEntry(globalVariable.getRandomDouble(-10, 10));
            }
        });

        exportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exportDataToExcel.exportData(yValues);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}

