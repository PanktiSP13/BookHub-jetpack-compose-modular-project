package com.pinu.domain.entities.network_service.response

import android.content.Context
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
        val mobileNumber: String,
        val gender: GenderType = GenderType.NONE,
        val profilePicUrl: String
    ) {
        fun getGender(context: Context): String {
            return when (gender) {
                GenderType.MALE -> context.getString(R.string.male)
                GenderType.FEMALE -> context.getString(R.string.female)
                GenderType.OTHER -> context.getString(R.string.other)
                GenderType.NONE -> ""
                else -> ""
            }
        }
    }


}


enum class GenderType {
    MALE, FEMALE, OTHER, NONE
}
