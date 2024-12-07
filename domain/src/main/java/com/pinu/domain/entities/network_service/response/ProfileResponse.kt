package com.pinu.domain.entities.network_service.response

import android.content.Context
import androidx.compose.ui.text.toLowerCase
import com.google.gson.annotations.SerializedName
import com.pinu.domain.R

data class ProfileResponse(
    val success: Boolean = false,
    val message: String = "",
    val status: Int? = null,
    val data: ProfileData? = null){

    class ProfileData(
        val id: String,
        val name: String,
        val email: String,
        @SerializedName("mobile_number")val mobileNumber: String,
        val gender: String ,
        @SerializedName("profile_pic_url")val profilePicUrl: String
    ) {
        fun getGender(context: Context): String {
            return when (gender.lowercase()) {
                GenderType.MALE.type -> context.getString(R.string.male)
                GenderType.FEMALE.type -> context.getString(R.string.female)
                GenderType.OTHER.type -> context.getString(R.string.other)
                else -> ""
            }
        }
    }
}


enum class GenderType(val type: String) {
    MALE("male"), FEMALE("female"), OTHER("other")
}
