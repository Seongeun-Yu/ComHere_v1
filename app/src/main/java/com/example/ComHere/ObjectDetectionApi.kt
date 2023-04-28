package com.example.ComHere

import retrofit2.http.GET
import retrofit2.Call

public interface ObjectDetectionApi {
    @GET("v1/object-detection/recent")
    fun getRecentResults(): Call<List<ObjectDetectionResult>>

    /*
    @GET("v1/object-detection/recent")
    fun getRecentResults(): Call<List<ObjectDetectionResult>>

    @GET("v1/object-detection/recent")
    fun getRecentResults(): Call<List<ObjectDetectionResult>>

     */
}