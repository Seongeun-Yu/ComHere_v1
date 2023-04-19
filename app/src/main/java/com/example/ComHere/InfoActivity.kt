package com.example.ComHere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog


class InfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        // 각 학습공간 클릭 시 세부 정보 다이얼로그 띄우는 이벤트 설정
        val aFront: Button = findViewById(R.id.aFrontBtn)
        val aBack: Button = findViewById(R.id.bBackBtn)
        val bFront: Button = findViewById(R.id.bFrontBtn)
        aFront.setOnClickListener {
            cuDialog(aFront, R.layout.dialog, R.drawable.a_front)
        }
        bFront.setOnClickListener {
            cuDialog(bFront, R.layout.dialog, R.drawable.b_front)
        }
        aBack.setOnClickListener {
            cuDialog(aBack, R.layout.dialog, R.drawable.b_back)
        }
        
        // 깜빡이는 애니메이션 적용
        startBlinkingAnimation(aFront)
        startBlinkingAnimation(bFront)
        startBlinkingAnimation(aBack)
    }
    
    // 깜빡이는 애니메이션
    private fun startBlinkingAnimation(button: Button) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.blink_animation)
        button.startAnimation(anim)
    }
    
    // 다이얼로그 띄우기 : 학습 공간명, dialog 레이아웃, 학습 공간 이미지를 파라미터로 받음 
    private fun cuDialog(view: View, layout: Int, imageRes: Int) {
        val myLayout = layoutInflater.inflate(layout, null)
        val build = AlertDialog.Builder(view.context).apply {
            setView(myLayout)
        }
        val dialog = build.create()
        dialog.show()

        val location: TextView = myLayout.findViewById(R.id.location)
        val shutdownBtn:Button = myLayout.findViewById(R.id.shutdownBtn)
        val image: ImageView = myLayout.findViewById(R.id.image)
        val description:TextView = myLayout.findViewById(R.id.description)
        image.setImageResource(imageRes)
        if (imageRes == R.drawable.a_front) { // A동 정문에 대한 설명
            location.setText("A동 정문")
            description.setText("전체 좌석 수 : 20석\n주변 : 남자 화장실, 탁구장\n")

        } else if (imageRes == R.drawable.b_front) { // B동 정문에 대한 설명
            location.setText("B동 정문")
            description.setText("전체 좌석 수 : 20석\n주변 : 여자 화장실, 여학생 휴게실, 취창업 라운지\n")
        } else {
            location.setText("B동 후문") // B동 후문에 대한 설명
            description.setText("전체 좌석 수 : 24석\n주변 : 여자 화장실, 여학생 휴게실, 취창업 라운지\n")
        }
        // 다이얼로그의 닫기 버튼을 누르면 다이얼로그 종료
        shutdownBtn.setOnClickListener{
            dialog.dismiss()
        }
    }
}