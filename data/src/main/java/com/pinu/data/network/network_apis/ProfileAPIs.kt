package com.pinu.data.network.network_apis

import com.pinu.domain.entities.network_service.request.ProfileRequest
import com.pinu.domain.entities.network_service.response.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ProfileAPIs {

    // Fetch Profile Data
    @GET("/fetch-profile-data")
    suspend fun fetchProfile(): ProfileResponse

    // Add or Update Profile Data
    @POST("/add-update-profile-data")
    suspend fun addUpdateProfile(@Body profileRequest: ProfileRequest): ProfileResponse

    // Update Profile Picture
    @Multipart
    @POST("/update-profile-pic")
    suspend fun updateProfilePicture(@Part profilePic: MultipartBody.Part): ProfileResponse

}