package com.pinu.domain.entities.events

import com.pinu.domain.entities.network_service.request.ProfileRequest

sealed class ProfileEvents {

    data object OnNavigateBack : ProfileEvents()
    data class AddUpdateProfileData(val profileData: ProfileRequest) : ProfileEvents()
    data class UpdateProfilePic(val profilePic: String) : ProfileEvents()
}