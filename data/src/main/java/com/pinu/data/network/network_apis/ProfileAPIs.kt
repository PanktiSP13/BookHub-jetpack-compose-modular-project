package com.pinu.data.network.network_apis

import com.pinu.domain.entities.network_service.response.ProfileResponse
import okhttp3.MultipartBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part

interface ProfileAPIs {

    @GET("api/fetch-profile-data")
    suspend fun fetchProfile(): ProfileResponse

    @FormUrlEncoded
    @PUT("api/add-update-profile-data")
    suspend fun addUpdateProfile(
        @Field("name") name: String, @Field("email") email: String,
        @Field("mobile_number") mobileNumber: String
    ): ProfileResponse

    @Multipart
    @POST("api/update-profile-pic")
    suspend fun updateProfilePicture(@Part profilePic: MultipartBody.Part): ProfileResponse

}