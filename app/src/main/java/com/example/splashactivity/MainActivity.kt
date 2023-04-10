package com.example.splashactivity

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class MainActivity : AppCompatActivity() {
    lateinit var swipe: SwipeRefreshLayout
    lateinit var colorsItems: ArrayList<Int>
    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 알림 설정 버튼 & 클릭 이벤트
        var alarmBtn: Button = findViewById(R.id.alarm_btn)
        alarmBtn.setOnClickListener {
            var alarmIntent: Intent = Intent(this, AlarmActivity::class.java)
            startActivity(alarmIntent)
        }

        // 학습 공간 정보 버튼 & 클릭 이벤트
        var infoBtn: Button = findViewById(R.id.info_btn)
        infoBtn.setOnClickListener {
            var infoIntent: Intent = Intent(this, InfoActivity::class.java)
            startActivity(infoIntent)
        }


        // 파이 차트
        var aFront: PieChart = findViewById(R.id.a_front)
        var bFront: PieChart = findViewById(R.id.b_front)
        var bBack: PieChart = findViewById(R.id.b_back)

        // 파이 차트 데이터
        val entries1 = listOf(PieEntry(5f), PieEntry(15f))
        val entries2 = listOf(PieEntry(2f), PieEntry(18f))
        val entries3 = listOf(PieEntry(10f), PieEntry(12f))

        // 파이 차트 항목들의 색상
        colorsItems = ArrayList<Int>()
        colorsItems.add(resources.getColor(R.color.inu_blue, null))
        colorsItems.add(resources.getColor(R.color.inu_lightblue, null))

        // PieDataSet 객체 생성
        val pieDataSet1 = PieDataSet(entries1, "")
        val pieDataSet2 = PieDataSet(entries2, "")
        val pieDataSet3 = PieDataSet(entries3, "")
        applytoDataset(pieDataSet1)
        applytoDataset(pieDataSet2)
        applytoDataset(pieDataSet3)

        // PieData 객체를 PieDataSet으로 생성
        val pieData1 = PieData(pieDataSet1)
        val pieData2 = PieData(pieDataSet2)
        val pieData3 = PieData(pieDataSet3)
        applytoPieData(aFront, pieData1, "15")
        applytoPieData(bFront, pieData2, "18")
        applytoPieData(bBack, pieData3, "12")

        // 범례 지우기
        val legend1: Legend = aFront.legend
        legend1.setEnabled(false)
        val legend2: Legend = bFront.legend
        legend2.setEnabled(false)
        val legend3: Legend = bBack.legend
        legend3.setEnabled(false)

        // 새로고침
        swipe = findViewById(R.id.swipe)
        setListener()
    }
    // 새로고침 이벤트
    private fun setListener() {
        swipe.setOnRefreshListener {
            swipe.isRefreshing = false
        }
    }
    private fun applytoDataset(dataset: PieDataSet) {
        dataset.apply {
            colors = colorsItems
            valueTextColor = Color.TRANSPARENT // 항목 세부값 안보이게 하기
            valueTextSize = 16f
        }
    }
    private fun applytoPieData(piechart: PieChart, pieData: PieData, text:String) {
        piechart.apply {
            data = pieData
            description.isEnabled = false // 설명 지우기
            isRotationEnabled = false // 회전 불가능하게 함
            centerText = text
            setHoleRadius(70f)
            setCenterTextSize(30f)
            setEntryLabelColor(Color.BLACK)
            animateY(1400, Easing.EasingOption.EaseInOutQuad)
            animate()
        }
    }
}