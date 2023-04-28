package com.example.ComHere

import com.google.gson.annotations.SerializedName

data class ObjectDetectionResult(
    val xmin: Double,
    val ymin: Double,
    val xmax: Double,
    val ymax: Double,
    val confidence: Double,
    @SerializedName("class")val className: Int,
    val name: String
)