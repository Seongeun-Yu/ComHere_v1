package com.example.ComHere

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat

class AlarmActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        val aFront_alarm: Switch = findViewById(R.id.aFront)
        val bFront_alarm: Switch = findViewById(R.id.bFront)
        val bBack_alarm: Switch = findViewById(R.id.bBack)
    }
}