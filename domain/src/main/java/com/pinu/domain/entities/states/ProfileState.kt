package com.pinu.domain.entities.states

import com.pinu.domain.entities.ToastMessage
import com.pinu.domain.entities.network_service.response.ProfileResponse

data class ProfileState(
    val isLoading:Boolean= false,
    val userProfileData: ProfileResponse.ProfileData? = null,
    val updatedProfilePath: String = "",
    val selectedGender: String = "",
    val profileUpdateSuccess : Boolean = false,
    val toastMessage: ToastMessage = ToastMessage()
)