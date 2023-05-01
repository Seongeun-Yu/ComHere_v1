package com.example.ComHere

import retrofit2.http.GET
import retrofit2.Call

public interface ObjectDetectionApi {
    @GET("v1/object-detection/A")
    fun getAFrontResults(): Call<List<ObjectDetectionResult>>

    @GET("v1/object-detection/B")
    fun getBFrontResults(): Call<List<ObjectDetectionResult>>

    @GET("v1/object-detection/C")
    fun getBBackResults(): Call<List<ObjectDetectionResult>>

}