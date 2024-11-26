package com.pinu.domain.entities.events

import com.pinu.domain.entities.network_service.request.ProfileRequest

sealed interface ProfileEvents {

    data object OnNavigateBack : ProfileEvents
    data object FetchProfileData : ProfileEvents
    data class AddUpdateProfileData(val profileData: ProfileRequest) : ProfileEvents
    data class UpdateProfilePic(val profilePic: String) : ProfileEvents
}