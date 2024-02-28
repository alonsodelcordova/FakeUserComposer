package com.jacs.cordova.fakeusercomposer.datasource

import com.jacs.cordova.fakeusercomposer.model.ApiResponse
import retrofit2.http.GET

interface RestDataSource {

    @GET("?inc=name")
    suspend fun getUserNames(): ApiResponse

    @GET("?inc=location")
    suspend fun getUserLocation() : ApiResponse

    @GET("?inc=picture")
    suspend fun getUserPicture() : ApiResponse

    // https://randomuser.me/api/?inc=name,location,picture

}