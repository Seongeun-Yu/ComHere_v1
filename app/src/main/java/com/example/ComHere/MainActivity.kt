package com.example.ComHere

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private val BASE_URL = "http://117.16.244.20:31415/"

    lateinit var colorsItems_red: ArrayList<Int>
    lateinit var colorsItems_yellow: ArrayList<Int>
    lateinit var colorsItems_blue: ArrayList<Int>
    private var resultList = mutableListOf<ObjectDetectionResult>()

    // 파이 차트
    lateinit var aFront: PieChart
    lateinit var bFront: PieChart
    lateinit var bBack: PieChart

    // 파이 차트 텍스트(사용중 좌석 수 / 전체 좌석 수)
    lateinit var aFrontText:TextView
    lateinit var bFrontText:TextView
    lateinit var bBackText:TextView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawBasic()
        getObjectDetectionResult()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
        drawBasic()
        getObjectDetectionResult()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun drawBasic(){
        // 알림 설정 버튼 & 클릭 이벤트
        val alarmBtn: Button = findViewById(R.id.alarm_btn)
        alarmBtn.setOnClickListener {
            val alarmIntent: Intent = Intent(this, AlarmActivity::class.java)
            startActivity(alarmIntent)
        }

        // 학습 공간 정보 버튼 & 클릭 이벤트
        val infoBtn: Button = findViewById(R.id.info_btn)
        infoBtn.setOnClickListener {
            val infoIntent: Intent = Intent(this, InfoActivity::class.java)
            startActivity(infoIntent)
        }

        // 파이 차트
        aFront = findViewById(R.id.a_front)
        bFront = findViewById(R.id.b_front)
        bBack = findViewById(R.id.b_back)

        // 파이 차트 텍스트(사용중 좌석 수 / 전체 좌석 수)
        aFrontText = findViewById(R.id.aFrontText)
        bFrontText = findViewById(R.id.bFrontText)
        bBackText = findViewById(R.id.bBackText)

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

        // 범례 지우기
        val legend1: Legend = aFront.legend
        legend1.setEnabled(false)
        val legend2: Legend = bFront.legend
        legend2.setEnabled(false)
        val legend3: Legend = bBack.legend
        legend3.setEnabled(false)

        // 새로고침
        val swipe:SwipeRefreshLayout = findViewById(R.id.swipe)
        swipe.setOnRefreshListener{
            swipe.isRefreshing = false
            getObjectDetectionResult()
        }
    }
    private fun getObjectDetectionResult() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ObjectDetectionApi::class.java)

        api.getAFrontResults().enqueue(object : Callback<List<ObjectDetectionResult>> {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onResponse(call: Call<List<ObjectDetectionResult>>, response: Response<List<ObjectDetectionResult>>) {
                if(response.isSuccessful){
                    val data = response.body()
                    processData(aFront, data!!.toMutableList(), 20f, aFrontText) // callback 메소드 호출
                    Log.d("retrofit", (resultList).toString())
                    Log.d("retrofit", (resultList.size).toString())
                }
                else{
                    val errorBody = response.errorBody().toString()
                    Log.d("error", errorBody)
                }
            }
            override fun onFailure(call: Call<List<ObjectDetectionResult>>, t: Throwable) {
                Log.d("retrofit_error", t.toString())
            }
        })


        api.getBFrontResults().enqueue(object : Callback<List<ObjectDetectionResult>> {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onResponse(call: Call<List<ObjectDetectionResult>>, response: Response<List<ObjectDetectionResult>>) {
                if(response.isSuccessful){
                    val data = response.body()
                    processData(bFront, data!!.toMutableList(), 20f, bFrontText) // callback 메소드 호출
                    Log.d("retrofit", (resultList).toString())
                    Log.d("retrofit", (resultList.size).toString())
                }
                else{
                    val errorBody = response.errorBody().toString()
                    Log.d("error", errorBody)
                }
            }
            override fun onFailure(call: Call<List<ObjectDetectionResult>>, t: Throwable) {
                Log.d("retrofit_error", t.toString())
            }
        })

        api.getBBackResults().enqueue(object : Callback<List<ObjectDetectionResult>> {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onResponse(call: Call<List<ObjectDetectionResult>>, response: Response<List<ObjectDetectionResult>>) {
                if(response.isSuccessful){
                    val data = response.body()
                    processData(bBack, data!!.toMutableList(), 24f, bBackText) // callback 메소드 호출
                    Log.d("retrofit", (resultList).toString())
                    Log.d("retrofit", (resultList.size).toString())
                }
                else{
                    val errorBody = response.errorBody().toString()
                    Log.d("error", errorBody)
                }
            }
            override fun onFailure(call: Call<List<ObjectDetectionResult>>, t: Throwable) {
                Log.d("retrofit_error", t.toString())
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun processData(pieChart:PieChart, data: MutableList<ObjectDetectionResult>?, full:Float, pieText:TextView){
        if(data != null){
            var listLen = (data.size).toFloat() // 배열 길이
            Log.d("output", data.toString()) // 초기 배열 확인
            Log.d("output", listLen.toString()) //  초기 배열 길이 확인
            if(listLen != 0f)  listLen = updateSeatNum(listLen, data) // 배열 길이 업데이트
            drawPieGraph(pieChart, listLen, full, pieText)
        }
    }

    // 정확도 낮은 것 제외하여 잔여석 수 업데이트
    private fun updateSeatNum(seat:Float, resultList:MutableList<ObjectDetectionResult>) : Float{
        var original = seat
        for(i in 0 until seat.toInt()) {
            if (resultList[i].confidence <= 0.25) original--
        }
        return original
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