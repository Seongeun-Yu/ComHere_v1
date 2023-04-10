package com.example.splashactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.R
import android.graphics.Color
import android.view.View
import com.github.mikephil.charting.components.Legend
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


class PieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.splashactivity.R.layout.activity_pie)

        val mPieChart: PieChart = findViewById(com.example.splashactivity.R.id.pieChart)

        mPieChart.addPieSlice(PieModel("사용 중", 15f, Color.parseColor("#064A9A")))
        mPieChart.addPieSlice(PieModel("사용 가능", 25f, Color.parseColor("#D0DDEC")))

        mPieChart.apply{
            isUsePieRotation = false
            animationTime = 1000
            startAnimation()
        }


    }
}