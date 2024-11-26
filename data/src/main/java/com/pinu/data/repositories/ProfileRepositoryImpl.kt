package com.pinu.data.repositories

import com.pinu.data.network.Network
import com.pinu.data.network.network_apis.ProfileAPIs
import com.pinu.domain.entities.network_service.request.ProfileRequest
import com.pinu.domain.entities.network_service.response.ProfileResponse
import com.pinu.domain.repositories.ProfileRepository
import com.pinu.domain.utils.NetworkUtils
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileAPIs: ProfileAPIs,
    private val network: Network
) :
    ProfileRepository {

    override suspend fun fetchProfileData(): Flow<Result<ProfileResponse>> {
        return NetworkUtils.safeApiCall { profileAPIs.fetchProfile() }
    }

    override suspend fun addUpdateProfileData(profileRequest: ProfileRequest): Flow<Result<ProfileResponse>> {
        return NetworkUtils.safeApiCall { profileAPIs.addUpdateProfile(profileRequest) }
    }

    override suspend fun updateProfilePic(img: String): Flow<Result<ProfileResponse>> {
        return NetworkUtils.safeApiCall {
            profileAPIs.updateProfilePicture(
                network.prepareFilePart(
                    file = File(img),
                    mimeType = "image/*",
                    partName = "profilePic"
                )
            )
        }
    }
}