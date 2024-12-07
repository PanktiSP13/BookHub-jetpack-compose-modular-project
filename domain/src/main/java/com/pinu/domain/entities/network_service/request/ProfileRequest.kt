package com.pinu.domain.entities.network_service.request

import com.google.gson.annotations.SerializedName

class ProfileRequest(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("mobile_number") val mobileNumber: String)