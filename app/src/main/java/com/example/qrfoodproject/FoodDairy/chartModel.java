package com.example.qrfoodproject.FoodDairy;

import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;

import java.util.ArrayList;
import java.util.List;

public class chartModel {
    PieChart chart;
    float a,b,percent;
    chartModel(PieChart pieChart,float aa,float bb,float percents){
        chart = pieChart;
        a = aa;
        b = bb;
        percent = percents;
    }
    public void gg() {

        List<PieEntry> strings = new ArrayList<>();
        strings.add(new PieEntry(a));
        strings.add(new PieEntry(b));

        PieDataSet dataSet = new PieDataSet(strings, "Label");
        ArrayList<Integer> colors = new ArrayList<Integer>();
        colors.add(Color.rgb(255, 253, 108));
        colors.add(Color.rgb(107, 142, 35));
        dataSet.setColors(colors);


        PieData pieData = new PieData(dataSet);
        pieData.setDrawValues(true);
        pieData.setValueFormatter(new PercentFormatter());
        chart.setData(pieData);
        Description description = new Description();
        description.setText("");
        description.setEnabled(false);
        chart.setDescription(description);
        chart.setDrawEntryLabels(false);
        pieData.setDrawValues(false);
        pieData.setValueTextSize(20f); //取消數據上的文字


        chart.getLegend().setEnabled(false);//禁用圖標圖例（默認是有圖表圖例的）
        chart.setEntryLabelColor(Color.WHITE); //設置條目標籤的顏色
        chart.setEntryLabelTextSize(8f); //設置條目標籤的字體大小
        chart.setCenterText(percent+"%"); //設置繪製在餅圖中心的文字內容

        chart.setCenterTextColor(Color.rgb(0, 0, 0)); //設置繪製在餅圖中心的文字顏色
        chart.setCenterTextSize(13f); //設置繪製在餅圖中心的文字大小
        chart.invalidate();
    }
}
