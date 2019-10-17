package com.example.qrfoodproject.FoodDairy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.qrfoodproject.R;
import com.github.mikephil.charting.charts.PieChart;

public class FoodDairy_intake_nutrition extends AppCompatActivity {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.fooddairy_intake_nutrition);

        PieChart pieChart = (PieChart)findViewById(R.id.chart1);
        PieChart pieChart2 = (PieChart)findViewById(R.id.chart2);
        PieChart pieChart3 = (PieChart)findViewById(R.id.chart3);
        chartModel model = new chartModel(pieChart,900f,4500f,20);
        chartModel mode2 = new chartModel(pieChart2,300f,4000f,7.5f);
        chartModel mode3 = new chartModel(pieChart3,200f,1500f,13.3f);
        model.gg();
        mode2.gg();
        mode3.gg();
    }
}
