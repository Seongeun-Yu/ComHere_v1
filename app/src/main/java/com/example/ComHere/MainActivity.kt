package com.example.ComHere

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class MainActivity : AppCompatActivity() {
    lateinit var colorsItems_red: ArrayList<Int>
    lateinit var colorsItems_yellow: ArrayList<Int>
    lateinit var colorsItems_blue: ArrayList<Int>

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataRefresh()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        dataRefresh()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun dataRefresh(){

        var aFrontSeat = 4f // A동 정문 잔여석 수
        var bFrontSeat = 9f // B동 정문 잔여석 수
        var bBackSeat = 15f // B동 후문 잔여석 수

        var aFrontFull = 20f // A동 정문 전체 좌석 수
        var bFrontFull = 20f // B동 정문 전체 좌석 수
        var bBackFull = 24f // B동 후문 전체 좌석 수

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

        var aFrontText:TextView = findViewById(R.id.aFrontText)
        var bFrontText:TextView = findViewById(R.id.bFrontText)
        var bBackText:TextView = findViewById(R.id.bBackText)

        // 파이 차트 항목들의 색상
        colorsItems_red = ArrayList<Int>()
        colorsItems_red.add(resources.getColor(R.color.dark_red, null))
        colorsItems_red.add(resources.getColor(R.color.inu_lightblue, null))

        colorsItems_yellow = ArrayList<Int>()
        colorsItems_yellow.add(resources.getColor(R.color.inu_yellow, null))
        colorsItems_yellow.add(resources.getColor(R.color.inu_lightblue, null))

        colorsItems_blue = ArrayList<Int>()
        colorsItems_blue.add(resources.getColor(R.color.inu_blue, null))
        colorsItems_blue.add(resources.getColor(R.color.inu_lightblue, null))

        // 파이 차트 그리기
        drawPieGraph(aFront, aFrontSeat, aFrontFull, aFrontText)
        drawPieGraph(bFront, bFrontSeat, bFrontFull, bFrontText)
        drawPieGraph(bBack, bBackSeat, bBackFull, bBackText)

        // 범례 지우기
        val legend1: Legend = aFront.legend
        legend1.setEnabled(false)
        val legend2: Legend = bFront.legend
        legend2.setEnabled(false)
        val legend3: Legend = bBack.legend
        legend3.setEnabled(false)

        // 새로고침
        var swipe:SwipeRefreshLayout = findViewById(R.id.swipe)
        swipe.setOnRefreshListener{
            swipe.isRefreshing = false
            val random1 = (0..20).random()
            val random2 = (0..20).random()
            val random3 = (0..24).random()
            aFrontSeat = random1.toFloat()    // A동 정문 잔여석 업데이트
            bFrontSeat = random2.toFloat()     // B동 정문 잔여석 업데이트
            bBackSeat = random3.toFloat()     // B동 후문 잔여석 업데이트
            drawPieGraph(aFront, aFrontSeat, aFrontFull, aFrontText)
            drawPieGraph(bFront, bFrontSeat, bFrontFull, bFrontText)
            drawPieGraph(bBack, bBackSeat, bBackFull, bBackText)
        }
    }
    private fun drawPieGraph(pieChart:PieChart, seat:Float, full:Float, pieText:TextView){
        // 파이 차트 데이터(사용 중인 좌석 수, 잔여석)
        val entries = listOf(PieEntry(full-seat), PieEntry(seat))

        // PieDataSet 객체 생성
        val pieDataSet = PieDataSet(entries, "")
        applytoDataset(pieDataSet, seat, full)

        // PieData 객체를 PieDataSet으로 생성
        val pieData = PieData(pieDataSet)
        applytoPieData(pieChart, pieData, seat.toInt().toString())

        val str = (full-seat).toInt().toString() + "/" + full.toInt().toString()
        pieText.setText(str)
    }
    private fun applytoDataset(dataset: PieDataSet, seat:Float, full:Float) {
        dataset.apply {
            val congestion = (full-seat) / full // 사용 중인 좌석 비율
            if(congestion >= 0.8) colors = colorsItems_red
            else if(congestion >= 0.5) colors = colorsItems_yellow
            else colors = colorsItems_blue
            valueTextColor = Color.TRANSPARENT // 항목 세부값 안 보이게 하기
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