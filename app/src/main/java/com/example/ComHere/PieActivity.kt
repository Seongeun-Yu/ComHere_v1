package com.example.ComHere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.graphics.Color
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel


class PieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.ComHere.R.layout.activity_pie)

        val mPieChart: PieChart = findViewById(com.example.ComHere.R.id.pieChart)

        mPieChart.addPieSlice(PieModel("사용 중", 15f, Color.parseColor("#064A9A")))
        mPieChart.addPieSlice(PieModel("사용 가능", 25f, Color.parseColor("#D0DDEC")))

        mPieChart.apply{
            isUsePieRotation = false
            animationTime = 1000
            startAnimation()
        }


    }
}