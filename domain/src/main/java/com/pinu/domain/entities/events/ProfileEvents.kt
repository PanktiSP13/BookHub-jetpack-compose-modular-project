package com.pinu.domain.entities.events

import com.pinu.domain.entities.network_service.request.ProfileRequest
import java.io.File

sealed interface ProfileEvents {
    data object NavigateToProfileScreen : ProfileEvents
    data object OnNavigateBack : ProfileEvents
    data object FetchProfileData : ProfileEvents
    data class AddUpdateProfileData(val profileData: ProfileRequest) : ProfileEvents
    data class UpdateProfilePic(val profilePic: File) : ProfileEvents
}