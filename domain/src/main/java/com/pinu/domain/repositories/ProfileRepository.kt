package com.pinu.domain.repositories

import com.pinu.domain.entities.network_service.request.ProfileRequest
import com.pinu.domain.entities.network_service.response.ProfileResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

   suspend fun fetchProfileData() : Flow<Result<ProfileResponse>>

   suspend fun addUpdateProfileData(profileRequest: ProfileRequest) : Flow<Result<ProfileResponse>>

   suspend fun updateProfilePic(img: String) : Flow<Result<ProfileResponse>>
}